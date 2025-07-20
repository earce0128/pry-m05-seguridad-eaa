package mx.com.qtx.cotizadorv1ds;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import mx.com.qtx.cotizadorv1ds.web.api.jaxrs.CorsFilter;
import mx.com.qtx.cotizadorv1ds.web.api.jaxrs.CotizacionRest;
import mx.com.qtx.cotizadorv1ds.web.api.jaxrs.GenericExceptionMapper;
import mx.com.qtx.cotizadorv1ds.seguridad.web.AutenticacionRest;

@Configuration
public class ConfiguracionJersey extends ResourceConfig {
	
	private static Logger bitacora = LoggerFactory.getLogger(ConfiguracionJersey.class);
	 
	public ConfiguracionJersey() {
		
		bitacora.trace("ConfiguracionJersey()");
		
		this.register(CotizacionRest.class);
		this.register(AutenticacionRest.class);
		this.register(CorsFilter.class);
		this.register(GenericExceptionMapper.class);
	}
}
