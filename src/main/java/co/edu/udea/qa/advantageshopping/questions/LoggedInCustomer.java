package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.LOGGED_USERNAME;

@Subject("the username of the signed in customer")
public class LoggedInCustomer implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return LOGGED_USERNAME.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static LoggedInCustomer username() {
        return new LoggedInCustomer();
    }
}
