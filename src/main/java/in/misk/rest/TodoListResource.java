package in.misk.rest;

import in.misk.dao.TodoDAO;
import in.misk.dao.entities.TodoEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rest service that provides CRUD access to Todo objects.
 * 
 * <p>
 * The service sends and receives JSON data.
 * </p>
 * 
 */
@Path("/todolist/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
public class TodoListResource {

	/** Static logger instance. */
	private static final Logger LOGGER = Logger
			.getLogger(TodoListResource.class);

	@Autowired
	private TodoDAO dao;

	/** Mapper to convert between DAO and REST entities. */
	private final EntityMapper mapper = new EntityMapper();

	/**
	 * Example method to get multiple values. Shows use of a query param.
	 * 
	 * @param title
	 *            the title of models to find
	 * @return a Response containing JSON data.
	 */
	@GET
	@Path("todos")
	public Response getAllValues(@QueryParam("title") final String title) {
		final TodoEntity[] all = dao.get();
		return Response.ok().entity(mapper.fromEntity(all)).build();
	}

	@OPTIONS
	@Path("todos")
	public Response getOptions() {
		return Response.ok().build();
	}

	@OPTIONS
	@Path("todos/{id}")
	public Response getOptions(@PathParam("id") final String ida) {
		return Response.ok().build();
	}

	/**
	 * Returns a specific model by id.
	 * 
	 * @param id
	 *            the of the matching model
	 * @return the request document as JSON
	 */
	@GET
	@Path("todos/{id}")
	public Response read(@PathParam("id") final String id,
			@Context final SecurityContext context) {
		ResponseBuilder response = null;
		final Todo todo = mapper.fromEntity(dao.get(id));
		if (todo == null) {
			response = Response.status(Status.NOT_FOUND);
		} else {
			response = Response.status(Status.OK).entity(todo);
		}
		return response.build();
	}

	@POST
	@Path("todos")
	public Response create(@Context final UriInfo uriInfo, final Todo todo) {
		ResponseBuilder response = null;
		LOGGER.info("create:\n" + writeJSON(todo));
		LOGGER.info(uriInfo.getRequestUri().toString());
		if (todo.getId() == null || dao.get(todo.getId()) == null) {
			if (todo.getId() == null) {
				todo.setId(UUID.randomUUID().toString());
			}
			dao.add(mapper.toEntity(todo));
			try {
				response = Response.status(Status.CREATED)
						.location(new URI("/helloworld/todos/" + todo.getId()))
						.entity(todo);
			} catch (final URISyntaxException e) {
				response = Response.serverError();

			}
		} else {
			response = Response.status(Status.CONFLICT);
		}
		return response.build();
	}

	@PUT
	@Path("todos/{id}")
	public Response update(@PathParam("id") final String id, final Todo todo) {
		ResponseBuilder response = null;
		LOGGER.info("update:\n" + writeJSON(todo));
		if (dao.get(id) == null) {
			response = Response.status(Status.NOT_FOUND).entity("Not found");
		} else {
			todo.setId(id);
			if (dao.update(mapper.toEntity(todo))) {
				response = Response.status(Status.OK).entity(todo);
			} else {
				response = Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity("Not found");
			}
		}

		return response.build();
	}

	@DELETE
	@Path("todos/{id}")
	public Response delete(@PathParam("id") final String id) {
		ResponseBuilder response = null;
		LOGGER.info("delete: " + id);
		if (dao.get(id) == null) {
			response = Response.status(Status.NOT_FOUND).entity("Not found");
		} else {
			final Todo todo = mapper.fromEntity(dao.remove(id));
			response = Response.ok(todo);
		}

		return response.build();
	}

	/**
	 * @param returnVal
	 * @return
	 */
	private String writeJSON(final Object returnVal) {
		final ObjectMapper jsonMapper = new ObjectMapper();
		final StringWriter writer = new StringWriter();
		try {

			jsonMapper.writerWithDefaultPrettyPrinter().writeValue(writer,
					returnVal);
		} catch (final IOException e) {
			e.printStackTrace(new PrintWriter(writer));
		}
		final String str = writer.toString() + '\n';
		return str;
	}
}
