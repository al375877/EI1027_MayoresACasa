package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.ContratoDao;
import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContratoValidator implements Validator {

    @Autowired
    EmpresaDao empresaDao;
    @Autowired
    ContratoDao contratoDao;

    @Override
    public boolean supports(Class<?> cls) {
        return Contrato.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contrato contrato = (Contrato)obj;
        String emp=contrato.getEmpresa();

        if (contrato.getEmpresa().trim().equals(""))
            errors.rejectValue("empresa", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getTiposervicio().trim().equals(""))
            errors.rejectValue("tiposervicio", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getFechainicial()==null)
            errors.rejectValue("fechainicial", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getFechafinal()==null)
            errors.rejectValue("fechafinal", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getFechafinal()!=null && contrato.getFechafinal().compareTo(contrato.getFechainicial())<0)
            errors.rejectValue("fechafinal", "menor",
                    " La fecha final debe ser mayor que la inicial");
        if (contrato.getDias_semana().trim().equals(""))
            errors.rejectValue("dias_semana", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getHorainicial()==null)
            errors.rejectValue("horainicial", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getHorafinal()==null)
            errors.rejectValue("horafinal", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getHorafinal()!=null && contrato.getHorafinal().compareTo(contrato.getHorainicial())<0)
            errors.rejectValue("horafinal", "menor",
                    " La hora final debe ser mas tarde que la inicial");
        if (contrato.getPreciounidad()<=0)
            errors.rejectValue("preciounidad", "obligatorio",
                    " Hay que introducir un valor correcto");


    }
}
