package com.revature.hydra.curriculum.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.hydra.curriculum.bean.Curriculum;
import com.revature.hydra.curriculum.pojos.BamUser;
import com.revature.hydra.curriculum.service.CurriculumService;

@RestController
@RequestMapping(value = "/")
public class InternalCurriculumController {

	@Autowired
	CurriculumService curriculumService;

	@HystrixCommand(fallbackMethod = "emptyMethod")
	@PostMapping("deleteCurriculum")
	public void deleteCurriculum(@RequestBody Curriculum curriculum){
		curriculumService.deleteCurriculum(curriculum);
	}
	
	public void emptyMethod() {
		
	}
	
	@HystrixCommand(fallbackMethod = "emptyMethod")
	@PostMapping("deleteCurriculumSubtopics")
	public void deleteCurriculumSubtopics(@RequestBody Curriculum curriculum){
		curriculumService.deleteCurriculumSubtopics(curriculum);
	}
	
	@HystrixCommand(fallbackMethod = "getCurriculumList")
	@PostMapping("findAllCurriculumByName/{name}")
	public List<Curriculum> findAllCurriculumByName(@PathVariable String name){
		return curriculumService.findAllCurriculumByName(name);
	}
	
	public List<Curriculum> getCurriculumList() {
		return new ArrayList<Curriculum>();
	}
	
	@HystrixCommand(fallbackMethod = "getCurriculumList")
	@PostMapping("findAllCurriculumByNameAndIsMaster/{name}")
	public List<Curriculum> findAllCurriculumByNameAndIsMaster(@PathVariable String name, @RequestBody Integer isMaster){
		return curriculumService.findAllCurriculumByNameAndIsMaster(name, isMaster);
	}
	
	@SuppressWarnings("rawtypes")
	@HystrixCommand(fallbackMethod = "getCurriculumMap")
	@PostMapping("getAllCurriculum")
	public Map<String, List> getAllCurriculum(@RequestBody List<BamUser> users){
		return curriculumService.getAllCurriculum(users);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, List> getCurriculumMap() {
		return new HashMap<String, List>();
	}
	
	@HystrixCommand(fallbackMethod = "getCurriculum")
	@PostMapping("getCuricullumById/{id}")
	public Curriculum getCuricullumById(@PathVariable Integer id){
		return curriculumService.getCuricullumById(id);
	}
	
	public Curriculum getCurriculum() {
		return new Curriculum();
	}
	
	@HystrixCommand(fallbackMethod = "getCurriculum")
	@PostMapping("getCuricullumByIdKeepPwd/{id}")
	public Curriculum getCuricullumByIdKeepPwd(@PathVariable Integer id){
		return curriculumService.getCuricullumByIdKeepPwd(id);
	}
	
	@HystrixCommand(fallbackMethod = "getCurriculum")
	@PostMapping("save")
	public Curriculum save(@RequestBody Curriculum curriculum){
		return curriculumService.save(curriculum);
	}

}
