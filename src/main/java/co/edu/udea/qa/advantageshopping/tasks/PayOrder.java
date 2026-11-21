package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.Pause;
import co.edu.udea.qa.advantageshopping.models.PaymentDetails;
import co.edu.udea.qa.advantageshopping.models.PaymentMethod;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.SUCCESS_MESSAGE;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.MASTER_CREDIT_PAY_NOW_BUTTON;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.SAFEPAY_PAY_NOW_BUTTON;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Paga la orden en el checkout con el medio de pago elegido y espera la confirmación de la compra.
 */
public class PayOrder implements Task {

    private final PaymentDetails paymentDetails;
    private final String paymentMethod;

    public PayOrder(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
        this.paymentMethod = paymentDetails.getMethod().getDisplayName();
    }

    @Override
    @Step("{0} pays the order with #paymentMethod")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnterPaymentDetails.using(paymentDetails),
                Pause.forTheDemo(),
                ClickOn.the(paymentDetails.getMethod() == PaymentMethod.SAFEPAY
                        ? SAFEPAY_PAY_NOW_BUTTON
                        : MASTER_CREDIT_PAY_NOW_BUTTON),
                WaitUntil.the(SUCCESS_MESSAGE, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
    }

    public static PayOrder using(PaymentDetails paymentDetails) {
        return instrumented(PayOrder.class, paymentDetails);
    }
}
