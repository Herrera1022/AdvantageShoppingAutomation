package co.edu.udea.qa.advantageshopping.models;

import lombok.Builder;
import lombok.Getter;

/**
 * Datos personales y de cuenta que usa un cliente para crear una cuenta en la tienda.
 */
@Getter
@Builder(toBuilder = true)
public class Customer {

    private final String username;
    private final String email;
    private final String password;
    private final String passwordConfirmation;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String country;
    private final String city;
    private final String address;
    private final String state;
    private final String postalCode;
}
