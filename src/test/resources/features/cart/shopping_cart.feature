Feature: Gestión del carrito de compras
  Como cliente de la tienda en línea
  quiero agregar, modificar y retirar productos de mi carrito
  para comprar exactamente lo que necesito

  Background:
    Given que Ana ingresa a la tienda en línea

  @happy_path @smoke
  Scenario: Agregar varios productos de diferentes categorías con distintas cantidades
    When agrega al carrito los siguientes productos
      | categoria | producto                             | cantidad |
      | Laptops   | HP Pavilion 15z Touch Laptop         | 2        |
      | Speakers  | Bose Soundlink Bluetooth Speaker III | 3        |
      | Mice      | HP Z3200 Wireless Mouse              | 1        |
    Then el carrito tiene todos los artículos agregados
    And el total del carrito es la suma del precio por la cantidad de cada producto

  @happy_path
  Scenario Outline: Agregar un producto con diferentes cantidades
    When agrega <cantidad> unidades de "<producto>" de la categoría "<categoria>"
    Then el carrito tiene <cantidad> artículos
    And el total del carrito es la suma del precio por la cantidad de cada producto

    Examples:
      | categoria  | producto                      | cantidad |
      | Tablets    | HP Pro Tablet 608 G1          | 1        |
      | Headphones | Logitech USB Headset H390     | 4        |
      | Speakers   | HP Roar Mini Wireless Speaker | 10       |

  @alternate
  Scenario Outline: Modificar la cantidad de un producto del carrito
    Given que Ana tiene <cantidad inicial> unidades de "<producto>" de la categoría "<categoria>" en el carrito
    When cambia la cantidad de "<producto>" a <cantidad nueva>
    Then el carrito muestra <cantidad nueva> unidades de "<producto>"
    And el total del carrito es la suma del precio por la cantidad de cada producto

    Examples:
      | categoria | producto                             | cantidad inicial | cantidad nueva |
      | Laptops   | HP Pavilion 15z Touch Laptop         | 1                | 3              |
      | Speakers  | Bose Soundlink Bluetooth Speaker III | 4                | 2              |

  @alternate
  Scenario Outline: Retirar un producto del carrito
    Given que Ana tiene en el carrito los siguientes productos
      | categoria | producto                             | cantidad |
      | Laptops   | HP Pavilion 15z Touch Laptop         | 1        |
      | Speakers  | Bose Soundlink Bluetooth Speaker III | 2        |
    When retira "<producto>" del carrito
    Then el carrito ya no contiene "<producto>"
    And el carrito tiene todos los artículos que conservó

    Examples:
      | producto                             |
      | HP Pavilion 15z Touch Laptop         |
      | Bose Soundlink Bluetooth Speaker III |

  @alternate
  Scenario Outline: Vaciar el carrito retirando su único producto
    Given que Ana tiene <cantidad> unidades de "<producto>" de la categoría "<categoria>" en el carrito
    When retira "<producto>" del carrito
    Then ve que su carrito está vacío

    Examples:
      | categoria | producto                | cantidad |
      | Mice      | HP Z3200 Wireless Mouse | 2        |
      | Tablets   | HP Pro Tablet 608 G1    | 1        |

  @exception
  Scenario Outline: Un producto agotado no se puede agregar al carrito
    When intenta agregar <cantidad> unidades del producto agotado "<producto>" de la categoría "<categoria>"
    Then ve el producto marcado como agotado
    And el producto no queda en el carrito

    Examples:
      | categoria  | producto                                         | cantidad |
      | Headphones | Bose SoundLink Around-ear Wireless Headphones II | 1        |
