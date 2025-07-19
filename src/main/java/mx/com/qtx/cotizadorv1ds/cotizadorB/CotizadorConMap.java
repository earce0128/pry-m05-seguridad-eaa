package mx.com.qtx.cotizadorv1ds.cotizadorB;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import mx.com.qtx.cotizadorv1ds.core.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.ComponenteInvalidoException;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.DetalleCotizacion;

public class CotizadorConMap implements ICotizador {
	private Map<Componente,Integer> mapCompsYcants;

	public CotizadorConMap() {
		super();
		this.mapCompsYcants = new HashMap<>();
	}

	@Override
	public void agregarComponente(int cantidad, Componente componente) {
		this.mapCompsYcants.put(componente, cantidad);
	}

	@Override
	public void eliminarComponente(String idComponente) throws ComponenteInvalidoException {
		try {
    	   Componente llave = this.mapCompsYcants.keySet()
    			                    .stream()
    			                    .filter(k->k.getId().equals(idComponente))
    			                     .findFirst()
    			                     .get();
               
		
    	   this.mapCompsYcants.remove(llave);
		}
		catch(Exception ex) {
			throw new ComponenteInvalidoException("Error subyacente", ex, null);
		}
	}

	@Override
	public Cotizacion generarCotizacion() {
        BigDecimal total = new BigDecimal(0);
        
        Cotizacion cotizacion = new CotizacionFmtoB();
        int i=0;
        for(Componente compI:this.mapCompsYcants.keySet()) {
        	int cantidadI = this.mapCompsYcants.get(compI);
        	BigDecimal importeCotizadoI = new BigDecimal(0);
        	i++;
        	
        	importeCotizadoI = compI.cotizar(cantidadI);
        	        
        	DetalleCotizacion detI = new DetalleCotizacion((i), compI.getId(), compI.getDescripcion(), cantidadI, 
        			                                        compI.getPrecioBase(), importeCotizadoI, compI.getCategoria());
        	cotizacion.agregarDetalle(detI);
            total = total.add(importeCotizadoI);
        }
        cotizacion.setTotal(total);
   	
		return cotizacion;
	}

	@Override
	public void listarComponentes() {
        System.out.println("=== Componentes a cotizar en CotizadorConMap ===");
        for(Componente compI:this.mapCompsYcants.keySet())  {
        	int cantidad = this.mapCompsYcants.get(compI);
            System.out.println(cantidad + " " + compI.getDescripcion() 
            		 + ": $" + compI.getPrecioBase() + " ID:" + compI.getId());        	
        }
	}

	@Override
	public Cotizacion getCotizacionXID(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
