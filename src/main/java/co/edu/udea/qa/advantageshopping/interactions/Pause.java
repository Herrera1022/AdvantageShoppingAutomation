package co.edu.udea.qa.advantageshopping.interactions;

import co.edu.udea.qa.advantageshopping.utils.DemoSettings;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

/**
 * Pausa intencional para que el público pueda ver cada acción durante la demo en vivo.
 * La duración se configura con demo.delay.seconds (serenity.conf o -D); con 0 no hace nada.
 * Serenity la muestra en el reporte como "pause"; es el único Thread.sleep del proyecto.
 */
public class Pause implements Interaction {

    private static final long MILLIS_PER_SECOND = 1000L;

    @Override
    public <T extends Actor> void performAs(T actor) {
        waitTheConfiguredTime();
    }

    public static Pause forTheDemo() {
        return new Pause();
    }

    public static void waitTheConfiguredTime() {
        long seconds = DemoSettings.delayInSeconds();
        if (seconds == 0) {
            return;
        }
        try {
            Thread.sleep(seconds * MILLIS_PER_SECOND);
        } catch (InterruptedException interruption) {
            Thread.currentThread().interrupt();
        }
    }
}
