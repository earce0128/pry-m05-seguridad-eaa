package mx.com.qtx.cotizadorv1ds.seguridad.entidades;

public class TokenJWT {
	private String token;

	public TokenJWT(String token) {
		super();
		this.token = token;
	}

	public TokenJWT() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
