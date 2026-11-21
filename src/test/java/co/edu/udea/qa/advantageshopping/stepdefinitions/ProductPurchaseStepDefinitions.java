package co.edu.udea.qa.advantageshopping.stepdefinitions;

import co.edu.udea.qa.advantageshopping.exceptions.PurchaseException;
import co.edu.udea.qa.advantageshopping.models.PaymentDetails;
import co.edu.udea.qa.advantageshopping.models.PaymentField;
import co.edu.udea.qa.advantageshopping.models.PaymentMethod;
import co.edu.udea.qa.advantageshopping.questions.ExpectedCartTotal;
import co.edu.udea.qa.advantageshopping.questions.OrderAmount;
import co.edu.udea.qa.advantageshopping.questions.OrderDetail;
import co.edu.udea.qa.advantageshopping.questions.PaymentAvailability;
import co.edu.udea.qa.advantageshopping.questions.PurchaseConfirmation;
import co.edu.udea.qa.advantageshopping.tasks.EnterPaymentDetails;
import co.edu.udea.qa.advantageshopping.tasks.PayOrder;
import co.edu.udea.qa.advantageshopping.tasks.ProceedToCheckout;
import co.edu.udea.qa.advantageshopping.utils.PaymentDetailsFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

import static co.edu.udea.qa.advantageshopping.exceptions.PurchaseException.INCOMPLETE_PAYMENT_ACCEPTED;
import static co.edu.udea.qa.advantageshopping.exceptions.PurchaseException.ORDER_NOT_CONFIRMED;
import static co.edu.udea.qa.advantageshopping.exceptions.PurchaseException.ORDER_WITHOUT_NUMBER;
import static co.edu.udea.qa.advantageshopping.exceptions.PurchaseException.WRONG_PAYMENT_METHOD;
import static co.edu.udea.qa.advantageshopping.exceptions.PurchaseException.WRONG_SUBTOTAL;
import static co.edu.udea.qa.advantageshopping.exceptions.PurchaseException.WRONG_TOTAL;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class ProductPurchaseStepDefinitions {

    private static final String PURCHASE_CONFIRMED_TEXT = "Thank you for buying with Advantage";
    private static final String NUMERIC_PATTERN = "\\d+";

    private PaymentMethod chosenPaymentMethod;

    @When("paga la orden con {string}")
    public void paysTheOrderWith(String paymentMethodName) {
        chosenPaymentMethod = PaymentMethod.fromName(paymentMethodName);
        theActorInTheSpotlight().attemptsTo(
                ProceedToCheckout.withTheRegisteredShippingDetails(),
                PayOrder.using(PaymentDetailsFactory.validDetailsFor(chosenPaymentMethod))
        );
    }

    @When("intenta pagar con {string} sin diligenciar {string}")
    public void triesToPayWithoutRequiredData(String paymentMethodName, String missingData) {
        chosenPaymentMethod = PaymentMethod.fromName(paymentMethodName);
        PaymentDetails incompleteDetails = PaymentField.fromLabel(missingData)
                .leaveEmptyIn(PaymentDetailsFactory.validDetailsFor(chosenPaymentMethod));
        theActorInTheSpotlight().attemptsTo(
                ProceedToCheckout.withTheRegisteredShippingDetails(),
                EnterPaymentDetails.using(incompleteDetails)
        );
    }

    @Then("la compra queda confirmada con un número de orden")
    public void thePurchaseIsConfirmed() {
        theActorInTheSpotlight().should(
                seeThat(PurchaseConfirmation.message(), equalTo(PURCHASE_CONFIRMED_TEXT))
                        .orComplainWith(PurchaseException.class, ORDER_NOT_CONFIRMED),
                seeThat(OrderDetail.orderNumber(), matchesPattern(NUMERIC_PATTERN))
                        .orComplainWith(PurchaseException.class, ORDER_WITHOUT_NUMBER)
        );
    }

    @Then("la orden queda pagada con {string}")
    public void theOrderIsPaidWith(String paymentMethodName) {
        theActorInTheSpotlight().should(seeThat(OrderDetail.paymentMethod(), containsString(paymentMethodName))
                .orComplainWith(PurchaseException.class, WRONG_PAYMENT_METHOD));
    }

    @Then("el subtotal de la orden corresponde a los productos del carrito")
    public void theSubtotalMatchesTheCartProducts() {
        Actor actor = theActorInTheSpotlight();
        actor.should(seeThat(OrderAmount.subtotal(),
                comparesEqualTo(actor.asksFor(ExpectedCartTotal.ofTheAddedProducts())))
                .orComplainWith(PurchaseException.class, WRONG_SUBTOTAL));
    }

    @Then("el total pagado es el subtotal más el costo de envío")
    public void theTotalIsSubtotalPlusShipping() {
        Actor actor = theActorInTheSpotlight();
        actor.should(seeThat(OrderAmount.total(), comparesEqualTo(actor.asksFor(OrderAmount.subtotalPlusShipping())))
                .orComplainWith(PurchaseException.class, WRONG_TOTAL));
    }

    @Then("no puede confirmar el pago de la orden")
    public void cannotConfirmThePayment() {
        theActorInTheSpotlight().should(seeThat(PaymentAvailability.isEnabledFor(chosenPaymentMethod), is(false))
                .orComplainWith(PurchaseException.class, INCOMPLETE_PAYMENT_ACCEPTED));
    }
}
