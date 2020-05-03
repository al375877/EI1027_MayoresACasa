package es.uji.ei1027.Mayorescasa.dao;




import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.Mayorescasa.model.UserDetails;

@Repository
public class FakeUserProvider implements UserDao {
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

    public FakeUserProvider() {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        UserDetails userTeresa = new UserDetails();
        userTeresa.setUsername("tere");
        userTeresa.setPassword(passwordEncryptor.encryptPassword("tere"));
        userTeresa.setAutorizado("admin");
        userTeresa.setDni("45125874P");
        knownUsers.put("tere", userTeresa);

        UserDetails userJesus = new UserDetails();
        userJesus.setUsername("jesus");
        userJesus.setPassword(passwordEncryptor.encryptPassword("jesus"));
        userJesus.setAutorizado("ben");
        userJesus.setDni("54781268Q");
        knownUsers.put("jesus", userJesus);
    }

    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        UserDetails user = knownUsers.get(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return user;
        }
        else {
            return null; // bad login!
        }
    }

    @Override
    public Collection<UserDetails> listAllUsers() {
        return knownUsers.values();
    }
}
