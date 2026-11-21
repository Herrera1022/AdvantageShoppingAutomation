package co.edu.udea.qa.advantageshopping.tasks;

import co.edu.udea.qa.advantageshopping.interactions.ClickOn;
import co.edu.udea.qa.advantageshopping.models.Customer;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.USER_MENU;
import static co.edu.udea.qa.advantageshopping.userinterfaces.LoginModal.CREATE_NEW_ACCOUNT;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.ADDRESS;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.CITY;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.COUNTRY;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.EMAIL;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.FIRST_NAME;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.LAST_NAME;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.PASSWORD;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.PASSWORD_CONFIRMATION;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.PHONE_NUMBER;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.POSTAL_CODE;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.STATE;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.TERMS_AGREEMENT;
import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.USERNAME;
import static co.edu.udea.qa.advantageshopping.utils.Constants.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Abre el formulario de creación de cuenta desde el menú de usuario y llena los datos del cliente,
 * aceptando las condiciones de uso. No envía el formulario.
 */
public class FillRegistrationForm implements Task {

    private final Customer customer;
    private final String username;

    public FillRegistrationForm(Customer customer) {
        this.customer = customer;
        this.username = customer.getUsername();
    }

    @Override
    @Step("{0} fills in the account form with the username #username")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickOn.the(USER_MENU),
                ClickOn.the(CREATE_NEW_ACCOUNT),
                WaitUntil.the(USERNAME, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Enter.theValue(customer.getUsername()).into(USERNAME),
                Enter.theValue(customer.getEmail()).into(EMAIL),
                Enter.theValue(customer.getPassword()).into(PASSWORD),
                Enter.theValue(customer.getPasswordConfirmation()).into(PASSWORD_CONFIRMATION),
                Enter.theValue(customer.getFirstName()).into(FIRST_NAME),
                Enter.theValue(customer.getLastName()).into(LAST_NAME),
                Enter.theValue(customer.getPhoneNumber()).into(PHONE_NUMBER),
                SelectFromOptions.byVisibleText(customer.getCountry()).from(COUNTRY),
                Enter.theValue(customer.getCity()).into(CITY),
                Enter.theValue(customer.getAddress()).into(ADDRESS),
                Enter.theValue(customer.getState()).into(STATE),
                Enter.theValue(customer.getPostalCode()).into(POSTAL_CODE),
                ClickOn.the(TERMS_AGREEMENT)
        );
    }

    public static FillRegistrationForm with(Customer customer) {
        return instrumented(FillRegistrationForm.class, customer);
    }
}
