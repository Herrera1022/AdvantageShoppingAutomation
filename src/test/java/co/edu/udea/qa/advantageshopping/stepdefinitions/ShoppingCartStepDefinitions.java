package co.edu.udea.qa.advantageshopping.stepdefinitions;

import co.edu.udea.qa.advantageshopping.exceptions.ShoppingCartException;
import co.edu.udea.qa.advantageshopping.models.Product;
import co.edu.udea.qa.advantageshopping.questions.CartItems;
import co.edu.udea.qa.advantageshopping.questions.CartProducts;
import co.edu.udea.qa.advantageshopping.questions.EmptyCartMessage;
import co.edu.udea.qa.advantageshopping.questions.ExpectedCartTotal;
import co.edu.udea.qa.advantageshopping.questions.ProductAvailability;
import co.edu.udea.qa.advantageshopping.questions.ProductQuantityInCart;
import co.edu.udea.qa.advantageshopping.questions.ShoppingCartTotal;
import co.edu.udea.qa.advantageshopping.tasks.AddProductToCart;
import co.edu.udea.qa.advantageshopping.tasks.AddProductsToCart;
import co.edu.udea.qa.advantageshopping.tasks.GoToShoppingCart;
import co.edu.udea.qa.advantageshopping.tasks.RemoveProductFromCart;
import co.edu.udea.qa.advantageshopping.tasks.UpdateProductQuantity;
import co.edu.udea.qa.advantageshopping.utils.CartNotebook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

import static co.edu.udea.qa.advantageshopping.exceptions.ShoppingCartException.CART_NOT_EMPTY;
import static co.edu.udea.qa.advantageshopping.exceptions.ShoppingCartException.SOLD_OUT_NOT_SHOWN;
import static co.edu.udea.qa.advantageshopping.exceptions.ShoppingCartException.WRONG_NUMBER_OF_ITEMS;
import static co.edu.udea.qa.advantageshopping.exceptions.ShoppingCartException.WRONG_PRODUCTS;
import static co.edu.udea.qa.advantageshopping.exceptions.ShoppingCartException.WRONG_QUANTITY;
import static co.edu.udea.qa.advantageshopping.exceptions.ShoppingCartException.WRONG_TOTAL;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ShoppingCartStepDefinitions {

    private static final String EMPTY_CART_TEXT = "Your shopping cart is empty";

    @When("agrega al carrito los siguientes productos")
    public void addsTheFollowingProducts(List<Product> products) {
        theActorInTheSpotlight().attemptsTo(AddProductsToCart.fromTheList(products));
    }

    @Given("que {actor} tiene en el carrito los siguientes productos")
    public void hasTheFollowingProductsInTheCart(Actor actor, List<Product> products) {
        actor.wasAbleTo(AddProductsToCart.fromTheList(products));
    }

    @When("agrega {int} unidades de {string} de la categoría {string}")
    public void addsUnitsOfAProduct(int quantity, String productName, String category) {
        theActorInTheSpotlight().attemptsTo(AddProductToCart.ofProduct(new Product(category, productName, quantity)));
    }

    @Given("que {actor} tiene {int} unidades de {string} de la categoría {string} en el carrito")
    public void hasUnitsOfAProductInTheCart(Actor actor, int quantity, String productName, String category) {
        actor.wasAbleTo(AddProductToCart.ofProduct(new Product(category, productName, quantity)));
    }

    @When("intenta agregar {int} unidades del producto agotado {string} de la categoría {string}")
    public void triesToAddASoldOutProduct(int quantity, String productName, String category) {
        theActorInTheSpotlight().attemptsTo(AddProductToCart.ofProduct(new Product(category, productName, quantity)));
    }

    @When("cambia la cantidad de {string} a {int}")
    public void changesTheQuantity(String productName, int quantity) {
        theActorInTheSpotlight().attemptsTo(UpdateProductQuantity.of(productName, quantity));
    }

    @When("retira {string} del carrito")
    public void removesTheProduct(String productName) {
        theActorInTheSpotlight().attemptsTo(RemoveProductFromCart.named(productName));
    }

    @Then("el carrito tiene {int} artículos")
    public void theCartHasItems(int expectedItems) {
        theActorInTheSpotlight().should(seeThat(CartItems.count(), equalTo(expectedItems))
                .orComplainWith(ShoppingCartException.class, WRONG_NUMBER_OF_ITEMS));
    }

    @Then("el carrito tiene todos los artículos agregados")
    public void theCartHasAllTheAddedItems() {
        theCartHasTheItemsWrittenDown();
    }

    @Then("el carrito tiene todos los artículos que conservó")
    public void theCartHasAllTheKeptItems() {
        theCartHasTheItemsWrittenDown();
    }

    @Then("el producto no queda en el carrito")
    public void theProductIsNotInTheCart() {
        theCartHasItems(0);
    }

    // El actor anotó cada producto que agregó o retiró, por eso la cantidad esperada no se repite en el feature
    private void theCartHasTheItemsWrittenDown() {
        Actor actor = theActorInTheSpotlight();
        theCartHasItems(CartNotebook.expectedItems(actor));
    }

    @Then("el total del carrito es la suma del precio por la cantidad de cada producto")
    public void theCartTotalIsTheSumOfEachProduct() {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(GoToShoppingCart.toReviewIt());
        actor.should(seeThat(ShoppingCartTotal.displayed(),
                comparesEqualTo(actor.asksFor(ExpectedCartTotal.ofTheAddedProducts())))
                .orComplainWith(ShoppingCartException.class, WRONG_TOTAL));
    }

    @Then("el carrito muestra {int} unidades de {string}")
    public void theCartShowsTheQuantityOf(int expectedQuantity, String productName) {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(GoToShoppingCart.toReviewIt());
        actor.should(seeThat(ProductQuantityInCart.of(productName), equalTo(expectedQuantity))
                .orComplainWith(ShoppingCartException.class, WRONG_QUANTITY));
    }

    @Then("el carrito ya no contiene {string}")
    public void theCartNoLongerContains(String productName) {
        theActorInTheSpotlight().should(seeThat(CartProducts.names(), not(hasItem(productName.toUpperCase())))
                .orComplainWith(ShoppingCartException.class, WRONG_PRODUCTS));
    }

    @Then("ve que su carrito está vacío")
    public void seesTheCartEmpty() {
        theActorInTheSpotlight().should(
                seeThat(EmptyCartMessage.displayed(), equalTo(EMPTY_CART_TEXT))
                        .orComplainWith(ShoppingCartException.class, CART_NOT_EMPTY),
                seeThat(CartItems.count(), equalTo(0))
                        .orComplainWith(ShoppingCartException.class, CART_NOT_EMPTY)
        );
    }

    @Then("ve el producto marcado como agotado")
    public void seesTheProductSoldOut() {
        theActorInTheSpotlight().should(seeThat(ProductAvailability.isSoldOut(), is(true))
                .orComplainWith(ShoppingCartException.class, SOLD_OUT_NOT_SHOWN));
    }
}
