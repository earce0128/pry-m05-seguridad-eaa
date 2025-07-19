package mx.com.qtx.cotizadorv1ds.servicios;

import java.math.BigDecimal;

public class ComponenteDTO {
    private String idComponente;
    private String descripcion;
    private String marca;
    private String modelo;
    private BigDecimal costo;
    private BigDecimal precioBase;
    private String tipo;
    private String capacidadAlm;
    private String memoria;
    private Long numPromocion;

    public ComponenteDTO() {}

    // Getters y setters
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCapacidadAlm() {
        return capacidadAlm;
    }

    public void setCapacidadAlm(String capacidadAlm) {
        this.capacidadAlm = capacidadAlm;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public Long getNumPromocion() {
        return numPromocion;
    }

    public void setNumPromocion(Long numPromocion) {
        this.numPromocion = numPromocion;
    }

	@Override
	public String toString() {
		return "ComponenteDTO [idComponente=" + idComponente + ", descripcion=" + descripcion + ", marca=" + marca
				+ ", modelo=" + modelo + ", costo=" + costo + ", precioBase=" + precioBase + ", tipo=" + tipo
				+ ", capacidadAlm=" + capacidadAlm + ", memoria=" + memoria + ", numPromocion=" + numPromocion + "]";
	}
    
    
}
