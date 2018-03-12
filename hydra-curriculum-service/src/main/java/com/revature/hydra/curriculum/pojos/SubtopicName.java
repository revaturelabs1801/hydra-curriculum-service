package com.revature.hydra.curriculum.pojos;

public class SubtopicName {

	private Integer id;

	private String name;

	private TopicName topic;

	private SubtopicType type;

	public SubtopicName() {
		super();
	}

	public SubtopicName(Integer id, String name, TopicName topic, SubtopicType type) {
		super();
		this.id = id;
		this.name = name;
		this.topic = topic;
		this.type = type;
	}

	public SubtopicName(String name, TopicName topic, SubtopicType type) {
		super();
		this.name = name;
		this.topic = topic;
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

	public TopicName getTopic() {
		return topic;
	}

	public void setTopic(TopicName topic) {
		this.topic = topic;
	}

	public SubtopicType getType() {
		return type;
	}

	public void setType(SubtopicType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SubtopicName [id=" + id + ", name=" + name + ", topic=" + topic + "]";
	}

}
