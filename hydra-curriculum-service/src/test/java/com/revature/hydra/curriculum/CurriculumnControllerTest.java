package com.revature.hydra.curriculum;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.hydra.curriculum.bean.Curriculum;
import com.revature.hydra.curriculum.bean.CurriculumSubtopic;
import com.revature.hydra.curriculum.controller.CurriculumController;
import com.revature.hydra.curriculum.pojos.BamUser;
import com.revature.hydra.curriculum.pojos.Subtopic;
import com.revature.hydra.curriculum.pojos.SubtopicName;
import com.revature.hydra.curriculum.pojos.SubtopicType;
import com.revature.hydra.curriculum.pojos.TopicName;
import com.revature.hydra.curriculum.service.CurriculumService;
import com.revature.hydra.curriculum.service.CurriculumSubtopicService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CurriculumnControllerTest {

	@LocalServerPort
	private int port;

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private CurriculumService curriculumService;

	@MockBean
	private CurriculumSubtopicService curriculumSubtopicService;

	@Autowired
	@InjectMocks
	private CurriculumController curriculumController;

	@Autowired
	private MockMvc mockMvc;

	// @LoadBalanced
	// @Bean
	// public RestTemplate buildRestTemplate(RestTemplateBuilder
	// restTemplateBuilder) {
	// return restTemplateBuilder.build();
	// }

	List<Curriculum> dummyCurriculums;
	Curriculum dummyCurriculum;
	Curriculum dummyCurriculum_isMaster;
	List<CurriculumSubtopic> dummyCurriculumSubtopics;
	CurriculumSubtopic curriculumSubtopic1;
	CurriculumSubtopic curriculumSubtopic2;
	CurriculumSubtopic curriculumSubtopic3;
	
	List<SubtopicName> dummySubtopicNames;
	SubtopicName dummySubtopicName1;
	SubtopicName dummySubtopicName2;
	
	List<BamUser> dummyBamUsers;
	BamUser dummyBamUser1;
	BamUser dummyBamUser2;
	
	List<Subtopic> dummySubtopics;
	Subtopic dummySubtopic1;
	Subtopic dummySubtopic2;
	
	Map<String, List> dummyCurriculumInfo;
	

	@Before
	public void setUp() throws Exception {
		dummyCurriculums = new ArrayList<>();
		dummyCurriculum = new Curriculum(12, "Angular", 2, 4, 8, "March 16, 2012", 2, 0);
		dummyCurriculum_isMaster = new Curriculum(56, "Angular", 5, 10, 9, "December 27, 2009", 4, 1);
		dummyCurriculums.add(dummyCurriculum);
		dummyCurriculums.add(dummyCurriculum_isMaster);
		dummyCurriculumSubtopics = new ArrayList<>();
		curriculumSubtopic1 = new CurriculumSubtopic(7, 7, dummyCurriculum, 7, 1);
		curriculumSubtopic2 = new CurriculumSubtopic(8, 8, dummyCurriculum, 8, 2);
		curriculumSubtopic3 = new CurriculumSubtopic(9, 9, dummyCurriculum, 9, 3);
		dummyCurriculumSubtopics.add(curriculumSubtopic1);
		dummyCurriculumSubtopics.add(curriculumSubtopic2);
		dummyCurriculumSubtopics.add(curriculumSubtopic3);
		
		dummySubtopicNames = new ArrayList<>();
		dummySubtopicName1 = new SubtopicName(1, "Sub1", new TopicName(1, "Topic1"), new SubtopicType(1, "SubType1"));
		dummySubtopicName2 = new SubtopicName(2, "Sub2", new TopicName(2, "Topic2"), new SubtopicType(2, "SubType2"));
		dummySubtopicNames.add(dummySubtopicName1);
		dummySubtopicNames.add(dummySubtopicName2);
		
		dummyBamUsers = new ArrayList<>();
		dummyBamUser1 = new BamUser();
		dummyBamUser1.setUserId(1);
		dummyBamUser2 = new BamUser();
		dummyBamUser2.setUserId(2);
		dummyBamUsers.add(dummyBamUser1);
		dummyBamUsers.add(dummyBamUser2);
		
		dummySubtopics = new ArrayList<>();
		dummySubtopic1 = new Subtopic();
		dummySubtopic1.setSubtopicId(1);
		dummySubtopic2 = new Subtopic();
		dummySubtopic2.setSubtopicId(2);
		dummySubtopics.add(dummySubtopic1);
		dummySubtopics.add(dummySubtopic2);
		
		dummyCurriculumInfo = new Hashtable<>();
		dummyCurriculumInfo.put("curriculumList", dummyCurriculums);
		dummyCurriculumInfo.put("users", dummyBamUsers);
	}

	@After
	public void tearDown() throws Exception {
		dummyCurriculums = null;
		dummyCurriculum = null;
		dummyCurriculum_isMaster = null;
		dummyCurriculumSubtopics = null;
		curriculumSubtopic1 = null;
		curriculumSubtopic2 = null;
		curriculumSubtopic3 = null;
		
		dummySubtopicNames = null;
		dummySubtopicName1 = null;
		dummySubtopicName2 = null;
		
		dummyBamUsers = null;
		dummyBamUser1 = null;
		dummyBamUser2 = null;
		
		dummySubtopics = null;
		dummySubtopic1 = null;
		dummySubtopic2 = null;
		
		dummyCurriculumInfo = null;
	}
	
	/**
	 * Test if get all curriculum returns ok
	 */
	@Test
	public void testGetAllCurriculum_returnsOk() throws Exception {
		when(curriculumService.getAllCurriculum()).thenReturn(dummyCurriculums);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dummyCurriculums);
		
		 this.mockMvc.perform(get("/api/v2/curriculum/all/"))
		 .andExpect(status().isOk())
		 .andExpect(content().json(json));
	}
	
	/**
	 * Test if get all curriculum fallback triggers
	 */
	@Test
	public void testGetAllCurriculum_fallback() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(new ArrayList<Curriculum>());
		
		 this.mockMvc.perform(get("/api/v2/curriculum/all/"))
		 .andExpect(status().isOk())
		 .andExpect(content().json(json));
	}

	/**
	 * Test if get curriculum by id gets curriculum.
	 */
	@Test
	public void testGetCurriculumById_getsCurriculum() throws Exception {
		 when(curriculumService.getCuricullumById(dummyCurriculum.getId())).thenReturn(dummyCurriculum);
		 
		 ObjectMapper mapper = new ObjectMapper();
		 String json = mapper.writeValueAsString(dummyCurriculum);
		 
		 this.mockMvc.perform(get("/api/v2/curriculum/getcurriculum/" + dummyCurriculum.getId()))
		 .andExpect(status().isOk())
		 .andExpect(content().json(json));
	}
	
	/**
	 * Test if malformed parameter throws bad request
	 */
	@Test
	public void testGetCurriculumById_ExpectBadRequestException() throws Exception {
		 when(curriculumService.getCuricullumById(dummyCurriculum.getId())).thenReturn(null);
		 
		 this.mockMvc.perform(get("/api/v2/curriculum/getcurriculum/aaa"))
		 .andExpect(status().isBadRequest());
	}
	
	/**
	 * Test if get curriculum by id throws expect no content
	 */
	@Test
	public void testGetCurriculumById_ExpectNoContent() throws Exception {
		 when(curriculumService.getCuricullumById(dummyCurriculum.getId())).thenReturn(null);
		 
		 this.mockMvc.perform(get("/api/v2/curriculum/getcurriculum/" + dummyCurriculum.getId()))
		 .andExpect(status().isNoContent());
	}

	/**
	 * Test if get all curriculum schedules works with correct data
	 */
	@Test
	public void testGetAllCurriculumSchedules_returnCurriculums() throws Exception {
		 when(curriculumService.getCuricullumById(dummyCurriculum.getId())).thenReturn(dummyCurriculum);
		 when(curriculumSubtopicService.getCurriculumSubtopicForCurriculum(dummyCurriculum)).thenReturn(dummyCurriculumSubtopics);
		 
		 ObjectMapper mapper = new ObjectMapper();
		 String json = mapper.writeValueAsString(dummyCurriculumSubtopics);
		 this.mockMvc.perform(get("/api/v2/curriculum/schedule/" +
		 dummyCurriculum.getId()))
		 .andExpect(status().isOk())
		 .andExpect(content().json(json));
	}
	
	/**
	 * Test if bad request status is sent
	 */
	@Test
	public void testGetAllCurriculumSchedules_ExpectBadRequest() throws Exception {
		 when(curriculumService.getCuricullumById(dummyCurriculum.getId())).thenReturn(null);
		 
		 ObjectMapper mapper = new ObjectMapper();
		 String json = mapper.writeValueAsString(dummyCurriculumSubtopics);
		 // perform get with malform url
		 this.mockMvc.perform(get("/api/v2/curriculum/schedule/aaa"))
		 .andExpect(status().isBadRequest());
	}
	
	/**
	 * Test if no content status is sent
	 */
	@Test
	public void testGetAllCurriculumSchedules_NoContentException() throws Exception {
		when(curriculumService.getCuricullumById(dummyCurriculum.getId())).thenReturn(dummyCurriculum);
		when(curriculumSubtopicService.getCurriculumSubtopicForCurriculum(dummyCurriculum)).thenReturn(new ArrayList<CurriculumSubtopic>());
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dummyCurriculumSubtopics);
		this.mockMvc.perform(get("/api/v2/curriculum/schedule/" + dummyCurriculum.getId()))
				.andExpect(status().isNoContent());
	}

	/**
	 * Test if get topic pool returns ok
	 * @throws Exception
	 */
	@Test
	public void testGetTopicPool_returnsOk() throws Exception {
		ParameterizedTypeReference<List<SubtopicName>> ptr = new ParameterizedTypeReference<List<SubtopicName>>() {
		};
		
		when(this.restTemplate.exchange("http://hydra-topic-service/api/v2/subtopicService/getAllSubtopicNames", HttpMethod.GET, null, ptr))
		.thenReturn((new ResponseEntity<List<SubtopicName>>(dummySubtopicNames, HttpStatus.OK)));
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dummySubtopicNames);
		
		this.mockMvc.perform(get("/api/v2/curriculum/topicpool/"))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}
	
	/**
	 * Test if get topic pool fallback triggers
	 */
	@Test
	public void testGetTopicPool_fallback() throws Exception {
		List<SubtopicName> fallbackList = new ArrayList<SubtopicName>();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(fallbackList);
		
		this.mockMvc.perform(get("/api/v2/curriculum/topicpool/"))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}


	/**
	 * Test if get subtopic pool returns ok
	 */
	@Test
	public void testGetSubtopicPool_returnsOk() throws Exception {
		ParameterizedTypeReference<List<Subtopic>> ptr = new ParameterizedTypeReference<List<Subtopic>>() {
		};
		
		when(this.restTemplate.exchange("http://hydra-topic-service/api/v2/subtopicService/getAllSubtopics", HttpMethod.GET, null, ptr))
		.thenReturn(new ResponseEntity<List<Subtopic>>(dummySubtopics, HttpStatus.OK));
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dummySubtopics);
		
		this.mockMvc.perform(get("/api/v2/curriculum/subtopicpool/"))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}
	
	/**
	 * Test if get subtopic pool fallback triggers
	 */
	@Test
	public void testGetSubtopicPool_fallback() throws Exception {		
		List<Subtopic> fallbackList = new ArrayList<Subtopic>();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(fallbackList);
		
		this.mockMvc.perform(get("/api/v2/curriculum/subtopicpool/"))
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}

	@Test
	public void testAddSchedule() {
		
	}

	/**
	 * Test if mark curriculum as a master returns ok
	 */
	@Test
	public void testMarkCurriculumAsMaster_marksCurriculum() throws Exception {
		when(curriculumService.getCuricullumByIdKeepPwd(dummyCurriculum.getId())).thenReturn(dummyCurriculum);
		when(curriculumService.findAllCurriculumByName(dummyCurriculum.getCurriculumName())).thenReturn(dummyCurriculums);

		this.mockMvc.perform(get("/api/v2/curriculum/makemaster/" + dummyCurriculum.getId()))
		.andExpect(status().isOk());
		
		assertEquals(1, dummyCurriculum.getIsMaster());
		assertEquals(0, dummyCurriculum_isMaster.getIsMaster());
		
		verify(curriculumService, times(1)).save(dummyCurriculum_isMaster);
		verify(curriculumService, times(1)).save(dummyCurriculum);
	}
	
	/**
	 * Test if mark curriculum as master throws Bad request
	 */
	@Test
	public void testMarkCurriculumAsMaster_throwsBadRequest() throws Exception{		
		this.mockMvc.perform(get("/api/v2/curriculum/makemaster/aaa"))
		.andExpect(status().isBadRequest());
	}

	
	@Test
	public void testSyncBatch() {
		
	}
	
	/**
	 * Test if sync batch fallback triggers 
	 */
	@Test
	public void testSyncBatch_fallback() throws Exception {
		this.mockMvc.perform(get("/api/v2/curriculum/syncbatch/1"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test if delete curriculum version deletes version 
	 */
	@Test
	public void testDeleteCurriculumVersion() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dummyCurriculum);
		 this.mockMvc.perform(
				 post("/api/v2/curriculum/deleteversion/")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(json))
		 .andExpect(status().isOk());
		 verify(curriculumService, times(1)).deleteCurriculum(dummyCurriculum);
	}

}
