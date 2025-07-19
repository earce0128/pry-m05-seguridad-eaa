package mx.com.qtx.cotizadorv1ds.servicios;

public class DetallePromoDsctoXCantDTO {
    private Long numPromocion;
    private Integer numDetPromocion;
    private Integer numDscto;
    private Integer cantidad;
    private Double dscto;

    public DetallePromoDsctoXCantDTO() {}

	public Long getNumPromocion() {
		return numPromocion;
	}

	public void setNumPromocion(Long numPromocion) {
		this.numPromocion = numPromocion;
	}

	public Integer getNumDetPromocion() {
		return numDetPromocion;
	}

	public void setNumDetPromocion(Integer numDetPromocion) {
		this.numDetPromocion = numDetPromocion;
	}

	public Integer getNumDscto() {
		return numDscto;
	}

	public void setNumDscto(Integer numDscto) {
		this.numDscto = numDscto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getDscto() {
		return dscto;
	}

	public void setDscto(Double dscto) {
		this.dscto = dscto;
	}

	@Override
	public String toString() {
		return "DetallePromoDsctoXCantDTO [numPromocion=" + numPromocion + ", numDetPromocion=" + numDetPromocion
				+ ", numDscto=" + numDscto + ", cantidad=" + cantidad + ", dscto=" + dscto + "]";
	}
    
}
