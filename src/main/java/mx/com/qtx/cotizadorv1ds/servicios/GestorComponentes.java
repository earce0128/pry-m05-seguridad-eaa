package mx.com.qtx.cotizadorv1ds.servicios;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mx.com.qtx.cotizadorv1ds.core.IServicioComponentes;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.componentes.PcBuilder;
import mx.com.qtx.cotizadorv1ds.core.promos.Promocion;
import mx.com.qtx.cotizadorv1ds.core.promos.PromocionBuilder;
import mx.com.qtx.cotizadorv1ds.persistencia.ComponenteDao;

public class GestorComponentes implements IServicioComponentes {
	
	private IGestorPersistenciaComponentes gestorPersistencia;

	public GestorComponentes() {
		super();
		this.gestorPersistencia = new ComponenteDao();
	}

	@Override
	public Componente getComponenteXID(String id) {
		Componente componente = null;
		try {
			ComponenteDTO componenteDTO = this.gestorPersistencia.getComponenteDtoXID(id);
			switch (componenteDTO.getTipo()) {
				case "monitor" -> { 
					componente = Componente.crearMonitor(componenteDTO.getIdComponente(), 
														 componenteDTO.getDescripcion(), 
														 componenteDTO.getMarca(), 
														 componenteDTO.getModelo(), 
														 componenteDTO.getCosto(), 
														 componenteDTO.getPrecioBase());
					}
				case "disco-duro" -> { 
					componente = Componente.crearDiscoDuro(componenteDTO.getIdComponente(), 
														 componenteDTO.getDescripcion(), 
														 componenteDTO.getMarca(), 
														 componenteDTO.getModelo(), 
														 componenteDTO.getCosto(), 
														 componenteDTO.getPrecioBase(),
														 componenteDTO.getCapacidadAlm());
					}
				case "tarjeta-video" -> { 
					componente = Componente.crearTarjetaVideo(componenteDTO.getIdComponente(), 
														 componenteDTO.getDescripcion(), 
														 componenteDTO.getMarca(), 
														 componenteDTO.getModelo(), 
														 componenteDTO.getCosto(), 
														 componenteDTO.getPrecioBase(),
														 componenteDTO.getMemoria());
					}
				case "pc" -> {
					componente = this.crearPc(componenteDTO);
				}

				default -> {
					throw new IllegalArgumentException("Unexpected value: " + componenteDTO.getTipo());
				}
			}
			this.asignarPromocion(componente, componenteDTO.getNumPromocion());
			return componente;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Componente crearPc(ComponenteDTO componenteDTO) throws SQLException {
		Map<ComponenteDTO, Integer> subcomponentesPc = this.gestorPersistencia.getSubcomponentes(componenteDTO.getIdComponente());
		
		PcBuilder builder = Componente.getPcBuilder();
		builder = builder.definirId(componenteDTO.getIdComponente())
				         .definirMarcaYmodelo(componenteDTO.getMarca(), componenteDTO.getModelo())
				         .definirDescripcion(componenteDTO.getDescripcion());
		
		builder = agregarTarjetasAlBuilder(subcomponentesPc, builder);
		builder = agregarDiscosAlBuilder(subcomponentesPc, builder);
		builder = agregarMonitoresAlBuilder(subcomponentesPc, builder);
		
		return builder.build();
	}

	private PcBuilder agregarMonitoresAlBuilder(Map<ComponenteDTO, Integer> subcomponentesPc, PcBuilder builder) {
		List<ComponenteDTO> monitores = subcomponentesPc.keySet().stream()
																.filter(k->k.getTipo().equalsIgnoreCase("monitor")) 
												                .toList();
		
		for(ComponenteDTO monI:monitores) {
			int nMonitores = subcomponentesPc.get(monI);
			for(int i=0; i<nMonitores; i++) {
				builder = builder.agregarMonitor(monI.getIdComponente(), 
												monI.getDescripcion(), 
												monI.getMarca(), 
												monI.getModelo(), 
												monI.getCosto(), 
												monI.getPrecioBase() 
						);
			}
		}
		return builder;
	}

	private PcBuilder agregarDiscosAlBuilder(Map<ComponenteDTO, Integer> subcomponentesPc, PcBuilder builder) {
		List<ComponenteDTO> discos = subcomponentesPc.keySet().stream()
                                                              .filter(k->k.getTipo().equalsIgnoreCase("disco-duro")) 
                                                              .toList();
		for(ComponenteDTO discoI:discos) {
			int nDiscos = subcomponentesPc.get(discoI);
			for(int i=0; i<nDiscos; i++) {
				builder = builder.agregarDisco(discoI.getIdComponente(), 
												discoI.getDescripcion(), 
												discoI.getMarca(), 
												discoI.getModelo(), 
												discoI.getCosto(), 
												discoI.getPrecioBase(), 
												discoI.getCapacidadAlm());
			}
		}
		return builder;
	}

	private PcBuilder agregarTarjetasAlBuilder(Map<ComponenteDTO, Integer> subcomponentesPc, PcBuilder builder) {
		List<ComponenteDTO> tarjetas = subcomponentesPc.keySet().stream()
				                                                .filter(k->k.getTipo().equalsIgnoreCase("tarjeta-video")) 
				                                                .toList();
		for(ComponenteDTO tarjetaI:tarjetas) {
			int nTarjetas = subcomponentesPc.get(tarjetaI);
			for(int i=0; i<nTarjetas; i++) {
				builder = builder.agregarTarjetaVideo(tarjetaI.getIdComponente(), 
													  tarjetaI.getDescripcion(), 
													  tarjetaI.getMarca(), 
													  tarjetaI.getModelo(), 
												      tarjetaI.getCosto(), 
													  tarjetaI.getPrecioBase(), 
													  tarjetaI.getMemoria());
			}
		}
		return builder;
	}

	private void asignarPromocion(Componente componente, Long numPromocion) {
		if(numPromocion == null) {
			componente.setPromo(null);
			return;
		}
		try {
			PromocionBuilder builderPromo = new PromocionBuilder();
			List<DetallePromocionDTO> detsPromo = this.gestorPersistencia.getDetallesPromocion(numPromocion);
			
			builderPromo = configurarPromoBaseEnBuilder(builderPromo, detsPromo);
			builderPromo = configurarPromosAdicionalesEnBuilder(builderPromo, detsPromo);
			Promocion promo = builderPromo.build();
			
			componente.setPromo(promo);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			componente.setPromo(null);
		}
	}

	private PromocionBuilder configurarPromosAdicionalesEnBuilder(PromocionBuilder builderPromo,
			List<DetallePromocionDTO> detsPromo) throws Exception {
		List<DetallePromocionDTO> detsPromosAdicionales = 
				detsPromo.stream()
						.filter(detPrI -> detPrI.getEsBase() == false)
						.sorted((a,b)-> a.getNumDetPromocion() >= b.getNumDetPromocion() ? 1 : -1)
						.toList();
		
		for(DetallePromocionDTO detPromI : detsPromosAdicionales) {
			switch(detPromI.getTipoPromAcumulable()) {
				case "dscto-plano" -> {
					float dsctoPlano = detPromI.getPorcDsctoPlano().floatValue();
					builderPromo = builderPromo.agregarDsctoPlano(dsctoPlano);
				}
				case "dscto-cantidad" ->{
					Map<Integer,Double> mapCantVsDscto = getMapaDesctos(detPromI.getNumPromocion(),detPromI.getNumDetPromocion());
					builderPromo = builderPromo.agregarDsctoXcantidad(mapCantVsDscto);
				}
			}
		}
		return builderPromo;
	}

	private PromocionBuilder configurarPromoBaseEnBuilder(PromocionBuilder builderPromo,
			List<DetallePromocionDTO> detsPromo) {
		List<DetallePromocionDTO> detsPromoBase = detsPromo.stream()
		        .filter(detPrI -> detPrI.getEsBase())
		        .toList();
		if(detsPromoBase.size() == 0) {
			builderPromo = builderPromo.conPromocionBaseSinDscto();
		}
		else {
			DetallePromocionDTO promoBase = detsPromoBase.get(0);
			builderPromo = builderPromo.conPromocionBaseNXM(promoBase.getLleveN(), promoBase.getPagueM());
		}
		return builderPromo;
	}

	private Map<Integer, Double> getMapaDesctos(long numProm, int numDetProm) throws Exception {
		Map<Integer, Double> mapDsctos = new TreeMap<>();
		List<DetallePromoDsctoXCantDTO> detsDsctos = this.gestorPersistencia.getDesctosPromocion(numProm, numDetProm);
		detsDsctos.stream()
		          .sorted((a,b)->a.getNumDscto() >= b.getNumDscto() ? 1 : -1)
		          .forEach(dsctoI->mapDsctos.put(dsctoI.getCantidad(), dsctoI.getDscto()));
		return mapDsctos;
	}

}
