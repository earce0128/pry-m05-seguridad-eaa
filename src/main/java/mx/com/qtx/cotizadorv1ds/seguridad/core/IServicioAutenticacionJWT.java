package mx.com.qtx.cotizadorv1ds.seguridad.core;

import mx.com.qtx.cotizadorv1ds.seguridad.entidades.Autenticacion;

public interface IServicioAutenticacionJWT {
	IResultadoOperacion registrarAutenticación(Autenticacion aut);
}
