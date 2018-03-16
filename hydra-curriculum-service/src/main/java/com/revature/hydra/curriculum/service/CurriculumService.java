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
	public Map<String, List> getAllCurriculum(List<BamUser> users) {
		List<Curriculum> curriculumList = curriculumRepository.findAll();
		// obfuscate password
		for (Curriculum element : curriculumList) {
			for (BamUser user : users) {
				if (element.getCurriculumCreator() == user.getUserId()) {
					user.setPwd("");
				}
				if (element.getCurriculumModifier() != null && element.getCurriculumModifier() == user.getUserId()) {
					user.setPwd("");
				}
			}
		}
		Map<String, List> curriculumUsers = new HashMap<>();
		curriculumUsers.put("curriculumList", curriculumList);
		curriculumUsers.put("users", users);
		return curriculumUsers;
	}

	public Curriculum getCuricullumById(Integer id) {
		// obfuscate password
		Curriculum curriculum = curriculumRepository.findById(id);
		// curriculum.getCurriculumCreator().setPwd("");
		// if(curriculum.getCurriculumModifier() != null)
		// curriculum.getCurriculumModifier().setPwd("");
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