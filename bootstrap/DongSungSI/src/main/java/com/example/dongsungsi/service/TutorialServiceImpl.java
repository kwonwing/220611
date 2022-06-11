package com.example.dongsungsi.service;

import com.example.dongsungsi.dao.TutorialDao;
import com.example.dongsungsi.model.Tutorial;
import com.example.dongsungsi.paging.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.dongsungsi.service
 * fileName : TutorialServiceImpl
 * author : naraekwon
 * date : 2022/05/25
 * description : dao, model 이용해서 비지니스로직 구현 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/25         naraekwon          최초 생성
 */
@Service
public class TutorialServiceImpl implements TutorialService {

//    @Autowired : 스프링에 생성된 객체를 받아옴
    @Autowired
    private TutorialDao tutorialDao;

//    로그 찍는 객체
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
//    아이디로 검색하는 서비스
    public Optional<Tutorial> findById(long id) {
        return tutorialDao.findById(id);
    }

    @Override
    public List<Tutorial> findByPublished(String published) {
        return tutorialDao.findByPublished(published);
    }
// TODO: 2개 수정 findByTitleContaining,findAll
    @Override
    public List<Tutorial> findByTitleContaining(Criteria criteria) {
//        빈값으로 초기화
        List<Tutorial> tutorials = Collections.emptyList();

//      title이  null인지 체크(null -> '')
        Optional<String> optionalCriteria = Optional.ofNullable(criteria.getTitle());
//        테이블의 총 데이터 건수
        int totalCount = tutorialDao.selectTotalCount(optionalCriteria.orElse(""));

//        title 이 없으면 전체검색 -> criteria: 페이징 처리 클래스 객체
        criteria.setTotalItems(totalCount);
//       총 페이지 개수 =  테이블의 총 건수/페이지당 출력할 데이터 개수(totalCount/size)
        criteria.setTotalPages(totalCount/ criteria.getSize());
//        전체검색 : 전체검색에 타이틀없으므로 null 에러 안남 (title이 null이면 findAll호출 )
        if(criteria.getTitle() == null){
            tutorials = tutorialDao.findAll(criteria);
//        title 이 있으면 제목검색(title이 null이 아님)
        }else{
            tutorials = tutorialDao.findByTitleContaining(criteria);
        }

        return tutorials;
    }

    @Override
    public List<Tutorial> findAll(Criteria criteria) {
        return tutorialDao.findAll(criteria);
    }

    @Override
    public boolean save(Tutorial tutorial) {
        int queryResult =0;

//        매개변수 tutorial 안의 값을 로그로 출력
        logger.info("tutorial{}", tutorial);

        if(tutorial.getId()==null){
//            id 값이 없으면 insert 문 실행
            queryResult = tutorialDao.insertTutorial(tutorial);
        }else{
//            id 값이 있으면 update 문 실행
            queryResult = tutorialDao.updateTutorial(tutorial);
        }
        return (queryResult>=1)?true:false;
    }

    @Override
    public boolean deleteById(Long id) {
        int queryResult =0;

        queryResult = tutorialDao.deleteTutorial(id);

        return (queryResult>=1)?true:false;
    }

    @Override
    public boolean deleteAll() {
        int queryResult =0;

        queryResult = tutorialDao.deleteAll();

        return (queryResult>=1)?true:false;
    }
}
