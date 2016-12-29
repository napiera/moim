package ac.moim.study.service;

import ac.moim.common.repository.CityRepository;
import ac.moim.common.repository.SubjectRepository;
import ac.moim.study.dto.StudyDto;
import ac.moim.study.dto.StudyMemberDto;
import ac.moim.study.entity.Study;
import ac.moim.study.repository.StudyMemberRepository;
import ac.moim.study.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SONG_HOHOON on 2016-12-26.
 */
@Service
public class StudyServiceImpl implements StudyService {

	@Autowired
	private StudyRepository studyRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public Study saveStudy(StudyDto.Request request) {
		Study study =  studyRepository.saveAndFlush(toEntity(request));

		return study;
	}

	private Study toEntity(StudyDto.Request request) {

		Study study = new Study();
		study.setTitle(request.getTitle());
		study.setIntro(request.getIntro());
		study.setMemberLimit(request.getMemberLimit());
		study.setCityId(cityRepository.findOne(request.getCityCode()));
		study.setSubjectId(subjectRepository.findOne(Integer.valueOf(request.getSubjectId())));

		return study;
	}
}