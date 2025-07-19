package mx.com.qtx.cotizadorv1ds.servicios;

import java.sql.SQLException;
import java.util.List;

public interface IGestorPersistenciaCotizaciones {
	CotizacionDTO insertarCotizacion(CotizacionDTO cotizacion) throws SQLException; 
	CotizacionDTO getCotizacionXID(long numCotizacion) throws SQLException; 
	DetalleCotizacionDTO insertarDetalleCotizacion(DetalleCotizacionDTO detCot) throws SQLException; 
	List<DetalleCotizacionDTO> getDetallesCotizacion(long numCotizacion) throws SQLException;
	long insertarCotizacion(CotizacionDTO cotDTO, List<DetalleCotizacionDTO> lstDetsCotDTO) throws SQLException; 
}
