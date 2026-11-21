package co.edu.udea.qa.advantageshopping.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class RegisterPage {

    public static final Target USERNAME = Target.the("username").located(By.cssSelector("input[name='usernameRegisterPage']"));
    public static final Target EMAIL = Target.the("email").located(By.cssSelector("input[name='emailRegisterPage']"));
    public static final Target PASSWORD = Target.the("password").located(By.cssSelector("input[name='passwordRegisterPage']"));
    public static final Target PASSWORD_CONFIRMATION = Target.the("password confirmation")
            .located(By.cssSelector("input[name='confirm_passwordRegisterPage']"));
    public static final Target FIRST_NAME = Target.the("first name").located(By.cssSelector("input[name='first_nameRegisterPage']"));
    public static final Target LAST_NAME = Target.the("last name").located(By.cssSelector("input[name='last_nameRegisterPage']"));
    public static final Target PHONE_NUMBER = Target.the("phone number")
            .located(By.cssSelector("input[name='phone_numberRegisterPage']"));
    public static final Target COUNTRY = Target.the("country").located(By.cssSelector("select[name='countryListboxRegisterPage']"));
    public static final Target CITY = Target.the("city").located(By.cssSelector("input[name='cityRegisterPage']"));
    public static final Target ADDRESS = Target.the("address").located(By.cssSelector("input[name='addressRegisterPage']"));
    public static final Target STATE = Target.the("state").located(By.cssSelector("input[name='state_/_province_/_regionRegisterPage']"));
    public static final Target POSTAL_CODE = Target.the("postal code").located(By.cssSelector("input[name='postal_codeRegisterPage']"));
    public static final Target TERMS_AGREEMENT = Target.the("terms and conditions agreement")
            .located(By.cssSelector("input[name='i_agree']"));
    public static final Target REGISTER_BUTTON = Target.the("register button").located(By.cssSelector("#register_btn"));
    public static final Target REGISTRATION_ERROR = Target.the("registration error message")
            .locatedBy("//button[@id='register_btn']/ancestor::div[1]/label[contains(@class,'smollMargin')]");
    public static final Target FIELD_VALIDATION_MESSAGE = Target.the("validation message of field {0}")
            .locatedBy("//input[@name='{0}']/following-sibling::label");

    private RegisterPage() {
    }
}
