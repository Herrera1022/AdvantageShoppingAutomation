package co.edu.udea.qa.advantageshopping.questions;

import co.edu.udea.qa.advantageshopping.models.PaymentMethod;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.MASTER_CREDIT_PAY_NOW_BUTTON;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderPaymentPage.SAFEPAY_PAY_NOW_BUTTON;

/**
 * La tienda solo habilita el pago cuando los datos del medio elegido están completos y son válidos.
 */
public class PaymentAvailability implements Question<Boolean> {

    private final PaymentMethod method;

    public PaymentAvailability(PaymentMethod method) {
        this.method = method;
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return (method == PaymentMethod.SAFEPAY ? SAFEPAY_PAY_NOW_BUTTON : MASTER_CREDIT_PAY_NOW_BUTTON)
                .resolveFor(actor).isCurrentlyEnabled();
    }

    @Override
    public String getSubject() {
        return "whether the payment with " + method.getDisplayName() + " can be confirmed";
    }

    public static PaymentAvailability isEnabledFor(PaymentMethod method) {
        return new PaymentAvailability(method);
    }
}
