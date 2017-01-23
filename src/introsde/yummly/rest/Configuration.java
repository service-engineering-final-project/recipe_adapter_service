package introsde.yummly.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


/**
 * A configuration class used by jersey to load resource classes and additional features
 * (or modules) provided by Jersey.
 * 
 * @author alan
 *
 */

@ApplicationPath("rest")
public class Configuration extends ResourceConfig {
	public Configuration () {
		packages("introsde.yummly.rest");	// Jersey will load all the resources on this package
	}
}