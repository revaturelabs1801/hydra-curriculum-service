package com.revature.hydra.curriculum.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.revature.hydra.curriculum.bean.CurriculumSubtopic;
import com.revature.hydra.curriculum.pojos.BamUser;
import com.revature.hydra.curriculum.pojos.Batch;
import com.revature.hydra.curriculum.pojos.CurriculumSubtopicDTO;
import com.revature.hydra.curriculum.pojos.DaysDTO;
import com.revature.hydra.curriculum.pojos.Subtopic;
import com.revature.hydra.curriculum.pojos.SubtopicName;
import com.revature.hydra.curriculum.pojos.WeeksDTO;
import com.revature.hydra.curriculum.service.CurriculumService;
import com.revature.hydra.curriculum.service.CurriculumSubtopicService;

@RestController
public class CurriculumController {

	@LoadBalanced
	@Bean
	public RestTemplate buildRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Autowired
	private RestTemplate restTemplate;

	// @HystrixCommand(fallbackMethod = "cachedTopic")
	// @GetMapping("/getTopic")
	// public Topic getTopic() {
	// System.out.println("hit /getTopic");
	// Topic t =
	// restTemplate.getForObject("http://hydra-topic-service/api/v2/Topic",
	// Topic.class);
	// return t;
	// }

	// public Topic cachedTopic() {
	// return new Topic();
	// }

	@Autowired
	CurriculumService curriculumService;

	@Autowired
	CurriculumSubtopicService curriculumSubtopicService;

	/***
	 * @author Nam Mai Method is needed for injecting mocked services for unit test
	 */
	@Autowired
	public CurriculumController(CurriculumService cs, CurriculumSubtopicService css) {
		curriculumService = cs;
		curriculumSubtopicService = css;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/All")
	public List<Curriculum> getAllCurriculum() {
		ParameterizedTypeReference<List<BamUser>> ptr = new ParameterizedTypeReference<List<BamUser>>() {
		};
		ResponseEntity<List<BamUser>> userResponseEntity = this.restTemplate
				.exchange("http://hydra-user-service/api/v2/Users/", HttpMethod.GET, null, ptr);
		Map<String, List> lists = curriculumService.getAllCurriculum(userResponseEntity.getBody());
		for (BamUser user : (List<BamUser>) lists.get("users"))
			this.restTemplate.postForEntity("http://hydra-user-service/api/v2/Users/Update", HttpMethod.POST,
					BamUser.class, user);
		return (List<Curriculum>) lists.get("curriculumList");
	}

	@GetMapping(value = "/GetCurriculum")
	public Curriculum getCurriculumById(HttpServletRequest request) {
		int curriculumId = Integer.parseInt(request.getParameter("curriculumId"));
		return curriculumService.getCuricullumById(curriculumId);
	}

	@GetMapping(value = "/Schedule")
	public List<CurriculumSubtopic> getAllCurriculumSchedules(HttpServletRequest request) {
		Curriculum c = new Curriculum();
		c.setId(Integer.parseInt(request.getParameter("curriculumId")));
		return curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
	}

	/*
	 * @GetMapping(value = "TopicPool") public List<SubtopicName> getTopicPool() {
	 * return subtopicService.getAllSubtopics(); }
	 * 
	 * @GetMapping(value = "SubtopicPool") public List<Subtopic> getSubtopicPool() {
	 * return subtopicService.getSubtopics(); }
	 */

	@PostMapping(value = "/AddCurriculum")
	public void addSchedule(@RequestBody String json) throws JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		CurriculumSubtopicDTO c = mapper.readValue(json, CurriculumSubtopicDTO.class);

		// save curriculum object first

		Curriculum curriculum = new Curriculum();
		curriculum.setCurriculumCreator(c.getMeta().getCurriculum().getCurriculumCreator());
		curriculum.setCurriculumDateCreated(c.getMeta().getCurriculum().getCurriculumDateCreated());
		curriculum.setCurriculumName(c.getMeta().getCurriculum().getCurriculumName());
		curriculum.setCurriculumNumberOfWeeks(c.getMeta().getCurriculum().getCurriculumNumberOfWeeks());
		curriculum.setCurriculumVersion(c.getMeta().getCurriculum().getCurriculumVersion());
		curriculum.setIsMaster(c.getMeta().getCurriculum().getIsMaster());

		curriculumService.save(curriculum);

		int numWeeks = c.getWeeks().length;
		for (int i = 0; i < numWeeks; i++) {
			DaysDTO[] days = c.getWeeks()[i].getDays();
			for (int j = 0; j < days.length; j++) {
				SubtopicName[] subtopic = days[j].getSubtopics();
				for (int k = 0; k < subtopic.length; k++) {
					CurriculumSubtopic cs = new CurriculumSubtopic();
					cs.setCurriculum(curriculum);
					cs.setCurriculumSubtopicNameId(subtopic[k].getId());
					cs.setCurriculumSubtopicWeek(i + 1);
					cs.setCurriculumSubtopicDay(j + 1);
					curriculumSubtopicService.saveCurriculumSubtopic(cs);
				}
			}
		}
	}

	@GetMapping(value = "/MakeMaster")
	public void markCurriculumAsMaster(HttpServletRequest request) {
		Curriculum c = curriculumService.getCuricullumById(Integer.parseInt(request.getParameter("curriculumId")));
		c.setIsMaster(1);

		// find the curriculum with same name and isMaster = 1; set to 0; save
		List<Curriculum> curriculumList = curriculumService.findAllCurriculumByName(c.getCurriculumName());

		try {
			Curriculum prevMaster = null;
			for (int i = 0; i < curriculumList.size(); i++) {
				if (curriculumList.get(i).getIsMaster() == 1)
					prevMaster = curriculumList.get(i);
			}
			if (prevMaster != null) {
				prevMaster.setIsMaster(0);
				curriculumService.save(prevMaster);
			} else {
				LogManager.getRootLogger().error(prevMaster);
			}
		} catch (NullPointerException e) {
			LogManager.getRootLogger().error(e);
		}

		// save new master curriculum
		curriculumService.save(c);
	}

	// syncs a curriculum with batch from Assignforce
	@GetMapping(value = "/SyncBatch/{id}")
	public void syncBatch(@PathVariable int id) throws Exception {
		Batch currBatch = restTemplate.getForObject("http://hydra-batch-service/api/v2/Batches/" + id, Batch.class);
		String batchType = currBatch.getType().getName();

		// get curriculums with same batchTypes
		List<Curriculum> curriculumList = curriculumService.findAllCurriculumByName(batchType);

		// find the master curriculum; otherwise find one with most up to date version
		Curriculum c = null;
		for (int i = 0; i < curriculumList.size(); i++) {
			// master version found
			if (curriculumList.get(i).getIsMaster() == 1)
				c = curriculumList.get(i);
		}

		// if master not found, get latest version
		if (c == null) {
			int min = curriculumList.get(0).getCurriculumVersion();
			Curriculum tempCurric = curriculumList.get(0);
			for (int i = 1; i < curriculumList.size(); i++) {
				if (curriculumList.get(i).getCurriculumVersion() > min) {
					min = curriculumList.get(i).getCurriculumVersion();
					tempCurric = curriculumList.get(i);
				}
			}
			c = tempCurric;
		}

		// get all curriculumSubtopics associated with curriculum
		List<CurriculumSubtopic> subtopicList = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);

		// logic goes here to add to calendar
		ParameterizedTypeReference<List<Subtopic>> ptr = new ParameterizedTypeReference<List<Subtopic>>() {
		};
		// Request URL currently not implemented
		ResponseEntity<List<Subtopic>> subtopicsResponseEntity = this.restTemplate
				.exchange("http://hydra-topic-service/api/v2/Subtopic/AllByBatchId/" + id, HttpMethod.GET, null, ptr);
		if (subtopicsResponseEntity.getBody().size() == 0) {
			// Request URL currently not implemented
			this.restTemplate.postForEntity(
					"http://hydra-batch-service/api/v2/Batches/addCurriculumSubTopicsToBatch/" + id, HttpMethod.POST,
					List.class, subtopicList);
		} else {
			throw new Exception("Batch already synced");
		}
	}

}
