package co.edu.udea.qa.advantageshopping.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.REGISTRATION_ERROR;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.USERNAME;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;

/**
 * Después de enviar el formulario de cuenta, la tienda muestra un error debajo del botón de registro
 * o acepta la cuenta y sale del formulario. Esta interacción espera hasta que pase una de las dos cosas.
 */
public class WaitForRegistrationResponse implements Interaction {

    @Override
    public <T extends Actor> void performAs(T actor) {
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(MAX_WAIT_SECONDS))
                .ignoring(StaleElementReferenceException.class)
                .until(driver -> !USERNAME.resolveFor(actor).isCurrentlyVisible()
                        || hasErrorMessage(actor));
    }

    private static boolean hasErrorMessage(Actor actor) {
        return REGISTRATION_ERROR.resolveAllFor(actor).stream()
                .anyMatch(error -> error.isCurrentlyVisible() && !error.getText().isBlank());
    }

    public static WaitForRegistrationResponse fromTheStore() {
        return new WaitForRegistrationResponse();
    }
}
