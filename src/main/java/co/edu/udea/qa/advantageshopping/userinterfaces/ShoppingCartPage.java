package co.edu.udea.qa.advantageshopping.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class ShoppingCartPage {

    private static final String PRODUCT_ROW =
            "//div[@id='shoppingCart']//tbody/tr[.//label[contains(@class,'productName') and normalize-space()='{0}']]";

    public static final Target PRODUCT_NAMES = Target.the("products in the cart")
            .located(By.cssSelector("#shoppingCart tbody label.productName"));
    public static final Target PRODUCT_QUANTITY = Target.the("quantity of {0}")
            .locatedBy(PRODUCT_ROW + "//td[contains(@class,'quantityMobile')]/label[2]");
    public static final Target REMOVE_PRODUCT = Target.the("remove option of {0}")
            .locatedBy(PRODUCT_ROW + "//a[contains(@class,'remove')]");
    public static final Target EDIT_PRODUCT = Target.the("edit option of {0}")
            .locatedBy(PRODUCT_ROW + "//a[contains(@class,'edit')]");
    public static final Target TOTAL = Target.the("cart total").located(By.cssSelector("#shoppingCart tfoot span.roboto-medium"));
    public static final Target CHECKOUT_BUTTON = Target.the("checkout button").located(By.cssSelector("#checkOutButton"));
    public static final Target EMPTY_CART_MESSAGE = Target.the("empty cart message")
            .located(By.cssSelector("div.bigEmptyCart label"));

    private ShoppingCartPage() {
    }
}
