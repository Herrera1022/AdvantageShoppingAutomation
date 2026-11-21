package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ShoppingCartPage.EMPTY_CART_MESSAGE;

@Subject("the empty cart message")
public class EmptyCartMessage implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return EMPTY_CART_MESSAGE.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static EmptyCartMessage displayed() {
        return new EmptyCartMessage();
    }
}
