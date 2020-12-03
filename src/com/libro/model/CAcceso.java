package com.libro.model;

public class CAcceso {
	private int idacceso;
	private boolean accesoidioma;
	private boolean accesoregusuario;
	private boolean accesoreportes;
	private boolean accesorecibidos;
	private boolean accesoprocesos;
	private boolean accesoconfiguracion;
	private boolean accesolistausuario;
	public boolean isAccesolistausuario() {
		return accesolistausuario;
	}
	public void setAccesolistausuario(boolean accesolistausuario) {
		this.accesolistausuario = accesolistausuario;
	}
	public boolean isAccesoconfiguracion() {
		return accesoconfiguracion;
	}
	public void setAccesoconfiguracion(boolean accesoconfiguracion) {
		this.accesoconfiguracion = accesoconfiguracion;
	}
	public boolean isAccesorecibidos() {
		return accesorecibidos;
	}
	public void setAccesorecibidos(boolean accesorecibidos) {
		this.accesorecibidos = accesorecibidos;
	}
	public boolean isAccesoprocesos() {
		return accesoprocesos;
	}
	public void setAccesoprocesos(boolean accesoprocesos) {
		this.accesoprocesos = accesoprocesos;
	}
	public int getIdacceso() {
		return idacceso;
	}
	public void setIdacceso(int idacceso) {
		this.idacceso = idacceso;
	}
	public boolean isAccesoidioma() {
		return accesoidioma;
	}
	public void setAccesoidioma(boolean accesoidioma) {
		this.accesoidioma = accesoidioma;
	}
	public boolean isAccesoregusuario() {
		return accesoregusuario;
	}
	public void setAccesoregusuario(boolean accesoregusuario) {
		this.accesoregusuario = accesoregusuario;
	}
	public boolean isAccesoreportes() {
		return accesoreportes;
	}
	public void setAccesoreportes(boolean accesoreportes) {
		this.accesoreportes = accesoreportes;
	}
	public CAcceso(){
		idacceso=0;
		accesoidioma=false;
		accesoregusuario=false;
		accesoreportes=false;
		accesorecibidos=false;
		accesoprocesos=false;
		accesoconfiguracion=false;
		accesolistausuario=false;
	}
	public CAcceso(int pidacceso,boolean idioma,boolean regusuario,boolean reportes,boolean recibido,boolean proceso,boolean configuracion,boolean listusuario){
		idacceso=pidacceso;
		accesoidioma=idioma;
		accesoregusuario=regusuario;
		accesoreportes=reportes;
		accesorecibidos=recibido;
		accesoprocesos=proceso;
		accesoconfiguracion=configuracion;
		accesolistausuario=listusuario;
	}
}
