package com.revature.hydra.curriculum.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.hydra.curriculum.bean.Curriculum;
import com.revature.hydra.curriculum.bean.Topic;

@RestController
@RequestMapping(value = "/api/v2/Curriculum/")
public class CurriculumController {

	@GetMapping("/")
	public Curriculum home() {
		return new Curriculum();
	}

	@LoadBalanced
	@Bean
	public RestTemplate buildRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "cachedTopic")
	@GetMapping("/getTopic")
	public Topic getTopic() {
		System.out.println("hit /getTopic");
		Topic t = restTemplate.getForObject("http://hydra-topic-service/api/v2/Topic/", Topic.class);
		return t;
	}

	public Topic cachedTopic() {
		return new Topic();
	}

	/*
	 * @Autowired CurriculumService curriculumService;
	 * 
	 * @Autowired CurriculumSubtopicService curriculumSubtopicService;
	 * 
	 * @Autowired SubtopicService subtopicService;
	 * 
	 * @Autowired BatchService batchService;
	 * 
	 * public CurriculumService get(){ return curriculumService; }
	 */

	/***
	 * @author Nam Mai Method is needed for injecting mocked services for unit test
	 */
	/*
	 * @Autowired public CurriculumController(CurriculumService cs,
	 * CurriculumSubtopicService css, SubtopicService ss){ curriculumService = cs;
	 * curriculumSubtopicService =css; subtopicService = ss; }
	 * 
	 * @RequestMapping(value = "All", method = RequestMethod.GET, produces =
	 * "application/json")
	 * 
	 * @ResponseBody public List<Curriculum> getAllCurriculum(){ return
	 * curriculumService.getAllCurriculum(); }
	 * 
	 * @RequestMapping(value = "GetCurriculum", method = RequestMethod.GET, produces
	 * = "application/json")
	 * 
	 * @ResponseBody public Curriculum getCurriculumById(HttpServletRequest
	 * request){ int curriculumId =
	 * Integer.parseInt(request.getParameter("curriculumId")); return
	 * curriculumService.getCuricullumById(curriculumId); }
	 * 
	 * @RequestMapping(value = "Schedule", method = RequestMethod.GET, produces =
	 * "application/json")
	 * 
	 * @ResponseBody public List<CurriculumSubtopic>
	 * getAllCurriculumSchedules(HttpServletRequest request){ Curriculum c = new
	 * Curriculum();
	 * c.setCurriculumId(Integer.parseInt(request.getParameter("curriculumId")));
	 * return curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c); }
	 * 
	 * @RequestMapping(value = "TopicPool", method = RequestMethod.GET, produces =
	 * "application/json")
	 * 
	 * @ResponseBody public List<SubtopicName> getTopicPool(){ return
	 * subtopicService.getAllSubtopics(); }
	 * 
	 * @RequestMapping(value = "SubtopicPool", method = RequestMethod.GET, produces
	 * = "application/json")
	 * 
	 * @ResponseBody public List<Subtopic> getSubtopicPool(){ return
	 * subtopicService.getSubtopics(); }
	 * 
	 * @RequestMapping(value = "AddCurriculum", method = RequestMethod.POST) public
	 * void addSchedule(@RequestBody String json) throws JsonMappingException,
	 * IOException{ ObjectMapper mapper = new ObjectMapper(); CurriculumSubtopicDTO
	 * c = mapper.readValue(json, CurriculumSubtopicDTO.class);
	 * 
	 * //save curriculum object first
	 * 
	 * Curriculum curriculum = new Curriculum();
	 * curriculum.setCurriculumCreator(c.getMeta().getCurriculum().
	 * getCurriculumCreator());
	 * curriculum.setCurriculumdateCreated(c.getMeta().getCurriculum().
	 * getCurriculumdateCreated());
	 * curriculum.setCurriculumName(c.getMeta().getCurriculum().getCurriculumName())
	 * ; curriculum.setCurriculumNumberOfWeeks(c.getMeta().getCurriculum().
	 * getCurriculumNumberOfWeeks());
	 * curriculum.setCurriculumVersion(c.getMeta().getCurriculum().
	 * getCurriculumVersion());
	 * curriculum.setIsMaster(c.getMeta().getCurriculum().getIsMaster());
	 * 
	 * curriculumService.save(curriculum);
	 * 
	 * 
	 * int numWeeks = c.getWeeks().length; for(int i = 0; i < numWeeks; i++){
	 * DaysDTO[] days = c.getWeeks()[i].getDays(); for(int j = 0; j < days.length;
	 * j++){ SubtopicName[] subtopic = days[j].getSubtopics(); for(int k = 0; k <
	 * subtopic.length; k++){ CurriculumSubtopic cs = new CurriculumSubtopic();
	 * cs.setCurriculumSubtopicCurriculumID(curriculum);
	 * cs.setCurriculumSubtopicNameId(subtopic[k]); cs.setCurriculumSubtopicWeek(i +
	 * 1); cs.setCurriculumSubtopicDay(j + 1);
	 * curriculumSubtopicService.saveCurriculumSubtopic(cs); } } } }
	 * 
	 * @RequestMapping(value = "MakeMaster", method = RequestMethod.GET) public void
	 * markCurriculumAsMaster(HttpServletRequest request){ Curriculum c =
	 * curriculumService.getCuricullumById(Integer.parseInt(request.getParameter(
	 * "curriculumId"))); c.setIsMaster(1);
	 * 
	 * //find the curriculum with same name and isMaster = 1; set to 0; save
	 * List<Curriculum> curriculumList =
	 * curriculumService.findAllCurriculumByName(c.getCurriculumName());
	 * 
	 * try { Curriculum prevMaster = null;
	 * 
	 * for (int i = 0; i < curriculumList.size(); i++) { if
	 * (curriculumList.get(i).getIsMaster() == 1) prevMaster =
	 * curriculumList.get(i); } if (prevMaster != null) { prevMaster.setIsMaster(0);
	 * curriculumService.save(prevMaster); } else {
	 * LogManager.getRootLogger().error(prevMaster); } } catch (NullPointerException
	 * e) { LogManager.getRootLogger().error(e); }
	 * 
	 * //save new master curriculum curriculumService.save(c); }
	 * 
	 * //syncs a curriculum with batch from Assignforce
	 * 
	 * @RequestMapping(value = "SyncBatch/{id}", method = RequestMethod.GET) public
	 * void syncBatch(@PathVariable int id) throws CustomException{ Batch currBatch
	 * = batchService.getBatchById(id); String batchType =
	 * currBatch.getType().getName();
	 * 
	 * //get curriculums with same batchTypes List<Curriculum> curriculumList =
	 * curriculumService.findAllCurriculumByName(batchType);
	 * 
	 * //find the master curriculum; otherwise find one with most up to date version
	 * Curriculum c = null; for(int i = 0; i < curriculumList.size(); i++){ //master
	 * version found if(curriculumList.get(i).getIsMaster() == 1) c =
	 * curriculumList.get(i); }
	 * 
	 * //if master not found, get latest version if(c == null){ int min =
	 * curriculumList.get(0).getCurriculumVersion(); Curriculum tempCurric =
	 * curriculumList.get(0); for(int i = 1; i < curriculumList.size(); i++){
	 * if(curriculumList.get(i).getCurriculumVersion() > min){ min =
	 * curriculumList.get(i).getCurriculumVersion(); tempCurric =
	 * curriculumList.get(i); } } c = tempCurric; }
	 * 
	 * //get all curriculumSubtopics associated with curriculum
	 * List<CurriculumSubtopic> subtopicList =
	 * curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
	 * 
	 * //logic goes here to add to calendar
	 * if(subtopicService.getNumberOfSubtopics(id) == 0){
	 * batchService.addCurriculumSubtopicsToBatch(subtopicList, currBatch); }else{
	 * throw new CustomException("Batch already synced"); } }
	 */

}
