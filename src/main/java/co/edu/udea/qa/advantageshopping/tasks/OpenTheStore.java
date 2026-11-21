package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.WaitForLoader;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.CATEGORY;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static co.edu.udea.qa.advantageshopping.utils.Constants.STORE_URL_PAGE_NAME;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class OpenTheStore implements Task {

    private static final String ANY_CATEGORY = "laptops";

    public OpenTheStore() {
    }

    @Override
    @Step("{0} opens the online store catalog")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.browserOn().thePageNamed(STORE_URL_PAGE_NAME),
                WaitForLoader.toFinish(),
                WaitUntil.the(CATEGORY.of(ANY_CATEGORY), isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
    }

    public static OpenTheStore catalog() {
        return instrumented(OpenTheStore.class);
    }
}
