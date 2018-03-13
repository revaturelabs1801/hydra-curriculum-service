package com.revature.hydra.curriculum.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v2/curriculum/")
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

	public CurriculumService get() {
		return curriculumService;
	}

	/***
	 * @author Nam Mai Method is needed for injecting mocked services for unit test
	 */
	@Autowired
	public CurriculumController(CurriculumService cs, CurriculumSubtopicService css) {
		curriculumService = cs;
		curriculumSubtopicService = css;
	}

	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 *         getAllCurriculum: method to get all curriculums
	 * @return List<Curriculum>, HttpStatus.OK if successful, HttpStatus.NO_CONTENT
	 *         if list is empty
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "all")
	public ResponseEntity<List<Curriculum>> getAllCurriculum() {
		ParameterizedTypeReference<List<BamUser>> ptr = new ParameterizedTypeReference<List<BamUser>>() {
		};
		ResponseEntity<List<BamUser>> userResponseEntity = this.restTemplate.exchange(
				"http://ec2-18-219-133-200.us-east-2.compute.amazonaws.com:9000/api/v2/Users/all", HttpMethod.GET, null,
				ptr);
		Map<String, List> lists = curriculumService.getAllCurriculum(userResponseEntity.getBody());
		// /*for (BamUser user : (List<BamUser>) lists.get("users"))
		// this.restTemplate.postForEntity("http://hydra-user-service/api/v2/Users/Update",
		// HttpMethod.POST,
		// BamUser.class, user);*/
		// return (List<Curriculum>) lists.get("curriculumList");
		if (lists.get("curriculumList") != null && !lists.get("curriculumList").isEmpty()) {
			return new ResponseEntity<List<Curriculum>>(lists.get("curriculumList"), HttpStatus.OK);
		}
		return new ResponseEntity<List<Curriculum>>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 *         getCurriculumById: method to get a Curriculum by its Id
	 * @return Curriculum, HttpStatus.OK if successful HttpStatus.NO_CONTENT if id
	 *         doesn't match, HttpStatus.BAD_REQUEST if missing parameters
	 */
	@SuppressWarnings("unused")
	@GetMapping(value = "getcurriculum/{cId}")
	public ResponseEntity<Curriculum> getCurriculumById(@PathVariable int cId) {
		Curriculum result = new Curriculum();
		try {
			result = curriculumService.getCuricullumById(cId);
			BamUser creator = restTemplate.getForObject("http://hydra-user-service/api/v2/users/byid/" + result.getCurriculumCreator(), BamUser.class);
			BamUser modifier = restTemplate.getForObject("http://hydra-user-service/api/v2/users/byid/" + result.getCurriculumModifier(), BamUser.class);
			creator.setPwd("");
			this.restTemplate.postForEntity("http://hydra-user-service/api/v2/users/update", HttpMethod.POST, BamUser.class, creator);
			if(modifier != null) {
				modifier.setPwd("");
				this.restTemplate.postForEntity("http://hydra-user-service/api/v2/users/update", HttpMethod.POST, BamUser.class, modifier);
			}
		} catch (NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (result != null) {
			return new ResponseEntity<Curriculum>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 * @param PathVariable:
	 *            int cId holds curriculumId getAllCurriculumSchedules: method to
	 *            retrieve list of curriculum subtopics given a curriculumId
	 * @return List<CurriculumSubtopics>, HttpStatus.OK if successful
	 *         HttpStatus.NO_CONTENT if id doesn't match, HttpStatus.BAD_REQUEST if
	 *         missing parameters
	 */
	@GetMapping(value = "schedule/{cId}")
	public ResponseEntity<List<CurriculumSubtopic>> getAllCurriculumSchedules(@PathVariable int cId) {
		Curriculum c = new Curriculum();

		try {
			c = curriculumService.getCuricullumById(cId);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		c.setId(cId);

		List<CurriculumSubtopic> result = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<List<CurriculumSubtopic>>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve) getTopicPool:
	 *         method to get list of topics
	 * @return List<SubtopicName>, HttpStatus.OK if successful,
	 *         HttpStatus.NO_CONTENT if list is empty
	 */
	@GetMapping("topicpool")
	public ResponseEntity<List<SubtopicName>> getTopicPool() {
		ParameterizedTypeReference<List<SubtopicName>> ptr = new ParameterizedTypeReference<List<SubtopicName>>() {
		};
		ResponseEntity<List<SubtopicName>> result = this.restTemplate.exchange(
				"http://hydra-topic-service/api/v2/subtopicname/all", HttpMethod.GET, null,
				ptr);
		if (result != null) {
			return result;
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 *         getSubtopicPool: method to get list of subtopics with associated
	 *         batch and status
	 * @return List<Subtopic>, HttpStatus.OK if successful, HttpStatus.NO_CONTENT if
	 *         list is empty
	 */
	@GetMapping("subtopicpool")
	public ResponseEntity<List<Subtopic>> getSubtopicPool() {
		ParameterizedTypeReference<List<Subtopic>> ptr = new ParameterizedTypeReference<List<Subtopic>>() {
		};
		ResponseEntity<List<Subtopic>> result = this.restTemplate.exchange(
				"http://hydra-topic-service/api/v2/Subtopic/all", HttpMethod.GET, null,
				ptr);
		if (result != null) {
			return result;
		}
		return new ResponseEntity<List<Subtopic>>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @author Carter Taylor (1712-Steve)
	 * @param json
	 *            String that contains curriculum subtopic object addSchedule:
	 *            method that takes a curriculum subtopic (schedule) as input from
	 *            request body and saves both curriculum and curriculum subtopic.
	 *            Handles case of incoming curriculum being marked as master
	 *            version.
	 * @return Curriculum, HttpStatus.CREATED if successful
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@PostMapping(value = "addcurriculum")
	public ResponseEntity<Curriculum> addSchedule(@RequestBody String json) throws JsonMappingException, IOException {
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

		if (curriculum.getIsMaster() == 1) {
			List<Curriculum> curriculumList = curriculumService.findAllCurriculumByName(curriculum.getCurriculumName());
			Curriculum prevMaster = null;

			for (int i = 0; i < curriculumList.size(); i++) {
				if (curriculumList.get(i).getIsMaster() == 1)
					prevMaster = curriculumList.get(i);
			}
			if (prevMaster != null) {
				prevMaster.setIsMaster(0);
				curriculumService.save(prevMaster);
			}
		}

		Curriculum addedCurr = curriculumService.save(curriculum);

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
		return new ResponseEntity<Curriculum>(addedCurr, HttpStatus.CREATED);
	}

	/**
	 * @author Jordan DeLong Carter Taylor (1712-Steve)
	 * @param PathVariable:
	 *            int cId that holds curriculumId markCurricullumAsMaster: method
	 *            that marks selected curriculum as master version (identified by id
	 *            sent as request parameter), and sets previous master version to
	 *            non-master status.
	 * @return HttpStatus.BAD_REQUEST if missing parameter, HttpStatus.ACCEPTED if
	 *         successful
	 */
	@GetMapping(value = "makemaster/{cId}")
	public ResponseEntity<?> markCurriculumAsMaster(@PathVariable int cId) {
		Curriculum c = new Curriculum();

		try {
			c = curriculumService.getCuricullumByIdKeepPwd(cId);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

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
		c.setIsMaster(1);
		curriculumService.save(c);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	/**
	 * @author Carter Taylor (1712-Steve)
	 * @param PathVariable
	 *            int id batch id given as path variable syncBatch: sync batch by
	 *            getting list of curriculum subtopics related to that batch type
	 * @return HttpStatus.RESET_CONTENT if successful, HttpStatus.NO_CONTENT if
	 *         already synced
	 * @throws CustomException
	 */
	@GetMapping("syncbatch/{id}")
	public ResponseEntity<?> syncBatch(@PathVariable int id) throws Exception {
		Batch currBatch = restTemplate.getForObject("http://hydra-batch-service/api/v2/Batches/" + id, Batch.class);
		String batchType = currBatch.getType().getName();
		List<Curriculum> curriculumList = curriculumService.findAllCurriculumByNameAndIsMaster(batchType, 1);

		// get master version
		Curriculum c = null;
		for (int i = 0; i < curriculumList.size(); i++) {
			// master version found
			if (curriculumList.get(i).getIsMaster() == 1) {
				c = curriculumList.get(i);
			}
		}

		// if master not found, get latest version
		if (c == null) {
			curriculumList = curriculumService.findAllCurriculumByName(batchType);
			if (curriculumList != null) {
				int min = curriculumList.get(0).getCurriculumVersion();
				Curriculum tempCurric = curriculumList.get(0);
				for (int i = 1; i < curriculumList.size(); i++) {
					if (curriculumList.get(i).getCurriculumVersion() > min) {
						min = curriculumList.get(i).getCurriculumVersion();
						tempCurric = curriculumList.get(i);
					}
				}
				c = tempCurric;
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}

		List<CurriculumSubtopic> subtopicListMonday = curriculumSubtopicService.getCurriculumSubtopicsForDay(c, 1);
		List<CurriculumSubtopic> subtopicListTuesday = curriculumSubtopicService.getCurriculumSubtopicsForDay(c, 2);
		List<CurriculumSubtopic> subtopicListWednesday = curriculumSubtopicService.getCurriculumSubtopicsForDay(c, 3);
		List<CurriculumSubtopic> subtopicListThursday = curriculumSubtopicService.getCurriculumSubtopicsForDay(c, 4);
		List<CurriculumSubtopic> subtopicListFriday = curriculumSubtopicService.getCurriculumSubtopicsForDay(c, 5);
		
		Map<Integer, List<CurriculumSubtopic>> map = new ConcurrentHashMap<Integer, List<CurriculumSubtopic>>();
		
		map.put(1, subtopicListMonday);
		map.put(2, subtopicListTuesday);
		map.put(3, subtopicListWednesday);
		map.put(4, subtopicListThursday);
		map.put(5, subtopicListFriday);

		// logic goes here to add to calendar
		/*ParameterizedTypeReference<List<Subtopic>> ptr = new ParameterizedTypeReference<List<Subtopic>>() {
		};
		// Request URL currently not implemented
		ResponseEntity<List<Subtopic>> persistedSubtopics = this.restTemplate
				.exchange("http://hydra-topic-service/api/v2/Subtopic/AllByBatchId/" + id, HttpMethod.GET, null, ptr);
		if (subtopicsResponseEntity.getBody().size() == 0) {
			// Request URL currently not implemented
			this.restTemplate.postForEntity(
					"http://hydra-batch-service/api/v2/Batches/addCurriculumSubTopicsToBatch/" + id, HttpMethod.POST,
					List.class, subtopicList);
		} else {
			throw new Exception("Batch already synced");
		}*/
		
		Map<String, Object> m = new HashMap<>();
		m.put("map", map);
		m.put("currBatch", currBatch);
		List<Subtopic> persistedSubtopics = (List<Subtopic>) this.restTemplate.postForEntity("http://hydra-topic-service/api/v2/Subtopics/map", HttpMethod.POST, Map.class, m);
		//List<Subtopic> persistedSubtopics = curriculumSubtopicService.mapCurriculumSubtopicsToSubtopics(map, currBatch);
		
		if(persistedSubtopics.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else{
			return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
		}
	}
	
	/**
	 * @author Carter Taylor, James Holzer (1712-Steve)
	 * @param RequestBody Curriculum version
	 * deleteCurriculumVersion: Deletes a curriculum version along with it's related CurriculumSubtopics
	 * @return HttpStatus.OK if successful
	 */
	@PostMapping("deleteversion")
	public ResponseEntity<?> deleteCurriculumVersion(@RequestBody Curriculum version)
	{
		curriculumService.deleteCurriculum(version);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
