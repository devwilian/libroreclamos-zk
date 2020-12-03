package com.libro.dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.libro.model.*;

public class CGaleriaDAO extends CConexion{
	private ArrayList<CGaleria> listaImagenesPaquete;
	/********************/
	public CGaleriaDAO() {
		// TODO Auto-generated constructor stub
		super();
	}
	public ArrayList<CGaleria> getListaImagenesPaquete() {
		return listaImagenesPaquete;
	}
	public void setListaImagenesPaquete(ArrayList<CGaleria> listaImagenesPaquete) {
		this.listaImagenesPaquete = listaImagenesPaquete;
	}
	/**********************/
	public List recuperarImagenesPaqueteBD(String id)
	{
		Object[] values={id};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperargaleriaporreclamo", values);
	}
	public List recuperarCodigodeGaleria(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarcodigogaleria");
	}
	public List insertarGaleria(CGaleria gal,String recla){
		Object []values={recla,gal.getcRutaImagen(),true};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertargaleria",values);
	}
	public void asignarListaImagenes(List lista) throws UnsupportedEncodingException
	{
		if(lista!=null)
		{
			listaImagenesPaquete=new ArrayList<CGaleria>();
			for(int i=0;i<lista.size();i++)
			{
				Map row=(Map)lista.get(i);
				listaImagenesPaquete.add(new CGaleria((long)row.get("idgaleria"), 
						(String)row.get("cpaquetecod"),(String)row.get("rutaimage"), 
						(boolean)row.get("estado")));
			}
		}
	}
	public void asignarListaImagenesReclamo(List lista)
	{
		if(lista!=null)
		{
			listaImagenesPaquete=new ArrayList<CGaleria>();
			for(int i=0;i<lista.size();i++)
			{
				Map row=(Map)lista.get(i);
				CGaleria gal=new CGaleria((String)row.get("idreclamo"), (String)row.get("rutaimage"), (boolean)row.get("estado"));
				listaImagenesPaquete.add(gal);
			}
		}
	}
	public List insertarImagen(CGaleria galeria)
	{
		System.out.println("VALOR CODIGO->"+galeria.getCpaquetecod());
		System.out.println("VALOR RUTA->"+galeria.getcRutaImagen());
		System.out.println("VALOR BOOLEANO->"+galeria.isBestado());
		Object[] values={galeria.getCpaquetecod(),
				galeria.getcRutaImagen(),galeria.isBestado()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertargaleria", values);
	}
	public List modificarImagen(CGaleria galeria)
	{
		Object[] values={(int)galeria.getNgaleriapaquetecod(),galeria.isBestado()};
		return getEjecutorSQL().ejecutarProcedimiento("Pricing_sp_modificargaleria", values);
	}
	
	public List eliminarImagenGaleriaPaquete(long codgaleriaImagenPaquete){
		Object[] values={codgaleriaImagenPaquete};
		return getEjecutorSQL().ejecutarProcedimiento("Pricing_sp_EliminarImagenGaleriaPaquete",values);
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public int CodigoGaleria(List lista)
	{
		Map row=(Map)lista.get(0);
		int correcto=(int)row.get("cod");
		return correcto;
	}
}
