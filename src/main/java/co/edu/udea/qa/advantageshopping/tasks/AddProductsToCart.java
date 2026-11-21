package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.models.Product;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AddProductsToCart implements Task {

    private final List<Product> products;

    public AddProductsToCart(List<Product> products) {
        this.products = products;
    }

    @Override
    @Step("{0} adds #products to the cart")
    public <T extends Actor> void performAs(T actor) {
        products.forEach(product -> actor.attemptsTo(AddProductToCart.ofProduct(product)));
    }

    public static AddProductsToCart fromTheList(List<Product> products) {
        return instrumented(AddProductsToCart.class, products);
    }
}
