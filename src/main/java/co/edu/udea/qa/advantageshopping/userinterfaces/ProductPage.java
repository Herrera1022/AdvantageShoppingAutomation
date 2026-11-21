package co.edu.udea.qa.advantageshopping.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class ProductPage {

    public static final Target PRODUCT_IN_CATEGORY = Target.the("product {0} in the category")
            .locatedBy("//a[contains(@class,'productName') and normalize-space()='{0}']");
    public static final Target PRODUCT_TITLE = Target.the("product title").located(By.cssSelector("#Description h1"));
    public static final Target PRODUCT_PRICE = Target.the("product price").located(By.cssSelector("#Description h2"));
    public static final Target SOLD_OUT_LABEL = Target.the("sold out label")
            .located(By.cssSelector("#Description span[translate='SOUL_OUT']"));
    public static final Target QUANTITY = Target.the("quantity").located(By.cssSelector("input[name='quantity']"));
    public static final Target ADD_TO_CART_BUTTON = Target.the("add to cart button")
            .located(By.cssSelector("button[name='save_to_cart']"));

    private ProductPage() {
    }
}
