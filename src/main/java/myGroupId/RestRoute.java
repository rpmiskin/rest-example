package myGroupId;

import myGroupId.rest.TodoListResource;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends SpringRouteBuilder {

	@Autowired
	private TodoListResource helloWorld;

	@Override
	public void configure() throws Exception {
		//@formatter:off
		from("jetty://http://0.0.0.0:9000?matchOnUriPrefix=true")
		.to("cxfbean:todoListResource?providers=#json");
		// @formatter:on
	}
}
