package com.libro.viewModel;

import com.libro.dao.CreclamoDAO;
import com.libro.model.CListaReclamo;
import com.libro.model.CReclamo;
import com.libro.viewModel.*;
import java.util.*;
import org.zkoss.zk.ui.Component;

import javax.servlet.http.*;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

public class listaprocesoVM {
	HttpSession seshttp;
	
	private CReclamo reclamo;
	
	private CreclamoDAO reclamoDAO;
	
	private ArrayList<CReclamo> listaReclamos;
	private ArrayList<CListaReclamo> listadeReclamos;
	
	private Component comp;
	
	private int ordenar;
	
	private boolean crearEstado;
	private boolean verEstado;
	
	@Init
	public void initVM(){
		seshttp=(HttpSession)Sessions.getCurrent().getNativeSession();
		
		crearEstado=false;
		verEstado=false;
		
		ordenar=1;
		listaReclamos=new ArrayList<CReclamo>();
		listadeReclamos=new ArrayList<CListaReclamo>();
		reclamoDAO=new CreclamoDAO();
		inicializar();
	}
	public void inicializar(){
		reclamoDAO.asginarListaReclamos(reclamoDAO.recuperarListaProcesoDB(ordenar));
		listadeReclamos=reclamoDAO.getListadeReclamos();
		System.out.println(listadeReclamos.size());
	}
	@Command
	@NotifyChange({"listadeReclamos"})
	public void ordenar(@BindingParam("valor")String valor){
		System.out.println("ordenar "+valor);
		if (valor.toLowerCase().equals("fecha")) {
			ordenar=1;
		}
		if(valor.toLowerCase().equals("nombre")){
			ordenar=2;
		}
		inicializar();
	}
	@Command
	@GlobalCommand
	@NotifyChange({"verEstado"})
	public void verEstadoReclamo(@BindingParam("idreclamo")String valor){
		System.out.println("Ver estadoReclamo "+valor);
		verEstado=true;
	}
	@Command
	@NotifyChange({"crearEstado"})
	public void verCrearEstado(){
		crearEstado=true;
	}
	@GlobalCommand
	@NotifyChange({"crearEstado","listadeReclamos"})
	public void cerrarCrearEstado(){
		crearEstado=false;
		inicializar();
	}
	@GlobalCommand
	@NotifyChange({"verEstado","listadeReclamos"})
	public void cerrarVerEstado(@BindingParam("comp")Component comp){
		verEstado=false;
		inicializar();
	}
	public CReclamo getReclamo() {
		return reclamo;
	}
	public void setReclamo(CReclamo reclamo) {
		this.reclamo = reclamo;
	}
	public ArrayList<CReclamo> getListaReclamos() {
		return listaReclamos;
	}
	public void setListaReclamos(ArrayList<CReclamo> listaReclamos) {
		this.listaReclamos = listaReclamos;
	}
	public ArrayList<CListaReclamo> getListadeReclamos() {
		return listadeReclamos;
	}
	public void setListadeReclamos(ArrayList<CListaReclamo> listadeReclamos) {
		this.listadeReclamos = listadeReclamos;
	}
	public int getOrdenar() {
		return ordenar;
	}
	public void setOrdenar(int ordenar) {
		this.ordenar = ordenar;
	}
	public boolean isCrearEstado() {
		return crearEstado;
	}
	public void setCrearEstado(boolean crearEstado) {
		this.crearEstado = crearEstado;
	}
	public boolean isVerEstado() {
		return verEstado;
	}
	public void setVerEstado(boolean verEstado) {
		this.verEstado = verEstado;
	}
}
