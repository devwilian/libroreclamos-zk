package com.libro.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.libro.model.*;

public class CtipoDocumentoDAO extends CConexion{
	private CTipoDocumento tipodoc;
	private ArrayList<CTipoDocumento> listaTipoDocumento;
	
	public CTipoDocumento getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(CTipoDocumento tipodoc) {
		this.tipodoc = tipodoc;
	}
	public ArrayList<CTipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}
	public void setListaTipoDocumento(ArrayList<CTipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}
	public List obtenerTiposdeDocumento(){
		//System.out.println("Estamos en procedimiento");
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperartipodocumento");
	}
	public List obtenerTiposdeDocumentoCodigo(int doc){
		//System.out.println("Estamos en procedimiento");
		Object[]values={doc};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperartipodocumentoporcodigo",values);
	}
	public void asignarListaTipoDocuemnto(List lista)
	{
		System.out.println("Catidad de Documentos "+lista.size());
		listaTipoDocumento=new ArrayList<CTipoDocumento>();
		for(int i=0;i<lista.size();i++)
		{
			Map row=(Map)lista.get(i);
			listaTipoDocumento.add(new CTipoDocumento((int)row.get("idtipodoc"),(String)row.get("descripcion")));
		}
		
	}
	public void asignarTipoDocuemnto(List lista)
	{
		Map row=(Map)lista.get(0);
		tipodoc = new CTipoDocumento((int)row.get("idtipodoc"),(String)row.get("descripcion"));
	}
}
