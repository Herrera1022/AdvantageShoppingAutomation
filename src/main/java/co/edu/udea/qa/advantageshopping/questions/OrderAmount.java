package co.edu.udea.qa.advantageshopping.questions;

import co.edu.udea.qa.advantageshopping.utils.PriceConverter;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import java.math.BigDecimal;

import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.SHIPPING;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.SUBTOTAL;
import static co.edu.udea.qa.advantageshopping.userinterfaces.OrderConfirmationPage.TOTAL;

/**
 * Valores del resumen de la orden confirmada: subtotal, envío, total y el total recalculado
 * como subtotal más envío.
 */
public class OrderAmount implements Question<BigDecimal> {

    private final String subject;
    private final Target[] addends;

    public OrderAmount(String subject, Target... addends) {
        this.subject = subject;
        this.addends = addends;
    }

    @Override
    public BigDecimal answeredBy(Actor actor) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Target addend : addends) {
            amount = amount.add(PriceConverter.toAmount(addend.resolveFor(actor).waitUntilVisible().getText()));
        }
        return amount;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public static OrderAmount subtotal() {
        return new OrderAmount("the order subtotal", SUBTOTAL);
    }

    public static OrderAmount total() {
        return new OrderAmount("the order total", TOTAL);
    }

    public static OrderAmount subtotalPlusShipping() {
        return new OrderAmount("the order subtotal plus shipping", SUBTOTAL, SHIPPING);
    }
}
