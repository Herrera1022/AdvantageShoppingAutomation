package co.edu.udea.qa.advantageshopping.interactions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;

/**
 * Espera hasta que ningún elemento del target se muestre (oculto o retirado de la página).
 * Revisa el estado actual de los elementos (isCurrentlyVisible) en lugar del matcher isNotVisible de Serenity,
 * que con los elementos ocultos de esta tienda siempre consumía todo el tiempo de espera.
 */
public class WaitUntilHidden implements Interaction {

    private final Target target;

    public WaitUntilHidden(Target target) {
        this.target = target;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(MAX_WAIT_SECONDS))
                .ignoring(StaleElementReferenceException.class)
                .until(driver -> target.resolveAllFor(actor).stream().noneMatch(WebElementFacade::isCurrentlyVisible));
    }

    public static WaitUntilHidden the(Target target) {
        return new WaitUntilHidden(target);
    }
}
