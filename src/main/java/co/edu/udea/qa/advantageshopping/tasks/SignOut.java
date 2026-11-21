package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.WaitUntilHidden;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.LOGGED_USERNAME;
import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.SIGN_OUT_OPTION;
import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.USER_MENU;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SignOut implements Task {

    public SignOut() {
    }

    @Override
    @Step("{0} signs out of the store")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(USER_MENU),
                ClickOn.the(SIGN_OUT_OPTION),
                WaitUntilHidden.the(LOGGED_USERNAME)
        );
    }

    public static SignOut ofTheStore() {
        return instrumented(SignOut.class);
    }
}
