package es.fempa;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AuthService {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    // Crea el usuario de prueba disponible al iniciar la aplicación.
    public AuthService() {
        users.put("admin", new User("Administrador", "admin", "1234"));
    }

    // Comprueba si las credenciales pertenecen a un usuario registrado.
    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        return user != null && user.password().equals(password);
    }

    // Guarda un usuario nuevo si su correo todavía no está registrado.
    public boolean register(String name, String email, String password) {
        String username = email.trim().toLowerCase();
        return users.putIfAbsent(username, new User(name.trim(), username, password)) == null;
    }

    // Agrupa los datos que se guardan de cada usuario.
    public record User(String name, String username, String password) {
    }
}
