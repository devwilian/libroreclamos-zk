package com.libro.viewModel;

import org.zkoss.bind.annotation.*;

import com.libro.dao.*;
import com.libro.model.*;


public class inicioVM {
	private CempresaDAO empresaDAO;
	private CEmpresa empresa;
	private boolean seguimiento;
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
