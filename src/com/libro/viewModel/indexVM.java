package com.libro.viewModel;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

public class indexVM {
	private boolean inicio;
	private boolean aspectosgenerales;
	HttpSession seshttp;
	
	public boolean isInicio() {
		return inicio;
	}
	public void setInicio(boolean inicio) {
		this.inicio = inicio;
	}
	public boolean isAspectosgenerales() {
		return aspectosgenerales;
	}
	public void setAspectosgenerales(boolean aspectosgenerales) {
		this.aspectosgenerales = aspectosgenerales;
	}


	@Init
	public void iniVM(){
		this.inicio=true;
		this.aspectosgenerales=false;
		seshttp=(HttpSession)Sessions.getCurrent().getNativeSession();
	}
	
	@GlobalCommand
	@NotifyChange({"aspectosgenerales","inicio"})
	public void changeInterface(@BindingParam("id")String id){
		System.out.println("Se cambiara a "+id);
		if(id.equals("btncontinuar")){
			this.inicio=false;
			this.aspectosgenerales=true;
		}else if(id.equals("btninicio")){
			Executions.getCurrent().sendRedirect("/");
		}else if(id.equals("btnaceptar")){
			Executions.getCurrent().sendRedirect("/formReclamo.zul");
			seshttp.setAttribute("valido", true);
		}
	}
}
