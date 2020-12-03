package com.libro.model;

public class CCorreoSMTP 
{
	private int nCorreoSMTPCod;// int,
	private String cSMTPHost;// varchar(200),
	private int nSMTPPort;// int,
	private boolean bSSL;// boolean,
	private boolean bTLS;// boolean,
	private String cSMTPUserName;// varchar(200),
	private String cSMTPPassword;// varchar(200),
	private boolean editable;
	private String cifrado;
	/************************/
	public int getnCorreoSMTPCod() {
		return nCorreoSMTPCod;
	}
	public void setnCorreoSMTPCod(int nCorreoSMTPCod) {
		this.nCorreoSMTPCod = nCorreoSMTPCod;
	}
	public String getcSMTPHost() {
		return cSMTPHost;
	}
	public void setcSMTPHost(String cSMTPHost) {
		this.cSMTPHost = cSMTPHost;
	}
	public int getnSMTPPort() {
		return nSMTPPort;
	}
	public void setnSMTPPort(int nSMTPPort) {
		this.nSMTPPort = nSMTPPort;
	}
	public boolean isbSSL() {
		return bSSL;
	}
	public void setbSSL(boolean bSSL) {
		this.bSSL = bSSL;
	}
	public boolean isbTLS() {
		return bTLS;
	}
	public void setbTLS(boolean bTLS) {
		this.bTLS = bTLS;
	}
	public String getcSMTPUserName() {
		return cSMTPUserName;
	}
	public void setcSMTPUserName(String cSMTPUserName) {
		this.cSMTPUserName = cSMTPUserName;
	}
	public String getcSMTPPassword() {
		return cSMTPPassword;
	}
	public void setcSMTPPassword(String cSMTPPassword) {
		this.cSMTPPassword = cSMTPPassword;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getCifrado() {
		return cifrado;
	}
	public void setCifrado(String cifrado) {
		this.cifrado = cifrado;
	}
	/********************/
	public CCorreoSMTP() {
		// TODO Auto-generated constructor stub
		this.cSMTPHost="";
		this.cSMTPPassword="";
		this.cSMTPUserName="";
		this.bSSL=false;
		this.bTLS=false;
		this.nSMTPPort=0;
		this.editable=false;
		this.cifrado="";
	}
	public CCorreoSMTP(int nCorreoSMTPCod, String cSMTPHost, int nSMTPPort,
			boolean bSSL, boolean bTLS, String cSMTPUserName,
			String cSMTPPassword) {
		this.nCorreoSMTPCod = nCorreoSMTPCod;
		this.cSMTPHost = cSMTPHost;
		this.nSMTPPort = nSMTPPort;
		this.bSSL = bSSL;
		this.bTLS = bTLS;
		this.cSMTPUserName = cSMTPUserName;
		this.cSMTPPassword = cSMTPPassword;
		/***************/
		this.editable=false;
		if(bSSL)cifrado="SSL";
		else cifrado="TLS";
	}
}
