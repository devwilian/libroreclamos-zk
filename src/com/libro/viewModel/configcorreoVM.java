package com.libro.viewModel;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;

import com.libro.dao.CCorreoSmtpDAO;
import com.libro.model.CCorreoSMTP;

public class configcorreoVM {
	private boolean ver;
	private CCorreoSMTP correo;
	private CCorreoSMTP correoAux;
	private CCorreoSmtpDAO correoDAO;
	@Init
	public void initVM(){
		ver=false;
		correo=new CCorreoSMTP();
		correoAux=new CCorreoSMTP();
		correoDAO=new CCorreoSmtpDAO();
		correoDAO.asignarConfiguracionCorreoSMTP(correoDAO.recuperarCorreoSmtpDB());
		correo=correoDAO.getoCorreoSmtp();
		correoAux=correo;
	}

	@Command
	@NotifyChange({"ver"})
	public void editar(){
		ver=true;
	}
	@Command
	@NotifyChange({"ver"})
	public void cancelar(){
		ver=false;
	}
	@Command
	public void changeHost(@BindingParam("valor")String valor){
		if(!valor.trim().equals("")){
			correoAux.setcSMTPHost(valor);
		}
	}
	@Command
	public void changePort(@BindingParam("valor")String valor){
		if(!valor.trim().equals("")){
			correoAux.setnSMTPPort(Integer.parseInt(valor));
		}
	}
	@Command
	public void changeCifrado(@BindingParam("valor")String valor){
		if(valor.equals("ssl")){
			correoAux.setbSSL(true);
			correoAux.setbTLS(false);
		}else if(valor.equals("tls")){
			correoAux.setbSSL(false);
			correoAux.setbTLS(true);
		}
	}
	@Command
	public void changeUsername(@BindingParam("valor")String valor){
		if(!valor.trim().equals("")){
			correoAux.setcSMTPUserName(valor.toLowerCase());
		}
	}
	@Command
	public void changePassword(@BindingParam("valor")String valor){
		if(!valor.trim().equals("")){
			correoAux.setcSMTPPassword(valor);
		}
	}
	@Command
	public void actualizar(@BindingParam("comp")Component comp){
		if (isValido(correoAux, comp)) {
			if (correoDAO.isOperationCorrect(correoDAO.insertarCorreoSMTP(correoAux))) {
				Clients.showNotification("Se actualizo correctamente", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2500);
				ver=false;
				BindUtils.postNotifyChange(null, null, this, "ver");
			}else{
				Clients.showNotification("Error en la actualizacion", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2500);
			}
		}
	}
	public boolean isValido(CCorreoSMTP correo,Component comp){
		boolean valido=true;
		if(correo.getcSMTPHost()==""){
			valido=false;
			Clients.showNotification("Ingrese SMTP HOST", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(correo.getnSMTPPort()==0){
			valido=false;
			Clients.showNotification("Ingrese SMTP PORT", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(((correo.isbSSL() && correo.isbTLS()) || !(correo.isbSSL() || correo.isbTLS()))){
			valido=false;
			System.out.println("SSL "+correo.isbSSL()+" TLS "+correo.isbTLS());
			System.out.println((correo.isbSSL() && correo.isbTLS())+" <==> "+!(correo.isbSSL() || correo.isbTLS()));
			Clients.showNotification("Seleccione SSL o TLS", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if (correo.getcSMTPUserName()=="") {
			valido=false;
			Clients.showNotification("Ingrese Usuario", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if (correo.getcSMTPPassword()=="") {
			valido=false;
			Clients.showNotification("Ingrese Contrasenia", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		return valido;
	}
	public boolean isVer() {
		return ver;
	}
	public void setVer(boolean ver) {
		this.ver = ver;
	}
	public CCorreoSMTP getCorreo() {
		return correo;
	}
	public void setCorreo(CCorreoSMTP correo) {
		this.correo = correo;
	}
}
