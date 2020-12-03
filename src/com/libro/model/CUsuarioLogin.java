package com.libro.model;

import java.io.Serializable;
import java.util.Date;

public class CUsuarioLogin implements Serializable{
	//======ATRIBUTOS====
	private static final long serialVersionUID = 1L;
	private String cResultado;
    private String cMensaje;
    private String cUsuarioCod;
    private String cClave;
    private String imgUsuario;
    private int nPerfilCod;
    private Date dFechaInicio;
    private Date dFechaFin;
    private String cNroDoc;
    private String cNombres;
    private String cSexo;
    private Date dFechaNac;
    private String apellidos;
    private String cCorreo;
    private String cCelular;
    private boolean bEstado;
    /***********************/
    private boolean conPerfilExistente;
    private boolean conPerfilNuevo;
    //=====GETTER AND SETTER====
	public String getcResultado() {
		return cResultado;
	}
	public void setcResultado(String cResultado) {
		this.cResultado = cResultado;
	}
	public String getcMensaje() {
		return cMensaje;
	}
	public void setcMensaje(String cMensaje) {
		this.cMensaje = cMensaje;
	}
	public String getcUsuarioCod() {
		return cUsuarioCod;
	}
	public void setcUsuarioCod(String cUsuarioCod) {
		this.cUsuarioCod = cUsuarioCod;
	}
	public String getcClave() {
		return cClave;
	}
	public void setcClave(String cClave) {
		this.cClave = cClave;
	}
	public int getnPerfilCod() {
		return nPerfilCod;
	}
	public void setnPerfilCod(int nPerfilCod) {
		this.nPerfilCod = nPerfilCod;
	}
	public Date getdFechaInicio() {
		return dFechaInicio;
	}
	public void setdFechaInicio(Date dFechaInicio) {
		this.dFechaInicio = dFechaInicio;
	}
	public Date getdFechaFin() {
		return dFechaFin;
	}
	public void setdFechaFin(Date dFechaFin) {
		this.dFechaFin = dFechaFin;
	}
	public String getcNroDoc() {
		return cNroDoc;
	}
	public void setcNroDoc(String cNroDoc) {
		this.cNroDoc = cNroDoc;
	}
	public Date getdFechaNac() {
		return dFechaNac;
	}
	public void setdFechaNac(Date date) {
		this.dFechaNac = date;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
    public String getcNombres() {
		return cNombres;
	}
	public void setcNombres(String cNombres) {
		this.cNombres = cNombres;
	}
	
	public String getImgUsuario() {
		return imgUsuario;
	}
	public void setImgUsuario(String imgUsuario) {
		this.imgUsuario = imgUsuario;
	}
	public String getcSexo() {
		return cSexo;
	}
	public void setcSexo(String cSexo) {
		this.cSexo = cSexo;
	}
	public boolean isConPerfilExistente() {
		return conPerfilExistente;
	}
	public void setConPerfilExistente(boolean conPerfilExistente) {
		this.conPerfilExistente = conPerfilExistente;
	}
	public boolean isConPerfilNuevo() {
		return conPerfilNuevo;
	}
	public void setConPerfilNuevo(boolean conPerfilNuevo) {
		this.conPerfilNuevo = conPerfilNuevo;
	}
	public String getcCorreo() {
		return cCorreo;
	}
	public void setcCorreo(String cCorreo) {
		this.cCorreo = cCorreo;
	}
	public boolean isbEstado() {
		return bEstado;
	}
	public void setbEstado(boolean bEstado) {
		this.bEstado = bEstado;
	}
	public String getcCelular() {
		return cCelular;
	}
	public void setcCelular(String cCelular) {
		this.cCelular = cCelular;
	}
	//=====CONSTRUCTORES=====
	public CUsuarioLogin()
	{
		this.cResultado ="";
		this.cMensaje ="";
		this.cUsuarioCod ="";
		this.cClave ="";
		this.nPerfilCod =0;
		this.cNroDoc ="";
		this.cNombres ="";
		this.cSexo ="";
		this.apellidos ="";
		this.dFechaNac=new Date();
		this.imgUsuario="";
		this.cCorreo="";
		this.cCelular="";
		/************/
		conPerfilExistente=true;
		conPerfilNuevo=false;
	}
	public CUsuarioLogin(String cUsuarioCod, String cClave, String imgUsuario,
			int nPerfilCod, String cNroDoc,
			String cNombres, String cSexo, Date dFechaNac, String apellidos,
			Date dFechaInicio,String cCorreo, boolean bEstado,String celular) {
		this.cUsuarioCod = cUsuarioCod;
		this.cClave = cClave;
		this.imgUsuario = imgUsuario;
		this.nPerfilCod = nPerfilCod;
		this.dFechaInicio = dFechaInicio;
		this.cNroDoc = cNroDoc;
		this.cNombres = cNombres;
		this.cSexo = cSexo;
		this.dFechaNac = dFechaNac;
		this.apellidos = apellidos;
		this.cCorreo = cCorreo;
		this.bEstado = bEstado;
		this.cCelular=celular;
	}
}
