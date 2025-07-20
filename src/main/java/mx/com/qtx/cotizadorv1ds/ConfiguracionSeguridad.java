package mx.com.qtx.cotizadorv1ds;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
	
	// Permite el acceso de todo lo que esté autenticado
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		
//		http.authorizeHttpRequests((autorize) -> autorize.anyRequest().authenticated())
//			.httpBasic(Customizer.withDefaults())
//			.formLogin(Customizer.withDefaults());
//		
//		return http.build();
//	}
	
	// Permite configurar las reglas de autenticación
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((autorizador) -> autorizador
			.requestMatchers("/css/**").permitAll()
			.requestMatchers("vistaComodin.html").permitAll()
			.requestMatchers("/infoGeneral","vistaInformacion.html").permitAll()
			.requestMatchers("/api/cot/**").hasRole("VTAS")
			.requestMatchers("/buscarCotizacion","vistaBuscarCotizacion").hasRole("VTAS")
			.requestMatchers("/buscarCompPorCat").hasAnyRole("SISTEMAS","ADMIN")
			.requestMatchers("/altaUsuario").hasRole("ADMIN")
			.requestMatchers("/**").authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());
		
		return http.build();
	}
	
	//Servicio de control de usuarios en memoria.
//	@Bean
//	UserDetailsService crearGestorDeUsuarios() {
//		
//		UserDetails usuarioEdgar = User.withDefaultPasswordEncoder()
//									   .username("Edgar")
//									   .password("passEdgar")
//									   .roles("ADMIN","SISTEMAS")
//									   .build();
//		
//		UserDetails usuarioAlex = User.withDefaultPasswordEncoder()
//									  .username("Alex")
//									  .password("passAlex")
//									  .roles("VTAS")
//									  .build();
//		
//		UserDetails usuarioLulu = User.withDefaultPasswordEncoder()
//									  .username("Lulu")
//									  .password("passlulu")
//									  .roles("SISTEMAS")
//									  .build();
//		
//		InMemoryUserDetailsManager gestorUsuarios = new InMemoryUserDetailsManager(usuarioEdgar,
//																				   usuarioAlex,
//																				   usuarioLulu);
//		
//		return gestorUsuarios;
//	}
	
	//Servicio de control de usuarios para bd por esquema default de spring security.
	@Bean
	UserDetailsManager getGestorBdSegUsuarios(DataSource dataSource) {
		
		UserDetails usuarioEdgar = User.withDefaultPasswordEncoder()
				   .username("Edgar")
				   .password("passEdgar")
				   .roles("ADMIN","SISTEMAS")
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
		
		// Es el componente que Spring recominda para usar su 
		JdbcUserDetailsManager gestorUsuariosBdSeg = new JdbcUserDetailsManager(dataSource);
		
		if(gestorUsuariosBdSeg.userExists(usuarioEdgar.getUsername()) == false)
			gestorUsuariosBdSeg.createUser(usuarioEdgar);
		if(gestorUsuariosBdSeg.userExists(usuarioAlex.getUsername()) == false)
			gestorUsuariosBdSeg.createUser(usuarioAlex);
		if(gestorUsuariosBdSeg.userExists(usuarioLulu.getUsername()) == false)
			gestorUsuariosBdSeg.createUser(usuarioLulu);
		
		return gestorUsuariosBdSeg;
	}

}
