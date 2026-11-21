package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

import java.util.List;
import java.util.stream.Collectors;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ShoppingCartPage.PRODUCT_NAMES;

@Subject("the products in the cart")
public class CartProducts implements Question<List<String>> {

    @Override
    public List<String> answeredBy(Actor actor) {
        return Text.ofEach(PRODUCT_NAMES).answeredBy(actor).stream()
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public static CartProducts names() {
        return new CartProducts();
    }
}
