package mx.com.qtx.cotizadorv1ds.web.api.jaxrs;

import static mx.com.qtx.cotizadorv1ds.web.api.jaxrs.ErrorRest.getError;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.qtx.cotizadorv1ds.core.IServicioComponentes;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.componentes.DiscoDuro;
import mx.com.qtx.cotizadorv1ds.core.componentes.Monitor;
import mx.com.qtx.cotizadorv1ds.core.componentes.Pc;
import mx.com.qtx.cotizadorv1ds.core.componentes.TarjetaVideo;

@Path("comp")
public class ComponenteRest {

	@Autowired
	private IServicioComponentes componente;
	
	private static Logger bitacora = LoggerFactory.getLogger(ComponenteRest.class);
	
    @Path("/monitores")
    @GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Monitor> getMonitores() {
		
		List<Monitor> monitores = null;
		List<Componente> componentes = null;
		
		componentes = this.componente.getComponentesXCategoria("monitor");
		bitacora.info("Componentes: " + componentes.size());
		
		if(componentes.size() > 0) {
			bitacora.info("Recuperando monitores");
			monitores = new ArrayList<>();
			for(Componente comp : componentes) {
				monitores.add((Monitor)comp);
			}
			return monitores;
		} else {
			String nomClaseEnFallo = this.componente.getClass().getName();
			reportarErrorEnBitacora(nomClaseEnFallo, 
									"getMonitores()", 
									"getComponentesXCategoria(\"\")", 
									"devolvió null");
			throw getError("Falló el sistema subyacente", 
					        ErrorRest.ERR_FALLA_SERVICIO, 
					        Response.Status.BAD_REQUEST);
		}
		
	}
    
    @Path("/pc")
    @GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Pc> getPcs() {
		
		List<Pc> pcs = null;
		List<Componente> componentes = null;
		
		componentes = this.componente.getComponentesXCategoria("pc");
		bitacora.info("Componentes: " + componentes.size());
		
		if(componentes.size() > 0) {
			bitacora.info("Recuperando pcs");
			pcs = new ArrayList<>();
			for(Componente comp : componentes) {
				pcs.add((Pc)comp);
			}
			return pcs;
		} else {
			String nomClaseEnFallo = this.componente.getClass().getName();
			reportarErrorEnBitacora(nomClaseEnFallo, 
									"getPcs()", 
									"getComponentesXCategoria(\"\")", 
									"devolvió null");
			throw getError("Falló el sistema subyacente", 
					        ErrorRest.ERR_FALLA_SERVICIO, 
					        Response.Status.BAD_REQUEST);
		}
		
	}
    
    @Path("/discos")
    @GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<DiscoDuro> getDiscos() {
		
		List<DiscoDuro> discos = null;
		List<Componente> componentes = null;
		
		componentes = this.componente.getComponentesXCategoria("disco-duro");
		bitacora.info("Componentes: " + componentes.size());
		
		if(componentes.size() > 0) {
			bitacora.info("Recuperando discos duros");
			discos = new ArrayList<>();
			for(Componente comp : componentes) {
				discos.add((DiscoDuro)comp);
			}
			return discos;
		} else {
			String nomClaseEnFallo = this.componente.getClass().getName();
			reportarErrorEnBitacora(nomClaseEnFallo, 
									"getDiscos()", 
									"getComponentesXCategoria(\"\")", 
									"devolvió null");
			throw getError("Falló el sistema subyacente", 
					        ErrorRest.ERR_FALLA_SERVICIO, 
					        Response.Status.BAD_REQUEST);
		}
		
	}
	
    @Path("/tarjetas")
    @GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<TarjetaVideo> getTarjetas() {
		
		List<TarjetaVideo> tarjetas = null;
		List<Componente> componentes = null;
		
		componentes = this.componente.getComponentesXCategoria("tarjeta-video");
		bitacora.info("Componentes: " + componentes.size());
		
		if(componentes.size() > 0) {
			bitacora.info("Recuperando tarjetas de video");
			tarjetas = new ArrayList<>();
			for(Componente comp : componentes) {
				tarjetas.add((TarjetaVideo)comp);
			}
			return tarjetas;
		} else {
			String nomClaseEnFallo = this.componente.getClass().getName();
			reportarErrorEnBitacora(nomClaseEnFallo, 
									"getTarjetas()", 
									"getComponentesXCategoria(\"\")", 
									"devolvió null");
			throw getError("Falló el sistema subyacente", 
					        ErrorRest.ERR_FALLA_SERVICIO, 
					        Response.Status.BAD_REQUEST);
		}
		
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
