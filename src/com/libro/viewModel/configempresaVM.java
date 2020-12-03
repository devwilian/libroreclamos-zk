package com.libro.viewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;

import com.libro.model.*;
import com.libro.dao.*;

public class configempresaVM {
	private CEmpresa empresa;
	private CempresaDAO empresaDAO;
	private boolean editable;
	
	@Init
	public void initVM(){
		editable=false;
		empresa=new CEmpresa();
		empresaDAO=new CempresaDAO();
		empresaDAO.pasarDatos(empresaDAO.recuperarDatosEmpresa());
		empresa=empresaDAO.getEmpresa();
	}
	@Command
	public void actualizar(@BindingParam("comp")Component comp){
		if(esValido(comp)){
			if (empresaDAO.isOperationCorrect(empresaDAO.insertarDatosEmpresa(empresa))) {
				Clients.showNotification("Se actualizo los datos de la empresa", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2500);
				editable=false;
				BindUtils.postNotifyChange(null, null, this, "editable");
			}else{
				Clients.showNotification("Error en actualizar datos de la empresa", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2500);
			}
		}
	}
	@Command
	@NotifyChange({"editable"})
	public void editar(){
		editable=!editable;
	}
	@Command
	public void changeNroTelefono(@BindingParam("valor")String valor,@BindingParam("opcion")int opcion,@BindingParam("comp")Component comp){
		if (telfValido(valor)) {
			if(opcion==1){
				empresa.setTelefono1(valor);
			}else if(opcion==2){
				empresa.setTelefono2(valor);
			}
		}else{
			Clients.showNotification("Error en el telefono", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
	}
	@Command
	public void changeCorreo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if (mailValido(valor)) {
			empresa.setCorreo(valor);
		}else{
			Clients.showNotification("Error en el correo", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
	}
	public boolean esValido(Component comp){
		boolean valido=true;
		if(!telfValido(empresa.getTelefono1())){
			valido=false;
			Clients.showNotification("Error en el Telefono 1", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(!telfValido(empresa.getTelefono2())){
			valido=false;
			Clients.showNotification("Error en el Telefono 2", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(!mailValido(empresa.getCorreo())){
			valido=false;
			Clients.showNotification("Error en el correo", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		return valido;
	}
	public boolean telfValido(String telf)
	{
		boolean correcto=true;
		for(int i=0;i<telf.length();i++)
		{
			if((telf.charAt(i)<'0' || telf.charAt(i)>'9') && telf.charAt(i)!=' ' && telf.charAt(i)!='-' && telf.charAt(i)!='+')
			{
				correcto=false;
				break;
			}
		}
		return correcto;
	}
	public boolean validoText(String nombre)
	{
		System.out.println("Este es el nombre del contacto--> "+nombre);
		boolean correcto=true;
		for(int i=0;i<nombre.length();i++)
		{
			if((nombre.charAt(i)<'A' || nombre.charAt(i)>'Z') && nombre.charAt(i)!=' ' &&
					nombre.charAt(i)!='\u00c7' && nombre.charAt(i)!='\u00c3' && 
					nombre.charAt(i)!='\u00c2' && nombre.charAt(i)!='\u00c1' &&
					nombre.charAt(i)!='\u00c0' && nombre.charAt(i)!='\u00d5' &&
					nombre.charAt(i)!='\u00d4' && nombre.charAt(i)!='\u00d3' &&
					nombre.charAt(i)!='\u00ca' && nombre.charAt(i)!='\u00c9' &&
					nombre.charAt(i)!='\u00cd' && nombre.charAt(i)!='\u00c3' &&
					nombre.charAt(i)!='\u00da' && nombre.charAt(i)!='\u00d1' &&
					nombre.charAt(i)!='\u00f1')
			{
				correcto=false;
				break;
			}
		}
		return correcto;
	}
	public boolean mailValido(String mail)
	{
		 Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	       Matcher mat = pat.matcher(mail);
	       if(mat.find()){
	          return true;
	       }else
	          return false;
	}
	public CEmpresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(CEmpresa empresa) {
		this.empresa = empresa;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
