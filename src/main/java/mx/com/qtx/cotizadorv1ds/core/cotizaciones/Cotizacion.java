package mx.com.qtx.cotizadorv1ds.core.cotizaciones;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Cotizacion {
	private static long nCotizaciones = 0;
	
	protected long num;
	protected LocalDate fecha;
	protected BigDecimal total;
	
	protected Map<Integer,DetalleCotizacion> detalles;
	
	public Cotizacion() {
		super();
		nCotizaciones++;
		this.num = nCotizaciones;
		this.fecha = LocalDate.now();
		this.total = new BigDecimal(0);
		this.detalles = new TreeMap<>();
	}
	
	public void agregarDetalle(DetalleCotizacion detI) {
		this.detalles.put(detI.getNumDetalle(), detI);
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public Map<Integer, DetalleCotizacion> getDetalles() {
		return detalles;
	}

	public void setDetalles(Map<Integer, DetalleCotizacion> detalles) {
		this.detalles = detalles;
	}

	public void emitirComoReporte() {
		System.out.println("================== Cotizacion número:" + this.num + " del " + this.fecha + " ==================\n");
		for(Integer k:this.detalles.keySet()) {
			this.desplegarLineaCotizacion(this.detalles.get(k));
		}
		System.out.printf("\n%72s","Total:$" + String.format("%8.2f",this.getTotal()));
		
	}
	
	protected void desplegarLineaCotizacion(DetalleCotizacion detI) {
		System.out.println(String.format("%3d",detI.getCantidad()) + " " 
							+ String.format("Categoría:%-20s", detI.getCategoria()) 
							+ String.format("%-20s", detI.getDescripcion())
							+ " con precio base de $" + String.format("%8.2f",detI.getPrecioBase())
							+ " cuesta(n) " + String.format("%8.2f",detI.getImporteCotizado()));
	}

	@Override
	public String toString() {
		return "Cotizacion [num=" + num + ", fecha=" + fecha + ", total=" + total + "]";
	}
	
	
}
