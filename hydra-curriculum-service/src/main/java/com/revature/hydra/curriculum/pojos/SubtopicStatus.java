package com.revature.hydra.curriculum.pojos;

public class SubtopicStatus {

	private Integer id;

	private String name;

	public SubtopicStatus() {
		//Empty Because No Args
	}

	public SubtopicStatus(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicStatus(String name) {
		super();
		this.name = name;
	}//NOSONAR

	public Integer getId() {
		return id;
	}//NOSONAR

	public void setId(Integer id) {
		this.id = id;
	}//NOSONAR

	public String getName() {
		return name;
	}//NOSONAR

	public void setName(String name) {
		this.name = name;
	}//NOSONAR

	@Override
	public String toString() {
		return "SubtopicStatus [id=" + id + ", name=" + name + "]";//NOSONAR
	}

}
