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
@XmlType(propOrder={"id","name","image","url"})

public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/********************************************************************************
	 * DEFINITION OF ALL THE PRIVATE ATTRIBUTES OF THE CLASS						*
	 ********************************************************************************/
	
	@XmlElement private String id;
	@XmlElement private String name;
	@XmlElement private String image;
	@XmlElement private String url;
	
	
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
	 * A method that returns the URL of the detailed recipe.
	 * @return url: the URL of the detailed recipe
	 */
	public String getUrl() {
		return url;
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
	 * A method that sets the URL of the detailed recipe.
	 * @param url: the URL of the detailed recipe
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}