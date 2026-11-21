package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.Pause;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.NEXT_BUTTON;
import static co.edu.udea.qa.advantageshopping.userinterfaces.ShoppingCartPage.CHECKOUT_BUTTON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Inicia el checkout desde el carrito y confirma los datos de envío que registró el cliente.
 */
public class ProceedToCheckout implements Task {

    public ProceedToCheckout() {
    }

    @Override
    @Step("{0} proceeds to checkout and confirms the shipping details")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                GoToShoppingCart.toReviewIt(),
                ClickOn.the(CHECKOUT_BUTTON),
                Pause.forTheDemo(),
                ClickOn.the(NEXT_BUTTON)
        );
    }

    public static ProceedToCheckout withTheRegisteredShippingDetails() {
        return instrumented(ProceedToCheckout.class);
    }
}
