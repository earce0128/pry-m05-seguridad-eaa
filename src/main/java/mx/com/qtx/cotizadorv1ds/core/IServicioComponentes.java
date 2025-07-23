package mx.com.qtx.cotizadorv1ds.core;

import java.util.List;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;

public interface IServicioComponentes {
	Componente getComponenteXID(String id);
	List<Componente> getComponentesXCategoria(String categoria);
}
