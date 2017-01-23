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
	
	
	/**
	 * A method that returns the id of the recipe.
	 * @return id: the id of the recipe
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * A method that returns the name of the recipe.
	 * @return name: the name of the recipe
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * A method that returns the image URL of the recipe.
	 * @return image: the image URL of the recipe
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * A method that returns the URL of the recipe.
	 * @return url: the URL of the recipe
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * A method that returns the amount of proteins of the recipe.
	 * @return proteins: the amount of proteins of the recipe
	 */
	public double getProteins() {
		return proteins;
	}
	
	/**
	 * A method that returns the amount of carbohydrates of the recipe.
	 * @return carbohydrates: the amount of carbohydrates of the recipe
	 */
	public double getCarbohydrates() {
		return carbohydrates;
	}
	
	/**
	 * A method that returns the amount of lipids of the recipe.
	 * @return lipids: the amount of lipids of the recipe
	 */
	public double getLipids() {
		return lipids;
	}
	
	/**
	 * A method that returns the amount of saturated lipids of the recipe.
	 * @return saturatedLipids: the amount of saturated lipids of the recipe
	 */
	public double getSaturatedLipids() {
		return saturatedLipids;
	}
	
	/**
	 * A method that returns the amount of calories of the recipe.
	 * @return calories: the amount of calories of the recipe
	 */
	public double getCalories() {
		return calories;
	}
	
	/**
	 * A method that returns the amount of sodium of the recipe.
	 * @return sodium: the amount of sodium of the recipe
	 */
	public double getSodium() {
		return sodium;
	}
	
	/**
	 * A method that returns the amount of potassium of the recipe.
	 * @return potassium: the amount of potassium of the recipe
	 */
	public double getPotassium() {
		return potassium;
	}
	
	/**
	 * A method that returns the amount of calcium of the recipe.
	 * @return calcium: the amount of calcium of the recipe
	 */
	public double getCalcium() {
		return calcium;
	}
	
	/**
	 * A method that returns the amount of starch of the recipe.
	 * @return starch: the amount of starch of the recipe
	 */
	public double getStarch() {
		return starch;
	}
	
	/**
	 * A method that returns the amount of fiber of the recipe.
	 * @return fiber: the amount of fiber of the recipe
	 */
	public double getFiber() {
		return fiber;
	}
	
	/**
	 * A method that sets the id of the recipe.
	 * @param id: the id of the recipe
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * A method that sets the name of the recipe.
	 * @param name: the name of the recipe
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * A method that sets the image URL of the recipe.
	 * @param image: the image URL of the recipe
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * A method that sets the URL of the recipe.
	 * @param url: the URL of the recipe
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * A method that sets the amount of proteins of the recipe.
	 * @param proteins: the amount of proteins of the recipe
	 */
	public void setProteins(double proteins) {
		this.proteins = proteins;
	}
	
	/**
	 * A method that sets the amount of carbohydrates of the recipe.
	 * @param carbohydrates: the amount of carbohydrates of the recipe
	 */
	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	
	/**
	 * A method that sets the amount of lipids of the recipe.
	 * @param lipids: the amount of lipids of the recipe
	 */
	public void setLipids(double lipids) {
		this.lipids = lipids;
	}
	
	/**
	 * A method that sets the amount of saturated lipids of the recipe.
	 * @param saturatedLipids: the amount of saturated lipids of the recipe
	 */
	public void setSaturatedLipids(double saturatedLipids) {
		this.saturatedLipids = saturatedLipids;
	}
	
	/**
	 * A method that sets the amount of calories of the recipe.
	 * @param calories: the amount of calories of the recipe
	 */
	public void setCalories(double calories) {
		this.calories = calories;
	}
	
	/**
	 * A method that sets the amount of sodium of the recipe.
	 * @param sodium: the amount of sodium of the recipe
	 */
	public void setSodium(double sodium) {
		this.sodium = sodium;
	}
	
	/**
	 * A method that sets the amount of potassium of the recipe.
	 * @param potassium: the amount of potassium of the recipe
	 */
	public void setPotassium(double potassium) {
		this.potassium = potassium;
	}
	
	/**
	 * A method that sets the amount of calcium of the recipe.
	 * @param calcium: the amount of calcium of the recipe
	 */
	public void setCalcium(double calcium) {
		this.calcium = calcium;
	}
	
	/**
	 * A method that sets the amount of starch of the recipe.
	 * @param starch: the amount of starch of the recipe
	 */
	public void setStarch(double starch) {
		this.starch = starch;
	}
	
	/**
	 * A method that sets the amount of fiber of the recipe.
	 * @param fiber: the amount of fiber of the recipe
	 */
	public void setFiber(double fiber) {
		this.fiber = fiber;
	}	
}