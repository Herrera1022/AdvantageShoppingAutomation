package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ProductPage.SOLD_OUT_LABEL;

@Subject("whether the product is sold out")
public class ProductAvailability implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return SOLD_OUT_LABEL.resolveFor(actor).isCurrentlyVisible();
    }

    public static ProductAvailability isSoldOut() {
        return new ProductAvailability();
    }
}
