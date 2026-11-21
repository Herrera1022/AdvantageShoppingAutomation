package co.edu.udea.qa.advantageshopping.questions;

import co.edu.udea.qa.advantageshopping.utils.CartNotebook;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.math.BigDecimal;

/**
 * Suma del precio unitario por la cantidad de cada producto que agregó el actor, con los precios vistos en el catálogo.
 */
@Subject("the expected total (unit price x quantity of each product)")
public class ExpectedCartTotal implements Question<BigDecimal> {

    @Override
    public BigDecimal answeredBy(Actor actor) {
        return CartNotebook.expectedTotal(actor);
    }

    public static ExpectedCartTotal ofTheAddedProducts() {
        return new ExpectedCartTotal();
    }
}
