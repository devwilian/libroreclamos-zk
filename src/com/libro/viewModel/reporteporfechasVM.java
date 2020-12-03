package com.libro.viewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;

import com.libro.dao.CsolucionadoDAO;
import com.libro.model.CListaResueltos;
import com.libro.model.CSolucionado;
import com.libro.util.ScannUtil;

public class reporteporfechasVM {
	private CSolucionado solucionado;
	private CsolucionadoDAO solucionadoDAO;
	private ArrayList<CListaResueltos> listaSolucionado;
	private int ordenar;
	private Date desde;
	private Date hasta;
	@Init
	public void initVM(){
		ordenar=1;
		
		solucionadoDAO=new CsolucionadoDAO();
		listaSolucionado=new ArrayList<CListaResueltos>();
	}
	public void iniciar(){
		solucionadoDAO.asignarListaSolucionados(solucionadoDAO.recuperarListaSolucionadosporFecha(ordenar, desde, hasta));
		listaSolucionado=solucionadoDAO.getListaSolucionado();
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
		cargarLista();
	}
	@Command
	@NotifyChange({"listaSolucionado"})
	public void fechaDesde(@BindingParam("fecha")Date fecha){
		if (desde==null) {
			desde=new Date();
			desde=fecha;
		}else{
			desde=fecha;
		}
		cargarLista();
	}
	@Command
	@NotifyChange({"listaSolucionado"})
	public void fechaHasta(@BindingParam("fecha")Date fecha){
		if (hasta==null) {
			hasta=new Date();
			hasta=fecha;
		}else{
			hasta=fecha;
		}
		cargarLista();
	}
	@NotifyChange({"listaSolucionado"})
	public void cargarLista(){
		System.out.println(desde+"/"+hasta);
		if(desde!=null && hasta!=null){
			iniciar();
		}else{
			System.out.println("falta alguno de fechas");
		}
	}
	@Command
	public void generarReporte(@BindingParam("comp")Component comp) throws Exception{
//		try {
			convertirResueltos(listaSolucionado,comp);
//		} catch (Exception e) {
//			Clients.showNotification("Cierre el ultimo documento que se genero", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
//		}
	}
	 public void convertirResueltos(ArrayList<CListaResueltos>list,Component comp) throws Exception {
		 String nombreArchivo="R_Resu_"+new SimpleDateFormat("dd-MM-yyyy").format(desde)+"-"+new SimpleDateFormat("dd-MM-yyyy").format(hasta)+".xls";
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, "Reporte Resueltos por fecha");

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
	public CSolucionado getSolucionado() {
		return solucionado;
	}
	public void setSolucionado(CSolucionado solucionado) {
		this.solucionado = solucionado;
	}
	public ArrayList<CListaResueltos> getListaSolucionado() {
		return listaSolucionado;
	}
	public void setListaSolucionado(ArrayList<CListaResueltos> listaSolucionado) {
		this.listaSolucionado = listaSolucionado;
	}
	public int getOrdenar() {
		return ordenar;
	}
	public void setOrdenar(int ordenar) {
		this.ordenar = ordenar;
	}
}
