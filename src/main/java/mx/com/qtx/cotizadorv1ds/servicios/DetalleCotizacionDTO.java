package mx.com.qtx.cotizadorv1ds.servicios;

import java.math.BigDecimal;

public class DetalleCotizacionDTO {
    private long numCotizacion;
    private int numDetalle;
    private String idComponente;
    private String descripcion;
    private int cantidad;
    private BigDecimal precioBase;
    private BigDecimal importeCotizado;
    private String categoria;

    public DetalleCotizacionDTO() {}

    public long getNumCotizacion() {
        return numCotizacion;
    }

    public void setNumCotizacion(long numCotizacion) {
        this.numCotizacion = numCotizacion;
    }

    public int getNumDetalle() {
        return numDetalle;
    }

    public void setNumDetalle(int numDetalle) {
        this.numDetalle = numDetalle;
    }

    public String getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(String idComponente) {
        this.idComponente = idComponente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public BigDecimal getImporteCotizado() {
        return importeCotizado;
    }

    public void setImporteCotizado(BigDecimal importeCotizado) {
        this.importeCotizado = importeCotizado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

	@Override
	public String toString() {
		return "DetalleCotizacionDTO [numCotizacion=" + numCotizacion + ", numDetalle=" + numDetalle + ", idComponente="
				+ idComponente + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precioBase="
				+ precioBase + ", importeCotizado=" + importeCotizado + ", categoria=" + categoria + "]";
	}
    
 }