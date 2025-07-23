package mx.com.qtx.cotizadorv1ds.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import mx.com.qtx.cotizadorv1ds.servicios.ComponenteDTO;
import mx.com.qtx.cotizadorv1ds.servicios.DetallePromoDsctoXCantDTO;
import mx.com.qtx.cotizadorv1ds.servicios.DetallePromocionDTO;
import mx.com.qtx.cotizadorv1ds.servicios.IGestorPersistenciaComponentes;
import mx.com.qtx.cotizadorv1ds.servicios.PromocionDTO;

@Repository
public class ComponenteDao implements IGestorPersistenciaComponentes {
    private final String url;
    private final String user;
    private final String password;

    public ComponenteDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public ComponenteDao() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.url = "jdbc:mysql://localhost:3306/bd_cotizaciones?useSSL=false&serverTimezone=UTC";
        this.user = "root";
        this.password = "rootAdmin2022#";
	}

	private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    
	@Override
    public ComponenteDTO getComponenteDtoXID(String id) throws SQLException {
        String sql = "SELECT * FROM componente WHERE id_componente = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToDto(rs);
                }
                return null;
            }
        }
    }
	
	@Override
	public List<ComponenteDTO> getComponentesXTipo(String tipo) throws SQLException {
		String sql = "SELECT * FROM componente WHERE tipo = ?";
        List<ComponenteDTO> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ComponenteDTO dto = new ComponenteDTO();
                    dto.setIdComponente(rs.getString("id_componente"));
                    dto.setDescripcion(rs.getString("descripcion"));
                    dto.setMarca(rs.getString("marca"));
                    dto.setModelo(rs.getString("modelo"));
                    dto.setDescripcion(rs.getString("descripcion"));
                    dto.setCosto(rs.getBigDecimal("costo"));
                    dto.setPrecioBase(rs.getBigDecimal("precio_base"));
                    dto.setTipo(rs.getString("tipo"));
                    dto.setCapacidadAlm(rs.getString("capacidad_alm"));
                    dto.setMemoria(rs.getString("memoria"));
                    dto.setNumPromocion(rs.getLong("num_promocion"));
                    lista.add(dto);
                }
            }
        }
        return lista;
	}
	
	@Override
    public boolean insertarComponenteDto(ComponenteDTO dto) throws SQLException {
        String sql = "INSERT INTO componente (id_componente, descripcion, marca, modelo, costo, precio_base, tipo, capacidad_alm, memoria, num_promocion)"
                   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getIdComponente());
            ps.setString(2, dto.getDescripcion());
            ps.setString(3, dto.getMarca());
            ps.setString(4, dto.getModelo());
            ps.setBigDecimal(5, dto.getCosto());
            ps.setBigDecimal(6, dto.getPrecioBase());
            ps.setString(7, dto.getTipo());
            ps.setString(8, dto.getCapacidadAlm());
            ps.setString(9, dto.getMemoria());
            if (dto.getNumPromocion() != null) ps.setLong(10, dto.getNumPromocion());
            else ps.setNull(10, Types.BIGINT);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean actualizarComponenteDto(ComponenteDTO dto) throws SQLException {
        String sql = "UPDATE componente SET descripcion=?, marca=?, modelo=?, costo=?, precio_base=?, tipo=?, capacidad_alm=?, memoria=?, num_promocion=?"
                   + " WHERE id_componente = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getDescripcion());
            ps.setString(2, dto.getMarca());
            ps.setString(3, dto.getModelo());
            ps.setBigDecimal(4, dto.getCosto());
            ps.setBigDecimal(5, dto.getPrecioBase());
            ps.setString(6, dto.getTipo());
            ps.setString(7, dto.getCapacidadAlm());
            ps.setString(8, dto.getMemoria());
            if (dto.getNumPromocion() != null) ps.setLong(9, dto.getNumPromocion());
            else ps.setNull(9, Types.BIGINT);
            ps.setString(10, dto.getIdComponente());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminarComponenteDto(String id) throws SQLException {
        String sql = "DELETE FROM componente WHERE id_componente = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        }
    }

 	@Override
    public Map<ComponenteDTO, Integer> getSubcomponentes(String id) throws SQLException {
        String sql = "SELECT c.*, sp.cantidad"
                   + " FROM subcomponent_pc AS sp"
                   + " JOIN componente AS c ON sp.id_sub_componente = c.id_componente"
                   + " WHERE sp.id_componente_pc = ?";
        Map<ComponenteDTO,Integer> subMap = new LinkedHashMap<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ComponenteDTO dto = mapRowToDto(rs);
                    int cantidad = rs.getInt("cantidad");
                    subMap.put(dto, cantidad);
                }
            }
        }
        return subMap;
    }

    private ComponenteDTO mapRowToDto(ResultSet rs) throws SQLException {
        ComponenteDTO dto = new ComponenteDTO();
        dto.setIdComponente(rs.getString("id_componente"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setMarca(rs.getString("marca"));
        dto.setModelo(rs.getString("modelo"));
        dto.setCosto(rs.getBigDecimal("costo"));
        dto.setPrecioBase(rs.getBigDecimal("precio_base"));
        dto.setTipo(rs.getString("tipo"));
        dto.setCapacidadAlm(rs.getString("capacidad_alm"));
        dto.setMemoria(rs.getString("memoria"));
        long promo = rs.getLong("num_promocion");
        if (!rs.wasNull()) dto.setNumPromocion(promo);
        return dto;
    }
    
 	@Override
    public PromocionDTO getPromocionXID(Long numPromocion) throws SQLException {
        String sql = "SELECT * FROM promocion WHERE num_promocion = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, numPromocion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PromocionDTO dto = new PromocionDTO();
                    dto.setNumPromocion(rs.getLong("num_promocion"));
                    dto.setNombre(rs.getString("nombre"));
                    dto.setDescripcion(rs.getString("descripcion"));
                    dto.setFecVigenciaDesde(rs.getDate("fec_vigencia_desde").toLocalDate());
                    dto.setFecVigenciaHasta(rs.getDate("fec_vigencia_hasta").toLocalDate());
                    return dto;
                }
                return null;
            }
        }
    }
    
 	@Override
    public List<DetallePromocionDTO> getDetallesPromocion(Long numPromocion) throws SQLException {
        String sql = "SELECT * FROM detalle_promocion WHERE num_promocion = ?";
        List<DetallePromocionDTO> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, numPromocion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallePromocionDTO dto = new DetallePromocionDTO();
                    dto.setNumPromocion(rs.getLong("num_promocion"));
                    dto.setNumDetPromocion(rs.getInt("num_det_promocion"));
                    dto.setNombre(rs.getString("nombre"));
                    dto.setDescripcion(rs.getString("descripcion"));
                    dto.setEsBase(rs.getBoolean("es_base"));
                    dto.setTipoPromBase(rs.getString("tipo_prom_base"));
                    dto.setPorcDsctoPlano(rs.getDouble("porc_dscto_plano"));
                    dto.setTipoPromAcumulable(rs.getString("tipo_prom_acumulable"));
                    dto.setLevelN(rs.getInt("lleve_n"));
                    dto.setPagueM(rs.getInt("pague_m"));
                    lista.add(dto);
                }
            }
        }
        return lista;
    }
    
 	@Override
    public List<DetallePromoDsctoXCantDTO> getDesctosPromocion(Long numPromocion, Integer numDetPromocion) throws SQLException {
        String sql = "SELECT * FROM detalle_promp_dscto_x_cant " +
                     "WHERE num_promocion = ? AND num_det_promocion = ?";
        List<DetallePromoDsctoXCantDTO> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, numPromocion);
            ps.setInt(2, numDetPromocion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallePromoDsctoXCantDTO dto = new DetallePromoDsctoXCantDTO();
                    dto.setNumPromocion(rs.getLong("num_promocion"));
                    dto.setNumDetPromocion(rs.getInt("num_det_promocion"));
                    dto.setNumDscto(rs.getInt("num_dscto"));
                    dto.setCantidad(rs.getInt("cantidad"));
                    dto.setDscto(rs.getDouble("dscto"));
                    lista.add(dto);
                }
            }
        }
        return lista;
    }

	


}
