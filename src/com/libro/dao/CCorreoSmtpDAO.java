package com.libro.dao;

import java.util.List;
import java.util.Map;

import com.libro.model.*;

public class CCorreoSmtpDAO extends CConexion
{
	private CCorreoSMTP oCorreoSmtp;
	/*****************/
	public CCorreoSMTP getoCorreoSmtp() {
		return oCorreoSmtp;
	}
	public void setoCorreoSmtp(CCorreoSMTP oCorreoSmtp) {
		this.oCorreoSmtp = oCorreoSmtp;
	}
	/*****************/
	public CCorreoSmtpDAO() {
		// TODO Auto-generated constructor stub
		super();
	}
	/*****************/
	public List insertarCorreoSMTP(CCorreoSMTP correo)
	{
		Object[] values={
				correo.getcSMTPHost(),
				correo.getnSMTPPort(),
				correo.isbSSL(),
				correo.isbTLS(),
				correo.getcSMTPUserName(),
				correo.getcSMTPPassword()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarcorreosmtp", values);
	}
	public List recuperarCorreoSmtpDB()
	{
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarcorreosmtp");
	}
	public void asignarConfiguracionCorreoSMTP(List lista)
	{
		if(!lista.isEmpty())
		{
			Map row=(Map)lista.get(0);
			oCorreoSmtp=new CCorreoSMTP((int)row.get("idcorreosmtp"),
					(String)row.get("smptphost"),(int)row.get("smtpport"),
					(boolean)row.get("ssl"),(boolean)row.get("tls"),
					(String)row.get("smtpusername"),(String)row.get("smtppassword"));
		}else
			oCorreoSmtp=new CCorreoSMTP();
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
}
