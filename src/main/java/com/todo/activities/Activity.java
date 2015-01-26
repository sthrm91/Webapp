package com.todo.activities;

public class Activity {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean isDone) {
		this.done = isDone;
	}	
	
	@Override
	public String toString() {
		return "Activity [title=" + title + ", description=" + description
				+ ", ownerId=" + ownerId + ", isDone=" + done + "]";
	}
	
	private String id;
	public String title;
	public String description;
	private String ownerId;
	public boolean done;	
}
