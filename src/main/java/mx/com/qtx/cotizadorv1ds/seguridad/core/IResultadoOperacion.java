package mx.com.qtx.cotizadorv1ds.seguridad.core;

public interface IResultadoOperacion {
	Object getObjResultadoOk();
	void setObjResultadoOk(Object objResultadoOk);
	boolean todoOk();
	void agregarError(int codError, String adicion);
	String getResumenErrores();
}
