package co.edu.udea.qa.advantageshopping.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import static co.edu.udea.qa.advantageshopping.userinterfaces.HomePage.LOADER;

/**
 * La tienda muestra una capa de carga mientras habla con sus servicios; las acciones sobre la página
 * solo son confiables cuando esa capa desaparece.
 */
public class WaitForLoader implements Interaction {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(WaitUntilHidden.the(LOADER));
    }

    public static WaitForLoader toFinish() {
        return new WaitForLoader();
    }
}
