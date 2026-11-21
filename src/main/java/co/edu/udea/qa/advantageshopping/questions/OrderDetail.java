package co.edu.udea.qa.advantageshopping.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.ORDER_NUMBER;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.PAYMENT_METHOD;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.TRACKING_NUMBER;

/**
 * Datos de texto de la orden confirmada: número de orden, número de seguimiento y medio de pago usado.
 */
public class OrderDetail implements Question<String> {

    private final Target detail;

    public OrderDetail(Target detail) {
        this.detail = detail;
    }

    @Override
    public String answeredBy(Actor actor) {
        return detail.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    @Override
    public String getSubject() {
        return "the " + detail.getName();
    }

    public static OrderDetail orderNumber() {
        return new OrderDetail(ORDER_NUMBER);
    }

    public static OrderDetail trackingNumber() {
        return new OrderDetail(TRACKING_NUMBER);
    }

    public static OrderDetail paymentMethod() {
        return new OrderDetail(PAYMENT_METHOD);
    }
}
