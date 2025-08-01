package mx.com.qtx.cotizadorv1ds.servicios;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGestorPersistenciaComponentes {
	boolean insertarComponenteDto(ComponenteDTO dto) throws SQLException;
	ComponenteDTO getComponenteDtoXID(String id) throws SQLException;
	List<ComponenteDTO> getComponentesXTipo(String tipo) throws SQLException;
	Map<ComponenteDTO, Integer> getSubcomponentes(String id) throws SQLException;
	PromocionDTO getPromocionXID(Long numPromocion) throws SQLException;
	List<DetallePromocionDTO> getDetallesPromocion(Long numPromocion) throws SQLException;
	List<DetallePromoDsctoXCantDTO> getDesctosPromocion(Long numPromocion, Integer numDetPromocion) throws SQLException;
}
