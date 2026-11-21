package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.Pause;
import co.edu.udea.qa.advantageshopping.interactions.WaitForLoader;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.USER_MENU;
import static co.edu.udea.qa.advantageshopping.userinterfaces.LoginModal.PASSWORD;
import static co.edu.udea.qa.advantageshopping.userinterfaces.LoginModal.SIGN_IN_BUTTON;
import static co.edu.udea.qa.advantageshopping.userinterfaces.LoginModal.USERNAME;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LogIn implements Task {

    private final String username;
    private final String password;

    public LogIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    @Step("{0} signs in with the username #username")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(USER_MENU),
                WaitUntil.the(USERNAME, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Enter.theValue(username).into(USERNAME),
                Enter.theValue(password).into(PASSWORD),
                Pause.forTheDemo(),
                ClickOn.the(SIGN_IN_BUTTON),
                WaitForLoader.toFinish()
        );
    }

    public static LogIn withCredentials(String username, String password) {
        return instrumented(LogIn.class, username, password);
    }
}
