package mx.com.qtx.cotizadorv1ds.persistencia;

import java.sql.*;
import java.util.*;

import mx.com.qtx.cotizadorv1ds.servicios.CotizacionDTO;
import mx.com.qtx.cotizadorv1ds.servicios.DetalleCotizacionDTO;
import mx.com.qtx.cotizadorv1ds.servicios.IGestorPersistenciaCotizaciones;

import java.math.BigDecimal;

public class CotizacionDao implements IGestorPersistenciaCotizaciones {
    private final String url;
    private final String user;
    private final String password;

    public CotizacionDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
     public CotizacionDao() {
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
    public CotizacionDTO insertarCotizacion(CotizacionDTO cot) throws SQLException {
        String sql = "INSERT INTO cotizacion (fec_cotizacion) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(cot.getFecCotizacion()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cot.setNumCotizacion(keys.getLong(1));
                }
            }
        }
        return cot;
    }

    @Override
    public CotizacionDTO getCotizacionXID(long numCot) throws SQLException {
        String sql = "SELECT * FROM cotizacion WHERE num_cotizacion = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, numCot);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CotizacionDTO cot = new CotizacionDTO();
                    cot.setNumCotizacion(rs.getLong("num_cotizacion"));
                    cot.setFecCotizacion(rs.getTimestamp("fec_cotizacion").toLocalDateTime());
                    // total no existe en tabla, calcular sum de detalles? dejar null o BigDecimal.ZERO
                    cot.setTotal(BigDecimal.ZERO);
                    return cot;
                }
            }
        }
        return null;
    }

    @Override
    public DetalleCotizacionDTO insertarDetalleCotizacion(DetalleCotizacionDTO det) throws SQLException {
        String sql = "INSERT INTO detalle_cotizacion (num_cotizacion, num_detalle, id_componente, cantidad, precio_base, importe_cotizado, categoria)"
                   + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, det.getNumCotizacion());
            ps.setInt(2, det.getNumDetalle());
            ps.setString(3, det.getIdComponente());
            ps.setInt(4, det.getCantidad());
            ps.setBigDecimal(5, det.getPrecioBase());
            ps.setBigDecimal(6, det.getImporteCotizado());
            ps.setString(7, det.getCategoria());
            ps.executeUpdate();
        }
        return det;
    }

    @Override
    public List<DetalleCotizacionDTO> getDetallesCotizacion(long numCot) throws SQLException {
        String sql = "SELECT * FROM detalle_cotizacion WHERE num_cotizacion = ? ORDER BY num_detalle";
        List<DetalleCotizacionDTO> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, numCot);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleCotizacionDTO det = new DetalleCotizacionDTO();
                    det.setNumCotizacion(rs.getLong("num_cotizacion"));
                    det.setNumDetalle(rs.getInt("num_detalle"));
                    det.setIdComponente(rs.getString("id_componente"));
                    det.setDescripcion(rs.getString("descripcion"));
                    det.setCantidad(rs.getInt("cantidad"));
                    det.setPrecioBase(rs.getBigDecimal("precio_base"));
                    det.setImporteCotizado(rs.getBigDecimal("importe_cotizado"));
                    det.setCategoria(rs.getString("categoria"));
                    lista.add(det);
                }
            }
        }
        return lista;
    }

	@Override
	public long insertarCotizacion(CotizacionDTO cot, List<DetalleCotizacionDTO> lstDetsCotDTO) throws SQLException{
//		System.out.println("insertarCotizacion(" + cot + " , " + lstDetsCotDTO + ")" );
        String sql = "INSERT INTO cotizacion (fec_cotizacion) VALUES (?)";
        String sqlDet = "INSERT INTO detalle_cotizacion (num_cotizacion, num_detalle, id_componente, cantidad, precio_base, importe_cotizado, categoria, descripcion)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	 PreparedStatement psDet = conn.prepareStatement(sqlDet)	) {
        	conn.setAutoCommit(false);
        	conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        	try {
	            ps.setTimestamp(1, Timestamp.valueOf(cot.getFecCotizacion()));
	            ps.executeUpdate();
	            try (ResultSet keys = ps.getGeneratedKeys()) {
	                if (keys.next()) {
	                    cot.setNumCotizacion(keys.getLong(1));
	                }
	            }
	            for(DetalleCotizacionDTO det : lstDetsCotDTO) {
	            	psDet.setLong(1, cot.getNumCotizacion());
	            	psDet.setInt(2, det.getNumDetalle());
	            	psDet.setString(3, det.getIdComponente());
	            	psDet.setInt(4, det.getCantidad());
	            	psDet.setBigDecimal(5, det.getPrecioBase());
	            	psDet.setBigDecimal(6, det.getImporteCotizado());
	            	psDet.setString(7, det.getCategoria());
	            	psDet.setString(8, det.getDescripcion());
	            	psDet.executeUpdate();
	            	
	            }
	            conn.commit();
	            
        	}
        	catch (Exception ex){
        		conn.rollback();
        		conn.setAutoCommit(true);
        		throw ex;
        	}
        }
        return cot.getNumCotizacion();
	}
}
