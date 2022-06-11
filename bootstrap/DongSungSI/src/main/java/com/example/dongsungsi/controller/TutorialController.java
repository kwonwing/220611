package com.example.dongsungsi.controller;

import com.example.dongsungsi.model.Tutorial;
import com.example.dongsungsi.paging.Criteria;
import com.example.dongsungsi.service.TutorialServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.dongsungsi.controller
 * fileName : TutorialController
 * author : naraekwon
 * date : 2022/05/25
 * description : url 정보
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/25         naraekwon          최초 생성
 */
//@RestController REST API 호출을 위한 어노테이션, json형태로 들어
    @CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialServiceImpl tutorialService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/tutorials")
//    ResponseEntity : 프론트엔드 요청시 전달할 객체
    public ResponseEntity<Tutorial>
            createTutorial(@RequestBody Tutorial tutorial){

        logger.info("createTutorial:tutorial{}", tutorial);
//        insert or update 호출 (id 값을 체크)
        boolean bSuccess = tutorialService.save(tutorial);

        try{
            if(bSuccess == true){
//                정상 실행된 경우
//                ResponseEntity<> (매개변수객체, 상태정보 )
                return new ResponseEntity<>(tutorial, HttpStatus.CREATED);
            }
//            정상 실행안된 경우: NOT_FOUND 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
//            DB 에러가 난 경우 :  INTERNAL_SERVER_ERROR  프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial>
            getTutorialById(@PathVariable("id")long id){
//        Optional<Tutorial> ; Tutorial 이 null이면 ""바꿔줌
//        Tutorial.get() : "".get() => 콘솔에러 null이면 ""바꿔줌 / 목적: null포인트 에러 방지
        Optional<Tutorial> tutorial = tutorialService.findById(id);

//        Optional메소드 : 값이 있으면
        if(tutorial.isPresent()){
//            ResponseEntity<> (객체, 상태정보 )=> 프론트엔드로 전송
            return new ResponseEntity<>(tutorial.get(), HttpStatus.OK);
        }else {
//            프론트엔드로 전송 : NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    REST API 호출 :http://localhost:8080/board/{id}
//   @RequestParam : 전통 호출 (쿼리스트링) :http://localhost:8080/board?name=id
// TODO : 아래 getTitleTutorials 수정 -> getAllTitlePage
    @GetMapping("/tutorials")
//    Map : key, value
    public ResponseEntity<Map<String, Object>>
        getAllTitlePage(Criteria criteria){
        logger.info("criteria{}", criteria); // totalItems, totalPages = null
//        totalItems, totalPages 계산해서 criteria객체에 저장
//          title을 조회하는 서비스를 호출(여러건:List)
        List<Tutorial> tutorials = tutorialService.findByTitleContaining(criteria);
        logger.info("tutorials{}", tutorials);

        try {
            if(tutorials.isEmpty()){
//                조회 데이터가 없으면
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("criteria{}", criteria); // totalItems, totalPages = 값이 있음
//            조회 데이터가 있으면 : tutorials 전송, 상태정보(OK) 전송
//            TODO : Map 에 넣어 객체 + 페이지 정보를 vue로 전송
            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", tutorials);
//            현재 페이지
            response.put("currentPage", criteria.getPage());
//            총 건수
            response.put("totalItems", criteria.getTotalItems());
//            총 페이지 개수
            response.put("totalPages", criteria.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
//          DB에러가 날 경우
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    UPDATE :@PutMapping
//    @PathVariable : url
//    @RequestBody : json 객체 형태로 데이터 받기
    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial){
// id 에 해당하는 데이터가 있는지 조회
//        Optional<객체> 객체가 null이면 "" 자동 바꿈 (null point error 방지)
        Optional<Tutorial> tutorialData = tutorialService.findById(id);

        if(tutorialData.isPresent()){
//            tutorialData가 있으면 save호출(id가 있으면 update가 됨)
            boolean bSuccess = tutorialService.save(tutorial);
            if(bSuccess==true){
//                save 호출이 성공하면
                return new ResponseEntity<>(tutorial, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
//            tutorialData 가 없으면
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PutMapping() : update 문 실행
//delete : delete_yn='Y'
    @PutMapping("/tutorials/deletion/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id){
//        update 문 호출(DELETE_YN = 'Y')
        boolean bSuccess = tutorialService.deleteById(id);
//        프론트엔드 쪽으로 상태정보를 전달
        try {
            if(bSuccess ==true){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
//            update 문이 실패했을 경우(0건 수정이 될 경우 = 정상 실행됐지만 수정할게없음)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
//            DB 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    @PutMapping() : update 문 실행
//deleteALl : 모두삭제, delete_yn='Y'
    @PutMapping("/tutorials/deletion")
    public ResponseEntity<HttpStatus> deleteAllTutorials(){
//        update 문 호출(DELETE_YN = 'Y')
        boolean bSuccess = tutorialService.deleteAll();
//        프론트엔드 쪽으로 상태정보를 전달
        try {
            if(bSuccess ==true){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
//            update 문이 실패했을 경우(0건 수정이 될 경우 = 정상 실행됐지만 수정할게없음)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
//            DB 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping : select 문 호출
    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished (){
//        테이블 컬럼중에 published가 'Y'인 데이터만 조회
        List<Tutorial> tutorials = tutorialService.findByPublished("Y");
//        프론트엔드 쪽으로 상태정보를 전달
        try {
            if(tutorials.isEmpty()){
//                tutorials 가 없으면
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
//            tutorials 가 있으면
            return new ResponseEntity<>(tutorials,HttpStatus.OK);
        }catch (Exception e){
//            DB 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
