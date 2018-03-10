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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Curriculum")
public class Curriculum {

	@Id
	@Column(name = "Curriculum_Id")
	@SequenceGenerator(name = "Curriculum_ID_SEQ", sequenceName = "Curriculum_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Curriculum_ID_SEQ")
	private Integer id;
	
	@Column(name= "Curriculum_name")
	@NotEmpty(message = "Curriculum name cannot be empty")
	private String curriculumName;
	
	@Column(name ="Curriculum_version")
	private int curriculumVersion;
	
	@Column(name = "Curriculum_Creator")
	private Integer curriculumCreator;
	
	@Column(name = "Curriculum_Modifier")
	private Integer curriculumModifier;
	
	@Column(name = "Curriculum_Date_Created")
	@NotEmpty(message = "Curriculum Date Created cannot be empty")
	private String curriculumDateCreated;
	
	@Column(name = "Curriculum_Number_Of_Weeks")
	private int curriculumNumberOfWeeks;
	
	@Column(name = "Curriculum_Is_Master")
	private int isMaster;
	
	public Curriculum() {
		
	}

	public Curriculum(Integer id, String curriculumName, int curriculumVersion, Integer curriculumCreator,
			Integer curriculumModifier, String curriculumDateCreated, int curriculumNumberOfWeeks, int isMaster) {
		super();
		this.id = id;
		this.curriculumName = curriculumName;
		this.curriculumVersion = curriculumVersion;
		this.curriculumCreator = curriculumCreator;
		this.curriculumModifier = curriculumModifier;
		this.curriculumDateCreated = curriculumDateCreated;
		this.curriculumNumberOfWeeks = curriculumNumberOfWeeks;
		this.isMaster = isMaster;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}

	public int getCurriculumVersion() {
		return curriculumVersion;
	}

	public void setCurriculumVersion(int curriculumVersion) {
		this.curriculumVersion = curriculumVersion;
	}

	public Integer getCurriculumCreator() {
		return curriculumCreator;
	}

	public void setCurriculumCreator(Integer curriculumCreator) {
		this.curriculumCreator = curriculumCreator;
	}

	public Integer getCurriculumModifier() {
		return curriculumModifier;
	}

	public void setCurriculumModifier(Integer curriculumModifier) {
		this.curriculumModifier = curriculumModifier;
	}

	public String getCurriculumDateCreated() {
		return curriculumDateCreated;
	}

	public void setCurriculumDateCreated(String curriculumDateCreated) {
		this.curriculumDateCreated = curriculumDateCreated;
	}

	public int getCurriculumNumberOfWeeks() {
		return curriculumNumberOfWeeks;
	}

	public void setCurriculumNumberOfWeeks(int curriculumNumberOfWeeks) {
		this.curriculumNumberOfWeeks = curriculumNumberOfWeeks;
	}

	public int getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(int isMaster) {
		this.isMaster = isMaster;
	}

	@Override
	public String toString() {
		return "Curriculum [id=" + id + ", curriculumName=" + curriculumName + ", curriculumVersion="
				+ curriculumVersion + ", curriculumCreator=" + curriculumCreator + ", curriculumModifier="
				+ curriculumModifier + ", curriculumDateCreated=" + curriculumDateCreated + ", curriculumNumberOfWeeks="
				+ curriculumNumberOfWeeks + ", isMaster=" + isMaster + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curriculumCreator == null) ? 0 : curriculumCreator.hashCode());
		result = prime * result + ((curriculumDateCreated == null) ? 0 : curriculumDateCreated.hashCode());
		result = prime * result + ((curriculumModifier == null) ? 0 : curriculumModifier.hashCode());
		result = prime * result + ((curriculumName == null) ? 0 : curriculumName.hashCode());
		result = prime * result + curriculumNumberOfWeeks;
		result = prime * result + curriculumVersion;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + isMaster;
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
		Curriculum other = (Curriculum) obj;
		if (curriculumCreator == null) {
			if (other.curriculumCreator != null)
				return false;
		} else if (!curriculumCreator.equals(other.curriculumCreator))
			return false;
		if (curriculumDateCreated == null) {
			if (other.curriculumDateCreated != null)
				return false;
		} else if (!curriculumDateCreated.equals(other.curriculumDateCreated))
			return false;
		if (curriculumModifier == null) {
			if (other.curriculumModifier != null)
				return false;
		} else if (!curriculumModifier.equals(other.curriculumModifier))
			return false;
		if (curriculumName == null) {
			if (other.curriculumName != null)
				return false;
		} else if (!curriculumName.equals(other.curriculumName))
			return false;
		if (curriculumNumberOfWeeks != other.curriculumNumberOfWeeks)
			return false;
		if (curriculumVersion != other.curriculumVersion)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isMaster != other.isMaster)
			return false;
		return true;
	}
	
}
