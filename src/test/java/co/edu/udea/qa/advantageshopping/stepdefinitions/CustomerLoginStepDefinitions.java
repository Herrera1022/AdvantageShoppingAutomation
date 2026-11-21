package co.edu.udea.qa.advantageshopping.stepdefinitions;

import co.edu.udea.qa.advantageshopping.exceptions.LoginException;
import co.edu.udea.qa.advantageshopping.models.Customer;
import co.edu.udea.qa.advantageshopping.questions.LoggedInCustomer;
import co.edu.udea.qa.advantageshopping.questions.LoginErrorMessage;
import co.edu.udea.qa.advantageshopping.tasks.LogIn;
import co.edu.udea.qa.advantageshopping.tasks.RegisterCustomer;
import co.edu.udea.qa.advantageshopping.tasks.SignOut;
import co.edu.udea.qa.advantageshopping.utils.CustomerFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

import static co.edu.udea.qa.advantageshopping.exceptions.LoginException.CUSTOMER_NOT_SIGNED_IN;
import static co.edu.udea.qa.advantageshopping.exceptions.LoginException.INVALID_CREDENTIALS_ACCEPTED;
import static co.edu.udea.qa.advantageshopping.utils.Constants.REGISTERED_CUSTOMER_MEMORY_KEY;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class CustomerLoginStepDefinitions {

    @Given("que {actor} tiene una cuenta registrada y cerró su sesión")
    public void hasARegisteredAccountAndSignedOut(Actor actor) {
        actor.wasAbleTo(
                RegisterCustomer.withData(CustomerFactory.aNewCustomer()),
                SignOut.ofTheStore()
        );
    }

    @When("inicia sesión con sus credenciales")
    public void signsInWithTheirCredentials() {
        Actor actor = theActorInTheSpotlight();
        Customer customer = actor.recall(REGISTERED_CUSTOMER_MEMORY_KEY);
        actor.attemptsTo(LogIn.withCredentials(customer.getUsername(), customer.getPassword()));
    }

    @When("inicia sesión con el usuario {string} y la contraseña {string}")
    public void signsInWith(String username, String password) {
        theActorInTheSpotlight().attemptsTo(LogIn.withCredentials(username, password));
    }

    @Then("ve su nombre de usuario en la tienda")
    public void seesTheirUsername() {
        Actor actor = theActorInTheSpotlight();
        Customer customer = actor.recall(REGISTERED_CUSTOMER_MEMORY_KEY);
        actor.should(seeThat(LoggedInCustomer.username(), equalTo(customer.getUsername()))
                .orComplainWith(LoginException.class, CUSTOMER_NOT_SIGNED_IN));
    }

    @Then("ve el mensaje de error de inicio de sesión {string}")
    public void seesTheSignInError(String expectedMessage) {
        theActorInTheSpotlight().should(seeThat(LoginErrorMessage.displayed(), equalTo(expectedMessage))
                .orComplainWith(LoginException.class, INVALID_CREDENTIALS_ACCEPTED));
    }
}
