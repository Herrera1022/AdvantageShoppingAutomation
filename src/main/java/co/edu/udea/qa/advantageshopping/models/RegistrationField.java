package co.edu.udea.qa.advantageshopping.models;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Campos del formulario de registro que tienen reglas de validación, identificados por el nombre de negocio usado en los features.
 * Cada campo conoce su nombre en el formulario y cómo reemplazar su valor en un cliente.
 */
public enum RegistrationField {

    USERNAME("nombre de usuario", "usernameRegisterPage",
            (customer, value) -> customer.toBuilder().username(value).build()),
    EMAIL("correo electrónico", "emailRegisterPage",
            (customer, value) -> customer.toBuilder().email(value).build()),
    PASSWORD("contraseña", "passwordRegisterPage",
            (customer, value) -> customer.toBuilder().password(value).passwordConfirmation(value).build()),
    PASSWORD_CONFIRMATION("confirmación de contraseña", "confirm_passwordRegisterPage",
            (customer, value) -> customer.toBuilder().passwordConfirmation(value).build());

    private final String label;
    private final String inputName;
    private final BiFunction<Customer, String, Customer> valueReplacer;

    RegistrationField(String label, String inputName, BiFunction<Customer, String, Customer> valueReplacer) {
        this.label = label;
        this.inputName = inputName;
        this.valueReplacer = valueReplacer;
    }

    public String getInputName() {
        return inputName;
    }

    public Customer applyTo(Customer customer, String value) {
        return valueReplacer.apply(customer, value);
    }

    public static RegistrationField fromLabel(String label) {
        return Arrays.stream(values())
                .filter(field -> field.label.equalsIgnoreCase(label.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown registration field: " + label));
    }
}
