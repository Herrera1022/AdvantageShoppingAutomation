# Automatización Advantage Online Shopping — Screenplay + Serenity BDD

Taller de automatización — Calidad de Software 2026-2, Universidad de Antioquia.
Sitio automatizado: <https://advantageonlineshopping.com/> (Mega tienda online, ítem 3 del taller).

Pruebas E2E con el patrón **Screenplay**: un cliente se registra, inicia sesión, gestiona el carrito
(varios productos con distintas cantidades) y paga la orden con **SafePay** o **MasterCredit**,
validando la confirmación y los montos. El proyecto se generó con el **scaffold**
[ScreenplayArchitecture](https://github.com/jumosqu12/ScreenplayArchitecture) (plugin `io.github.jumosqu12.screenplayarchitecture` 2.0.1).

## Requisitos

| Herramienta | Versión |
|---|---|
| JDK | 17 o superior (probado con 21) |
| Gradle | 8.6 o superior (probado con 8.10.2), o el wrapper `gradlew` incluido |
| Google Chrome | Cualquier versión reciente (el driver se descarga solo) |

## Cómo ejecutar

En Windows se puede usar `gradlew.bat` en lugar de `gradle` (no requiere instalar Gradle).

```bash
# Todo + reporte Serenity (comando indicado por el profesor)
gradle clean test aggregate --info

# Un feature
gradle clean test --tests "*ProductPurchaseRunner" aggregate

# Por etiquetas
gradle clean test -Dcucumber.filter.tags="@smoke" aggregate
gradle clean test -Dcucumber.filter.tags="not @known_defect" aggregate

# Delay entre pasos para la demo en vivo (segundos; 0 lo desactiva). Por defecto: 1
gradle clean test --tests "*ProductPurchaseRunner" -Ddemo.delay.seconds=3 -Dcucumber.filter.tags="@smoke" aggregate

# Sin ventana (rápido, para verificar)
gradle clean test -Dheadless.mode=true aggregate
```

**Reporte:** `target/site/serenity/index.html`.

### Runners

| Runner | Feature |
|---|---|
| `CustomerRegistrationRunner` | `features/registration/customer_registration.feature` |
| `CustomerLoginRunner` | `features/login/customer_login.feature` |
| `ShoppingCartRunner` | `features/cart/shopping_cart.feature` |
| `ProductPurchaseRunner` | `features/purchase/product_purchase.feature` |

### Etiquetas

| Etiqueta | Significado |
|---|---|
| `@happy_path` | Ruta feliz |
| `@alternate` | Caminos alternos (modificar cantidad, retirar productos, vaciar el carrito) |
| `@exception` | Caminos excepcionales (datos inválidos, producto agotado, pago incompleto) |
| `@boundary` | Valores límite (longitud del nombre de usuario: 4 y 16 caracteres) |
| `@e2e` | Flujo completo de compra |
| `@smoke` | Selección corta para la demo |
| `@known_defect` | Escenario que falla por un defecto real de la tienda (ver abajo) |

## Arquitectura (Screenplay)

```
src/main/java/co/edu/udea/qa/advantageshopping
├── tasks            Tareas en lenguaje de negocio (QUÉ hace el cliente)
├── interactions     Interacciones de bajo nivel, "la menuda" (CÓMO lo hace en la página)
├── questions        Preguntas que verifican el resultado esperado
├── userinterfaces   Page Objects: Targets (localizadores) por página
├── models           Datos del negocio (Customer, Product, PaymentDetails...)
├── exceptions       Errores de negocio usados en orComplainWith
└── utils            Datos de prueba, conversión de precios, configuración del delay
src/test/java/.../stepdefinitions   Traducen Gherkin a tareas y preguntas del actor
src/test/java/.../runners           Un runner por feature (CucumberWithSerenity)
src/test/resources/features         Features en Gherkin
```

**Flujo:** Feature (Gherkin) → StepDefinition → el **actor** (Ana) `attemptsTo(Task)` → la Task se compone
de otras Tasks e Interactions → las Interactions usan los Targets de `userinterfaces` → el actor `should(seeThat(Question))`.

### Tasks (negocio)

| Task | Qué hace | Se compone de |
|---|---|---|
| `OpenTheStore` | Abre el catálogo | `Open`, `WaitForLoader` |
| `FillRegistrationForm` | Abre el formulario de cuenta y lo diligencia | `ClickOn`, `Enter`, `SelectFromOptions` |
| `ConfirmRegistration` | Envía el formulario y espera la respuesta | `ClickOn`, `WaitForRegistrationResponse` |
| `RegisterCustomer` | Crea la cuenta completa | `FillRegistrationForm`, `Pause`, `ConfirmRegistration` |
| `LogIn` / `SignOut` | Inicia y cierra sesión | `ClickOn`, `Enter`, `WaitUntilHidden` |
| `GoToCatalog` | Vuelve al catálogo con el logo, sin recargar la página | `ClickOn`, `WaitForLoader` |
| `ViewProduct` | Busca el producto en su categoría | `GoToCatalog`, `ClickOn` |
| `AddProductToCart` | Agrega N unidades y anota el precio unitario | `ViewProduct`, `ReplaceQuantity`, `ClickOn` |
| `AddProductsToCart` | Agrega una lista de productos | `AddProductToCart` |
| `GoToShoppingCart` | Abre el carrito | `ClickOn` |
| `UpdateProductQuantity` | Cambia la cantidad de un producto del carrito | `GoToShoppingCart`, `ReplaceQuantity`, `ClickOn` |
| `RemoveProductFromCart` | Retira un producto del carrito | `GoToShoppingCart`, `ClickOn`, `WaitUntilHidden` |
| `ProceedToCheckout` | Pasa al pago y confirma los datos de envío | `GoToShoppingCart`, `ClickOn` |
| `EnterPaymentDetails` | Elige el medio de pago y diligencia sus datos | `ClickOn`, `Enter`, `SelectFromOptions`, `EnterAndConfirm` |
| `PayOrder` | Paga y espera la confirmación | `EnterPaymentDetails`, `ClickOn` |

### Interactions ("la menuda")

| Interaction | Por qué existe |
|---|---|
| `ClickOn` | Espera a que termine la carga y a que el elemento sea clickeable. Si una animación lo tapa, despacha un evento de clic con JavaScript (sirve también para íconos SVG) |
| `WaitForLoader` | La tienda muestra un overlay de carga mientras consulta sus servicios |
| `WaitUntilHidden` | Espera a que un elemento desaparezca leyendo su estado actual (ver hallazgo 2) |
| `WaitForRegistrationResponse` | Tras enviar el registro espera el error o la salida del formulario (evita carreras) |
| `ReplaceQuantity` | El campo de cantidad solo acepta dígitos e ignora `clear()`: selecciona todo y escribe |
| `EnterAndConfirm` | Escribe un valor, lo lee de vuelta y lo reescribe si la tienda no lo guardó completo (CVV y titular de MasterCredit) |
| `Pause` | Delay de la demo (`demo.delay.seconds`). Aparece en el reporte como "Ana pause"; con 0 no espera nada |

### Questions

`LoggedInCustomer`, `FieldValidationMessage`, `RegistrationAvailability`, `AccountCreation`, `RegistrationErrorMessage`,
`LoginErrorMessage`, `CartItems`, `CartProducts`, `ProductQuantityInCart`, `ShoppingCartTotal`, `ExpectedCartTotal`,
`EmptyCartMessage`, `ProductAvailability`, `PaymentAvailability`, `PurchaseConfirmation`, `OrderDetail`, `OrderAmount`.

**Validación de montos:** al agregar cada producto, el actor anota en su memoria (`CartNotebook`) el precio unitario
que muestra el catálogo. Luego se verifica que:
- total del carrito = Σ (precio unitario × cantidad);
- subtotal de la orden = ese mismo valor;
- total pagado = subtotal + envío.

## Delay para la demo (rúbrica: socialización)

`demo.delay.seconds` (en `serenity.conf`, valor por defecto 1) se aplica en dos niveles:
1. **Entre cada paso de Gherkin** (hook `@AfterStep` en `Hooks`).
2. **Dentro de las tareas**, antes de las acciones clave (formulario lleno, cantidad elegida, antes de pagar), con la interacción `Pause`.

Se cambia sin tocar código: `-Ddemo.delay.seconds=3`.

## Decisiones y ajustes sobre el scaffold

| Ajuste | Motivo |
|---|---|
| `glue` de los runners corregido | El scaffold genera `co.edu.udea.qa.stepdefinitions` sin el paquete principal: Cucumber no encontraba los pasos |
| Lombok 1.18.22 → 1.18.34 | 1.18.22 no compila con JDK 21 |
| `hamcrest-all` 1.3 → `hamcrest` 2.2 | Se necesita `matchesPattern`; además evita dos versiones de Hamcrest en el classpath |
| `serenity-gradle-plugin` 4.1.3 desde el Gradle Plugin Portal | La 3.3.4 del correo es para Serenity 3 y el proyecto usa Serenity 4.1.0. La 4.1.0 del plugin no está publicada; la más cercana es 4.1.3 |
| Clases vacías del scaffold eliminadas, paso `@Given` genérico y URL de Google removidos | Código sin relleno |
| `use.unique.browser=false`, `restart.browser.for.each=example` y `@After drawTheCurtain` | Un navegador limpio por escenario y por ejemplo: sin sesión ni carrito del escenario anterior |
| `--window-size=1920,1080` | En modo headless la tienda pasaba a diseño móvil |

## Hallazgos técnicos

1. **Serenity confundía CSS con XPath.** `Target.locatedBy("a.create-new-account")` o `locatedBy("input[name='username']")`
   también son XPath válidos, así que Serenity los buscaba como XPath y no encontraba nada (y `div.loader` "pasaba" sin esperar).
   Solución: los CSS se declaran con `located(By.cssSelector(...))`; `locatedBy` solo se usa para XPath (`//...`) e `#id`.
2. **`isNotVisible` y `isEnabled()` consumían el timeout completo (30 s).** Con elementos ocultos de esta tienda, la espera
   de Serenity siempre agotaba el tiempo. Solución: `WaitUntilHidden` y preguntas con `isCurrentlyVisible()` /
   `isCurrentlyEnabled()`. Resultado: el login pasó de 384 s a ~25 s.
3. **El mensaje de error del login está siempre visible** (muestra "OR" antes de la respuesta). Se espera el estado `.invalid`.
4. **Los radios de SafePay y MasterCredit son `input` ocultos** con estilo personalizado (`opacity:0`). Se hace clic en la imagen del medio de pago.
5. **Tarjeta MasterCredit:** el campo acepta 12 dígitos (`maxlength=14` con espacios), aunque la ayuda diga "16 numbers".
   Además, después de escribir la tarjeta la tienda a veces se "come" la primera tecla del siguiente campo (el CVV "123" quedaba "23"). La interacción `EnterAndConfirm` lee el campo y lo vuelve a escribir si no quedó completo.
6. **`StaleElementReferenceException`** cuando la tienda navega durante una espera: las esperas propias ignoran esa excepción y reintentan.
7. **Carrito incompleto de forma intermitente:** volver al catálogo abriendo la URL recargaba la página y a veces
   cancelaba el guardado del carrito del cliente logueado. `GoToCatalog` vuelve con el logo, sin recargar, como lo haría el cliente.
8. **El reporte no tenía página de detalle por escenario:** Serenity 4 solo escribe `json` y `xml` por defecto, así que al abrir
   un escenario el enlace no llevaba a ninguna página. Se agregó `output.formats = "json,xml,html"` en `serenity.conf`.
9. **Navegador compartido entre ejemplos de un Scenario Outline:** con `restart.browser.for.each=scenario` todos los
   ejemplos usaban el mismo Chrome (y la misma sesión). Se usa `example`.

## Defecto encontrado en la tienda

**Registro con un nombre de usuario de 4 caracteres.** La tienda muestra "Use 5 character or longer", pero el botón
REGISTER sigue habilitado: **crea la cuenta e inicia sesión**. Con 16 caracteres sí la rechaza (aunque con un mensaje
inadecuado: "Incorrect user name or password.").
El escenario `@boundary @known_defect` falla a propósito y documenta el defecto. Para una ejecución "en verde":
`-Dcucumber.filter.tags="not @known_defect"`.

## Datos de prueba

- Cada registro usa un nombre de usuario único (`CustomerFactory`), porque la tienda no permite repetirlos.
- Los productos se eligen por categoría y nombre, tal como lo haría el cliente.
- **Producto agotado:** *Bose SoundLink Around-ear Wireless Headphones II*.
- Pagos con datos de demostración (`PaymentDetailsFactory`); el sitio advierte que no se usen datos reales.
