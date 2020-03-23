package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class ValidadorBeneficiario implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Beneficiario.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Beneficiario beneficiario = (Beneficiario) obj;
        if (beneficiario.getNombre().trim().equals(""))
            errors.rejectValue("nom", "obligatori",
                    "Cal introduir un valor");

        // Afegeix ací la validació per a Edat > 15 anys
        if(beneficiario.getEdad()<=60)
            errors.rejectValue("edat","obligatori","Cal ser major de 60 anys per a participar");

        List<String> valors = Arrays.asList("Femeni", "Masculi");
        if (!((List) valors).contains(beneficiario.getGenero()))
            errors.rejectValue("genere", "valor incorrecte",
                    "Deu ser: Femeni o Masculi");

    }
}
