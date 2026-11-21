package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.WaitForRegistrationResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.REGISTER_BUTTON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConfirmRegistration implements Task {

    public ConfirmRegistration() {
    }

    @Override
    @Step("{0} submits the account form")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(REGISTER_BUTTON),
                WaitForRegistrationResponse.fromTheStore()
        );
    }

    public static ConfirmRegistration ofTheAccount() {
        return instrumented(ConfirmRegistration.class);
    }
}
