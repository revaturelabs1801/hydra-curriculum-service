package com.revature.hydra.curriculum.pojos;

import java.sql.Timestamp;

public class Subtopic {

	private int subtopicId;

	private SubtopicName subtopicName;

	private Batch batch;

	private SubtopicStatus status;

	private Timestamp subtopicDate;

	public Subtopic() {
		super();
	}

	public Subtopic(SubtopicName subtopicName, Batch batch, SubtopicStatus status, Timestamp subtopicDate) {
		super();
		this.subtopicName = subtopicName;
		this.batch = batch;
		this.status = status;
		this.subtopicDate = subtopicDate;
	}

	public Subtopic(int subtopicId, SubtopicName subtopicName, Batch batch, SubtopicStatus status,
			Timestamp subtopicDate) {
		super();
		this.subtopicId = subtopicId;
		this.subtopicName = subtopicName;
		this.batch = batch;
		this.status = status;
		this.subtopicDate = subtopicDate;
	}

	public int getSubtopicId() {
		return subtopicId;
	}

	public void setSubtopicId(int subtopicId) {
		this.subtopicId = subtopicId;
	}

	public SubtopicName getSubtopicName() {
		return subtopicName;
	}

	public void setSubtopicName(SubtopicName subtopicName) {
		this.subtopicName = subtopicName;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public SubtopicStatus getStatus() {
		return status;
	}

	public void setStatus(SubtopicStatus status) {
		this.status = status;
	}

	public Timestamp getSubtopicDate() {
		return subtopicDate;
	}

	public void setSubtopicDate(Timestamp subtopicDate) {
		this.subtopicDate = subtopicDate;
	}

	@Override
	public String toString() {
		return "Subtopic [subtopicId=" + subtopicId + ", batch=" + batch + ", subtopicDate=" + subtopicDate + ", status=" + status +"]";
	}

}
