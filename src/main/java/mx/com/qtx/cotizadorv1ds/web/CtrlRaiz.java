package mx.com.qtx.cotizadorv1ds.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.com.qtx.cotizadorv1ds.core.ICotizador;
//import mx.com.qtx.cotizadorv1ds.core.IServicioComponentes;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.Cotizacion;
//import mx.com.qtx.cotizadorv1ds.core.cotizaciones.DetalleCotizacion;
//import mx.com.qtx.cotizadorv1ds.servicios.CotizadorPersistente;
//import mx.com.qtx.cotizadorv1ds.servicios.GestorComponentes;

@Controller
public class CtrlRaiz {
	
	@Autowired
	private ICotizador cotizador;
//	@Autowired
//	private IServicioComponentes gestorComponentes;
	
	private static Logger bitacora = LoggerFactory.getLogger(CtrlRaiz.class);
	
	public CtrlRaiz() {
		bitacora.info("instancia creada:CtrlRaiz()");
		//this.cotizador = new CotizadorPersistente();
		//this.gestorComponentes = new GestorComponentes();
	}
	
	@GetMapping("/")
	public String getWelcomeFile(Model modelo) {
		bitacora.trace("getWelcomeFile()");
		bitacora.debug("Principal: " + this.getPrincipal());
		return "vistaRaiz";
	}
	
	@GetMapping("/infoGeneral")
	public String getVistaInfo(Model modelo) {
		bitacora.trace("getVistaInfo()");
		bitacora.debug("Principal: " + this.getPrincipal());
		return "vistaInformacion";
	}
	
	@GetMapping("/altaUsuario")
	public String getCrearUsuario(Model modelo) {
		bitacora.trace("getCrearUsuario()");
		bitacora.debug("Principal: " + this.getPrincipal());
		return "vistaComodin";
	}
	
	@GetMapping("/buscarCompPorCat")
	public String getBuscarCompPorCategoria(Model modelo) {
		bitacora.trace("getBuscarCompPorCategoria()");
		bitacora.debug("Principal: " + this.getPrincipal());
		return "vistaComodin";
	}
	
	@GetMapping("/buscarCotizacion")
	public String buscarCotizacion(
			@RequestParam(required = false) Long id,
	        Model model) {
			
		bitacora.trace("buscarCotizacion()");
		bitacora.info("Buscará cotización con id: " + id);
			
		if(id != null) {
			Cotizacion cotizacion = this.cotizador.getCotizacionXID(id);
			if(cotizacion != null) {
//				Map<Integer,DetalleCotizacion> detCot = cotizacion.getDetalles();
//				//Collection<DetalleCotizacion> componentes = detCot.values();
//				for(DetalleCotizacion detI : detCot.values()){
//					bitacora.info("Num: " + detI.getNumDetalle());
//					bitacora.info("Cantidad: " + detI.getCantidad());
//					bitacora.info("Id: " + detI.getIdComponente());
//					bitacora.info("Descripcion: " + detI.getDescripcion());
//					bitacora.info("Precio Base: " + detI.getPrecioBase());
//					bitacora.info("Total: " + detI.getImporteCotizado());
//				}
//				bitacora.info("Total: " + cotizacion.getTotal());
				model.addAttribute("cotizacion",cotizacion);
				model.addAttribute("id",id);
				//cotizacion.emitirComoReporte();
			} else {
				bitacora.error("Cotización no encontrada con id: " + id);
				model.addAttribute("cotizacion",cotizacion);
				model.addAttribute("id",id);
			}
		} 
		
		
		bitacora.debug("Principal: " + this.getPrincipal());
		return "vistaBuscarCotizacion";
	}
	
	private String getPrincipal() {
		bitacora.warn("getPrincipal()");
		Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
		
		if(autenticacion == null) {
			bitacora.warn("Autenticacion nula");
			return "nula";
		}
		
		String nomPrincipal = autenticacion.getName();
		
		return nomPrincipal;
	}

}
