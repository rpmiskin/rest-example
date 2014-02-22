/**
 * 
 */
package in.misk.rest;

import in.misk.dao.entities.TodoEntity;

/**
 * Provides methods required to map between database entities and objects to
 * send over REST.
 * 
 * <p>
 * Note: not overly worthwhile at the moment, but the concept is that there
 * might be data that is used in the entities that should not be exposed over
 * REST.
 * </p>
 * 
 * @author richard
 * 
 */
public class EntityMapper {
	TodoEntity toEntity(final Todo dto) {
		final TodoEntity entity = new TodoEntity();
		entity.setId(dto.getId());
		entity.setTask(dto.getTask());
		entity.setComplete(dto.isComplete());
		entity.setCreationDate(dto.getCreationDate());
		return entity;
	}

	Todo fromEntity(final TodoEntity entity) {
		final Todo dto = new Todo();
		dto.setId(entity.getId());
		dto.setTask(entity.getTask());
		dto.setComplete(entity.isComplete());
		dto.setCreationDate(entity.getCreationDate());
		return dto;
	}

	Todo[] fromEntity(final TodoEntity[] entity) {
		final Todo[] dto = new Todo[entity.length];
		for (int i = 0; i < entity.length; i++) {
			dto[i] = fromEntity(entity[i]);
		}
		return dto;
	}
}
