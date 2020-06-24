package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.ContratoDao;
import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Empresa;
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
        // o, si volguérem tractar també les subclasses:
        // return Contrato.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contrato contrato = (Contrato)obj;
        String emp=contrato.getEmpresa();
        System.out.println("Empresaaaaa "+emp);



        if (contrato.getEmpresa().trim().equals(""))
            errors.rejectValue("empresa", "obligatorio",
                    " Hay que introducir un valor");

        try{
            Empresa empresa=empresaDao.getEmpresa(contrato.getEmpresa());
            if(!contrato.getTiposervicio().equals(empresa.getTiposervicio())){
                errors.rejectValue("tiposervicio", "obligatorio",
                        "No coinciden los tipos de servicios");
            }
        }catch (NullPointerException e){

        }

        try{
            Contrato contratoExistente=contratoDao.getContratoE(emp);
            if(contratoExistente!=null)
                errors.rejectValue("empresa", "obligatorio",
                        "Ya existe un contrato para esa empresa") ;
        }catch (NullPointerException e){

        }

    }
}
