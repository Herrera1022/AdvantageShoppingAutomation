Feature: Inicio de sesión de clientes
  Como cliente registrado de la tienda en línea
  quiero iniciar sesión con mis credenciales
  para acceder a mi cuenta y realizar compras

  Background:
    Given que Ana ingresa a la tienda en línea

  @happy_path
  Scenario: Inicio de sesión exitoso de un cliente registrado
    Given que Ana tiene una cuenta registrada y cerró su sesión
    When inicia sesión con sus credenciales
    Then ve su nombre de usuario en la tienda

  @exception
  Scenario Outline: Inicio de sesión rechazado por credenciales incorrectas
    When inicia sesión con el usuario "<usuario>" y la contraseña "<contraseña>"
    Then ve el mensaje de error de inicio de sesión "Incorrect user name or password."

    Examples:
      | usuario       | contraseña |
      | noexiste2026  | Udea2026   |
      | anaudea       | Clave0000  |
