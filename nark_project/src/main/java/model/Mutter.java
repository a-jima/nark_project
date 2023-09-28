package model;

import java.io.Serializable;

public class Mutter implements Serializable {
	private int num;
	private String acId;
	private String text;
	private String image;
	private String date;
	private int petNum;

	public Mutter() {

	}

	public Mutter(int num, String acId, String text, String image, String date, int petNum) {
		this.num=num;
		this.acId=acId;
		this.text=text;
		this.image=image;
		this.date=date;
		this.petNum=petNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getAcId() {
		return acId;
	}

	public void setAcId(String acId) {
		this.acId = acId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPetNum() {
		return petNum;
	}

	public void setPetNum(int petNum) {
		this.petNum = petNum;
	}

}
