package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.REGISTRATION_ERROR;

@Subject("the registration error message")
public class RegistrationErrorMessage implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return REGISTRATION_ERROR.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static RegistrationErrorMessage displayed() {
        return new RegistrationErrorMessage();
    }
}
