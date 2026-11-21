package co.edu.udea.qa.advantageshopping.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Un producto que el cliente quiere comprar: la categoría de la tienda a la que pertenece, su nombre y cuántas unidades.
 */
@Getter
@AllArgsConstructor
public class Product {

    private final String category;
    private final String name;
    private final int quantity;

    @Override
    public String toString() {
        return quantity + " x " + name;
    }
}
