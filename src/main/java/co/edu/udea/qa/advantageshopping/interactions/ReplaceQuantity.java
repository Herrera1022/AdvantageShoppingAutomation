package co.edu.udea.qa.advantageshopping.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.Keys;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ProductPage.QUANTITY;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * El campo de cantidad solo acepta dígitos e ignora el borrado normal, por eso se selecciona
 * el valor actual y se sobrescribe con la nueva cantidad.
 */
public class ReplaceQuantity implements Interaction {

    private final int quantity;

    public ReplaceQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    @Step("{0} sets the quantity to #quantity")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(QUANTITY, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Enter.keyValues(Keys.chord(Keys.CONTROL, "a"), String.valueOf(quantity)).into(QUANTITY)
        );
    }

    public static ReplaceQuantity with(int quantity) {
        return instrumented(ReplaceQuantity.class, quantity);
    }
}
