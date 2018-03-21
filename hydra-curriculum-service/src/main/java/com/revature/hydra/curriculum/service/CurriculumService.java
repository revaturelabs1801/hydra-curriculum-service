package com.revature.hydra.curriculum.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.hydra.curriculum.bean.Curriculum;
import com.revature.hydra.curriculum.pojos.BamUser;
import com.revature.hydra.curriculum.repository.CurriculumRepository;
import com.revature.hydra.curriculum.repository.CurriculumSubtopicRepository;

@Service("curriculumService")
public class CurriculumService {

	@Autowired
	CurriculumRepository curriculumRepository;

	@Autowired
	CurriculumSubtopicRepository curriculumSubtopicRepository;

	public CurriculumService() {
		super();
	}

	public CurriculumService(CurriculumRepository curriculumRepository,
			CurriculumSubtopicRepository curriculumSubtopicRepository) {
		super();
		this.curriculumRepository = curriculumRepository;
		this.curriculumSubtopicRepository = curriculumSubtopicRepository;
	}

	@SuppressWarnings("rawtypes")

public List<Curriculum> getAllCurriculum(){
		List<Curriculum> curriculumList = curriculumRepository.findAll();
		return curriculumList;
	}

	public Curriculum getCuricullumById(Integer id){
		Curriculum curriculum = curriculumRepository.findById(id);
		return curriculum;
	}

	/**
	 * @author Carter Taylor (1712-Steve)
	 * @param id
	 *            curriculumId getCuricullumByIdKeepPwd: this method is necessary
	 *            when updating curriculums server side. Setting the
	 *            curriculumCreator's password to empty throws
	 *            ConstraintViolationException when updating the corresponding
	 *            curriculum.
	 * @return curriculum object
	 */
	public Curriculum getCuricullumByIdKeepPwd(Integer id) {
		Curriculum curriculum = curriculumRepository.findById(id);
		return curriculum;
	}

	public Curriculum save(Curriculum c) {
		return curriculumRepository.save(c);
	}

	public List<Curriculum> findAllCurriculumByName(String name) {
		return curriculumRepository.findByCurriculumName(name);
	}

	public List<Curriculum> findAllCurriculumByNameAndIsMaster(String name, Integer isMaster) {
		List<Curriculum> master = curriculumRepository.findByCurriculumNameAndIsMaster(name, isMaster);
		return master;
	}

	/**
	 * @author Carter Taylor, James Holzer (1712-Steve)
	 * @param Curriculum
	 *            version deleteCurriculum: calls deleteCurriculumSubtopic() to
	 *            delete all related CurriculumSubtopics and then deletes the
	 *            version of a curriculum
	 */
	@Transactional
	public void deleteCurriculum(Curriculum version) {
		deleteCurriculumSubtopics(version);
		curriculumRepository.delete(version);
	}

	/**
	 * @author Carter Taylor, James Holzer (1712-Steve)
	 * @param Curriculum
	 *            version deleteCurriculumSubtopics: Deletes all CurriculumSubtopics
	 *            related to a curriculum version
	 */
	@Transactional
	public void deleteCurriculumSubtopics(Curriculum version) {
		curriculumSubtopicRepository.deleteByCurriculum(version);
	}
}