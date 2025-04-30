package com.desafio.user.dto;

public class ErrorResponse {
	private String mensaje;

    public ErrorResponse(String mensaje)
    {
        super();
        this.mensaje = mensaje;
    }

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
