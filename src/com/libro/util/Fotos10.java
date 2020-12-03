package com.libro.util;

public class Fotos10 {
	private boolean ver1;
	private boolean ver2;
	private boolean ver3;
	private boolean ver4;
	private boolean ver5;
	private boolean ver6;
	private boolean ver7;
	private boolean ver8;
	private boolean ver9;
	private boolean ver10;
	private String ruta1;
	private String ruta2;
	private String ruta3;
	private String ruta4;
	private String ruta5;
	private String ruta6;
	private String ruta7;
	private String ruta8;
	private String ruta9;
	private String ruta10;
	
	public void setVer(int i){
		switch (i) {
			case 1:	ver1=true;
					ver2=ver3=ver4=ver5=ver6=ver7=ver8=ver9=ver10=false;
					break;
			case 2: ver1=ver2=true;
					ver3=ver4=ver5=ver6=ver7=ver8=ver9=ver10=false;
					break;
			case 3:	ver1=ver2=ver3=true;
					ver4=ver5=ver6=ver7=ver8=ver9=ver10=false;
					break;
			case 4: ver1=ver2=ver3=ver4=true;
					ver5=ver6=ver7=ver8=ver9=ver10=false;
					break;
			case 5:	ver1=ver2=ver3=ver4=ver5=true;
					ver6=ver7=ver8=ver9=ver10=false;
					break;
			case 6: ver1=ver2=ver3=ver4=ver5=ver6=true;
					ver7=ver8=ver9=ver10=false;
					break;
			case 7:	ver1=ver2=ver3=ver4=ver5=ver6=ver7=true;
					ver8=ver9=ver10=false;
					break;
			case 8: ver1=ver2=ver3=ver4=ver5=ver6=ver7=ver8=true;
					ver9=ver10=false;
					break;
			case 9:	ver1=ver2=ver3=ver4=ver5=ver6=ver7=ver8=ver9=true;
					ver10=false;
					break;
			case 10:ver1=ver2=ver3=ver4=ver5=ver6=ver7=ver8=ver9=ver10=true;
					break;
			default:
				break;
		}
	}
	public boolean isVer1() {
		return ver1;
	}
	public void setVer1(boolean ver1) {
		this.ver1 = ver1;
	}
	public boolean isVer2() {
		return ver2;
	}
	public void setVer2(boolean ver2) {
		this.ver2 = ver2;
	}
	public boolean isVer3() {
		return ver3;
	}
	public void setVer3(boolean ver3) {
		this.ver3 = ver3;
	}
	public boolean isVer4() {
		return ver4;
	}
	public void setVer4(boolean ver4) {
		this.ver4 = ver4;
	}
	public boolean isVer5() {
		return ver5;
	}
	public void setVer5(boolean ver5) {
		this.ver5 = ver5;
	}
	public boolean isVer6() {
		return ver6;
	}
	public void setVer6(boolean ver6) {
		this.ver6 = ver6;
	}
	public boolean isVer7() {
		return ver7;
	}
	public void setVer7(boolean ver7) {
		this.ver7 = ver7;
	}
	public boolean isVer8() {
		return ver8;
	}
	public void setVer8(boolean ver8) {
		this.ver8 = ver8;
	}
	public boolean isVer9() {
		return ver9;
	}
	public void setVer9(boolean ver9) {
		this.ver9 = ver9;
	}
	public boolean isVer10() {
		return ver10;
	}
	public void setVer10(boolean ver10) {
		this.ver10 = ver10;
	}
	public String getRuta1() {
		return ruta1;
	}
	public void setRuta1(String ruta1) {
		this.ruta1 = ruta1;
	}
	public String getRuta2() {
		return ruta2;
	}
	public void setRuta2(String ruta2) {
		this.ruta2 = ruta2;
	}
	public String getRuta3() {
		return ruta3;
	}
	public void setRuta3(String ruta3) {
		this.ruta3 = ruta3;
	}
	public String getRuta4() {
		return ruta4;
	}
	public void setRuta4(String ruta4) {
		this.ruta4 = ruta4;
	}
	public String getRuta5() {
		return ruta5;
	}
	public void setRuta5(String ruta5) {
		this.ruta5 = ruta5;
	}
	public String getRuta6() {
		return ruta6;
	}
	public void setRuta6(String ruta6) {
		this.ruta6 = ruta6;
	}
	public String getRuta7() {
		return ruta7;
	}
	public void setRuta7(String ruta7) {
		this.ruta7 = ruta7;
	}
	public String getRuta8() {
		return ruta8;
	}
	public void setRuta8(String ruta8) {
		this.ruta8 = ruta8;
	}
	public String getRuta9() {
		return ruta9;
	}
	public void setRuta9(String ruta9) {
		this.ruta9 = ruta9;
	}
	public String getRuta10() {
		return ruta10;
	}
	public void setRuta10(String ruta10) {
		this.ruta10 = ruta10;
	}
}
