package com.revature.hydra.curriculum.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Curriculum_Creator", referencedColumnName = "User_Id")
	private BamUser curriculumCreator;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Curriculum_Modifier", referencedColumnName = "User_Id")
	private BamUser curriculumModifier;*/
	
	@Column(name = "Curriculum_Date_Created")
	@NotEmpty(message = "Curriculum Date Created cannot be empty")
	private String curriculumDateCreated;
	
	@Column(name = "Curriculum_Number_Of_Weeks")
	private int curriculumNumberOfWeeks;
	
	@Column(name = "Curriculum_Is_Master")
	private int isMaster;
	
	public Curriculum() {
		
	}
	
}
