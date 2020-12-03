package com.libro.model;

import java.util.ArrayList;
import java.util.Date;

public class CReclamo {
	private String idreclamo;
	private int tipodocumento;
	private int solicitante;
	private int tipoproblema;
	private int sedeocurrencia;
	private int areaocurrencia;
	private Date fecha;
	private Date fechareclamo;
	private String detallereclamo;
	private String solicitudreclamo;
	private boolean docmultimedia;
	private ArrayList<CGaleria> listaImagenes;
	private boolean recibido;
	private boolean proceso;
	private boolean solucionado;
	
	//contructores
	public ArrayList<CGaleria> getListaImagenes() {
		return listaImagenes;
	}
	public Date getFechareclamo() {
		return fechareclamo;
	}
	public void setFechareclamo(Date fechareclamo) {
		this.fechareclamo = fechareclamo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	public void setListaImagenes(ArrayList<CGaleria> listaImagenes) {
		this.listaImagenes = listaImagenes;
	}
	public int getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(int tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public String getIdreclamo() {
		return idreclamo;
	}
	public void setIdreclamo(String idreclamo) {
		this.idreclamo = idreclamo;
	}
	public int getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(int solicitante) {
		this.solicitante = solicitante;
	}
	public int getTipoproblema() {
		return tipoproblema;
	}
	public void setTipoproblema(int tipoproblema) {
		this.tipoproblema = tipoproblema;
	}
	public int getSedeocurrencia() {
		return sedeocurrencia;
	}
	public void setSedeocurrencia(int sedeocurrencia) {
		this.sedeocurrencia = sedeocurrencia;
	}
	public int getAreaocurrencia() {
		return areaocurrencia;
	}
	public void setAreaocurrencia(int areaocurrencia) {
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
	//metodos
	public CReclamo(){
		idreclamo="";
		solicitante=0;
		tipoproblema=0;
		sedeocurrencia=0;
		areaocurrencia=0;
		listaImagenes=new ArrayList<CGaleria>();
		fecha=new Date();
		fechareclamo=new Date();
		detallereclamo="";
		solicitudreclamo="";
		docmultimedia=false;
		recibido=false;
		proceso=false;
		solucionado=false;
	}
	public CReclamo(String pidreclamo,int psolicitante,int ptipoproblema,
			int psedeocurrencia,int pareaocurrencia,
			Date pfecha,String pdetallereclamo,
			String psolicitudreclamo,boolean pdocmultimedia,
			boolean precibido,boolean pproceso,boolean psolucionado,Date pfechareclamo)
	{
		idreclamo=pidreclamo;
		solicitante=psolicitante;
		tipoproblema=ptipoproblema;
		sedeocurrencia=psedeocurrencia;
		areaocurrencia=pareaocurrencia;
		fecha=pfecha;
		fechareclamo=pfechareclamo;
		detallereclamo=pdetallereclamo;
		solicitudreclamo=psolicitudreclamo;
		docmultimedia=pdocmultimedia;
		listaImagenes=new ArrayList<CGaleria>();
		recibido=precibido;
		proceso=pproceso;
		solucionado=psolucionado;
	}
}
