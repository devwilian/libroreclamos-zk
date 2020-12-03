package com.libro.viewModel;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import com.libro.dao.*;
import com.libro.model.*;

public class seguimientoVM{
	private String codigoReclamo;
	private CreclamoDAO reclamoDAO;
	private CseguimientoDAO seguimientoDAO;
	private CsolucionadoDAO solucionadoDAO;
	private CsolucionDAO solucionDAO;
	private CempresaDAO empresaDAO;
	
	private CSeguimiento seguimiento;
	private CSolucionado solucionado;
	private CSolucion solucion;
	private CEmpresa empresa;
	
	private String detallesolucion;
	private boolean ver100;
	private boolean ver50;
	private boolean recibido;
	private boolean inicio;
	private boolean document;
	
	@Init
	public void InitVM(){
		seguimientoDAO=new CseguimientoDAO();
		solucionadoDAO=new CsolucionadoDAO();
		solucionDAO=new CsolucionDAO();
		
		codigoReclamo="";
		reclamoDAO=new CreclamoDAO();
		ver100=false;
		ver50=false;
		recibido=false;
		inicio=true;
		document=false;
		
		detallesolucion="";
		
		empresaDAO=new CempresaDAO();
		empresa=new CEmpresa();
		
		empresaDAO.pasarDatos(empresaDAO.recuperarDatosEmpresa());
		empresa=empresaDAO.getEmpresa();
		
		
	}
	@Command
	@NotifyChange({"detallesolucion","inicio","recibido","ver50","ver100","document"})
	public void buscarReclamo(@BindingParam("valor")String codigo,@BindingParam("comp")Component comp){
		if (!codigo.equals("")) {
			codigoReclamo=codigo.trim().toUpperCase();
			try {
				seguimientoDAO.asignarEstadoSeguimiento(seguimientoDAO.recuperarReclamoporCodigo(codigoReclamo));
				CSeguimiento estado=seguimientoDAO.getSeguimiento();
				//System.out.println(estado.isRecibido()+" "+estado.isProceso()+" "+estado.isSolucionado()+" "+estado.getCodSolucion());
				if(estado.isSolucionado()){
					System.out.println("visible solucionado");
					solucionadoDAO.asignarSolucionado(solucionadoDAO.recuperarSolucionadodeReclamo(estado.getCodSolucion()));
					solucionado=solucionadoDAO.getSolucionado();
					if(!solucionado.getDoc().equals(""))
						document=true;
					solucionDAO.asignarSolucion(solucionDAO.recuperarSolucionpPorCodigo(solucionado.getIdsolucion()));
					solucion=solucionDAO.getSolucion();
					if(solucion.getIdsolucion()==0){
						solucionDAO.asignarOtraSolucion(solucionDAO.recuperarOtraSolucionPorCodigo(solucionado.getIdsolucionado()));
						solucion=solucionDAO.getSolucion();
					}
					
					detallesolucion=(solucion.getDetalles()).toUpperCase()+" - Los detalles se le envio al correo";
					recibido=false;
					ver100=true;
					ver50=false;
					inicio=false;
				}else if(estado.isProceso()){
					System.out.println("visible proceso");
					recibido=false;
					ver100=false;
					ver50=true;
					inicio=false;
				}else if(estado.isRecibido()){
					System.out.println("visible recibido");
					inicio=false;
					recibido=true;
					ver100=false;
					ver50=false;
				}
			} catch (Exception e) {
				Clients.showNotification("El codigo de reclamo invalido", Clients.NOTIFICATION_TYPE_WARNING, comp, "before_start", 2500);
			}			
		}else{
			Clients.showNotification("digite un codigo",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
		}
	}
	public String getCodigoReclamo() {
		return codigoReclamo;
	}
	public void setCodigoReclamo(String codigoReclamo) {
		this.codigoReclamo = codigoReclamo;
	}
	public CreclamoDAO getReclamoDAO() {
		return reclamoDAO;
	}
	public void setReclamoDAO(CreclamoDAO reclamoDAO) {
		this.reclamoDAO = reclamoDAO;
	}
	public String getDetallesolucion() {
		return detallesolucion;
	}
	public void setDetallesolucion(String detallesolucion) {
		this.detallesolucion = detallesolucion;
	}
	public CSeguimiento getSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(CSeguimiento seguimiento) {
		this.seguimiento = seguimiento;
	}
	public CSolucionado getSolucionado() {
		return solucionado;
	}
	public void setSolucionado(CSolucionado solucionado) {
		this.solucionado = solucionado;
	}
	public CSolucion getSolucion() {
		return solucion;
	}
	public void setSolucion(CSolucion solucion) {
		this.solucion = solucion;
	}
	public boolean isVer100() {
		return ver100;
	}
	public void setVer100(boolean ver100) {
		this.ver100 = ver100;
	}
	public boolean isVer50() {
		return ver50;
	}
	public void setVer50(boolean ver50) {
		this.ver50 = ver50;
	}
	public boolean isRecibido() {
		return recibido;
	}
	public void setRecibido(boolean recibido) {
		this.recibido = recibido;
	}
	public boolean isInicio() {
		return inicio;
	}
	public void setInicio(boolean inicio) {
		this.inicio = inicio;
	}
	public boolean isDocument() {
		return document;
	}
	public void setDocument(boolean document) {
		this.document = document;
	}
	public CEmpresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(CEmpresa empresa) {
		this.empresa = empresa;
	}
}
