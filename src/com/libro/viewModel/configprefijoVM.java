package com.libro.viewModel;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;

import com.libro.dao.CreclamoDAO;

public class configprefijoVM {
	private CreclamoDAO recla;
	private String prefijo;
	@Init
	public void initVM(){
		prefijo="";
		recla=new CreclamoDAO();
		prefijo=recla.recuperarPrefijo(recla.recuperarPrimeraVez());
	}
	@Command @NotifyChange({"visibleprimeravez"})
	public void insertarPrefijo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
//		CreclamoDAO recla=new CreclamoDAO();
//		if(recla.isOperationCorrect(recla.insertarPrefijoCodigo(valor.trim().toUpperCase()))){
//			Clients.showNotification("SE GUARDO EL PREFIJO DE LA BASE DE DATOS CON "+valor.toUpperCase(), Clients.NOTIFICATION_TYPE_INFO,comp, "middle_center", 3000);
//		}
		if (valor.trim()!="") {
			CreclamoDAO recla=new CreclamoDAO();
			//CreclamoDAO recla=new CreclamoDAO();
			if(recla.isOperationCorrect(recla.insertarPrefijoCodigo(valor.trim().toUpperCase()))){
				Clients.showNotification("SE GUARDO EL PREFIJO DE LA BASE DE DATOS CON "+valor.toUpperCase(), Clients.NOTIFICATION_TYPE_INFO,null, "top_center", 3000);
			}else{
				Clients.showNotification("ESTAMOS PRESENCIANDO INCONVENIENTES CON NUESTRA BASE DE DATOS POR FAVOR INTENTE MAS TARDE"+valor.toUpperCase(), Clients.NOTIFICATION_TYPE_INFO,null, "top_center", 3000);
			}
		}else{
			Clients.showNotification("Ingrese un prefijo valido!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_center", 2500);
		}
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
}
