package co.edu.udea.qa.advantageshopping.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class HomePage {

    public static final Target LOADER = Target.the("loading indicator").located(By.cssSelector("div.loader"));
    public static final Target STORE_LOGO = Target.the("store logo").located(By.cssSelector("div.logo a"));
    public static final Target CATEGORY =Target.the("{0} category").locatedBy("#{0}Img");
    public static final Target USER_MENU = Target.the("user menu").located(By.cssSelector("#menuUser"));
    public static final Target LOGGED_USERNAME = Target.the("logged in username")
            .located(By.cssSelector("#menuUserLink span.hi-user"));
    public static final Target SIGN_OUT_OPTION = Target.the("sign out option")
            .located(By.cssSelector("#loginMiniTitle label[translate='Sign_out']"));
    public static final Target CART_ICON = Target.the("shopping cart icon").located(By.cssSelector("#menuCart"));
    public static final Target CART_ITEMS_BADGE = Target.the("number of items in the cart")
            .located(By.cssSelector("#shoppingCartLink span.cart"));

    private HomePage() {
    }
}
