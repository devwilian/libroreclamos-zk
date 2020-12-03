package com.libro.model;

public class CPerfil {
	private int idperfil;
	private String descripcion;
	private int idacceso;
	public int getIdacceso() {
		return idacceso;
	}
	public void setIdacceso(int idacceso) {
		this.idacceso = idacceso;
	}
	public int getIdperfil() {
		return idperfil;
	}
	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CPerfil() {
		super();
		this.idperfil = 0;
		this.descripcion = "";
		this.idacceso=0;
	}
	public CPerfil(int idperfil, String descripcion,int acceso) {
		super();
		this.idperfil = idperfil;
		this.descripcion = descripcion;
		this.idacceso=acceso;
	}
}
