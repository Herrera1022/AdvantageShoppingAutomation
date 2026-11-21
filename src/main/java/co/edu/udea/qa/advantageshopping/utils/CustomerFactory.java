package co.edu.udea.qa.advantageshopping.utils;

import co.edu.udea.qa.advantageshopping.models.Customer;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Construye clientes con datos válidos. El nombre de usuario es único en cada ejecución porque la tienda
 * no permite dos cuentas con el mismo nombre de usuario (máximo 15 caracteres).
 */
public final class CustomerFactory {

    private static final String VALID_PASSWORD = "Udea2026";

    private CustomerFactory() {
    }

    public static Customer aNewCustomer() {
        String username = uniqueUsername();
        return Customer.builder()
                .username(username)
                .email(username + "@udea.edu.co")
                .password(VALID_PASSWORD)
                .passwordConfirmation(VALID_PASSWORD)
                .firstName("Ana")
                .lastName("Restrepo")
                .phoneNumber("3001234567")
                .country("Colombia")
                .city("Medellin")
                .address("Calle 67 53-108")
                .state("Antioquia")
                .postalCode("050010")
                .build();
    }

    /**
     * Nombre de usuario nunca usado, con exactamente la cantidad de caracteres pedida; sirve para probar los límites de longitud.
     */
    public static String uniqueUsernameOfLength(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder username = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int position = 0; position < length; position++) {
            username.append(letters.charAt(random.nextInt(letters.length())));
        }
        return username.toString();
    }

    private static String uniqueUsername() {
        long suffix = System.currentTimeMillis() % 100_000_000L + ThreadLocalRandom.current().nextInt(1000);
        return "ana" + suffix;
    }
}
