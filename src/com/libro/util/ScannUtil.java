package com.libro.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;

public class ScannUtil {
	
	public static final String separator = System.getProperty("file.separator");//Get de system separator
	
	public static boolean uploadFile(Media media) {
		return saveFile(media,getPath());
	}
	public static boolean uploadAuxFolder(Media media) {
		return saveFile(media,getPathAuxFolder());
	}
	public static boolean uploadFileReclamos(Media media){
		return saveFile(media, getPathImagenReclamos());
	}
	public static boolean uploadFileSolucion(Media media){
		return saveFile(media, getPathDocumentos());
	}
	public static boolean uploadFileUsuario(Media media)
	{
		return saveFile(media, getPathImagenUsuario());
	}
	public static boolean uploadFileCertificados(Media media)
	{
		return saveFile(media, getPathCertificados());
	}
	public static boolean uploadFileLogo(Media media){
		return saveFile(media,getPathLogo());
	}
	//Gets the path of the current web application
	public static String getPathDocumentos(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"DocumentosSolucion"+separator;
	}
	public static String getPath(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"DocumentosScanneados"+separator;
	}
	public static String getPathAuxFolder()
	{
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"img"+separator+"auxFolder"+separator;
	}
	public static String getPathCertificados(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"WEB-INF"+separator+"classes"+separator+"com"+separator+"libro"+separator+"resources"+separator;
	}
	public static String getPathImagenUsuario(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"img"+separator+"usuarios"+separator;
	}
	public static String getPathLogo(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"img"+separator;
	}
	public static String getPathImagenReclamos(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"img"+separator+"imagenesdereclamos"+separator;
	}
	public static String getPathImagenQR(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"img"+separator+"QR"+separator;
	}
	public static String getPathExcel(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator+"reportes"+separator;
	}
	public static String getPathGeneral(){
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath(separator)+separator;
	}
	//save file
	public static boolean saveFile(Media media, String path){
		boolean uploaded = false;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			InputStream ins = media.getStreamData();
			in = new BufferedInputStream(ins);
			String fileName = media.getName();
			File arc = new File(path + fileName);
			OutputStream aout = new FileOutputStream(arc);
			out = new BufferedOutputStream(aout);

			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while(ch != -1){
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
			uploaded = true;
			if(uploaded)System.out.println("Se llego a cargar la imagen scanneada");
		}catch (IOException ie) {
			throw new RuntimeException(ie);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
				try {
					if(out != null)
						out.close();
					if(in != null)
						in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return uploaded;
	}
}


