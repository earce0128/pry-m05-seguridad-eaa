package mx.com.qtx.cotizadorv1ds.web.api.jaxrs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	private static Logger bitacora = LoggerFactory.getLogger(GenericExceptionMapper.class);
	
	@Override
	public Response toResponse(Throwable exception) {
		bitacora.trace("toResponse");
		bitacora.debug(exception.getClass().getName() + ":[" +  exception.getMessage() + "]");
		
		// Construir respuesta Rest para el cliente
		RespuestaError respuestaError = new RespuestaError(
				Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
				"Servicio no disponible temporalmente"
			);
		
		Response respuesta = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
									 .entity(respuestaError)
									 .type(MediaType.APPLICATION_JSON)
									 .build();
		
		// Armar descripción para la Bitácora
		mostrarErrorEnBitacora(exception);
		return respuesta;
	}
	
	public void mostrarErrorEnBitacora(Throwable ex) {
		bitacora.error(ex.getClass().getName() + " [" + ex.getMessage() + "]");
		bitacora.error("Pila de ejecución:");
		List.of(ex.getStackTrace()).stream()
								   .filter(stI->stI.getClassName().contains("mx.com.qtx"))
								   .forEach(stI->bitacora.error(stI.getClassName() + "." 
										   + stI.getMethodName() 
										   + "(), linea " + stI.getLineNumber()));
		if(ex.getCause() == null) 
			return;
		bitacora.error("Causada por:");
		mostrarErrorEnBitacora(ex.getCause());
		
	}

}
