package in.misk.route;

import in.misk.rest.TodoListResource;

import org.apache.camel.spring.SpringRouteBuilder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Camel route to expose a REST webservice within the embedded jetty container.
 * 
 */
@Component
public class RestRoute extends SpringRouteBuilder {

	@Autowired
	private TodoListResource helloWorld;

	@Autowired
	CrossOriginFilter filter;

	@Override
	public void configure() throws Exception {
		//@formatter:off
		from("jetty://http://0.0.0.0:9000?matchOnUriPrefix=true&filtersRef=cors-filter")
		.to("cxfbean:todoListResource?providers=#json");
		// @formatter:on
	}
}
