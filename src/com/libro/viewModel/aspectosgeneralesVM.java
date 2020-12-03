package com.libro.viewModel;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Sessions;

import com.libro.dao.CempresaDAO;
import com.libro.model.CEmpresa;

public class aspectosgeneralesVM {
	private CempresaDAO empresaDAO;
	private CEmpresa empresa;
	private boolean seguimiento;
	HttpSession seshttp;
	
	public boolean isSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(boolean seguimiento) {
		this.seguimiento = seguimiento;
	}
	@Init
	public void initVM(){
		seguimiento=false;
		
		empresaDAO=new CempresaDAO();
		empresa=new CEmpresa();
		
		empresaDAO.pasarDatos(empresaDAO.recuperarDatosEmpresa());
		empresa=empresaDAO.getEmpresa();
		
		seshttp=(HttpSession)Sessions.getCurrent().getNativeSession();
	}
	@Command
	@NotifyChange({"seguimiento"})
	public void iraSeguimiento(){
		seguimiento=true;
	}
	@GlobalCommand
	@NotifyChange({"seguimiento"})
	public void botonaceptar(){
		seguimiento=false;
	}
	public CEmpresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(CEmpresa empresa) {
		this.empresa = empresa;
	}
}
