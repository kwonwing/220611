package com.example.dongsungsi.service;

import com.example.dongsungsi.model.Tutorial;
import com.example.dongsungsi.paging.Criteria;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.dongsungsi.service
 * fileName : TutorialService
 * author : naraekwon
 * date : 2022/05/25
 * description : DAO, Model 이용해서 비지니스로직을 구성하는 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/25         naraekwon          최초 생성
 */
public interface TutorialService {
// 아이디 검색
    public Optional<Tutorial> findById(long id);
//    published 검색
    public List<Tutorial> findByPublished(String published);
//    제목 검색
    public List<Tutorial> findByTitleContaining(Criteria criteria);
//    모두 검색
    public List<Tutorial> findAll(Criteria criteria);

//    insert / update 같이 구성되는 메소드
    public boolean save(Tutorial tutorial);
//    선택 삭제
    public boolean deleteById(Long id);
//    모두 삭제
    public boolean deleteAll();
}
