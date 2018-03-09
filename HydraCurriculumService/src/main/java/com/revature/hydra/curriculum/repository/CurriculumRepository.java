package com.revature.hydra.curriculum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.revature.hydra.curriculum.bean.Curriculum;

public interface CurriculumRepository extends CrudRepository<Curriculum, Integer> {

	public List<Curriculum> findAll();
	public Curriculum findById(Integer id);
	public List<Curriculum> findByCurriculumName(String curriculumName);

	
}
