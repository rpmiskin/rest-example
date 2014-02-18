package in.misk.rest;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

/**
 * Extension of the JacksonJsonProvider that formats the JSON output to make it
 * easier to read.
 */
@Component("json")
public class PrettyJsonProvider extends JacksonJsonProvider {
	public PrettyJsonProvider() {
		super();
		this.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}

}
