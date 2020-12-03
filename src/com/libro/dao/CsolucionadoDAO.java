package com.libro.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.libro.model.CListaResueltos;
import com.libro.model.CSedeOcurrencia;
import com.libro.model.CSolucionado;

public class CsolucionadoDAO extends CConexion{
	private CSolucionado solucionado;
	private ArrayList<CListaResueltos> listaSolucionado;
	
	public ArrayList<CListaResueltos> getListaSolucionado() {
		return listaSolucionado;
	}
	public void setListaSolucionado(ArrayList<CListaResueltos> listaSolucionado) {
		this.listaSolucionado = listaSolucionado;
	}
	public CSolucionado getSolucionado() {
		return solucionado;
	}
	public void setSolucionado(CSolucionado solucionado) {
		this.solucionado = solucionado;
	}
	public List recuperarSolucionadodeReclamo(int cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsolucionadoporcodigo",values);
	}
	public List recuperarSolucionados(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsolucionado");
	}
	public List recuperarListaSolucionados(int valor){
		Object[]values={valor};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarlistareclamosresueltos",values);
	}
	public List recuperarListaSolucionadosporFecha(int valor,Date desde,Date hasta){
		Object[]values={valor,desde,hasta};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarlistareclamosresueltosporfecha",values);
	}
	public List recuperarListaSolucionadosporSede(int orden,CSedeOcurrencia sede){
		Object[]values={orden,sede.getIdsedeocurrencia()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarlistareclamosresueltosporsede",values);
	}
	public List numeroReportes(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_reportesreclamossede");
	}
	public List recuperarListaSolucionadosporUsuario(int ordenar,String codigo){
		Object[]values={ordenar,codigo};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarlistareclamosresueltosporusuario", values);
	}
	public List recuperarListaBuscarSolucionadosporUsuario(int ordenar,String codigo,String valor){
		Object[]values={ordenar,codigo,valor};
		return getEjecutorSQL().ejecutarProcedimiento("libro_buscarreclamaosresueltosporusuario", values);
	}
	public List insertarSolucionado(CSolucionado solucionado){
		Object[]values={
				solucionado.getIdreclamo(),
				solucionado.getDoc(),
				solucionado.getDetalles(),
				solucionado.getIdsolucion(),
				solucionado.getCoduser()
		};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarsolucionado",values);
	}
	public void asignarSolucionado(List lista){
		Map row=(Map)lista.get(0);
		solucionado=new CSolucionado((int)row.get("idsolucionado"),(String)row.get("idreclamo"),
									(String)row.get("doc"),(String)row.get("detalles"),
									(Date)row.get("fechasolucion"),(int)row.get("idsolucion"),
									(String)row.get("idusuario"));
	}
	public void asignarListaSolucionados(List lista){
		listaSolucionado=new ArrayList<CListaResueltos>();
		for (int i = 0; i < lista.size(); i++) {
			Map row=(Map)lista.get(i);
			CListaResueltos solu=new CListaResueltos(
										(int)row.get("codsolucionado"),
										(String)row.get("codreclamo"),
										(String)row.get("nombres"),
										(String)row.get("apellidos"),
										(String)row.get("razonsocial"),
										(Date)row.get("fechasol"),
										(String)row.get("tipoproble"),
										(String)row.get("solucion"),
										(String)row.get("detalles"));
			listaSolucionado.add(solu);	
		}
	}
	public void asignarListaSolucionadosporUsuario(List lista){
		listaSolucionado=new ArrayList<CListaResueltos>();
		for (int i = 0; i < lista.size(); i++) {
			Map row=(Map)lista.get(i);
			CListaResueltos solu=new CListaResueltos(
										(int)row.get("codsolucionado"),
										(String)row.get("codreclamo"),
										(String)row.get("nombres"),
										(String)row.get("apellidos"),
										(String)row.get("razonsocial"),
										(Date)row.get("fechasol"),
										(String)row.get("tipoproble"),
										(String)row.get("solucion"),
										(String)row.get("detalles"),
										(String)row.get("iduser"));
			listaSolucionado.add(solu);	
		}
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public int codigoSolucionado(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		int cod=(int)row.get("cod");
		if(correcto)return cod;
		else return -1;
	}
	public Object[] obtenerReportes(List list){
		System.out.println("Tamaño del reporte "+list.size());
		Object[]ob=new Object[11];
		
		int total=-1;
		if(list!=null){
			Map row=(Map)list.get(0);
			ob[0] = (String)row.get("resultado");//resultado
			ob[1] = (int)row.get("cantidad");//nro total de personas
			ob[2] = (int)row.get("cantidadsol");
			ob[3] = (int)row.get("nroquejas");//nrototal de quejas
			ob[4] = (int)row.get("nroreclamos");//nrototlade reclamos
			ob[5] = (int)row.get("totalcusco");//nro total de problemas en cusco
			ob[6] = (int)row.get("totalmachu");//nro total de problemas en machu
			ob[7] = (int)row.get("nroqcusco");//quejas en cusco
			ob[8] = (int)row.get("nroqmachu");//quejas en machu
			ob[9] = (int)row.get("nrorecusco");//reclamos en cusco
			ob[10] = (int)row.get("nroremachu");//reclamos en machu
		}
		return ob;
	}
}
