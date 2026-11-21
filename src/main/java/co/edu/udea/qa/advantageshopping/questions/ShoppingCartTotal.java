package co.edu.udea.qa.advantageshopping.questions;

import co.edu.udea.qa.advantageshopping.utils.PriceConverter;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.math.BigDecimal;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ShoppingCartPage.TOTAL;

@Subject("the total shown in the cart")
public class ShoppingCartTotal implements Question<BigDecimal> {

    @Override
    public BigDecimal answeredBy(Actor actor) {
        return PriceConverter.toAmount(TOTAL.resolveFor(actor).waitUntilVisible().getText());
    }

    public static ShoppingCartTotal displayed() {
        return new ShoppingCartTotal();
    }
}
