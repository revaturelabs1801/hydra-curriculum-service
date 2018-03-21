package com.revature.hydra.curriculum.pojos;

import java.sql.Timestamp;

public class Batch {

	private Integer id;

	private String name;

	private Timestamp startDate;

	private Timestamp endDate;
	
	private Integer trainerID;

	private BatchType type;

	public Batch() {
		super();
	}
	
	

	public Batch(String name, Timestamp startDate, Timestamp endDate, int trainerID, BatchType type) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainerID = trainerID;
		this.type = type;
	}



	public Batch(Integer id, String name, Timestamp startDate, Timestamp endDate, int trainer, BatchType type) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainerID = trainer;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getTrainer() {
		// if needed we will get the trainer from the user micro service
		return trainerID;
	}

	public void setTrainer(int trainer) {
		// if needed we can change this to take in a trainer
		// and then grab the trainers id
		this.trainerID = trainer;
	}


	public int getTrainerID() {
		return trainerID;
	}



	public void setTrainerID(int trainerID) {
		this.trainerID = trainerID;
	}



	public BatchType getType() {
		return type;
	}



	public void setType(BatchType type) {
		this.type = type;
	}

	

	@Override
	public String toString() {
		return "Batch [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", trainerID=" + trainerID + ", type=" + type + "]";
	}

}
