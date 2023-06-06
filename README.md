# JWT
API authentication with Spring Security and JWT

### Versiones de librerias implementadas en Junio del 2023

- Spring Boot : 3.1.0
- Spring Security : 6.1.0
- JJWT : 0.11.5
- Nimbus-jose-jwt : 9.31

### Explicación

La clase ***JWTAuthorizationFilter*** es un filtro de autorización de Spring que se encarga de validar y procesar el token JWT en cada solicitud entrante. Aquí tienes una explicación de cada método y su función:

***validateToken(HttpServletRequest request):*** Este método se utiliza para validar el token JWT incluido en la solicitud. Extrae el token del encabezado de autorización de la solicitud, elimina el prefijo "Bearer " y utiliza la clave secreta para verificar la firma del token. Devuelve los claims (reclamaciones) del token JWT si la validación es exitosa.

***setUpSpringAuthentication(Claims claims):*** Este método se utiliza para establecer la autenticación en el flujo de Spring. Recibe los claims del token JWT y crea una instancia de UsernamePasswordAuthenticationToken con el sujeto del token (usuario) y las autoridades obtenidas del token. Luego, establece esta autenticación en el contexto de seguridad de Spring utilizando SecurityContextHolder.

***checkJWTToken(HttpServletRequest request, HttpServletResponse res):*** Este método verifica si el token JWT está presente en el encabezado de autorización de la solicitud y si tiene el prefijo correcto ("Bearer "). Retorna true si el token es válido, de lo contrario, retorna false.

***doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain):*** Este método es la implementación principal del filtro. Se ejecuta en cada solicitud entrante. Primero verifica si el token JWT es válido y luego procesa la autenticación de Spring utilizando los claims del token. Si la validación es exitosa, establece la autenticación en el contexto de seguridad de Spring. Si la validación falla, se limpia el contexto de seguridad. Luego, pasa la solicitud y la respuesta al siguiente filtro en la cadena (filterChain.doFilter()) para continuar con el procesamiento de la solicitud.

-----------

La clase ***JWTAuthtenticationConfig*** genera un token JWT para un usuario autenticado utilizando una clave secreta, el nombre de usuario, las autoridades y otros atributos relevantes. El token generado puede ser utilizado para la autenticación y autorización en las solicitudes posteriores.

Recuerda que la seguridad de los tokens JWT depende de la adecuada protección de la clave secreta, así como de otras medidas de seguridad en tu aplicación, como la validación adecuada del token en el lado del servidor y la protección contra ataques de falsificación de solicitudes entre sitios (CSRF), entre otros.

### Adicional

El método ***JwkGenerator()*** genera un JWK utilizando una clave secreta y lo convierte a formato JSON. El JWK resultante puede ser utilizado para la autenticación y la generación/validación de tokens JWT en una aplicación que implementa JSON Web Tokens (JWT). 

-----------

### Excluding the autoconfiguration class
To prevent this user from being auto-configured, we can exclude the autoconfiguration class, which means Spring Security won't set up, and log, the default user.

We can do this on our main application class:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@Configuration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Or via a properties file:
```
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
```