package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.SUCCESS_MESSAGE;

@Subject("the purchase confirmation message")
public class PurchaseConfirmation implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return SUCCESS_MESSAGE.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static PurchaseConfirmation message() {
        return new PurchaseConfirmation();
    }
}
