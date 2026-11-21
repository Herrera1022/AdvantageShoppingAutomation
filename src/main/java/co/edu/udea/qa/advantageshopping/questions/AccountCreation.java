package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.USERNAME;

/**
 * La tienda sale del formulario de cuenta solo cuando acepta la cuenta nueva.
 */
@Subject("whether the store accepted the new account")
public class AccountCreation implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return !USERNAME.resolveFor(actor).isCurrentlyVisible();
    }

    public static AccountCreation wasAccepted() {
        return new AccountCreation();
    }
}
