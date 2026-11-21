package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.Pause;
import co.edu.udea.qa.advantageshopping.interactions.ReplaceQuantity;
import co.edu.udea.qa.advantageshopping.interactions.WaitForLoader;
import co.edu.udea.qa.advantageshopping.models.CartLine;
import co.edu.udea.qa.advantageshopping.models.Product;
import co.edu.udea.qa.advantageshopping.utils.CartNotebook;
import co.edu.udea.qa.advantageshopping.utils.PriceConverter;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Text;

import static co.edu.udea.qa.advantageshopping.userinterfaces.ProductPage.ADD_TO_CART_BUTTON;
import static co.edu.udea.qa.advantageshopping.userinterfaces.ProductPage.PRODUCT_PRICE;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Busca un producto en el catálogo y agrega al carrito la cantidad pedida.
 * Anota el precio unitario que muestra la tienda para validar después los valores a pagar.
 */
public class AddProductToCart implements Task {

    private final Product product;

    public AddProductToCart(Product product) {
        this.product = product;
    }

    @Override
    @Step("{0} adds #product to the cart")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ViewProduct.inTheCatalog(product),
                ReplaceQuantity.with(product.getQuantity()),
                Pause.forTheDemo(),
                ClickOn.the(ADD_TO_CART_BUTTON),
                WaitForLoader.toFinish()
        );
        String displayedPrice = Text.of(PRODUCT_PRICE).answeredBy(actor);
        CartNotebook.write(actor,
                new CartLine(product.getName(), PriceConverter.toAmount(displayedPrice), product.getQuantity()));
    }

    public static AddProductToCart ofProduct(Product product) {
        return instrumented(AddProductToCart.class, product);
    }
}
