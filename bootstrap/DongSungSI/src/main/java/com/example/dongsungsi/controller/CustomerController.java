package com.example.dongsungsi.controller;

import com.example.dongsungsi.model.Customer;
import com.example.dongsungsi.paging.Criteria;
import com.example.dongsungsi.service.CustomerServiceImpl;
import com.example.dongsungsi.model.Customer;
import com.example.dongsungsi.service.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName : com.example.customerspring.controller
 * fileName : CustomerController
 * author : naraekwon
 * date : 2022/06/07
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/07         naraekwon          최초 생성
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomerController {
    @Autowired
    CustomerServiceImpl customerService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    모든 회원 조회 메뉴
    @GetMapping("/customers")
    public ResponseEntity<Map<String,Object>>
            getAllEmailPage(Criteria criteria){
//        모든 회원조회 서비스 호출
        List<Customer> customers = customerService.findByEmailContaining(criteria);
//        try {
//                return new ResponseEntity<Object>(customers,HttpStatus.OK);
//        }catch (Exception e){
////          DB에러가 날 경우
//            logger.error(e.getMessage(),e);
////            vue에 에러메세지 전송
//            return new ResponseEntity<Object> (HttpStatus.BAD_REQUEST);
//        }
        try {
            if(customers.isEmpty()){
//                조회 데이터가 없으면
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("criteria{}", criteria); // totalItems, totalPages = 값이 있음
//            조회 데이터가 있으면 : tutorials 전송, 상태정보(OK) 전송
//            TODO : Map 에 넣어 객체 + 페이지 정보를 vue로 전송
            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", customers);
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


// insert or update
    @PostMapping("/customers")
//    ResponseEntity : 프론트엔드 요청시 전달할 객체(HttpEntity 클래스를 상속받아 구현한 클래스)
// RequestEntity, ResponseEntity 클래스: HttpEntity 클래스를 상속받아 구현한 클래스

//    public ResponseEntity<Object>:<Object> 모든 객체의 조상으로 모든 객체를 다 받아옴
   public ResponseEntity<Object>
    createCustomer(@RequestBody Customer customer){

        logger.info("createTutorial:tutorial{}", customer);

//        insert or update 호출 (id 값을 체크)
//       save리턴값  Optional: Null체크 -> 옵셔널 벗기는것이 get()
        Customer savedCustomer = customerService.save(customer).get();
        try{
//            vue에 객체 + 성공메세지 전송
            return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
        }catch(Exception e){
//            DB 에러가 난 경우 :  vue에 보낼 에러 메세지 전송
            logger.error(e.getMessage(), e);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

    }
// id로 회원 조회 메뉴
    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Long id){
//      id로 회원 조회하는 서비스를 호출
        // .get() optional 에서 객체만 꺼내기
        Customer customer = customerService.findById(id).get();

        try {
            if(customer !=null){
//                성공시 vue에 객체 + 성공메세지 전송
                return new ResponseEntity<Object>(customer, HttpStatus.OK);
            }else{
//                vue에 데이터가 없을 경우 not found 전송
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
//            vue에 에러메세지 전송
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }
//      id 회원 수정 메뉴(update/insert)
//   vue에서 전송:  url 매개변수(@PathVariable로 받음) + 객체(@RequestBody 받음)
    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        try{
//            customer에 id값 저장 (customer에 id가 없어서 넣어줌)
            customer.setId(id);
//          save :  id==null -> insert / id!=null -> update
//            .get : 옵셔널로 싸여진 데이터를 Customer 객체 꺼냄
            Customer savedCustomer = customerService.save(customer).get();
//            vue에 db에 저장된 객체와 상테메세지 전송
            return new ResponseEntity<Object>(savedCustomer, HttpStatus.OK);

        }catch (Exception e){
            logger.error(e.getMessage(),e);
//            vue에 에러메세지 전송
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

//    id로 삭제
    @PutMapping("/customers/deletion/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id")Long id){
        try{
//            id로 삭제 서비스 호출 (내부적으로 update문 실행)
            customerService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
//            웹앱 개발: 클라이언트(vue, react, html) + 서버(springboot, node)
//            vue(클라이언트)에 에러메세지 전송
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
    }
}