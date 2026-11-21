package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.CART_ITEMS_BADGE;

/**
 * Cantidad de unidades en el carrito, como se ve junto al ícono del carrito (vacío cuando no hay ninguna).
 */
@Subject("the number of items in the cart")
public class CartItems implements Question<Integer> {

    @Override
    public Integer answeredBy(Actor actor) {
        String items = Text.of(CART_ITEMS_BADGE).answeredBy(actor).trim();
        return items.isEmpty() ? 0 : Integer.parseInt(items);
    }

    public static CartItems count() {
        return new CartItems();
    }
}
