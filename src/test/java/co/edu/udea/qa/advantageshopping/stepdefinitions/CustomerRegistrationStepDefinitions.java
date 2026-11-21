package co.edu.udea.qa.advantageshopping.stepdefinitions;

import co.edu.udea.qa.advantageshopping.exceptions.RegistrationException;
import co.edu.udea.qa.advantageshopping.models.Customer;
import co.edu.udea.qa.advantageshopping.models.RegistrationField;
import co.edu.udea.qa.advantageshopping.questions.AccountCreation;
import co.edu.udea.qa.advantageshopping.questions.FieldValidationMessage;
import co.edu.udea.qa.advantageshopping.questions.LoggedInCustomer;
import co.edu.udea.qa.advantageshopping.questions.RegistrationAvailability;
import co.edu.udea.qa.advantageshopping.questions.RegistrationErrorMessage;
import co.edu.udea.qa.advantageshopping.tasks.ConfirmRegistration;
import co.edu.udea.qa.advantageshopping.tasks.FillRegistrationForm;
import co.edu.udea.qa.advantageshopping.tasks.OpenTheStore;
import co.edu.udea.qa.advantageshopping.tasks.RegisterCustomer;
import co.edu.udea.qa.advantageshopping.utils.CustomerFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.conditions.Check;

import static co.edu.udea.qa.advantageshopping.exceptions.RegistrationException.ACCOUNT_NOT_CREATED;
import static co.edu.udea.qa.advantageshopping.exceptions.RegistrationException.DUPLICATED_USERNAME_ACCEPTED;
import static co.edu.udea.qa.advantageshopping.exceptions.RegistrationException.INVALID_DATA_ACCEPTED;
import static co.edu.udea.qa.advantageshopping.exceptions.RegistrationException.WRONG_VALIDATION_MESSAGE;
import static co.edu.udea.qa.advantageshopping.utils.Constants.REGISTERED_CUSTOMER_MEMORY_KEY;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CustomerRegistrationStepDefinitions {

    private static final String USERNAME_LABEL = "nombre de usuario";

    private RegistrationField invalidField;
    private Customer existingCustomer;

    @Given("que {actor} ingresa a la tienda en línea")
    public void entersTheOnlineStore(Actor actor) {
        actor.wasAbleTo(OpenTheStore.catalog());
    }

    @When("se registra con datos personales válidos")
    public void registersWithValidPersonalData() {
        theActorInTheSpotlight().attemptsTo(RegisterCustomer.withData(CustomerFactory.aNewCustomer()));
    }

    @Then("su cuenta queda creada y ve su nombre de usuario en la tienda")
    public void seesTheAccountCreated() {
        Actor actor = theActorInTheSpotlight();
        Customer customer = actor.recall(REGISTERED_CUSTOMER_MEMORY_KEY);
        actor.should(seeThat(LoggedInCustomer.username(), equalTo(customer.getUsername()))
                .orComplainWith(RegistrationException.class, ACCOUNT_NOT_CREATED));
    }

    @When("intenta registrarse con {string} diligenciado como {string}")
    public void triesToRegisterWithAnInvalidValue(String fieldLabel, String value) {
        invalidField = RegistrationField.fromLabel(fieldLabel);
        Customer customer = invalidField.applyTo(CustomerFactory.aNewCustomer(), value);
        theActorInTheSpotlight().attemptsTo(FillRegistrationForm.with(customer));
    }

    @When("intenta registrarse con un nombre de usuario nuevo de {int} caracteres")
    public void triesToRegisterWithAUsernameOfLength(int length) {
        triesToRegisterWithAnInvalidValue(USERNAME_LABEL, CustomerFactory.uniqueUsernameOfLength(length));
    }

    @Then("ve el mensaje de validación {string}")
    public void seesTheValidationMessage(String expectedMessage) {
        theActorInTheSpotlight().should(seeThat(FieldValidationMessage.of(invalidField), equalTo(expectedMessage))
                .orComplainWith(RegistrationException.class, WRONG_VALIDATION_MESSAGE));
    }

    @Then("no puede crear la cuenta")
    public void cannotCreateTheAccount() {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(
                Check.whether(RegistrationAvailability.isEnabled()).andIfSo(ConfirmRegistration.ofTheAccount())
        );
        actor.should(seeThat(AccountCreation.wasAccepted(), is(false))
                .orComplainWith(RegistrationException.class, INVALID_DATA_ACCEPTED));
    }

    @Given("que {actor} ya tiene una cuenta registrada")
    public void anotherCustomerAlreadyHasAnAccount(Actor anotherCustomer) {
        existingCustomer = CustomerFactory.aNewCustomer();
        anotherCustomer.wasAbleTo(
                OpenTheStore.catalog(),
                RegisterCustomer.withData(existingCustomer)
        );
    }

    @When("{actor} intenta registrarse con el mismo nombre de usuario")
    public void triesToRegisterWithTheSameUsername(Actor actor) {
        Customer customerWithTakenUsername = CustomerFactory.aNewCustomer().toBuilder()
                .username(existingCustomer.getUsername())
                .build();
        actor.attemptsTo(
                FillRegistrationForm.with(customerWithTakenUsername),
                ConfirmRegistration.ofTheAccount()
        );
    }

    @Then("ve el mensaje de error {string}")
    public void seesTheRegistrationError(String expectedMessage) {
        theActorInTheSpotlight().should(seeThat(RegistrationErrorMessage.displayed(), equalTo(expectedMessage))
                .orComplainWith(RegistrationException.class, DUPLICATED_USERNAME_ACCEPTED));
    }
}
