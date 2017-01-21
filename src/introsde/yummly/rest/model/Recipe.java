package introsde.yummly.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The JAVA class for the "recipe" model.
 * 
 * @author alan
 *
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement			// make it the root element

// The content order in the generated schema type
@XmlType(propOrder={"id","name","image","url","proteins","carbohydrates","lipids",
		"saturatedLipids","calories","sodium","potassium","calcium","starch","fiber"})

public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/********************************************************************************
	 * DEFINITION OF ALL THE PRIVATE ATTRIBUTES OF THE CLASS						*
	 ********************************************************************************/
	
	@XmlElement private String id;
	@XmlElement private String name;
	@XmlElement private String image;
	@XmlElement private String url;
	@XmlElement private double proteins;
	@XmlElement private double carbohydrates;
	@XmlElement private double lipids;
	@XmlElement private double saturatedLipids;
	@XmlElement private double calories;
	@XmlElement private double sodium;
	@XmlElement private double potassium;
	@XmlElement private double calcium;
	@XmlElement private double starch;
	@XmlElement private double fiber;
	
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getImage() {
		return image;
	}
	public String getUrl() {
		return url;
	}
	public double getProteins() {
		return proteins;
	}
	public double getCarbohydrates() {
		return carbohydrates;
	}
	public double getLipids() {
		return lipids;
	}
	public double getSaturatedLipids() {
		return saturatedLipids;
	}
	public double getCalories() {
		return calories;
	}
	public double getSodium() {
		return sodium;
	}
	public double getPotassium() {
		return potassium;
	}
	public double getCalcium() {
		return calcium;
	}
	public double getStarch() {
		return starch;
	}
	public double getFiber() {
		return fiber;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setProteins(double proteins) {
		this.proteins = proteins;
	}
	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	public void setLipids(double lipids) {
		this.lipids = lipids;
	}
	public void setSaturatedLipids(double saturatedLipids) {
		this.saturatedLipids = saturatedLipids;
	}
	public void setCalories(double calories) {
		this.calories = calories;
	}
	public void setSodium(double sodium) {
		this.sodium = sodium;
	}
	public void setPotassium(double potassium) {
		this.potassium = potassium;
	}
	public void setCalcium(double calcium) {
		this.calcium = calcium;
	}
	public void setStarch(double starch) {
		this.starch = starch;
	}
	public void setFiber(double fiber) {
		this.fiber = fiber;
	}	
}