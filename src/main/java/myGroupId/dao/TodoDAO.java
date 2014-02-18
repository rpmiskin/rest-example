/**
 * 
 */
package myGroupId.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author richard
 * 
 */
@Component
public class TodoDAO {

	private final Map<String, Todo> data = new LinkedHashMap<String, Todo>();

	public Todo[] getAll() {
		return data.values().toArray(new Todo[0]);
	}

	public Todo get(final String id) {
		return data.get(id);
	}

	public boolean update(final Todo input) {
		boolean success = false;
		if (data.containsKey(input.getId())) {
			data.put(input.getId(), input);
			success = true;
		}
		return success;
	}

	public boolean add(final Todo input) {
		boolean success = false;
		if (!data.containsKey(input.getId())) {
			input.setCreationDate(new Date());
			data.put(input.getId(), input);
			success = true;
		}
		return success;
	}

	public Todo remove(final String id) {
		return data.remove(id);
	}

}
