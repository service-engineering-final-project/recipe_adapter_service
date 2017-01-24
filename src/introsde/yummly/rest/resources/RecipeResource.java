package introsde.yummly.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
import introsde.yummly.rest.model.RecipeNutritionFacts;


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
	private final String yummlyId = "9adc19a6";
	private final String yummlyKey = "c62db4236d74d2ed2e187a1c6321febf";

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
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
	public RecipeNutritionFacts getRecipe(@PathParam("id") String id) {
		RecipeNutritionFacts recipe = null;
		
		// Send the request and get the relative response
		Response response = webTarget.path("recipe/" + id).request()
				.accept(MediaType.APPLICATION_JSON)
				.header("X-Yummly-App-ID", yummlyId)
				.header("X-Yummly-App-Key", yummlyKey)
				.get(Response.class);
		int statusCode = response.getStatus();
		
		// Check the HTTP status code
		if (statusCode==200) {
			try {
				JsonNode root = mapper.readTree(response.readEntity(String.class));
				JsonNode nutritionFacts = root.path("nutritionEstimates");
				
				// Set the attributes of the recipe
				recipe = new RecipeNutritionFacts();
				recipe.setId(root.path("id").asText());
				recipe.setName(root.path("name").asText());
				recipe.setImage(root.path("images").get(0).path("hostedMediumUrl").asText());
				recipe.setWebsiteUrl(root.path("attribution").path("url").asText());

				System.out.println("getRandomRecipe() called. [GET /" + root.path("id").asText() + "/]");
				for (int i=0; i<nutritionFacts.size(); i++) {
					String chemical = nutritionFacts.get(i).get("attribute").asText();
					double value = nutritionFacts.get(i).get("value").asDouble();
					
					switch(chemical) {
						case "PROCNT":
							System.out.println("\tProteins: " + Double.toString(value) + " g");
							recipe.setProteins(value);
							break;
						case "CHOCDF":
							System.out.println("\tCarbohydrates: " + Double.toString(value) + " g");
							recipe.setCarbohydrates(value);
							break;
						case "FAT":
							System.out.println("\tLipids: " + Double.toString(value) + " g");
							recipe.setLipids(value);
							break;
						case "FASAT":
							System.out.println("\tSaturated fatty acids: " + Double.toString(value) + " g");
							recipe.setSaturatedLipids(value);
							break;
						case "ENERC_KCAL":
							System.out.println("\tCalories: " + Double.toString(value) + " Kcal");
							recipe.setCalories(value);
							break;
						case "NA":
							System.out.println("\tSodium: " + Double.toString(value) + " g");
							recipe.setSodium(value);
							break;
						case "K":
							System.out.println("\tPotassium: " + Double.toString(value) + " g");
							recipe.setPotassium(value);
							break;
						case "CA":
							System.out.println("\tCalcium: " + Double.toString(value) + " g");
							recipe.setCalcium(value);
							break;
						case "STARCH":
							System.out.println("\tStarch: " + Double.toString(value) + " g");
							recipe.setStarch(value);
							break;
						case "FIBTG":
							System.out.println("\tFiber: " + Double.toString(value) + " g");
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
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Recipe> getRecipesList(
			@QueryParam("keyword") String keyword,
			@QueryParam("minKcal") int minKcal,
			@QueryParam("maxKcal") int maxKcal,
			@QueryParam("course") String course,
			@QueryParam("allergy") String allergy
	) {
		Recipe recipe = null;
		List<Recipe> recipesList = null;
		final int maxResults = 50;		// the maximum number of results to collect
		final double maxSodium = 1.0;	// the maximum amount of sodium allowed for each recipe
		
		// Set the base target with the static values
		WebTarget recipesWebTarget = webTarget.path("recipes")
				.queryParam("maxResult", maxResults)
				.queryParam("nutrition.NA.max", maxSodium);
		
		// Extend the base target with parameters given as input
		if (keyword != null) recipesWebTarget = recipesWebTarget.queryParam("q", keyword);
		if (minKcal != 0) recipesWebTarget = recipesWebTarget.queryParam("nutrition.ENERC_KCAL.min", minKcal);
		if (maxKcal != 0) recipesWebTarget = recipesWebTarget.queryParam("nutrition.ENERC_KCAL.max", maxKcal);
		if (course != null) {
			course = convertCourseSearchValue(course);
			recipesWebTarget = recipesWebTarget.queryParam("allowedCourse[]", course);
		}
		if (allergy != null) {
			allergy = convertAllergySearchValue(allergy);
			recipesWebTarget = recipesWebTarget.queryParam("allowedAllergy[]", allergy);
		}
		
		// Print the complete path performed on Yummly API
		// System.out.println(recipesWebTarget.toString());

		// Send the request and get the relative response
		Response response = recipesWebTarget.request()
				.accept(MediaType.APPLICATION_JSON)
				.header("X-Yummly-App-ID", yummlyId)
				.header("X-Yummly-App-Key", yummlyKey)
				.get(Response.class);
		int statusCode = response.getStatus();
		
		// Check the HTTP status code
		if (statusCode==200) {
			try {
				JsonNode root = mapper.readTree(response.readEntity(String.class));
				JsonNode recipesResults = root.path("matches");
				recipesList = new ArrayList<Recipe>();
				
				// Iterate through the whole list of found recipes
				if (recipesResults.size()>0) {
					System.out.println("getRecipesList() called. [GET /]");
					for (int i=0; i<recipesResults.size(); i++) {
						// Set the attributes of the recipe
						recipe = new Recipe();
						recipe.setId(recipesResults.get(i).path("id").asText());
						recipe.setName(recipesResults.get(i).path("recipeName").asText());
						if ((recipesResults.get(i).path("smallImageUrls").get(0)) != null) {
							recipe.setImage(recipesResults.get(i).path("smallImageUrls").get(0).asText());
						}
						recipe.setDetails("https://recipe-adapter-service-ar.herokuapp.com/rest/yummly/" + recipesResults.get(i).path("id").asText());
						recipesList.add(recipe);
					}
					System.out.println("\t" + recipesResults.size() + " recipes found. See the HTTP GET response for details.");
				} else {
					System.out.println("There is no recipe respecting the provided parameters!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new WebApplicationException(statusCode);
		}

		return recipesList;
	}
	
	/***
	 * A method that returns a random recipe from a list of recipes according to some input parameters.
	 * @param keyword: a keyword to filter results
	 * @param minKcal: the minimum amount of kilocalories
	 * @param maxKcal: the maximum amount of kilocalories
	 * @param course: the kind of plate
	 * @param allergy: a (non-mandatory) allergy
	 * @return a random recipe from a list of recipes respecting the input conditions
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/random")
	public Recipe getRandomRecipe(
			@QueryParam("keyword") String keyword,
			@QueryParam("minKcal") int minKcal,
			@QueryParam("maxKcal") int maxKcal,
			@QueryParam("course") String course,
			@QueryParam("allergy") String allergy
	) {
		Recipe recipe = null;
		Recipe randomRecipe = null;
		List<Recipe> recipesList = null;
		final int maxResults = 50;		// the maximum number of results to collect
		final double maxSodium = 1.0;	// the maximum amount of sodium allowed for each recipe
		int randomRecipeIndex;			// the variable useful to retrieve a random recipe
		
		// Set the base target with the static values
		WebTarget recipesWebTarget = webTarget.path("recipes")
				.queryParam("maxResult", maxResults)
				.queryParam("nutrition.NA.max", maxSodium);
		
		// Extend the base target with parameters given as input
		if (keyword != null) recipesWebTarget = recipesWebTarget.queryParam("q", keyword);
		if (minKcal != 0) recipesWebTarget = recipesWebTarget.queryParam("nutrition.ENERC_KCAL.min", minKcal);
		if (maxKcal != 0) recipesWebTarget = recipesWebTarget.queryParam("nutrition.ENERC_KCAL.max", maxKcal);
		if (course != null) {
			course = convertCourseSearchValue(course);
			recipesWebTarget = recipesWebTarget.queryParam("allowedCourse[]", course);
		}
		if (allergy != null) {
			allergy = convertAllergySearchValue(allergy);
			recipesWebTarget = recipesWebTarget.queryParam("allowedAllergy[]", allergy);
		}
		
		// Print the complete path performed on Yummly API
		// System.out.println(recipesWebTarget.toString());
		
		// Send the request and get the relative response
		Response response = recipesWebTarget.request()
				.accept(MediaType.APPLICATION_JSON)
				.header("X-Yummly-App-ID", yummlyId)
				.header("X-Yummly-App-Key", yummlyKey)
				.get(Response.class);
		int statusCode = response.getStatus();
				
		// Check the HTTP status code
		if (statusCode==200) {
			try {
				JsonNode root = mapper.readTree(response.readEntity(String.class));
				JsonNode recipesResults = root.path("matches");
				recipesList = new ArrayList<Recipe>();
						
				// Iterate through the whole list of found recipes
				if (recipesResults.size()>0) {
					System.out.println("getRandomRecipe() called. [GET /random/]");
					for (int i=0; i<recipesResults.size(); i++) {
						// Set the attributes of the recipe
						recipe = new Recipe();
						recipe.setId(recipesResults.get(i).path("id").asText());
						recipe.setName(recipesResults.get(i).path("recipeName").asText());
						if ((recipesResults.get(i).path("smallImageUrls").get(0)) != null) {
							recipe.setImage(recipesResults.get(i).path("smallImageUrls").get(0).asText());
						}
						recipe.setDetails("https://recipe-adapter-service-ar.herokuapp.com/rest/yummly/" + recipesResults.get(i).path("id").asText());
						recipesList.add(recipe);
					}
					
					randomRecipeIndex = ThreadLocalRandom.current().nextInt(0, recipesResults.size()+1);
					randomRecipe = recipesList.get(randomRecipeIndex);
					
					System.out.println("\t\"" + randomRecipe.getName() + "\" is the recipe to suggest. :-)");
				} else {
					System.out.println("There is no recipe respecting the provided parameters!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new WebApplicationException(statusCode);
		}

		return randomRecipe;
	}
	
	
	/********************************************************************************
	 * HELPER METHODS USEFUL FOR THE COMPUTATION OF OTHER METHODS IN THIS CLASS		*
	 ********************************************************************************/
	
	/***
	 * An accessory method that converts the course search value in order to make it 
	 * conform to the search value of the Yummly API.
	 * @param course: the course given as input
	 * @return the course ready to be used on the Yummly API.
	 */
	public String convertCourseSearchValue(String course) {
		switch(course) {
			case "main":
				course = "course^course-Main+Dishes";
				break;
			case "breakfast":
				course = "course^course-Breakfast+and+Brunch";
				break;
			case "lunch":
				course = "course^course-Lunch";
				break;
			case "dessert":
				course = "course^course-Desserts";
				break;
			case "appetizer":
				course = "course^course-Appetizers";
				break;
			case "beverage":
				course = "course^course-Beverages";
				break;
			default:
				course = "";
				System.out.println("Course: no particular course has been selected.");
		}
		
		return course;
	}
	
	/***
	 * An accessory method that converts the allergy search value in order to make it 
	 * conform to the search value of the Yummly API.
	 * @param allergy: the course given as input
	 * @return the allergy ready to be used on the Yummly API.
	 */
	public String convertAllergySearchValue(String allergy) {
		switch(allergy) {
			case "glutenfree":
				allergy = "393^Gluten-Free";
				break;
			case "peanutfree":
				allergy = "394^Peanut-Free";
				break;
			case "seafoodfree":
				allergy = "398^Seafood-Free";
				break;
			case "sesamefree":
				allergy = "399^Sesame-Free";
				break;
			case "soyfree":
				allergy = "400^Soy-Free";
				break;
			case "dairyfree":
				allergy = "396^Dairy-Free";
				break;
			case "eggfree":
				allergy = "397^Egg-Free";
				break;
			case "sulfitefree":
				allergy = "401^Sulfite-Free";
				break;
			case "treenutfree":
				allergy = "395^Tree+Nut-Free";
				break;
			case "wheatfree":
				allergy = "392^Wheat-Free";
				break;
			default:
				allergy = "";
				System.out.println("Allergy: no particular allergy has been selected.");
		}
		
		return allergy;
	}
}