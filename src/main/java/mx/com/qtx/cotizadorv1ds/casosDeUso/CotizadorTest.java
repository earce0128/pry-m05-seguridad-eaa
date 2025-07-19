package mx.com.qtx.cotizadorv1ds.casosDeUso;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import mx.com.qtx.cotizadorv1ds.core.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.ComponenteInvalidoException;
import mx.com.qtx.cotizadorv1ds.core.cotizaciones.Cotizacion;
import mx.com.qtx.cotizadorv1ds.cotizadorA.Cotizador;

public class CotizadorTest {
    public static void main(String[] args) {
//    	testCreacionPC();
    	
//    	testAgregarComponentes();
//    	testEliminarComponente();
    	
//    	testGenerarCotizacion();
//    	testMostrarCaracteristicas();
    	
//    	testCreacionPcOk_conPcBuilder();
//    	testCreacionPcErroneo_conPcBuilder_DiscosDeMas();
//    	testCreacionPcErroneo_conPcBuilder_SinDiscos();
    }

	private static void testEliminarComponente() {
		System.out.println("*** testEliminarComponente ***");
		ICotizador cotizador = getCotizadorActual();
		
		Componente monitor = Componente.crearMonitor("M001","Monitor 17 pulgadas","Samsung","Goliat-500",
						new BigDecimal(1000), new BigDecimal(2000));
		cotizador.agregarComponente(1, monitor);
		
		Componente monitor2 = Componente.crearMonitor("M022","Monitor 15 pulgadas","Sony","VR-30",
				new BigDecimal(1100), new BigDecimal(2000));
		cotizador.agregarComponente(4, monitor2);
		cotizador.agregarComponente(7, monitor2);
		try {
			cotizador.eliminarComponente(null);
		}
	// Invocar servicio corporativo de manejo de errores
		catch(ComponenteInvalidoException ex) {
//			System.out.println(ex.getClass().getName());
			Throwable causa = ex.getCause();
			System.out.println("Mensaje:" + ex.getMessage());
			if(causa == null)
				System.out.println("Se ha invocado al servicio con un parámetro erróneo");
			else {
				System.out.println("causado por " + causa.getClass().getName() + " " + causa.getMessage());
			}
			//	ex.printStackTrace();
				List.of(ex.getStackTrace())
				          .stream()
				          .map(stI->stI.getClassName() + ":" 
				                  + stI.getLineNumber() 
				                  + ", en " + stI.getMethodName())
				          .forEach(x->System.out.println(x));
		}
		catch(NoSuchElementException ex) {
//			System.out.println(ex.getClass().getName());
			System.out.println("Mensaje:" + ex.getMessage());
			System.out.println("Al parecer la implementación usada "
					+ "no valida parámetros de entrada");
		}
		catch(Exception ex) {
			System.out.println(ex.getClass().getName());
			System.out.println("Mensaje:" + ex.getMessage());
		}
		
	}

	private static void testMostrarCaracteristicas() {
		System.out.println("*** testMostrarCaracteristicas ***");
    	Componente disco1 = Componente.crearDiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
		disco1.mostrarCaracteristicas();
	}

	private static void testCreacionPC() {
		System.out.println("*** testCreacionPC ***");
    	Componente disco1 = Componente.crearDiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
       	Componente monitor = Componente.crearMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
                new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
        Componente tarjeta = Componente.crearTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
                new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
        
    	Componente miPc = Componente.crearPc("pc0001", "Laptop 15000 s300", "Dell", "Terminator",
    											List.of(disco1,monitor,tarjeta));
		miPc.mostrarCaracteristicas();
		
	}
	
	private static void testCreacionPcOk_conPcBuilder() {
		System.out.println("*** testCreacionPcOk_conPcBuilder ***");
		
		Componente miPc = Componente.getPcBuilder()
				                    .definirId("pc0001")
		                            .definirDescripcion("Laptop 15000 s300")
		                            .definirMarcaYmodelo("Dell", "Terminator")
		                            .agregarDisco("D001", "Disco Seagate", "TechXYZ", "X200", 
		             	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB")
		                            .agregarMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
		                                 new BigDecimal("3200.00"), new BigDecimal("6000.00"))
		                            .agregarTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
		                                 new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB")
		                            .agregarDisco("D002", "Disco Seagate", "TechXYZJr", "X100", 
			             	                new BigDecimal("1000.00"), new BigDecimal("1600.00"), "500GB")
		                            .build();
		miPc.mostrarCaracteristicas();
		
	}
	
	private static void testCreacionPcErroneo_conPcBuilder_DiscosDeMas() {
		System.out.println("*** testCreacionPcErroneo_conPcBuilder_DiscosDeMas ***");
		
		Componente miPc = Componente.getPcBuilder()
				                    .definirId("pc0001")
		                            .definirDescripcion("Laptop 15000 s300")
		                            .definirMarcaYmodelo("Dell", "Terminator")
		                            .agregarDisco("D001", "Disco Seagate", "TechXYZ", "X200", 
		             	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB")
		                            .agregarMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
		                                 new BigDecimal("3200.00"), new BigDecimal("6000.00"))
		                            .agregarTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
		                                 new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB")
		                            .agregarDisco("D002", "Disco Seagate", "TechXYZJr", "X100", 
			             	                new BigDecimal("1000.00"), new BigDecimal("1600.00"), "500GB")
		                            .agregarDisco("D003", "Disco Xtr 1TB", "Xtr-500", "Xtr", 
			             	                new BigDecimal("2000.00"), new BigDecimal("2600.00"), "1TB")
		                            .build();
		miPc.mostrarCaracteristicas();
		
	}
	
	private static void testCreacionPcErroneo_conPcBuilder_SinDiscos() {
		System.out.println("*** testCreacionPcOk_conPcBuilder ***");
		
		Componente miPc = Componente.getPcBuilder()
				                    .definirId("pc0001")
		                            .definirDescripcion("Laptop 15000 s300")
		                            .definirMarcaYmodelo("Dell", "Terminator")
		                            .agregarMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
		                                 new BigDecimal("3200.00"), new BigDecimal("6000.00"))
		                            .agregarTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
		                                 new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB")
		                            .build();
		miPc.mostrarCaracteristicas();
		
	}

	private static void testGenerarCotizacion() {
		
		ICotizador cotizador = getCotizadorActual();
		
		Componente monitor = Componente.crearMonitor("M001","Monitor 17 pulgadas","Samsung","Goliat-500",
						new BigDecimal(1000), new BigDecimal(2000));
		cotizador.agregarComponente(1, monitor);
		
		Componente monitor2 = Componente.crearMonitor("M022","Monitor 15 pulgadas","Sony","VR-30",
				new BigDecimal(1100), new BigDecimal(2000));
		cotizador.agregarComponente(4, monitor2);
		cotizador.agregarComponente(7, monitor2);
		
		Componente disco = Componente.crearDiscoDuro("D-23", "Disco estado sólido", "Seagate", "T-455", new BigDecimal(500), 
				new BigDecimal(1000), "2TB");
		cotizador.agregarComponente(10, disco);
	
	    Componente tarjeta = Componente.crearTarjetaVideo("C0XY", "Tarjeta THOR", "TechBrand", "X200-34", 
	            new BigDecimal("150.00"), new BigDecimal("300.00"), "8GB");
		cotizador.agregarComponente(10, tarjeta);
	    
    	Componente discoPc = Componente.crearDiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
	   	Componente monitorPc = Componente.crearMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
	            new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
	    Componente tarjetaPc = Componente.crearTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
	            new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
	    
		Componente miPc = Componente.crearPc("pc0001", "Laptop 15000 s300", "Dell", "Terminator",
												List.of(discoPc,monitorPc,tarjetaPc));
		cotizador.agregarComponente(1, miPc);
		Cotizacion cotizacion = cotizador.generarCotizacion();
		cotizacion.emitirComoReporte();
	}

	private static ICotizador getCotizadorActual() {
		return new Cotizador();
	}

}