package co.edu.udea.qa.advantageshopping.utils;

import net.thucydides.model.environment.SystemEnvironmentVariables;

import static co.edu.udea.qa.advantageshopping.utils.Constants.DEMO_DELAY_PROPERTY;

/**
 * Lee la pausa que se usa entre pasos para poder seguir la ejecución durante la demo en vivo.
 * El valor sale de serenity.conf y se puede cambiar con -Ddemo.delay.seconds=N (0 la desactiva).
 */
public final class DemoSettings {

    private DemoSettings() {
    }

    public static long delayInSeconds() {
        String configuredDelay = SystemEnvironmentVariables.currentEnvironmentVariables()
                .getProperty(DEMO_DELAY_PROPERTY, "0");
        try {
            return Math.max(0, Long.parseLong(configuredDelay.trim()));
        } catch (NumberFormatException invalidValue) {
            return 0;
        }
    }
}
