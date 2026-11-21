package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.REGISTER_BUTTON;

/**
 * La tienda solo habilita la creación de la cuenta cuando todos los campos son válidos.
 */
@Subject("whether the account can be created")
public class RegistrationAvailability implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return REGISTER_BUTTON.resolveFor(actor).isCurrentlyEnabled();
    }

    public static RegistrationAvailability isEnabled() {
        return new RegistrationAvailability();
    }
}
