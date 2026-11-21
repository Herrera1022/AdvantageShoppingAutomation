package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.Pause;
import co.edu.udea.qa.advantageshopping.models.Customer;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.LOGGED_USERNAME;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static co.edu.udea.qa.advantageshopping.utils.Constants.REGISTERED_CUSTOMER_MEMORY_KEY;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Crea una cuenta de cliente nueva. Después de un registro exitoso la tienda inicia la sesión del cliente,
 * por eso la tarea termina cuando la tienda muestra el nombre de usuario del cliente.
 */
public class RegisterCustomer implements Task {

    private final Customer customer;
    private final String username;

    public RegisterCustomer(Customer customer) {
        this.customer = customer;
        this.username = customer.getUsername();
    }

    @Override
    @Step("{0} registers a new account with the username #username")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                FillRegistrationForm.with(customer),
                Pause.forTheDemo(),
                ConfirmRegistration.ofTheAccount(),
                WaitUntil.the(LOGGED_USERNAME, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
        actor.remember(REGISTERED_CUSTOMER_MEMORY_KEY, customer);
    }

    public static RegisterCustomer withData(Customer customer) {
        return instrumented(RegisterCustomer.class, customer);
    }
}
