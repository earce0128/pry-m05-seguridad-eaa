package mx.com.qtx.cotizadorv1ds;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import mx.com.qtx.cotizadorv1ds.web.api.jaxrs.CorsFilter;
import mx.com.qtx.cotizadorv1ds.web.api.jaxrs.CotizacionRest;

@Configuration
public class ConfiguracionJersey extends ResourceConfig {
	
	private static Logger bitacora = LoggerFactory.getLogger(ConfiguracionJersey.class);
	 
	public ConfiguracionJersey() {
		
		bitacora.trace("ConfiguracionJersey()");
		
		this.register(CotizacionRest.class);
		this.register(CorsFilter.class);
	}
}
