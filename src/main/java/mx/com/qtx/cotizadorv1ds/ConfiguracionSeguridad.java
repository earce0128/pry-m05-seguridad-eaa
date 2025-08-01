package mx.com.qtx.cotizadorv1ds;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.zaxxer.hikari.HikariDataSource;

import mx.com.qtx.cotizadorv1ds.seguridad.web.FiltroTokensJwt_SS;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
	
	private static Logger bitacora = LoggerFactory.getLogger(ConfiguracionSeguridad.class);
	
	@Bean
	@Order(1)	
	SecurityFilterChain getSecurityFilterChainApiWeb(HttpSecurity http, FiltroTokensJwt_SS filtroJWT)			             
			            		throws Exception {
		
		http.securityMatchers(config -> config.requestMatchers("/api/**","/api/autenticacion"))
			.authorizeHttpRequests((authorize) ->  authorize
			     .requestMatchers("/api/autenticacion").permitAll()
			     .requestMatchers("/api/cot/**").hasRole("VTAS")
			     .requestMatchers("/api/comp/**").hasAnyRole("ADMIN","SISTEMAS")
			)
			.csrf(config -> config.disable()) // Se deshabilita
			.formLogin(config -> config.disable()) // Se deshabilita
			.logout(config -> config.disable()) // Se deshabilita
		  	.addFilterBefore(filtroJWT, UsernamePasswordAuthenticationFilter.class)
		  	.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
	
	@Bean
	@Order(2)	
	SecurityFilterChain getSecurityFilterChainMvc(HttpSecurity http) 
		    throws Exception {
		
		RequestMatcher urisXatender = this.buildRequestMatcherTodosExcepto("/api/**", "/api/autenticacion");
		
		http.securityMatcher(urisXatender)
			.authorizeHttpRequests((authorize) ->  authorize
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/vistaComodin.html").permitAll()
				.requestMatchers("/infoGeneral","/vistaInformacion.html").permitAll()
				.requestMatchers("/buscarCotizacion","/vistaBuscarCotizacion").hasRole("VTAS")
				.requestMatchers("/buscarCompPorCat","/vistaBuscarCompPorCat").hasAnyRole("SISTEMAS","ADMIN")
				.requestMatchers("/altaUsuario").hasRole("ADMIN")
			    .requestMatchers("/**").authenticated()
			)
			.csrf(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults())
			.logout(config -> config.invalidateHttpSession(true))
			.sessionManagement(config -> config.maximumSessions(1));

		return http.build();
	}
	
	// Usada para quitar las rutas de /api y /autenticacion
	private RequestMatcher buildRequestMatcherTodosExcepto(String urlExcluida1, String urlExcluida2) {
		OrRequestMatcher orRequestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(urlExcluida1), 
				                                                 new AntPathRequestMatcher(urlExcluida2)); 
		RequestMatcher patronUrlsXatender = new NegatedRequestMatcher(orRequestMatcher);
		return patronUrlsXatender;
	}
	
	// Utiliza un esquema personalizado de BD con JDBC
	@Bean
	UserDetailsService getGestorBdUsuariosPersonalizada(DataSource dataSource) {

		bitacora.trace("getGestorBdUsuariosPersonalizada()");
		HikariDataSource hds = (HikariDataSource) dataSource;
		bitacora.debug("Se ha instanciado data source mysql que apunta a BD:" + hds.getJdbcUrl());

		// Se usa una BD Personalizada. Ya debe contener los datos de usuarios y roles
		final String QUERY_DATOS_USUARIO_X_NOMBRE = "SELECT usr_nombre, usr_paswd, usr_habilitado "
				+ "FROM usuario WHERE usr_nombre = ?";

		// Los roles deben estar escritos en los registros de la base de datos con el
		// prefijo "ROLE_"
		// Por ejemplo, ROLE_AGENTE o ROLE_LOGISTICA
		final String QUERY_ROLES_X_USUARIO = "SELECT usr_nombre, aut_nombre " + "FROM usuario, autoridad "
				+ "WHERE usr_nombre = ? " + "AND usr_nombre = aut_nombre_usr";

		JdbcDaoImpl gestorBdUsuariosPersonalizada = new JdbcDaoImpl();
		gestorBdUsuariosPersonalizada.setDataSource(dataSource);
		gestorBdUsuariosPersonalizada.setUsersByUsernameQuery(QUERY_DATOS_USUARIO_X_NOMBRE);
		gestorBdUsuariosPersonalizada.setAuthoritiesByUsernameQuery(QUERY_ROLES_X_USUARIO);

		return gestorBdUsuariosPersonalizada;
	}
	
	@Bean
    AuthenticationManager publicarAuthenticationManagerDesdeConfiguracion(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	bitacora.trace("publicarAuthenticationManagerDesdeConfiguracion()");
    	AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
    	bitacora.debug("authenticationManager instanciado:" + authenticationManager.getClass().getName());
        return authenticationManager;
    }
	
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
	//@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		
//		http.authorizeHttpRequests((autorizador) -> autorizador
//			.requestMatchers("/api/autenticacion").permitAll()
//			.requestMatchers("/css/**").permitAll()
//			.requestMatchers("/vistaComodin.html").permitAll()
//			.requestMatchers("/infoGeneral","/vistaInformacion.html").permitAll()
//			.requestMatchers("/api/**").hasRole("VTAS")
//			.requestMatchers("/buscarCotizacion","/vistaBuscarCotizacion").hasRole("VTAS")
//			.requestMatchers("/buscarCompPorCat").hasAnyRole("SISTEMAS","ADMIN")
//			.requestMatchers("/altaUsuario").hasRole("ADMIN")
//			.requestMatchers("/**").authenticated()
//			)
//			.csrf(config -> config.ignoringRequestMatchers("/api/**"))
//			.httpBasic(Customizer.withDefaults())
//			.formLogin(Customizer.withDefaults());
//		
//		return http.build();
//	}
	
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
//	@Bean
//	UserDetailsManager getGestorBdSegUsuarios(DataSource dataSource) {
//		
//		UserDetails usuarioEdgar = User.withDefaultPasswordEncoder()
//				   .username("Edgar")
//				   .password("passEdgar")
//				   .roles("ADMIN","SISTEMAS")
//				   .build();
//
//		UserDetails usuarioAlex = User.withDefaultPasswordEncoder()
//				  .username("Alex")
//				  .password("passAlex")
//				  .roles("VTAS")
//				  .build();
//
//		UserDetails usuarioLulu = User.withDefaultPasswordEncoder()
//				  .username("Lulu")
//				  .password("passlulu")
//				  .roles("SISTEMAS")
//				  .build();
//		
//		// Es el componente que Spring recominda para usar su 
//		JdbcUserDetailsManager gestorUsuariosBdSeg = new JdbcUserDetailsManager(dataSource);
//		
//		if(gestorUsuariosBdSeg.userExists(usuarioEdgar.getUsername()) == false)
//			gestorUsuariosBdSeg.createUser(usuarioEdgar);
//		if(gestorUsuariosBdSeg.userExists(usuarioAlex.getUsername()) == false)
//			gestorUsuariosBdSeg.createUser(usuarioAlex);
//		if(gestorUsuariosBdSeg.userExists(usuarioLulu.getUsername()) == false)
//			gestorUsuariosBdSeg.createUser(usuarioLulu);
//		
//		return gestorUsuariosBdSeg;
//	}
	
	//Servicio de control de usuarios para bd por esquema default de spring security con password encriptado HASH.
//	@Bean
//	UserDetailsManager getGestorBdSegUsuarios(DataSource dataSource) {
//		
//		UserDetails usuarioEdgar = User.withUsername("Edgar")
//				   .password("{bcrypt}$2a$10$gLC1y0SA1z64M9xxG72KL.jefI8nIqAVnudfhmNv8oOUiXn9raMi6")
//				   .roles("ADMIN","SISTEMAS")
//				   .build();
//
//		UserDetails usuarioAlex = User.withUsername("Alex")
//				  .password("{bcrypt}$2a$10$tBzzQtR/C/DcnuNImrTuy.WUc/Maf2Jvj2nArecvfZtldErs5P7Qa")
//				  .roles("VTAS")
//				  .build();
//
//		UserDetails usuarioLulu = User.withUsername("Lulu")
//				  .password("{bcrypt}$2a$10$1v1J6dEhK8BTMtIEaXPcm.izLDYSZ473Fjn19EQQ6ZrFB4BXLOkMC")
//				  .roles("SISTEMAS")
//				  .build();
//		
//		// Es el componente que Spring recominda para usar su 
//		JdbcUserDetailsManager gestorUsuariosBdSeg = new JdbcUserDetailsManager(dataSource);
//		
//		if(gestorUsuariosBdSeg.userExists(usuarioEdgar.getUsername()) == false)
//			gestorUsuariosBdSeg.createUser(usuarioEdgar);
//		if(gestorUsuariosBdSeg.userExists(usuarioAlex.getUsername()) == false)
//			gestorUsuariosBdSeg.createUser(usuarioAlex);
//		if(gestorUsuariosBdSeg.userExists(usuarioLulu.getUsername()) == false)
//			gestorUsuariosBdSeg.createUser(usuarioLulu);
//		
//		return gestorUsuariosBdSeg;
//	}
	


}
