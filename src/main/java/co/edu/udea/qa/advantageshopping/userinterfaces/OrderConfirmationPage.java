package co.edu.udea.qa.advantageshopping.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class OrderConfirmationPage {

    private static final String SUMMARY_VALUE =
            "//div[@id='orderPaymentSuccess']//label[contains(normalize-space(),'{0}')]/a";

    public static final Target SUCCESS_MESSAGE = Target.the("purchase success message")
            .located(By.cssSelector("#orderPaymentSuccess h2 span"));
    public static final Target ORDER_NUMBER = Target.the("order number").located(By.cssSelector("#orderNumberLabel"));
    public static final Target TRACKING_NUMBER = Target.the("tracking number").located(By.cssSelector("#trackingNumberLabel"));
    public static final Target PAYMENT_METHOD = Target.the("payment method used")
            .locatedBy("//div[@id='orderPaymentSuccess']//span[normalize-space()='Payment method']"
                    + "/following-sibling::div[1]/label");
    public static final Target SUBTOTAL = Target.the("order subtotal").locatedBy(SUMMARY_VALUE.replace("{0}", "Subtotal"));
    public static final Target SHIPPING = Target.the("order shipping cost")
            .locatedBy(SUMMARY_VALUE.replace("{0}", "shipping"));
    public static final Target TOTAL = Target.the("order total").located(By.cssSelector("#orderPaymentSuccess label.total a"));

    private OrderConfirmationPage() {
    }
}
