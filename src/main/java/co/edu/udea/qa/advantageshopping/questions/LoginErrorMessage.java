package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.LoginModal.ERROR_MESSAGE;

@Subject("the sign in error message")
public class LoginErrorMessage implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return ERROR_MESSAGE.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static LoginErrorMessage displayed() {
        return new LoginErrorMessage();
    }
}
