package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.Pause;
import co.edu.udea.qa.advantageshopping.interactions.ReplaceQuantity;
import co.edu.udea.qa.advantageshopping.interactions.WaitForLoader;
import co.edu.udea.qa.advantageshopping.utils.CartNotebook;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ProductPage.ADD_TO_CART_BUTTON;
import static co.edu.udea.qa.advantageshopping.userinterfaces.ShoppingCartPage.EDIT_PRODUCT;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Cambia la cantidad de un producto que ya está en el carrito usando su opción de editar.
 */
public class UpdateProductQuantity implements Task {

    private final String productName;
    private final int quantity;

    public UpdateProductQuantity(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    @Override
    @Step("{0} changes the quantity of #productName to #quantity")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                GoToShoppingCart.toReviewIt(),
                ClickOn.the(EDIT_PRODUCT.of(productName.toUpperCase())),
                ReplaceQuantity.with(quantity),
                Pause.forTheDemo(),
                ClickOn.the(ADD_TO_CART_BUTTON),
                WaitForLoader.toFinish()
        );
        CartNotebook.changeQuantity(actor, productName, quantity);
    }

    public static UpdateProductQuantity of(String productName, int quantity) {
        return instrumented(UpdateProductQuantity.class, productName, quantity);
    }
}
