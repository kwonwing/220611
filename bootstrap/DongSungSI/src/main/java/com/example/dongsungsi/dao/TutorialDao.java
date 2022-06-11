package com.example.dongsungsi.dao;

import com.example.dongsungsi.model.Tutorial;
import com.example.dongsungsi.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.dongsungsi.dao
 * fileName : TutorialDao
 * author : naraekwon
 * date : 2022/05/25
 * description : DB CRUD를 위한 인터페이스(xml mybatis 연동)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/25         naraekwon          최초 생성
 */
// 원래라면 class 에 sql들어오지만 mybatis써서 interface
@Mapper
public interface TutorialDao {
//    화면에서 publish 버튼을 클릭할때 적용되는 메소드
    List<Tutorial> findByPublished(String published);
//      제목 검색을 위한 메소드
//    TODO : 2개 수정(findByTitleContaining, findAll), 1개 추가 (selectTotalCount)
    List<Tutorial> findByTitleContaining(Criteria criteria);
//    모든 목록 조회
    List<Tutorial> findAll(Criteria criteria);
//    제목에 다른 데이터 건수를 세는 메소드
    int selectTotalCount(String title);
//    id에 해당하는 값을 조회
//    Optional: null을 방지하는 클래스
    Optional<Tutorial> findById(Long id);

//    Tutorial 테이블에 데이터 넣기 메소드(insert)
    int insertTutorial(Tutorial tutorial);
//    Tutorial 테이블에 데이터 수정 메소드(update)
    int updateTutorial(Tutorial tutorial);
//    Tutorial 테이블에 데이터 삭제 메소드(delete)(deleteYn = 'Y' : 실제로는 수정 )
    int deleteTutorial(Long id);
//    Tutorial 테이블에 데이터 모두 삭제 메소드(deleteAll)
    int deleteAll();


}
