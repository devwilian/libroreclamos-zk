package com.libro.model;
import java.text.SimpleDateFormat;
import java.util.*;

public class CListaResueltos {
	private int idsolucionado;
	private String idreclamo;
	private String nombres;
	private String apellidos;
	private String fecha;
	private String tipo;
	private String solucion;
	private String detalles;
	private String razonsocial;
	private String idusuario;
	public String getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}
	public String getIdreclamo() {
		return idreclamo;
	}
	public void setIdreclamo(String idreclamo) {
		this.idreclamo = idreclamo;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getRazonsocial() {
		return razonsocial;
	}
	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}
	public int getIdsolucionado() {
		return idsolucionado;
	}
	public void setIdsolucionado(int idsolucionado) {
		this.idsolucionado = idsolucionado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSolucion() {
		return solucion;
	}
	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	public CListaResueltos() {
		super();
		this.idsolucionado = 0;
		this.nombres="";
		this.apellidos = "";
		this.fecha = "";
		this.tipo = "";
		this.solucion = "";
		this.detalles = "";
		this.razonsocial="";
		this.idreclamo="";
		this.idusuario="";
	}
	public CListaResueltos(int idsolucionado,String pidreclamo, String pnombres,String papellidos,String razonsocial, Date fecha, String tipo, String solucion,
			String detalles) {
		super();
		this.idreclamo=pidreclamo;
		this.idsolucionado = idsolucionado;
		this.nombres=pnombres;
		this.apellidos = papellidos;
		this.razonsocial=razonsocial;
		this.fecha = new SimpleDateFormat("yyyy-MM-dd").format(fecha);;
		this.tipo = tipo;
		this.solucion = solucion;
		this.detalles = detalles;
		this.idusuario="";
	}
	public CListaResueltos(int idsolucionado,String pidreclamo, String pnombres,String papellidos,String razonsocial, Date fecha, String tipo, String solucion,
			String detalles,String idusuario) {
		super();
		this.idreclamo=pidreclamo;
		this.idsolucionado = idsolucionado;
		this.nombres=pnombres;
		this.apellidos = papellidos;
		this.razonsocial=razonsocial;
		this.fecha = new SimpleDateFormat("yyyy-MM-dd").format(fecha);;
		this.tipo = tipo;
		this.solucion = solucion;
		this.detalles = detalles;
		this.idusuario=idusuario;
	}
}
