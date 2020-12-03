package com.libro.model;

public class CGaleria {
	private long ngaleriapaquetecod;
	private String cpaquetecod;
	private String cimage;
	private boolean bestado;
	private String cRutaImagen;
	private boolean visible;
	private String style_Select;
	private String idreclamo;
	private String rutaAux;
	private boolean video;
	//============
	public String getcRutaImagen() {
		return cRutaImagen;
	}
	public String getRutaAux() {
		return rutaAux;
	}
	public void setRutaAux(String rutaAux) {
		this.rutaAux = rutaAux;
	}
	public boolean isVideo() {
		return video;
	}
	public void setVideo(boolean video) {
		this.video = video;
	}
	public String getIdreclamo() {
		return idreclamo;
	}
	public void setIdreclamo(String idreclamo) {
		this.idreclamo = idreclamo;
	}
	public long getNgaleriapaquetecod() {
		return ngaleriapaquetecod;
	}
	public void setNgaleriapaquetecod(long ngaleriapaquetecod) {
		this.ngaleriapaquetecod = ngaleriapaquetecod;
	}
	public void setcRutaImagen(String cRutaImagen) {
		this.cRutaImagen = cRutaImagen;
	}
	public String getStyle_Select() {
		return style_Select;
	}
	public void setStyle_Select(String style_Select) {
		this.style_Select = style_Select;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getCpaquetecod() {
		return cpaquetecod;
	}
	public void setCpaquetecod(String cpaquetecod) {
		this.cpaquetecod = cpaquetecod;
	}
	public String getCimage() {
		return cimage;
	}
	public void setCimage(String cimage) {
		this.cimage = cimage;
	}
	public boolean isBestado() {
		return bestado;
	}
	public void setBestado(boolean bestado) {
		this.bestado = bestado;
	}
	//========contructores======
	public CGaleria(){
		super();
		this.visible=false;
		this.style_Select="div_content_imageHotel";
	}
	public CGaleria(long ngaleriapaquetecod, String cpaquetecod, String cRutaImagen, boolean bestado) {
		super();
		this.ngaleriapaquetecod = ngaleriapaquetecod;
		this.cpaquetecod = cpaquetecod;
		this.cRutaImagen = cRutaImagen;
		this.bestado = bestado;
		if(bestado)
			this.style_Select="div_content_imageHotel_selected";
		else
			this.style_Select="div_content_imageHotel";
		this.visible=true;
	}
    public CGaleria(String idreclamo, String cRutaImagen, boolean bestado) {
        super();
        this.idreclamo=idreclamo;
        this.cRutaImagen = cRutaImagen;
        this.bestado = bestado;
        if(bestado)
              this.style_Select="div_content_image_selected";
        else
              this.style_Select="div_content_image";
        this.visible=true;
  }
    public CGaleria(String idreclamo, String cRutaImagen,String rutaAux, boolean bestado) {
        super();
        this.rutaAux=rutaAux;
        this.idreclamo=idreclamo;
        this.cRutaImagen = cRutaImagen;
        this.bestado = bestado;
        if(bestado)
              this.style_Select="div_content_image_selected";
        else
              this.style_Select="div_content_image";
        this.visible=true;
  }
	//==========otros metodos========
	
	
}
