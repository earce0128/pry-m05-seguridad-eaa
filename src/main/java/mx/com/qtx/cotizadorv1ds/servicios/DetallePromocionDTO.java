package mx.com.qtx.cotizadorv1ds.servicios;

public class DetallePromocionDTO {
	   private Long numPromocion;
	    private Integer numDetPromocion;
	    private String nombre;
	    private String descripcion;
	    private Boolean esBase;
	    private String tipoPromBase;
	    private Double porcDsctoPlano;
	    private String tipoPromAcumulable;
	    private Integer levelN;
	    private Integer pagueM;

	    public DetallePromocionDTO() {}

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

		public Boolean getEsBase() {
			return esBase;
		}

		public void setEsBase(Boolean esBase) {
			this.esBase = esBase;
		}

		public String getTipoPromBase() {
			return tipoPromBase;
		}

		public void setTipoPromBase(String tipoPromBase) {
			this.tipoPromBase = tipoPromBase;
		}

		public Double getPorcDsctoPlano() {
			return porcDsctoPlano;
		}

		public void setPorcDsctoPlano(Double porcDsctoPlano) {
			this.porcDsctoPlano = porcDsctoPlano;
		}

		public String getTipoPromAcumulable() {
			return tipoPromAcumulable;
		}

		public void setTipoPromAcumulable(String tipoPromAcumulable) {
			this.tipoPromAcumulable = tipoPromAcumulable;
		}

		public Integer getLleveN() {
			return levelN;
		}

		public void setLevelN(Integer levelN) {
			this.levelN = levelN;
		}

		public Integer getPagueM() {
			return pagueM;
		}

		public void setPagueM(Integer pagueM) {
			this.pagueM = pagueM;
		}

		@Override
		public String toString() {
			return "DetallePromocionDTO [numPromocion=" + numPromocion + ", numDetPromocion=" + numDetPromocion
					+ ", nombre=" + nombre + ", descripcion=" + descripcion + ", esBase=" + esBase + ", tipoPromBase="
					+ tipoPromBase + ", porcDsctoPlano=" + porcDsctoPlano + ", tipoPromAcumulable=" + tipoPromAcumulable
					+ ", levelN=" + levelN + ", pagueM=" + pagueM + "]";
		}
	    
}
