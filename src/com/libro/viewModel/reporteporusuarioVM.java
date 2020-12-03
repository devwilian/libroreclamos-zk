package com.libro.viewModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;

import com.libro.dao.*;
import com.libro.model.*;
import com.libro.util.ScannUtil;

import groovy.beans.Bindable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpSession;

public class reporteporusuarioVM {
	private CUsuario usuario;
	private CusuarioDAO usuarioDAO;
	private CsolucionadoDAO solucionadoDAO;
	
	private ArrayList<CUsuario> listaUsuarios;
	private ArrayList<CLista> listaUsuariosCombo;
	private ArrayList<CListaResueltos> listaSolucionado;
	private boolean verLista;
	private int ordenar;
	private String codigo;
	@Init
	public void initVM(){
		verLista=false;
		codigo="";
		ordenar=1;
		
		listaSolucionado=new ArrayList<CListaResueltos>();
		usuarioDAO=new CusuarioDAO();
		solucionadoDAO=new CsolucionadoDAO();
		
		usuarioDAO.asignarListaUsuario(usuarioDAO.recuperarUsuarios());
		listaUsuarios=usuarioDAO.getListaUsuarios();
		pasarLista();
		
		solucionadoDAO.asignarListaSolucionados(solucionadoDAO.recuperarListaSolucionadosporUsuario(ordenar,codigo));
		listaSolucionado=solucionadoDAO.getListaSolucionado();
	}
	public void pasarLista(){
		listaUsuariosCombo=new ArrayList<CLista>();
		for (int i = 0; i < listaUsuarios.size(); i++) {
			listaUsuariosCombo.add(new CLista(listaUsuarios.get(i).getIdusuario(),listaUsuarios.get(i).getApellidos()+" "+listaUsuarios.get(i).getNombres()));
		}
	}
	@Command
	@NotifyChange({"listaSolucionado"})
	public void ordenar(@BindingParam("id")String valor){
		if(valor.equals("idrq")){
			ordenar=1;
		}else if(valor.equals("idr")){
			ordenar=2;
		}else if(valor.equals("idq")){
			ordenar=3;
		}
		recuperarLista();
	}
	public void recuperarLista(){
		solucionadoDAO.asignarListaSolucionados(solucionadoDAO.recuperarListaSolucionadosporUsuario(ordenar,codigo));
		listaSolucionado=solucionadoDAO.getListaSolucionado();
	}
	@Command
	@NotifyChange({"listaSolucionado"})
	public void changeUsuario(@BindingParam("id")String id,@BindingParam("valor")String valor){
		codigo=id;
		recuperarLista();
	}
	@Command
	@NotifyChange("verLista")
	public void verListaporUsuario(){
		verLista=true;
	}
	@Command
	public void generarReporte(@BindingParam("comp")Component comp) throws Exception{
		try {
			convertirResueltos(listaSolucionado,comp);
		} catch (Exception e) {
			Clients.showNotification("Cierre el ultimo documento que se genero", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
	}
	@Command
	@NotifyChange({"listaSolucionado"})
	public void buscarPorApellidos(@BindingParam("valor")String valor){
		System.out.println(ordenar+" == "+codigo+" == "+valor);
		solucionadoDAO.asignarListaSolucionados(solucionadoDAO.recuperarListaBuscarSolucionadosporUsuario(ordenar,codigo,valor));
		listaSolucionado=solucionadoDAO.getListaSolucionado();
		
	}
	 public void convertirResueltos(ArrayList<CListaResueltos>list,Component comp) throws Exception {
		 CUsuario usuario=new CUsuario();
		 CusuarioDAO userDAO=new CusuarioDAO();
		 userDAO.asignarUsuario(userDAO.recuperarUsuario(codigo));
		 usuario=userDAO.getUsuario();
		 String nombreArchivo="R_Reporte_"+codigo+".xls";
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, "Reporte Resueltos");

	        String[] headers = new String[]{
	            "Codigo",
	            "Nombres",
	            "Apellidos",
	            "Razon social",
	            "Fecha",
	            "Tipo",
	            "Solucion"
	        };

	        Object[][]data=new Object[list.size()][7];
	        for (int i = 0; i < list.size(); i++) {
				data[i][0]=list.get(i).getIdsolucionado();
				data[i][1]=list.get(i).getNombres();
				data[i][2]=list.get(i).getApellidos();
				data[i][3]=list.get(i).getRazonsocial();
				data[i][4]=list.get(i).getFecha();
				data[i][5]=list.get(i).getTipo();
				data[i][6]=list.get(i).getSolucion();
			}
	        	        
//	        for (int i = 0; i < list.size(); i++) {
//				for (int j = 0; j < 7; j++) {
//					System.out.print(data[i][j]+" ");
//				}
//				System.out.println();
//			}
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headerStyle.setFont(font);

	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

	        HSSFRow headerRow = sheet.createRow(0);
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            HSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }

	        for (int i = 0; i < data.length; ++i) {
	            HSSFRow dataRow = sheet.createRow(i + 1);

	            Object[] d = data[i];
	            String a = String.valueOf(d[0]);
	            String b = (String) d[1];
	            String c = (String) d[2];
	            String z = (String) d[3];
	            String e = (String) d[4];
	            String f = (String) d[5];
	            String g = (String) d[6];

	            dataRow.createCell(0).setCellValue(a);
	            dataRow.createCell(1).setCellValue(b);
	            dataRow.createCell(2).setCellValue(c);
	            dataRow.createCell(3).setCellValue(z);
	            dataRow.createCell(4).setCellValue(e);
	            dataRow.createCell(5).setCellValue(f);
	            dataRow.createCell(6).setCellValue(g);
	        }

	        HSSFRow dataRow = sheet.createRow(1 + data.length);
	        dataRow.createCell(0).setCellValue("Total");
	        dataRow.createCell(1).setCellValue(list.size());
	        
	        
	        HSSFRow dataRowaux = sheet.createRow(2 + data.length);
	        dataRowaux.createCell(0).setCellValue("Usuario");
	        dataRowaux.createCell(1).setCellValue(usuario.getApellidos()+" "+usuario.getNombres());

	        FileOutputStream file = new FileOutputStream(ScannUtil.getPathExcel()+nombreArchivo);
	        workbook.write(file);
	        file.close();
	        download(nombreArchivo,comp);
	}
	 public void download(String archivo,Component comp) {
			System.out.println("Click");
	        FileInputStream inputStream;
	        try {
	            File dosfile = new File(ScannUtil.getPathExcel()+archivo);
	            if (dosfile.exists()) {
	                inputStream = new FileInputStream(dosfile);
	                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
	                //Clients.showNotification("Se completo la descarga", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2500);
	            } else
	                System.out.println("NO se pudo descargar");

	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	public class CLista{
		private String idusuario;
		private String nombres;
		public String getIdusuario() {
			return idusuario;
		}
		public void setIdusuario(String idusuario) {
			this.idusuario = idusuario;
		}
		public String getNombres() {
			return nombres;
		}
		public void setNombres(String nombres) {
			this.nombres = nombres;
		}
		public CLista() {
			this.idusuario = "";
			this.nombres = "";
		}
		public CLista(String idusuario, String nombres) {
			this.idusuario = idusuario;
			this.nombres = nombres;
		}
	}
	
	public CUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(CUsuario usuario) {
		this.usuario = usuario;
	}
	public ArrayList<CUsuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(ArrayList<CUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public ArrayList<CLista> getListaUsuariosCombo() {
		return listaUsuariosCombo;
	}
	public void setListaUsuariosCombo(ArrayList<CLista> listaUsuariosCombo) {
		this.listaUsuariosCombo = listaUsuariosCombo;
	}
	public boolean isVerLista() {
		return verLista;
	}
	public void setVerLista(boolean verLista) {
		this.verLista = verLista;
	}
	public ArrayList<CListaResueltos> getListaSolucionado() {
		return listaSolucionado;
	}
	public void setListaSolucionado(ArrayList<CListaResueltos> listaSolucionado) {
		this.listaSolucionado = listaSolucionado;
	}
}
