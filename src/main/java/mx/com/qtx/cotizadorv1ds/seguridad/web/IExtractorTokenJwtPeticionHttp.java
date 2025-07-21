package mx.com.qtx.cotizadorv1ds.seguridad.web;

import jakarta.servlet.http.HttpServletRequest;

public interface IExtractorTokenJwtPeticionHttp {
	boolean peticionTieneTokenValido(HttpServletRequest request);
	String getNombreUsuario(HttpServletRequest request);
}
