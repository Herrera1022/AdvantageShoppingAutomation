package co.edu.udea.qa.advantageshopping.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Escribe un valor y vuelve a leer el campo; lo escribe otra vez mientras la tienda no lo haya guardado completo.
 * Después de reformatear el número de tarjeta, la tienda a veces pierde la primera tecla del siguiente campo
 * (el CVV "123" queda como "23").
 */
public class EnterAndConfirm implements Interaction {

    private static final int MAX_ATTEMPTS = 3;

    private final String value;
    private final Target target;

    public EnterAndConfirm(String value, Target target) {
        this.value = value;
        this.target = target;
    }

    @Override
    @Step("{0} enters #value into the #target and checks it was kept")
    public <T extends Actor> void performAs(T actor) {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS && !value.equals(currentValueFor(actor)); attempt++) {
            actor.attemptsTo(Enter.theValue(value).into(target));
        }
    }

    private String currentValueFor(Actor actor) {
        return target.resolveFor(actor).getValue();
    }

    public static Builder theValue(String value) {
        return new Builder(value);
    }

    public static class Builder {

        private final String value;

        private Builder(String value) {
            this.value = value;
        }

        public EnterAndConfirm into(Target target) {
            return instrumented(EnterAndConfirm.class, value, target);
        }
    }
}
