package mx.com.qtx.cotizadorv1ds.core.cotizaciones;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;

public class ComponenteInvalidoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Componente comp;

	public ComponenteInvalidoException(String message, Componente comp) {
		super(message);
		this.comp = comp;
	}

	public ComponenteInvalidoException(String message, Throwable cause, Componente comp) {
		super(message, cause);
		this.comp = comp;
	}

	public Componente getComp() {
		return comp;
	}

	public void setComp(Componente comp) {
		this.comp = comp;
	}
	

}
