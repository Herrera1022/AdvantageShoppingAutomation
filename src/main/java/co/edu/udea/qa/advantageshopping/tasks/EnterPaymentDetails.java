package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.interactions.EnterAndConfirm;
import co.edu.udea.qa.advantageshopping.models.PaymentDetails;
import co.edu.udea.qa.advantageshopping.models.PaymentMethod;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.CARDHOLDER_NAME;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.CARD_NUMBER;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.CVV;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.EXPIRATION_MONTH;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.EXPIRATION_YEAR;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.MASTER_CREDIT_OPTION;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.SAFEPAY_OPTION;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.SAFEPAY_PASSWORD;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.SAFEPAY_USERNAME;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Elige el medio de pago y llena sus datos. No confirma el pago.
 */
public class EnterPaymentDetails implements Task {

    private final PaymentDetails paymentDetails;
    private final String paymentMethod;

    public EnterPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
        this.paymentMethod = paymentDetails.getMethod().getDisplayName();
    }

    @Override
    @Step("{0} enters the payment details for #paymentMethod")
    public <T extends Actor> void performAs(T actor) {
        if (paymentDetails.getMethod() == PaymentMethod.SAFEPAY) {
            actor.attemptsTo(
                    ClickOn.the(SAFEPAY_OPTION),
                    WaitUntil.the(SAFEPAY_USERNAME, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                    Enter.theValue(paymentDetails.getSafePayUsername()).into(SAFEPAY_USERNAME),
                    Enter.theValue(paymentDetails.getSafePayPassword()).into(SAFEPAY_PASSWORD)
            );
        } else {
            actor.attemptsTo(
                    ClickOn.the(MASTER_CREDIT_OPTION),
                    WaitUntil.the(CARD_NUMBER, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                    Enter.theValue(paymentDetails.getCardNumber()).into(CARD_NUMBER),
                    SelectFromOptions.byVisibleText(paymentDetails.getExpirationMonth()).from(EXPIRATION_MONTH),
                    SelectFromOptions.byVisibleText(paymentDetails.getExpirationYear()).from(EXPIRATION_YEAR),
                    // La tienda reformatea el número de tarjeta después de escribirlo y puede perder la primera tecla
                    // del siguiente campo, por eso los campos escritos se leen de nuevo para confirmarlos
                    EnterAndConfirm.theValue(paymentDetails.getCvv()).into(CVV),
                    EnterAndConfirm.theValue(paymentDetails.getCardholderName()).into(CARDHOLDER_NAME)
            );
        }
    }

    public static EnterPaymentDetails using(PaymentDetails paymentDetails) {
        return instrumented(EnterPaymentDetails.class, paymentDetails);
    }
}
