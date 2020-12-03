package com.libro.model;

import java.util.*;
import java.text.SimpleDateFormat;
public class CListaReclamo {
	private String codreclamo;
	private String nombres;
	private String apellidos;
	private String correo;
	private String razonsocial;
	private String fecha;
	private String fechareclamo;
	private String tipoproblema;
	private String sedeocurrencia;
	private String areaocurrencia;
	private String detallereclamo;
	private String solicitudreclamo;
	private boolean docmultimedia;
	private boolean recibido;
	private boolean proceso;
	private boolean solucionado;
	public String getFechareclamo() {
		return fechareclamo;
	}
	public void setFechareclamo(String fechareclamo) {
		this.fechareclamo = fechareclamo;
	}
	public String getRazonsocial() {
		return razonsocial;
	}
	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}
	public String getCodreclamo() {
		return codreclamo;
	}
	public void setCodreclamo(String codreclamo) {
		this.codreclamo = codreclamo;
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipoproblema() {
		return tipoproblema;
	}
	public void setTipoproblema(String tipoproblema) {
		this.tipoproblema = tipoproblema;
	}
	public String getSedeocurrencia() {
		return sedeocurrencia;
	}
	public void setSedeocurrencia(String sedeocurrencia) {
		this.sedeocurrencia = sedeocurrencia;
	}
	public String getAreaocurrencia() {
		return areaocurrencia;
	}
	public void setAreaocurrencia(String areaocurrencia) {
		this.areaocurrencia = areaocurrencia;
	}
	public String getDetallereclamo() {
		return detallereclamo;
	}
	public void setDetallereclamo(String detallereclamo) {
		this.detallereclamo = detallereclamo;
	}
	public String getSolicitudreclamo() {
		return solicitudreclamo;
	}
	public void setSolicitudreclamo(String solicitudreclamo) {
		this.solicitudreclamo = solicitudreclamo;
	}
	public boolean isDocmultimedia() {
		return docmultimedia;
	}
	public void setDocmultimedia(boolean docmultimedia) {
		this.docmultimedia = docmultimedia;
	}
	public boolean isRecibido() {
		return recibido;
	}
	public void setRecibido(boolean recibido) {
		this.recibido = recibido;
	}
	public boolean isProceso() {
		return proceso;
	}
	public void setProceso(boolean proceso) {
		this.proceso = proceso;
	}
	public boolean isSolucionado() {
		return solucionado;
	}
	public void setSolucionado(boolean solucionado) {
		this.solucionado = solucionado;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public CListaReclamo() {
		super();
		this.codreclamo = "";
		this.nombres = "";
		this.apellidos = "";
		this.correo="";
		this.fecha = "";
		this.fechareclamo = "";
		this.tipoproblema = "";
		this.sedeocurrencia = "";
		this.areaocurrencia = "";
		this.detallereclamo = "";
		this.solicitudreclamo = "";
		this.docmultimedia = false;
		this.recibido = false;
		this.proceso = false;
		this.solucionado = false;
		this.razonsocial="";
	}
	
	public CListaReclamo(String codreclamo, String nombres, String apellidos,String prazonsocial,String correo, Date fecha,Date fecharecla, String tipoproblema,
			String sedeocurrencia, String areaocurrencia, String detallereclamo, String solicitudreclamo,
			boolean docmultimedia, boolean recibido, boolean proceso, boolean solucionado) {
		super();
		this.codreclamo = codreclamo;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.razonsocial=prazonsocial;
		this.correo=correo;
		String newstring = new SimpleDateFormat("dd-MM-yyyy").format(fecha);
		this.fecha = newstring;
		this.fechareclamo=new SimpleDateFormat("dd-MM-yyyy").format(fecharecla);
		this.tipoproblema = tipoproblema;
		this.sedeocurrencia = sedeocurrencia;
		this.areaocurrencia = areaocurrencia;
		this.detallereclamo = detallereclamo;
		this.solicitudreclamo = solicitudreclamo;
		this.docmultimedia = docmultimedia;
		this.recibido = recibido;
		this.proceso = proceso;
		this.solucionado = solucionado;
	}
}
