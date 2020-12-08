package com.libro.dao;

import com.libro.model.*;

import java.util.*;
public class CreclamoDAO extends CConexion{
	private CReclamo reclamo;
	private CListaReclamo listReclamo;
	private ArrayList<CReclamo> listaReclamos;
	private ArrayList<CListaReclamo>listadeReclamos;
	
	public CListaReclamo getListReclamo() {
		return listReclamo;
	}
	public void setListReclamo(CListaReclamo listReclamo) {
		this.listReclamo = listReclamo;
	}
	public ArrayList<CListaReclamo> getListadeReclamos() {
		return listadeReclamos;
	}
	public void setListadeReclamos(ArrayList<CListaReclamo> listadeReclamos) {
		this.listadeReclamos = listadeReclamos;
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
	public CreclamoDAO(){
		super();
		this.reclamo=new CReclamo();
	}
	public CreclamoDAO(CReclamo preclamo){
		reclamo=preclamo;
	}
	public List recuperarReclamosDB(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarreclamo");
	}
	public List recuperarReclamoPorCodigo(String val){
		System.out.println(val);
		Object[]values={val};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarreclamoporcodigo",values);
	}
	public List recuperarListaReclamosDB(int tipo,int ordenar){
		Object[]values={tipo,ordenar};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarlistareclamorecibidos",values);
	}
	public List recuperarListaProcesoDB(int aux){
		Object[]values={aux};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarlistareclamoproceso",values);
	}
	public List marcaEnProceso(String cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_actualizarmarcarprocesoreclamo",values);
	}
	public List insertarPrefijoCodigo(String valor){
		Object[]values={valor};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarprefijoparacodigo",values);
	}
	public List recuperarPrimeraVez(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarprimeravez");
	}
	public List insertarReclamo(CReclamo reclamo){
		Object[]values={
				reclamo.getSolicitante(),
				reclamo.getTipoproblema(),
				reclamo.getSedeocurrencia(),
				reclamo.getAreaocurrencia(),
				(Date)reclamo.getFecha(),
				reclamo.getDetallereclamo(),
				reclamo.getSolicitudreclamo(),
				reclamo.isDocmultimedia(),
				true,
				false,
				false,
				reclamo.getTipoBienContratado()
		};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertar_reclamo", values);
	}
	public void asginarListaReclamos(List lista){
		//System.out.println("CreservaDAO "+lista.size());
		listadeReclamos=new ArrayList<CListaReclamo>();
		for (int i = 0; i < lista.size(); i++) {
			Map row=(Map)lista.get(i);
				
			
			listReclamo=new CListaReclamo(
					(String)row.get("codreclamo"),
					(String)row.get("nombres"),
					(String)row.get("apellidos"),
					(String)row.get("ruc"),
					(String)row.get("correo"),
					(Date)row.get("fecha"),
					(Date)row.get("fecharecla"),
					(String)row.get("tipoproblema"),
					(String)row.get("sedeocurrencia"),
					(String)row.get("areaocurrencia"),
					(String)row.get("detallereclamo"),
					(String)row.get("solicitudreclamo"),
					(boolean)row.get("docmultimedia"),
					(boolean)row.get("recibido"),
					(boolean)row.get("proceso"),
					(boolean)row.get("solucionado"));
			listadeReclamos.add(listReclamo);
		}
	}
	public void asginarReclamo(List lista){
		listaReclamos=new ArrayList<CReclamo>();
//		for (int i = 0; i < lista.size(); i++) {
			Map row=(Map)lista.get(0);
			reclamo=new CReclamo((String)row.get("idreclamo"),(int)row.get("idsolicitante"),
					(int)row.get("idtipoproblema"),(int)row.get("idsedeocurrencia"),
					(int)row.get("idareaocurrencia"),(Date)row.get("fecha"),
					(String)row.get("detallereclamo"),(String)row.get("solicitudreclamo"),(boolean)row.get("docmultimedia"),
					(boolean)row.get("recibido"),(boolean)row.get("proceso"),(boolean)row.get("solucionado"),(Date)row.get("fechareclamo"),row.get("tipodebiencontratado").toString());
//		}
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public String codigoReclamo(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		String cod=(String)row.get("cod");
		if(correcto)return cod;
		else return null;
	}
	public String recuperarMensaje(List list){
		Map row=(Map)list.get(0);
		String msg=(String)row.get("resultado");
		return msg;
	}
	public boolean esPriemeraVez(List list){
		Map row=(Map)list.get(0);
		if(row.get("resultado").toString().equals("incorrecto"))
			return true;
		else
			return false;
	}
	public String recuperarPrefijo(List list){
		String prefijo="";
		Map row=(Map)list.get(0);
		if(!row.get("resultado").toString().equals("incorrecto"))
			prefijo=(String)row.get("pref");
		return prefijo;
	}
}
