package com.revature.hydra.curriculum.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.hydra.curriculum.bean.Curriculum;
import com.revature.hydra.curriculum.pojos.BamUser;
import com.revature.hydra.curriculum.repository.CurriculumRepository;

@Service("curriculumService")
public class CurriculumService {

	@Autowired
	CurriculumRepository curriculumRepository;
	
	@SuppressWarnings("rawtypes")
	public Map<String, List> getAllCurriculum(List<BamUser> users){
		List<Curriculum> curriculumList =  curriculumRepository.findAll();
		//obfuscate password
		for(Curriculum element : curriculumList){
			for(BamUser user: users) {
				if(element.getCurriculumCreator() == user.getUserId()) {
					user.setPwd("");
				}
				if(element.getCurriculumModifier() != null && 
				   element.getCurriculumModifier() == user.getUserId()) {
					user.setPwd("");
				}
			}
		}
		Map<String, List> curriculumUsers = new HashMap<>();
		curriculumUsers.put("curriculumList", curriculumList);
		curriculumUsers.put("users", users);
		return curriculumUsers;
	}
	
	public Curriculum getCuricullumById(Integer id){
		//obfuscate password
		Curriculum curriculum = curriculumRepository.findById(id);
		//curriculum.getCurriculumCreator().setPwd("");
		//if(curriculum.getCurriculumModifier() != null)
		//	curriculum.getCurriculumModifier().setPwd("");
		return curriculum;
	}
	
	public void save(Curriculum c){
		curriculumRepository.save(c);
	}
	
	public List<Curriculum> findAllCurriculumByName(String name){
		return curriculumRepository.findByCurriculumName(name);
	}
}