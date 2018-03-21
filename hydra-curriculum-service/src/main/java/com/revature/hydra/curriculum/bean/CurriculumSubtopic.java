package com.revature.hydra.curriculum.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Curriculum_Subtopic")
public class CurriculumSubtopic {
	
	@Id
	@Column(name = "Curriculum_Subtopic_Id")
	@SequenceGenerator(name = "Curriculum_Subtopic_ID_SEQ", sequenceName = "Curriculum_Subtopic_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Curriculum_Subtopic_ID_SEQ")
	private Integer curriculumSubtopicId;
	
	@Column(name = "curriculum_Subtopic_Name_Id")
	@NotNull(message="Curriculum Subtopic Name cannot be null")
	private int curriculumSubtopicNameId;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "curriculum_Subtopic_Cur_Id", referencedColumnName = "Curriculum_Id")
	private Curriculum curriculum;

	
	@Column(name = "Curriculum_Week")
	private int curriculumSubtopicWeek;
	
	@Column(name = "Curriculum_Day")
	private int curriculumSubtopicDay;
	
	public CurriculumSubtopic() {
		
	}

	public CurriculumSubtopic(Integer curriculumSubtopicId, int curriculumSubtopicNameId, Curriculum curriculum,
			int curriculumSubtopicWeek, int curriculumSubtopicDay) {
		super();
		this.curriculumSubtopicId = curriculumSubtopicId;
		this.curriculumSubtopicNameId = curriculumSubtopicNameId;
		this.curriculum = curriculum;
		this.curriculumSubtopicWeek = curriculumSubtopicWeek;
		this.curriculumSubtopicDay = curriculumSubtopicDay;
	}

	public Integer getCurriculumSubtopicId() {
		return curriculumSubtopicId;
	}

	public void setCurriculumSubtopicId(Integer curriculumSubtopicId) {
		this.curriculumSubtopicId = curriculumSubtopicId;
	}

	public int getCurriculumSubtopicNameId() {
		return curriculumSubtopicNameId;
	}

	public void setCurriculumSubtopicNameId(int curriculumSubtopicNameId) {
		this.curriculumSubtopicNameId = curriculumSubtopicNameId;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public int getCurriculumSubtopicWeek() {
		return curriculumSubtopicWeek;
	}

	public void setCurriculumSubtopicWeek(int curriculumSubtopicWeek) {
		this.curriculumSubtopicWeek = curriculumSubtopicWeek;
	}

	public int getCurriculumSubtopicDay() {
		return curriculumSubtopicDay;
	}

	public void setCurriculumSubtopicDay(int curriculumSubtopicDay) {
		this.curriculumSubtopicDay = curriculumSubtopicDay;
	}

	@Override
	public String toString() {
		return "CurriculumSubtopic [curriculumSubtopicId=" + curriculumSubtopicId + ", curriculumSubtopicNameId="
				+ curriculumSubtopicNameId + ", curriculum=" + curriculum + ", curriculumSubtopicWeek="
				+ curriculumSubtopicWeek + ", curriculumSubtopicDay=" + curriculumSubtopicDay + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curriculum == null) ? 0 : curriculum.hashCode());
		result = prime * result + curriculumSubtopicDay;
		result = prime * result + ((curriculumSubtopicId == null) ? 0 : curriculumSubtopicId.hashCode());
		result = prime * result + curriculumSubtopicNameId;
		result = prime * result + curriculumSubtopicWeek;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurriculumSubtopic other = (CurriculumSubtopic) obj;
		if (curriculum == null) {
			if (other.curriculum != null)
				return false;
		} else if (!curriculum.equals(other.curriculum))
			return false;
		if (curriculumSubtopicDay != other.curriculumSubtopicDay)
			return false;
		if (curriculumSubtopicId == null) {
			if (other.curriculumSubtopicId != null)
				return false;
		} else if (!curriculumSubtopicId.equals(other.curriculumSubtopicId))
			return false;
		if (curriculumSubtopicNameId != other.curriculumSubtopicNameId)
			return false;
		if (curriculumSubtopicWeek != other.curriculumSubtopicWeek)
			return false;
		return true;
	}
	
}
