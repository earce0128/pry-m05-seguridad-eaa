package mx.com.qtx.cotizadorv1ds;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
	
	// Permite el acceso de todo lo que esté autenticado
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((autorize) -> autorize.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	UserDetailsService crearGestorDeUsuarios() {
		
		UserDetails usuarioEdgar = User.withDefaultPasswordEncoder()
									   .username("Edgar")
									   .password("Sarale290310")
									   .roles("ADMIN","VTAS","SISTEMAS")
									   .build();
		
		UserDetails usuarioAlex = User.withDefaultPasswordEncoder()
									  .username("Alex")
									  .password("passAlex")
									  .roles("VTAS")
									  .build();
		
		UserDetails usuarioLulu = User.withDefaultPasswordEncoder()
									  .username("Lulu")
									  .password("passlulu")
									  .roles("SISTEMAS")
									  .build();
		
		InMemoryUserDetailsManager gestorUsuarios = new InMemoryUserDetailsManager(usuarioEdgar,
																				   usuarioAlex,
																				   usuarioLulu);
		
		return gestorUsuarios;
	}

}
