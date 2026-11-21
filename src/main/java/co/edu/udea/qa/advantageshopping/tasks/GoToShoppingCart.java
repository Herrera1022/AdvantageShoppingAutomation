package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.WaitForLoader;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.CART_ICON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GoToShoppingCart implements Task {

    public GoToShoppingCart() {
    }

    @Override
    @Step("{0} reviews the shopping cart")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(CART_ICON),
                WaitForLoader.toFinish()
        );
    }

    public static GoToShoppingCart toReviewIt() {
        return instrumented(GoToShoppingCart.class);
    }
}
