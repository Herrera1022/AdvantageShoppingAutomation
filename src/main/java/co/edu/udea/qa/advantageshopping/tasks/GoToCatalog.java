package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.WaitForLoader;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.CATEGORY;
import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.STORE_LOGO;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Vuelve al catálogo con el logo de la tienda, como lo haría un cliente. A diferencia de abrir la URL otra vez,
 * no recarga la página, así la tienda alcanza a guardar el carrito del cliente que inició sesión.
 */
public class GoToCatalog implements Task {

    private static final String ANY_CATEGORY = "laptops";

    public GoToCatalog() {
    }

    @Override
    @Step("{0} goes back to the catalog")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(STORE_LOGO),
                WaitForLoader.toFinish(),
                WaitUntil.the(CATEGORY.of(ANY_CATEGORY), isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
    }

    public static GoToCatalog fromTheHeader() {
        return instrumented(GoToCatalog.class);
    }
}
