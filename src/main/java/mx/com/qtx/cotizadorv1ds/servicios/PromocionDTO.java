package mx.com.qtx.cotizadorv1ds.servicios;

import java.time.LocalDate;

public class PromocionDTO {
    private Long numPromocion;
    private String nombre;
    private String descripcion;
    private LocalDate fecVigenciaDesde;
    private LocalDate fecVigenciaHasta;

    public PromocionDTO() {}

    // Getters y setters
    public Long getNumPromocion() {
        return numPromocion;
    }

    public void setNumPromocion(Long numPromocion) {
        this.numPromocion = numPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecVigenciaDesde() {
        return fecVigenciaDesde;
    }

    public void setFecVigenciaDesde(LocalDate fecVigenciaDesde) {
        this.fecVigenciaDesde = fecVigenciaDesde;
    }

    public LocalDate getFecVigenciaHasta() {
        return fecVigenciaHasta;
    }

    public void setFecVigenciaHasta(LocalDate fecVigenciaHasta) {
        this.fecVigenciaHasta = fecVigenciaHasta;
    }

	@Override
	public String toString() {
		return "PromocionDTO [numPromocion=" + numPromocion + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", fecVigenciaDesde=" + fecVigenciaDesde + ", fecVigenciaHasta=" + fecVigenciaHasta + "]";
	}

}
