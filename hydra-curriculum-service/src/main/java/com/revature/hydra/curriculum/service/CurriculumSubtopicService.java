package com.revature.hydra.curriculum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.hydra.curriculum.bean.Curriculum;
import com.revature.hydra.curriculum.bean.CurriculumSubtopic;
import com.revature.hydra.curriculum.repository.CurriculumSubtopicRepository;

@Service
public class CurriculumSubtopicService {

	@Autowired
	CurriculumSubtopicRepository curriculumSubtopic;
	
	public List<CurriculumSubtopic> getCurriculumSubtopicForCurriculum(Curriculum c){
		return curriculumSubtopic.findByCurriculum(c);
	}
	
	public void saveCurriculumSubtopic(CurriculumSubtopic cs){
		curriculumSubtopic.save(cs);
	}

	public List<CurriculumSubtopic> getCurriculumSubtopicsForDay(Curriculum c, int day) {
		List<CurriculumSubtopic> curriculumSubtopics = curriculumSubtopic.findByCurriculumAndCurriculumSubtopicDay(c, day);
		return curriculumSubtopics;
	}
	
}
