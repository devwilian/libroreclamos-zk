package com.libro.model;

public class CReportesede {
	private String nombresede;
	private String estilo;
	private int cantidad;
	public String getEstilo() {
		return estilo;
	}
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	public String getNombresede() {
		return nombresede;
	}
	public void setNombresede(String nombresede) {
		this.nombresede = nombresede;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public CReportesede(String nombresede, int cantidad) {
		this.nombresede = nombresede;
		this.cantidad = cantidad;
	}
	public CReportesede() {
		this.nombresede = "";
		this.cantidad = 0;
	}
}
