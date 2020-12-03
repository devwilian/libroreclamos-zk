package com.libro.model;

public class CSeguimiento {
	private String detalleSolucionado;
	private String detalleSolucion;
	private String urlDoc;
	private boolean proceso;
	private boolean recibido;
	private boolean solucionado;
	private int codSolucion;
	
	public int getCodSolucion() {
		return codSolucion;
	}
	public void setCodSolucion(int codSolucion) {
		this.codSolucion = codSolucion;
	}
	public boolean isProceso() {
		return proceso;
	}
	public void setProceso(boolean proceso) {
		this.proceso = proceso;
	}
	public boolean isRecibido() {
		return recibido;
	}
	public void setRecibido(boolean recibido) {
		this.recibido = recibido;
	}
	public boolean isSolucionado() {
		return solucionado;
	}
	public void setSolucionado(boolean solucionado) {
		this.solucionado = solucionado;
	}
	public String getDetalleSolucionado() {
		return detalleSolucionado;
	}
	public void setDetalleSolucionado(String detalleSolucionado) {
		this.detalleSolucionado = detalleSolucionado;
	}
	public String getDetalleSolucion() {
		return detalleSolucion;
	}
	public void setDetalleSolucion(String detalleSolucion) {
		this.detalleSolucion = detalleSolucion;
	}
	public String getUrlDoc() {
		return urlDoc;
	}
	public void setUrlDoc(String urlDoc) {
		this.urlDoc = urlDoc;
	}
	public CSeguimiento(){
		detalleSolucionado="";
		detalleSolucion="";
		urlDoc="";
		codSolucion=0;
		recibido=false;
		proceso=false;
		solucionado=false;
	}
	public CSeguimiento(String pdetalleSolucionado,String pdetalleSolucion,String purlDoc){
		detalleSolucionado=pdetalleSolucionado;
		detalleSolucion=pdetalleSolucion;
		urlDoc=purlDoc;
	}
	public CSeguimiento(boolean precibido,boolean pproceso,boolean psolucionado,int cod){
		recibido=precibido;
		proceso=pproceso;
		solucionado=psolucionado;
		codSolucion=cod;
	}
}
