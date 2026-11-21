package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.models.Product;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.CATEGORY;
import static co.edu.udea.qa.advantageshopping.userinterfaces.ProductPage.PRODUCT_IN_CATEGORY;
import static co.edu.udea.qa.advantageshopping.userinterfaces.ProductPage.PRODUCT_TITLE;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Navega por el catálogo: va a la categoría del producto y abre su detalle.
 */
public class ViewProduct implements Task {

    private final String productName;
    private final String category;

    public ViewProduct(Product product) {
        this.productName = product.getName();
        this.category = product.getCategory();
    }

    @Override
    @Step("{0} looks for #productName in the #category category")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                GoToCatalog.fromTheHeader(),
                ClickOn.the(CATEGORY.of(category.toLowerCase())),
                ClickOn.the(PRODUCT_IN_CATEGORY.of(productName)),
                WaitUntil.the(PRODUCT_TITLE, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
    }

    public static ViewProduct inTheCatalog(Product product) {
        return instrumented(ViewProduct.class, product);
    }
}
