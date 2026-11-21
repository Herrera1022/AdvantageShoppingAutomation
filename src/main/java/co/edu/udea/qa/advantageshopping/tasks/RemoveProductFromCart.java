package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.Pause;
import co.edu.udea.qa.advantageshopping.interactions.WaitUntilHidden;
import co.edu.udea.qa.advantageshopping.utils.CartNotebook;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ShoppingCartPage.REMOVE_PRODUCT;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RemoveProductFromCart implements Task {

    private final String productName;

    public RemoveProductFromCart(String productName) {
        this.productName = productName;
    }

    @Override
    @Step("{0} removes #productName from the cart")
    public <T extends Actor> void performAs(T actor) {
        String nameInCart = productName.toUpperCase();
        actor.attemptsTo(
                GoToShoppingCart.toReviewIt(),
                Pause.forTheDemo(),
                ClickOn.the(REMOVE_PRODUCT.of(nameInCart)),
                WaitUntilHidden.the(REMOVE_PRODUCT.of(nameInCart))
        );
        CartNotebook.erase(actor, productName);
    }

    public static RemoveProductFromCart named(String productName) {
        return instrumented(RemoveProductFromCart.class, productName);
    }
}
