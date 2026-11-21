Feature: Compra de productos en la tienda en línea
  Como cliente nuevo de la tienda en línea
  quiero pagar los productos de mi carrito con el medio de pago de mi preferencia
  para recibir mi pedido en la dirección que registré

  Background:
    Given que Ana ingresa a la tienda en línea
    And se registra con datos personales válidos

  @happy_path @e2e
  Scenario Outline: Compra completa de varios productos con diferentes cantidades
    Given que Ana tiene en el carrito los siguientes productos
      | categoria | producto                             | cantidad |
      | Laptops   | HP Pavilion 15z Touch Laptop         | 2        |
      | Speakers  | Bose Soundlink Bluetooth Speaker III | 3        |
    When paga la orden con "<medio de pago>"
    Then la compra queda confirmada con un número de orden
    And la orden queda pagada con "<medio de pago>"
    And el subtotal de la orden corresponde a los productos del carrito
    And el total pagado es el subtotal más el costo de envío

    @smoke
    Examples: Pago con billetera electrónica
      | medio de pago |
      | SafePay       |

    Examples: Pago con tarjeta de crédito
      | medio de pago |
      | MasterCredit  |

  @exception
  Scenario Outline: Pago no permitido cuando faltan datos del medio de pago
    Given que Ana tiene <cantidad> unidades de "<producto>" de la categoría "<categoria>" en el carrito
    When intenta pagar con "<medio de pago>" sin diligenciar "<dato>"
    Then no puede confirmar el pago de la orden

    Examples:
      | categoria  | producto                     | cantidad | medio de pago | dato                |
      | Speakers   | HP Roar Wireless Speaker     | 1        | SafePay       | usuario             |
      | Tablets    | HP Pro Tablet 608 G1         | 2        | SafePay       | contraseña          |
      | Mice       | HP Z4000 Wireless Mouse      | 3        | MasterCredit  | número de tarjeta   |
      | Headphones | Logitech USB Headset H390    | 1        | MasterCredit  | código de seguridad |
      | Laptops    | HP Pavilion 15z Touch Laptop | 1        | MasterCredit  | nombre del titular  |
