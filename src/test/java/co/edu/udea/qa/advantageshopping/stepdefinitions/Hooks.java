package co.edu.udea.qa.advantageshopping.stepdefinitions;

import co.edu.udea.qa.advantageshopping.interactions.Pause;
import co.edu.udea.qa.advantageshopping.models.Product;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.util.Map;

public class Hooks {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    /**
     * Cierra los navegadores de los actores para que cada escenario empiece sin la sesión ni el carrito del anterior.
     */
    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }

    /**
     * Pausa entre los pasos de Gherkin para poder seguir la ejecución en vivo (demo.delay.seconds).
     */
    @AfterStep
    public void pauseBetweenSteps() {
        Pause.waitTheConfiguredTime();
    }

    @ParameterType("[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+")
    public Actor actor(String name) {
        return OnStage.theActorCalled(name);
    }

    @DataTableType
    public Product product(Map<String, String> row) {
        return new Product(row.get("categoria"), row.get("producto"), Integer.parseInt(row.get("cantidad")));
    }
}
