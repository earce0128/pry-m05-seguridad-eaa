package mx.com.qtx.cotizadorv1ds.web;

import java.util.ArrayList;
import java.util.List;

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
import mx.com.qtx.cotizadorv1ds.core.IServicioComponentes;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.componentes.DiscoDuro;
import mx.com.qtx.cotizadorv1ds.core.componentes.Monitor;
import mx.com.qtx.cotizadorv1ds.core.componentes.Pc;
import mx.com.qtx.cotizadorv1ds.core.componentes.TarjetaVideo;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.Cotizacion;
//import mx.com.qtx.cotizadorv1ds.core.cotizaciones.DetalleCotizacion;
//import mx.com.qtx.cotizadorv1ds.servicios.CotizadorPersistente;
//import mx.com.qtx.cotizadorv1ds.servicios.GestorComponentes;

@Controller
public class CtrlRaiz {
	
	@Autowired
	private ICotizador cotizador;
	@Autowired
	private IServicioComponentes gestorComponentes;
	
	private static Logger bitacora = LoggerFactory.getLogger(CtrlRaiz.class);
	
	public CtrlRaiz() {
		bitacora.info("instancia creada:CtrlRaiz()");
		//this.cotizador = new CotizadorPersistente();
		//this.gestorComponentes = new GestorComponentes();
	}
	
	@GetMapping("/")
	public String getWelcomeFile(Model model) {
		bitacora.trace("getWelcomeFile()");
		bitacora.debug("Principal: " + this.getPrincipal());
		model.addAttribute("usuario", this.getPrincipal());
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
	public String getBuscarCompPorCategoria(
			@RequestParam(required = false) String categoria,
			Model model) {
		bitacora.trace("getBuscarCompPorCategoria()");
		bitacora.debug("Principal: " + this.getPrincipal());
		
		List<Monitor> monitores = null;
		List<TarjetaVideo> tarjetas = null;
		List<DiscoDuro> discos = null;
		List<Pc> pcs = null;
		String tipo = "";
		List<Componente> componentes = null;
		
		if(categoria != null) {
			
			bitacora.info("Categoria elegida: " + categoria);
			
			if(categoria.equals("PC")) tipo = "pc";
			else if(categoria.equals("Tarjeta de Video")) tipo = "tarjeta-video";
			else if(categoria.equals("Monitor")) tipo = "monitor";
			else if(categoria.equals("Disco Duro"))	tipo = "disco-duro";
			
			componentes = this.gestorComponentes.getComponentesXCategoria(tipo);
			bitacora.debug("Componentes Tamaño: " + componentes.size());
			
			if(componentes.size() > 0 || componentes != null ) {
				
				if(categoria.equals("PC")) pcs = new ArrayList<>();
				else if(categoria.equals("Tarjeta de Video")) tarjetas = new ArrayList<>();
				else if(categoria.equals("Monitor")) monitores = new ArrayList<>(); 
				else if(categoria.equals("Disco Duro"))	discos = new ArrayList<>();
				
				for(Componente comp : componentes) {
					if(categoria.equals("PC")) pcs.add((Pc)comp);
					else if(categoria.equals("Tarjeta de Video")) tarjetas.add((TarjetaVideo)comp);
					else if(categoria.equals("Monitor")) monitores.add((Monitor)comp);
					else if(categoria.equals("Disco Duro")) discos.add((DiscoDuro)comp);
					comp.toString();
				}
				
				if(categoria.equals("PC")) model.addAttribute("componentes",pcs);				
				else if(categoria.equals("Tarjeta de Video")) model.addAttribute("componentes",tarjetas);
				else if(categoria.equals("Monitor")) model.addAttribute("componentes",monitores);
				else if(categoria.equals("Disco Duro"))	model.addAttribute("componentes",discos);
				
				model.addAttribute("categoria",categoria);
			} 
			
		} else {
			bitacora.debug("Categoría vacía");
			model.addAttribute("usuario", this.getPrincipal());
			return "vistaBuscarCompPorCat";
		}

		model.addAttribute("usuario", this.getPrincipal());
		
		return "vistaBuscarCompPorCat";
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
				model.addAttribute("cotizacion",cotizacion);
				model.addAttribute("id",id);
			} else {
				bitacora.error("Cotización no encontrada con id: " + id);
				model.addAttribute("cotizacion",cotizacion);
				model.addAttribute("id",id);
			}
		} 
		
		bitacora.debug("Principal: " + this.getPrincipal());
		model.addAttribute("usuario", this.getPrincipal());
		
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
