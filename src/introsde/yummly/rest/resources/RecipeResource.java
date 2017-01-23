package introsde.yummly.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import introsde.yummly.rest.model.Recipe;


/***
 * The resource class that implements our service endpoints for the Recipe.
 * 
 * @author alan
 *
 */

// @Stateless
// @LocalBean
@Path("/yummly/")
public class RecipeResource {
	@Context UriInfo uriInfo;	// allows to insert contextual objects (uriInfo) into the class
	@Context Request request;	// allows to insert contextual objects (request) into the class
	
	DocumentBuilder docBuilder;
	WebTarget webTarget;
	ObjectMapper mapper = new ObjectMapper();
	
	// Definition of some useful constants
	final String baseUrl = "https://api.yummly.com/v1/api/";
	private final String yummlyId = "6adc16a9";
	private final String yummlyKey = "c92db4239d74d2ed2e187a1c9321febf";

	public RecipeResource() {
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		webTarget = ClientBuilder.newClient(
				new ClientConfig()).target(UriBuilder.fromUri(baseUrl).build()
		);
	}
	
	/***
	 * A method that returns a recipe with a particular ID given as parameter.
	 * @param id: the univocal identifier of the recipe
	 * @return the recipe having that very identifier
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{id}")
	public Recipe getRecipe(@PathParam("id") String id) {
		Recipe recipe = null;
		
		Response response = webTarget.path("recipe/" + id).request()
				.accept(MediaType.APPLICATION_JSON)
				.header("X-Yummly-App-ID", yummlyId)
				.header("X-Yummly-App-Key", yummlyKey)
				.get(Response.class);
		int statusCode = response.getStatus();
		
		if (statusCode==200) {
			try {
				JsonNode root = mapper.readTree(response.readEntity(String.class));
				JsonNode nutritionFacts = root.path("nutritionEstimates");
				
				recipe = new Recipe();
				recipe.setId(root.path("id").asText());
				recipe.setName(root.path("name").asText());
				recipe.setImage(root.path("images").get(0).path("hostedMediumUrl").asText());
				recipe.setUrl(root.path("attribution").path("url").asText());

				for (int i=0; i<nutritionFacts.size(); i++) {
					String chemical = nutritionFacts.get(i).get("attribute").asText();
					double value = nutritionFacts.get(i).get("value").asDouble();
					
					switch(chemical) {
						case "PROCNT":
							System.out.println("Proteins: " + Double.toString(value) + " g");
							recipe.setProteins(value);
							break;
						case "CHOCDF":
							System.out.println("Carbohydrates: " + Double.toString(value) + " g");
							recipe.setCarbohydrates(value);
							break;
						case "FAT":
							System.out.println("Lipids: " + Double.toString(value) + " g");
							recipe.setLipids(value);
							break;
						case "FASAT":
							System.out.println("Saturated fatty acids: " + Double.toString(value) + " g");
							recipe.setSaturatedLipids(value);
							break;
						case "ENERC_KCAL":
							System.out.println("Calories: " + Double.toString(value) + " Kcal");
							recipe.setCalories(value);
							break;
						case "NA":
							System.out.println("Sodium: " + Double.toString(value) + " g");
							recipe.setSodium(value);
							break;
						case "K":
							System.out.println("Potassium: " + Double.toString(value) + " g");
							recipe.setPotassium(value);
							break;
						case "CA":
							System.out.println("Calcium: " + Double.toString(value) + " g");
							recipe.setCalcium(value);
							break;
						case "STARCH":
							System.out.println("Starch: " + Double.toString(value) + " g");
							recipe.setStarch(value);
							break;
						case "FIBTG":
							System.out.println("Fiber: " + Double.toString(value) + " g");
							recipe.setFiber(value);
							break;
						default:
							// This chemical is not interesting to our study
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new WebApplicationException(statusCode);
		}
		
		return recipe;
	}
	
	/***
	 * A method that returns a list of recipes according to some input parameters.
	 * @param keyword: a keyword to filter results
	 * @param minKcal: the minimum amount of kilocalories
	 * @param maxKcal: the maximum amount of kilocalories
	 * @param course: the kind of plate
	 * @param allergy: a (non-mandatory) allergy
	 * @return a list of recipes respecting the input conditions
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Recipe> getRecipesList(
			@QueryParam("keyword") String keyword,
			@QueryParam("minKcal") int minKcal,
			@QueryParam("maxKcal") int maxKcal,
			@QueryParam("course") String course,
			@QueryParam("allergy") String allergy
	) {
		Recipe recipe = null;
		List<Recipe> recipesList = null;
		int maxResults = 50;
		double maxSodium = 1.0;
		
		if (keyword.equals(null)) keyword = "fruit";
		if (minKcal==0) minKcal = 500;
		if (maxKcal==0) maxKcal = 1500;
		if (course.equals(null)) course = "course%5Ecourse%2DMain%20Dishes";
		// allergy example: 393%5EGluten%2DFree
		
		WebTarget recipesWebTarget = webTarget.path("recipes")
				.queryParam("maxResult", maxResults)
				.queryParam("q", keyword)
				.queryParam("nutrition.NA.max", maxSodium)
				.queryParam("nutrition.ENERC_KCAL.min", minKcal)
				.queryParam("nutrition.ENERC_KCAL.max", maxKcal)
				.queryParam("allowedCourse[]", course)
				.queryParam("allowedAllergy[]", allergy);
				
		Response response = recipesWebTarget.request()
				.accept(MediaType.APPLICATION_JSON)
				.header("X-Yummly-App-ID", yummlyId)
				.header("X-Yummly-App-Key", yummlyKey)
				.get(Response.class);
		int statusCode = response.getStatus();
		
		if (statusCode==200) {
			try {
				JsonNode root = mapper.readTree(response.readEntity(String.class));
				JsonNode recipesResults = root.path("matches");
				recipesList = new ArrayList<Recipe>();
				
				for (int i=0; i<recipesResults.size(); i++) {
					recipe = new Recipe();
					recipe.setId(recipesResults.get(i).path("id").asText());
					recipe.setName(recipesResults.get(i).path("recipeName").asText());
					recipe.setImage(recipesResults.get(i).path("smallImageUrls").get(0).asText());
					recipe.setUrl("http://www.yummly.com/recipe/" + recipesResults.get(i).path("id").asText());
					recipesList.add(recipe);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new WebApplicationException(statusCode);
		}

		return recipesList;
	}
}