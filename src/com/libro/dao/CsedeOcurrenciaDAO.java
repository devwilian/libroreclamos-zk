package com.libro.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.libro.model.CReportesede;
import com.libro.model.CSedeOcurrencia;
import com.libro.model.CTipoDocumento;

public class CsedeOcurrenciaDAO extends CConexion{
	private CSedeOcurrencia sedeOcurrencia;
	private CReportesede reportesede;
	private ArrayList<CReportesede> listaReporteSede;
	private ArrayList<CSedeOcurrencia> listaSedeOcurrencia;
	
	public CReportesede getReportesede() {
		return reportesede;
	}
	public void setReportesede(CReportesede reportesede) {
		this.reportesede = reportesede;
	}
	public ArrayList<CReportesede> getListaReporteSede() {
		return listaReporteSede;
	}
	public void setListaReporteSede(ArrayList<CReportesede> listaReporteSede) {
		this.listaReporteSede = listaReporteSede;
	}
	public CSedeOcurrencia getSedeOcurrencia() {
		return sedeOcurrencia;
	}
	public void setSedeOcurrencia(CSedeOcurrencia sedeOcurrencia) {
		this.sedeOcurrencia = sedeOcurrencia;
	}
	public ArrayList<CSedeOcurrencia> getListaSedeOcurrencia() {
		return listaSedeOcurrencia;
	}
	public void setListaSedeOcurrencia(ArrayList<CSedeOcurrencia> listaSedeOcurrencia) {
		this.listaSedeOcurrencia = listaSedeOcurrencia;
	}
	public List obtenerSedeOcurrencia(){
		//System.out.println("Estamos en procedimiento");
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsedeocurrencia");
	}
	public List obtenerSedeOcurrenciaCodigo(int val){
		//System.out.println("Estamos en procedimiento");
		Object[]values={val};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsedeocurrenciaporcodigo",values);
	}
	public List numeroTotaldePersonas(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_numeropersonastotal");
	}
	public List insertarNuevoSede(String valor){
		Object[]values={valor};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarsedeocurrencia",values);
	}
	public List actualizarSede(CSedeOcurrencia valor){
		Object[]values={valor.getIdsedeocurrencia(),valor.getNombre()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_actualizarsede",values);
	}
	public List eliminarSedeOcurrencia(int cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_eliminarsedeocurrencia",values);
	}
	public List obtenerReporteporSede(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_reportereclamoporsede");
	}
	public void asignarListaSedeOcurrencia(List lista){
		//System.out.println("Catidad de Sede Ocurrencia "+lista.size());
		listaSedeOcurrencia=new ArrayList<CSedeOcurrencia>();
		for(int i=0;i<lista.size();i++)
		{
			Map row=(Map)lista.get(i);
			listaSedeOcurrencia.add(new CSedeOcurrencia((int)row.get("idsedeocurrencia"),(String)row.get("nombre")));
		}
		
	}
	
	public void asignarListaReporteporSede(List list){
		System.out.println("Tamaño de la lista "+list.size());
		listaReporteSede=new ArrayList<CReportesede>();
		for (int i = 0; i < list.size(); i++) {
			Map row=(Map)list.get(i);
			//System.out.println(row.get("nombresede")+" "+row.get("cantidad"));
			listaReporteSede.add(new CReportesede((String)row.get("nombresede"),(int)row.get("cantidad")));
		}
	}
	public void asignarSedeOcurrencia(List lista)
	{
			Map row=(Map)lista.get(0);
			sedeOcurrencia=new CSedeOcurrencia((int)row.get("idsedeocurrencia"),(String)row.get("nombre"));
	}
	public int obtenerNumeroPersonas(List list){
		int total=-1;
		if(list!=null){
			Map row=(Map)list.get(0);
			total = (int)row.get("cantidad");
		}
		return total;
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
}
