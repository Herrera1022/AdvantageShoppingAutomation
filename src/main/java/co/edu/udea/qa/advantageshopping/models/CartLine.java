package co.edu.udea.qa.advantageshopping.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Un producto agregado al carrito junto con el precio unitario que mostraba la tienda cuando se agregó.
 * Se usa para calcular el valor que el cliente espera pagar.
 */
@Getter
@AllArgsConstructor
public class CartLine {

    private final String productName;
    private final BigDecimal unitPrice;
    private final int quantity;

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
