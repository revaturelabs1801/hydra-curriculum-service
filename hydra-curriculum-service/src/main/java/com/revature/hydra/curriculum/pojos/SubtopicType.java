package com.revature.hydra.curriculum.pojos;

public class SubtopicType {

	private Integer id;

	private String name;

	public SubtopicType() {
		//Empty Because No Args
	}

	public SubtopicType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicType(String name) {
		super();//NOSONAR
		this.name = name;
	}

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
		return "SubtopicType [id=" + id + ", name=" + name + "]";//NOSONAR
	}

}
