package co.edu.udea.qa.advantageshopping.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class OrderPaymentPage {

    public static final Target NEXT_BUTTON = Target.the("next button of shipping details").located(By.cssSelector("#next_btn"));

    public static final Target SAFEPAY_OPTION = Target.the("SafePay option").located(By.cssSelector("img[alt='Safepay']"));
    public static final Target SAFEPAY_USERNAME = Target.the("SafePay username")
            .located(By.cssSelector("input[name='safepay_username']"));
    public static final Target SAFEPAY_PASSWORD = Target.the("SafePay password")
            .located(By.cssSelector("input[name='safepay_password']"));
    public static final Target SAFEPAY_PAY_NOW_BUTTON = Target.the("SafePay pay now button")
            .located(By.cssSelector("#pay_now_btn_SAFEPAY"));

    public static final Target MASTER_CREDIT_OPTION = Target.the("MasterCredit option")
            .located(By.cssSelector("img[alt='Master credit']"));
    public static final Target CARD_NUMBER = Target.the("card number").located(By.cssSelector("input[name='card_number']"));
    public static final Target CVV = Target.the("CVV number").located(By.cssSelector("input[name='cvv_number']"));
    public static final Target EXPIRATION_MONTH = Target.the("expiration month").located(By.cssSelector("select[name='mmListbox']"));
    public static final Target EXPIRATION_YEAR = Target.the("expiration year").located(By.cssSelector("select[name='yyyyListbox']"));
    public static final Target CARDHOLDER_NAME = Target.the("cardholder name")
            .located(By.cssSelector("input[name='cardholder_name']"));
    public static final Target MASTER_CREDIT_PAY_NOW_BUTTON = Target.the("MasterCredit pay now button")
            .located(By.cssSelector("#pay_now_btn_ManualPayment"));

    private OrderPaymentPage() {
    }
}
