package mx.com.qtx.cotizadorv1ds.web.api.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.qtx.cotizadorv1ds.core.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.Cotizacion;
import static mx.com.qtx.cotizadorv1ds.web.api.jaxrs.ErrorRest.getError;


@Path("cot")
public class CotizacionRest {
	
	@Autowired
	private ICotizador cotizador;
	
	private static Logger bitacora = LoggerFactory.getLogger(CotizacionRest.class);
	
	@Path("{id}")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Cotizacion getCotizacion(
			@PathParam("id")
			Long id) {
		bitacora.info("getCotizacion("+ id + ")");
		
		Cotizacion cotizacion = this.cotizador.getCotizacionXID(id);
		
		if(cotizacion == null) {
			String nomClaseEnFallo = this.cotizador.getClass().getName();
			reportarErrorEnBitacora(nomClaseEnFallo, 
									"getCotizacion()", 
									"getCotizacionXID(\"\")", 
									"devolvió null");
			throw getError("Falló el sistema subyacente", 
					        ErrorRest.ERR_FALLA_SERVICIO, 
					        Response.Status.BAD_REQUEST);
		}
		
		return cotizacion;
	}
	
	private void reportarErrorEnBitacora(String nomClaseEnFallo, String metodoCtrlr, String metodoDelegado,
			String falla) {
		bitacora.error("Falló en [" + nomClaseEnFallo + "]." 
				+ "Método en controlador:"
				+ metodoCtrlr 
				+ ". "
				+ "el método delegado "
				+ metodoDelegado 
				+ " "
				+ falla + 
				" ");
	}

}
