package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ShoppingCartPage.PRODUCT_QUANTITY;

public class ProductQuantityInCart implements Question<Integer> {

    private final String productName;

    public ProductQuantityInCart(String productName) {
        this.productName = productName;
    }

    @Override
    public Integer answeredBy(Actor actor) {
        String quantity = PRODUCT_QUANTITY.of(productName.toUpperCase()).resolveFor(actor)
                .waitUntilVisible().getText().trim();
        return Integer.parseInt(quantity);
    }

    @Override
    public String getSubject() {
        return "the quantity of " + productName + " in the cart";
    }

    public static ProductQuantityInCart of(String productName) {
        return new ProductQuantityInCart(productName);
    }
}
