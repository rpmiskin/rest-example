/**
 * 
 */
package in.misk.dao.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Database entity relating to Todo entries.
 * 
 */
@Entity()
@Table(name = "TODO")
public class TodoEntity {

	@Id
	private String id;
	@Column
	private String task;
	@Column
	private boolean complete;
	@Column
	private Date creationDate;

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(final boolean complete) {
		this.complete = complete;
	}

	public String getTask() {
		return task;
	}

	public void setTask(final String task) {
		this.task = task;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

}
