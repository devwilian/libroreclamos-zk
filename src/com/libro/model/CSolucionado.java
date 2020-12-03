package com.libro.model;
import java.util.*;

public class CSolucionado {
	private int idsolucionado;
	private String idreclamo;
	private String doc;
	private String detalles;
	private Date fechasolucion;
	private int idsolucion;
	private String coduser;
	public String getCoduser() {
		return coduser;
	}
	public void setCoduser(String coduser) {
		this.coduser = coduser;
	}
	public int getIdsolucionado() {
		return idsolucionado;
	}
	public void setIdsolucionado(int idsolucionado) {
		this.idsolucionado = idsolucionado;
	}
	public String getIdreclamo() {
		return idreclamo;
	}
	public void setIdreclamo(String idreclamo) {
		this.idreclamo = idreclamo;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	public Date getFechasolucion() {
		return fechasolucion;
	}
	public void setFechasolucion(Date fechasolucion) {
		this.fechasolucion = fechasolucion;
	}
	public int getIdsolucion() {
		return idsolucion;
	}
	public void setIdsolucion(int idsolucion) {
		this.idsolucion = idsolucion;
	}
	public CSolucionado(){
		idsolucionado=0;
		idreclamo="";
		doc="";
		detalles="";
		fechasolucion=new Date();
		idsolucion=0;
		coduser="";
	}
	public CSolucionado(int pidsolcionado,String pidreclamo,String purldoc,String pdetalles,Date pfechasolucion,int pidsolucion,String pcoduser){
		idsolucionado=pidsolcionado;
		idreclamo=pidreclamo;
		doc=purldoc;
		detalles=pdetalles;
		fechasolucion=pfechasolucion;
		idsolucion=pidsolucion;
		coduser=pcoduser;
	}
}
