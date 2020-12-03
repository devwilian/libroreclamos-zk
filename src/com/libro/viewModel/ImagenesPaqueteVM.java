package com.libro.viewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.au.AuService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.ScopeListener;
import org.zkoss.zk.ui.metainfo.ComponentDefinition;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

import com.libro.dao.*;
import com.libro.model.*;
import com.libro.util.ScannUtil;

public class ImagenesPaqueteVM {
	private ArrayList<CGaleriaPaquete4> listaImagenesPaquetes;
	private CGaleriaDAO galeriaPaqueteDAO;
	private CReclamo oReclamo;
	private boolean update;
	//===========getter and setter====
	
	public CReclamo getoReclamo() {
		return oReclamo;
	}
	public ArrayList<CGaleriaPaquete4> getListaImagenesPaquetes() {
		return listaImagenesPaquetes;
	}
	public void setListaImagenesPaquetes(ArrayList<CGaleriaPaquete4> listaImagenesPaquetes) {
		this.listaImagenesPaquetes = listaImagenesPaquetes;
	}
	public void setoReclamo(CReclamo oReclamo) {
		this.oReclamo = oReclamo;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	//===============contructores====
	@Init
	public void initVM()
	{
		listaImagenesPaquetes=new ArrayList<CGaleriaPaquete4>();
		oReclamo=new CReclamo();
		update=false;
	}
	@GlobalCommand
	public void muestraImagenesSubidasPaquete(@BindingParam("cPaquete")CReclamo paquete)
	{
		ArrayList<CGaleria> listaImagenes=new ArrayList<CGaleria>();
		setoReclamo(paquete);
		for(CGaleria galeria:paquete.getListaImagenes())
		{	
				listaImagenes.add(galeria);
		}
		mostrarImagenes(listaImagenes);
	}
	public boolean esVideo(String ruta){
		if(ruta.substring(ruta.length()-4, ruta.length()).toLowerCase().equals(".mp4")){
			return true;
		}else{
			return false;
		}
	}
	public boolean esDocumento(String ruta){
		if(ruta.substring(ruta.length()-4, ruta.length()).toLowerCase().equals(".pdf") || ruta.substring(ruta.length()-4, ruta.length()).toLowerCase().equals(".doc")){
			return true;
		}else{
			return false;
		}
	}
	public void mostrarImagenes(ArrayList<CGaleria> listaImagenes)
	{
		System.out.println("TAMAÑO DE LA LISTA "+listaImagenes.size());
		listaImagenesPaquetes.clear();
		for(int i=0;i<listaImagenes.size();i+=10)
		{
			//CGaleria galeria=new CGaleria(0, "img/imagenesdereclamos/default_001.png", true);
			
			CGaleriaPaquete4 imagenes=new CGaleriaPaquete4();
			
			if(esVideo(listaImagenes.get(i).getcRutaImagen())){
				imagenes.setGaleria1(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i).getcRutaImagen(), true));
				System.out.println("Es video ");
			}else if(esDocumento(listaImagenes.get(i).getcRutaImagen())){
				imagenes.setGaleria1(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i).getcRutaImagen(), true));
				System.out.println("Es documento ");
			}else{
				imagenes.setGaleria1(listaImagenes.get(i));
				System.out.println("No es video ");
			}
			if((i+1)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+1).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria2(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+1).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+1).getcRutaImagen())){
					imagenes.setGaleria2(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+1).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					imagenes.setGaleria2(listaImagenes.get(i+1));
					System.out.println("No es video ");
				}
			if((i+2)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+2).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria3(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+2).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+2).getcRutaImagen())){
					imagenes.setGaleria3(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+2).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria3(listaImagenes.get(i+2));
				}
			if((i+3)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+3).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria4(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+3).getcRutaImagen(), true));
					System.out.println(imagenes.getGaleria4().getcRutaImagen());
				}else if(esDocumento(listaImagenes.get(i+3).getcRutaImagen())){
					imagenes.setGaleria4(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+3).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria4(listaImagenes.get(i+3));
				}
			if((i+4)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+4).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria5(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+4).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+4).getcRutaImagen())){
					imagenes.setGaleria5(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+4).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria5(listaImagenes.get(i+4));
				}
			if((i+5)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+5).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria6(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+5).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+5).getcRutaImagen())){
					imagenes.setGaleria6(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+5).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria6(listaImagenes.get(i+5));
				}
			if((i+6)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+6).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria7(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+6).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+6).getcRutaImagen())){
					imagenes.setGaleria7(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+6).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria7(listaImagenes.get(i+6));
				}
			if((i+7)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+7).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria8(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+7).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+7).getcRutaImagen())){
					imagenes.setGaleria8(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+7).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria8(listaImagenes.get(i+7));
				}
			if((i+8)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+8).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria9(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+8).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+9).getcRutaImagen())){
					imagenes.setGaleria9(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+9).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria9(listaImagenes.get(i+8));
				}
			if((i+9)<listaImagenes.size())
				if(esVideo(listaImagenes.get(i+9).getcRutaImagen())){
					System.out.println("Es video ");
					imagenes.setGaleria10(new CGaleria("", "img/imagenesdereclamos/default_001.png",listaImagenes.get(i+9).getcRutaImagen(), true));
				}else if(esDocumento(listaImagenes.get(i+9).getcRutaImagen())){
					imagenes.setGaleria10(new CGaleria("", "img/imagenesdereclamos/default_002.png",listaImagenes.get(i+9).getcRutaImagen(), true));
					System.out.println("Es documento ");
				}else{
					System.out.println("No es video ");
					imagenes.setGaleria10(listaImagenes.get(i+9));
				}
			listaImagenesPaquetes.add(imagenes);
		}
		System.out.println("Direccion de las imagenes");
		System.out.println(listaImagenesPaquetes.get(0).getGaleria1().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria2().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria3().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria4().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria5().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria6().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria7().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria8().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria9().getcRutaImagen());
		System.out.println(listaImagenesPaquetes.get(0).getGaleria10().getcRutaImagen());
		
		BindUtils.postNotifyChange(null, null, this,"listaImagenesPaquetes");
	}
	
	@Command
	@NotifyChange({"update"})
	public void cambiosEnImagenPaquete(@BindingParam("galeria4")CGaleriaPaquete4 galeria4,@BindingParam("galeria")CGaleria galeria)
	{
		System.out.println("Nombre de la imagen: "+galeria.getCimage());
		if(!validoParaCambiarImagen(galeria.isBestado()))
			return;
		update=true;
		if(galeria4.getGaleria1().equals(galeria))
		{
			System.out.println("Entre a cambiar estado de imagen");
			if(galeria4.getGaleria1().isBestado())
			{
				galeria4.getGaleria1().setBestado(false);
				galeria4.getGaleria1().setStyle_Select("div_content_imagePaquete");
				quitarImagen(galeria4.getGaleria1().getcRutaImagen());
			}else{
				galeria4.getGaleria1().setBestado(true);
				galeria4.getGaleria1().setStyle_Select("div_content_imagePaquete_selected");
				agregarImagen(galeria4.getGaleria1().getcRutaImagen());
			}
		}else if(galeria4.getGaleria2().equals(galeria))
		{
			if(galeria4.getGaleria2().isBestado())
			{
				galeria4.getGaleria2().setBestado(false);
				galeria4.getGaleria2().setStyle_Select("div_content_imagePaquete");
				quitarImagen(galeria4.getGaleria2().getcRutaImagen());
			}else{
				galeria4.getGaleria2().setBestado(true);
				galeria4.getGaleria2().setStyle_Select("div_content_imagePaquete_selected");
				agregarImagen(galeria4.getGaleria2().getcRutaImagen());
			}
		}
		refrescarCambios(galeria4);
	}
	public boolean validoParaCambiarImagen(boolean Marcado)
	{
		System.out.println("Estado del marcado es: "+Marcado);
		boolean valido=true;
		for(CGaleria galeria:oReclamo.getListaImagenes())
		{
			System.out.println("Codigo del paquete en la gaeria: "+galeria.getCpaquetecod());
			if(galeria.getCpaquetecod()==null)
			{
				valido=false;
				break;
			}
		}
		System.out.println("Que paso con valido: "+valido);
		if(valido && !Marcado)
		{

		}
		System.out.println("validez de imagen: "+valido);
		return valido;
	}
	public boolean validoParaEliminarImagen()
	{
		boolean valido=true;
		for(CGaleria galeria:oReclamo.getListaImagenes())
		{
			System.out.println("Codigo del paquete en la gaeria: "+galeria.getCpaquetecod());
			if(galeria.getCpaquetecod()==null)
			{
				valido=false;
				break;
			}
		}
		return valido;
	}
	public void quitarImagen(String rutaImagen)
	{

	}
	
	public void agregarImagen(String rutaImagen)
	{

	}
	@Command
	@NotifyChange()
	public void eliminarImagenGaleriaPaquete(@BindingParam("galeria4")CGaleriaPaquete4 galeria4,@BindingParam("galeria")CGaleria galeria,@BindingParam("comp")Component comp)
	{

	}
	@Command
	public void descargarDoc(@BindingParam("galeria4")CGaleriaPaquete4 galeria4,@BindingParam("galeria")CGaleria galeria){
			//System.out.println("Se descarga galeria uno"+galeria.getcRutaImagen());
			if(galeria.getcRutaImagen().equals("img/imagenesdereclamos/default_001.png") || galeria.getcRutaImagen().equals("img/imagenesdereclamos/default_002.png")){
				//System.out.println("ruta original "+galeria.getRutaAux());
				download(galeria.getRutaAux());
			}else{
				download(galeria.getcRutaImagen());
			}
	}
	public void download(String archivo) {
		System.out.println("Click");
        FileInputStream inputStream;
        try {
            File dosfile = new File(ScannUtil.getPathGeneral()+archivo);
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
	public void borraFicheroServidor(CGaleria galeria)
	{
		String url=ScannUtil.getPathImagenReclamos();
		File fichero = new File(url+galeria.getCimage());
		if (fichero.delete())
			System.out.println("El fichero ha sido borrado satisfactoriamente");
		else
			System.out.println("El fichero no puede ser borrado");
	}
	@Command
	public void verImagenGrande(@BindingParam("valor")Image img){
		Window win_imagenes = (Window) Executions.createComponents("/imagenesPaquetes.zul", null, null);
		win_imagenes.doModal();
	}
	@Command
	@NotifyChange({"update"})
	public void guardarCambios(@BindingParam("componente")Component comp)
	{
//		CreclamoDAO paqueteDao=new CreclamoDAO();
//		boolean correcto=paqueteDao.isOperationCorrect(paqueteDao.modificarImagenesPaquete(oReclamo));
//		if(correcto)
//		{
//			update=false;
//			for(CGaleria galeria:oReclamo.getListaImagenes())
//			{
//				System.out.println("nombre->"+galeria.getNgaleriapaquetecod()+"y estado->"+galeria.isBestado());
//				CGaleriaDAO galeriaPaqueteDao=new CGaleriaDAO();
//				correcto=galeriaPaqueteDao.isOperationCorrect(galeriaPaqueteDao.modificarImagenPaquete(galeria));
//			}
//			Clients.showNotification("Los cambios efectuados se guardaron correctamente",Clients.NOTIFICATION_TYPE_INFO,comp,"before_start",300);
//		}
//		else
//			Clients.showNotification("No se pudieron guardar los cambios efectuados",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",300);
	}
	public void refrescarCambios(CGaleriaPaquete4 galeria4)
	{
		BindUtils.postNotifyChange(null, null, galeria4, "galeria1");
		BindUtils.postNotifyChange(null, null, galeria4, "galeria2");
		BindUtils.postNotifyChange(null, null, galeria4, "galeria3");
		BindUtils.postNotifyChange(null, null, galeria4, "galeria4");
		BindUtils.postNotifyChange(null, null, oReclamo, "listaImagenes");
	}
}
