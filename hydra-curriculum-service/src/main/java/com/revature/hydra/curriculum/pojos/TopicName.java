package com.revature.hydra.curriculum.pojos;

public class TopicName {

	private Integer id;

	private String name;

	public TopicName() {
		super();
	}

	public TopicName(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public TopicName(String name) {
		super(); //NOSONAR
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
		return "TopicName [id=" + id + ", name=" + name + "]";//NOSONAR
	}

}
