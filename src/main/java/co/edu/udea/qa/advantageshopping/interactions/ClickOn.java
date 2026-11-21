package co.edu.udea.qa.advantageshopping.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.ElementClickInterceptedException;

import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

/**
 * Da clic en un elemento cuando la tienda terminó de cargar y el elemento se puede presionar.
 * Si una animación o ventana emergente todavía lo tapa, se envía el evento de clic con JavaScript.
 */
public class ClickOn implements Interaction {

    private static final String DISPATCH_CLICK_EVENT =
            "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));";

    private final Target target;

    public ClickOn(Target target) {
        this.target = target;
    }

    @Override
    @Step("{0} clicks on the #target")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitForLoader.toFinish(),
                WaitUntil.the(target, isClickable()).forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
        WebElementFacade element = target.resolveFor(actor);
        try {
            element.click();
        } catch (ElementClickInterceptedException coveredElement) {
            // El evento de clic sirve para cualquier elemento, incluso íconos SVG que no tienen la función click()
            BrowseTheWeb.as(actor).evaluateJavascript(DISPATCH_CLICK_EVENT, element.getElement());
        }
    }

    public static ClickOn the(Target target) {
        return instrumented(ClickOn.class, target);
    }
}
