package mx.com.qtx.cotizadorv1ds.servicios;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CotizacionDTO {
    private long numCotizacion;
    private LocalDateTime fecCotizacion;
    private BigDecimal total;

    public CotizacionDTO() {}

    public long getNumCotizacion() {
        return numCotizacion;
    }

    public void setNumCotizacion(long numCotizacion) {
        this.numCotizacion = numCotizacion;
    }

    public LocalDateTime getFecCotizacion() {
        return fecCotizacion;
    }

    public void setFecCotizacion(LocalDateTime fecCotizacion) {
        this.fecCotizacion = fecCotizacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

	@Override
	public String toString() {
		return "CotizacionDTO [numCotizacion=" + numCotizacion + ", fecCotizacion=" + fecCotizacion + ", total=" + total
				+ "]";
	}
    
}
