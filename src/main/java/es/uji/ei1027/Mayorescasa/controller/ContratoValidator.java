package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Contrato;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContratoValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Contrato.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Contrato.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contrato contrato = (Contrato)obj;
        if (contrato.getEmpresa().trim().equals(""))
            errors.rejectValue("empresa", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getCod_pet().trim().equals(""))
            errors.rejectValue("cod_pet", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getcodcontrato().trim().equals(""))
            errors.rejectValue("codcontrato", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getcantidadservicios()<1)
            errors.rejectValue("cantidadservicios", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getpreciounidad()<1.0)
            errors.rejectValue("preciounidad", "obligatorio",
                    " Hay que introducir un valor");



    }
}
