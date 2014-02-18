package myGroupId.dao;

import java.io.Serializable;
import java.util.Date;

public class Todo implements Serializable {
	/** */
	private static final long serialVersionUID = 6134498129802081056L;
	private String id;
	private String task;
	private boolean complete;
	private Date creationDate;

	public Todo() {

	}

	public Todo(final String id, final String task) {
		this.id = id;
		this.task = task;
		this.complete = false;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(final boolean complete) {
		this.complete = complete;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(final String task) {
		this.task = task;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date date) {
		this.creationDate = date;
	}
}
