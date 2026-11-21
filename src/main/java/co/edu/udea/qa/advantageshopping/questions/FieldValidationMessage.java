package co.edu.udea.qa.advantageshopping.questions;

import co.edu.udea.qa.advantageshopping.models.RegistrationField;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.edu.udea.qa.advantageshopping.userinterfaces.RegisterPage.FIELD_VALIDATION_MESSAGE;

public class FieldValidationMessage implements Question<String> {

    private final RegistrationField field;

    public FieldValidationMessage(RegistrationField field) {
        this.field = field;
    }

    @Override
    public String answeredBy(Actor actor) {
        return FIELD_VALIDATION_MESSAGE.of(field.getInputName()).resolveFor(actor).waitUntilVisible().getText().trim();
    }

    @Override
    public String getSubject() {
        return "the validation message of the field " + field.name().toLowerCase();
    }

    public static FieldValidationMessage of(RegistrationField field) {
        return new FieldValidationMessage(field);
    }
}
