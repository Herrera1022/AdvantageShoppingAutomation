package co.edu.udea.qa.advantageshopping.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class LoginModal {

    public static final Target USERNAME = Target.the("login username").located(By.cssSelector("input[name='username']"));
    public static final Target PASSWORD = Target.the("login password").located(By.cssSelector("input[name='password']"));
    public static final Target SIGN_IN_BUTTON = Target.the("sign in button").located(By.cssSelector("#sign_in_btn"));
    public static final Target CREATE_NEW_ACCOUNT = Target.the("create new account link")
            .located(By.cssSelector("a.create-new-account"));
    public static final Target ERROR_MESSAGE = Target.the("sign in error message").located(By.cssSelector("#signInResultMessage.invalid"));

    private LoginModal() {
    }
}
