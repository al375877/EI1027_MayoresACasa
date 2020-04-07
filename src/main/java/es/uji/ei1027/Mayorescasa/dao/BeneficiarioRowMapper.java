package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class BeneficiarioRowMapper implements RowMapper<Beneficiario> {
    public Beneficiario mapRow(ResultSet rs, int RowNum) throws SQLException{
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setNombre(rs.getString("nombre"));
        beneficiario.setDni(rs.getString("dni"));
        beneficiario.setGenero(rs.getString("genero"));
        beneficiario.setUsuario(rs.getString("usuario"));
        beneficiario.setContraseña(rs.getString("contraseña"));
        beneficiario.setEmail(rs.getString("email"));
        beneficiario.setDireccion(rs.getString("direccion"));
        beneficiario.setUsuario_asis(rs.getString("usuario_asis"));
        beneficiario.setTipoDieta(rs.getString("tipoDieta"));
        beneficiario.setEdad(rs.getInt("edad"));
        beneficiario.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        beneficiario.setNumero_telefono(rs.getInt("telefono"));

        return beneficiario;

    }

}
