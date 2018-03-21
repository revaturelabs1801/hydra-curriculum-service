package com.revature.hydra.curriculum.pojos;

import java.util.Arrays;

public class DaysDTO {

	private Integer[] subtopics;
	
	public DaysDTO(){
		//Empty because of No Args
		
	}

	public Integer[] getSubtopics() {
		return subtopics;
	}

	public void setSubtopics(Integer[] subtopics) {
		this.subtopics = subtopics;
	}

	@Override
	public String toString() {
		return "DaysDTO [subtopics=" + Arrays.toString(subtopics) + "]";
	}
	
}
