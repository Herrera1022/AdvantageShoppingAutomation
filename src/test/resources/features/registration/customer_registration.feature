Feature: Registro de clientes en la tienda en línea
  Como cliente nuevo de la tienda en línea
  quiero crear mi cuenta con mis datos personales
  para poder comprar productos y hacer seguimiento a mis pedidos

  Background:
    Given que Ana ingresa a la tienda en línea

  @happy_path @smoke
  Scenario: Registro exitoso de un cliente nuevo
    When se registra con datos personales válidos
    Then su cuenta queda creada y ve su nombre de usuario en la tienda

  @exception
  Scenario Outline: Registro rechazado por un dato que no cumple las reglas
    When intenta registrarse con "<campo>" diligenciado como "<valor>"
    Then ve el mensaje de validación "<mensaje>"
    And no puede crear la cuenta

    Examples:
      | campo                      | valor      | mensaje                                      |
      | correo electrónico         | ana.correo | Your email address isn't formatted correctly |
      | contraseña                 | Ab1        | Use 4 character or longer                    |
      | contraseña                 | udea2026   | One upper letter required                    |
      | confirmación de contraseña | Otra2026   | Passwords do not match                       |

  # Valores límite: el nombre de usuario debe tener entre 5 y 15 caracteres.
  @exception @boundary
  Scenario Outline: Registro con un nombre de usuario fuera de la longitud permitida
    When intenta registrarse con un nombre de usuario nuevo de <longitud> caracteres
    Then ve el mensaje de validación "<mensaje>"
    And no puede crear la cuenta

    Examples: Por encima del máximo
      | longitud | mensaje                  |
      | 16       | Use maximum 15 character |

    # Defecto encontrado: la tienda muestra el mensaje de validación, pero aun así
    # acepta el nombre de usuario de 4 caracteres, crea la cuenta e inicia sesión.
    @known_defect
    Examples: Por debajo del mínimo
      | longitud | mensaje                   |
      | 4        | Use 5 character or longer |

  @exception
  Scenario: Registro rechazado por un nombre de usuario que ya existe
    Given que Luis ya tiene una cuenta registrada
    When Ana intenta registrarse con el mismo nombre de usuario
    Then ve el mensaje de error "User name already exists"
