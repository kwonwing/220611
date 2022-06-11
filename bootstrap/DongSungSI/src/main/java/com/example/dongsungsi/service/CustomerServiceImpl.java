package com.example.dongsungsi.service;

import com.example.dongsungsi.dao.CustomerDao;
import com.example.dongsungsi.model.Customer;
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
 * packageName : com.example.customerspring.service
 * fileName : CustomerServiceImpl
 * author : naraekwon
 * date : 2022/06/07
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/07         naraekwon          최초 생성
 */
@Service
public class CustomerServiceImpl implements CustomerService{
//    생성된 객체를 받아오는 어노테이션
    @Autowired
    private CustomerDao customerDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
//    id로 회원조회하는 메소드
    public Optional<Customer> findById(Long id) {
        return customerDao.findById(id);
    }

   // TODO: findAll() 수정, findByEmailContaining
    @Override
//    회원 전체 조회하는 메소드
    public List<Customer> findAll(Criteria criteria) {
        return customerDao.findAll(criteria);
    }

    @Override
    public List<Customer> findByEmailContaining(Criteria criteria) {
//        빈값으로 초기화
        List<Customer> customers = Collections.emptyList();

//      title이  null인지 체크(null -> '')
        Optional<String> optionalCriteria = Optional.ofNullable(criteria.getEmail());
//        테이블의 총 데이터 건수
        int totalCount = customerDao.selectTotalCount(optionalCriteria.orElse(""));

//        title 이 없으면 전체검색 -> criteria: 페이징 처리 클래스 객체
        criteria.setTotalItems(totalCount);
//       총 페이지 개수 =  테이블의 총 건수/페이지당 출력할 데이터 개수(totalCount/size)
        criteria.setTotalPages(totalCount/ criteria.getSize());
//        전체검색 : 전체검색에 타이틀없으므로 null 에러 안남 (title이 null이면 findAll호출 )
        if(criteria.getTitle() == null){
            customers = customerDao.findAll(criteria);
//        title 이 있으면 제목검색(title이 null이 아님)
        }else{
            customers = customerDao.findByEmailContaining(criteria);
        }

        return customers;
    }
    @Override
//    새 회원생성 또는 수정하는 메소드(insert / update)
    public Optional<Customer> save(Customer customer) {
        long seqId =0;

//        매개변수 tutorial 안의 값을 로그로 출력
        logger.info("customer{}", customer);

        if(customer.getId()==null){
//            id 값이 없으면 insert 문 실행(새글 저장)
             customerDao.insertCustomer(customer);
        }else{
//            id 값이 있으면 update 문 실행(수정)
             customerDao.updateCustomer(customer);
        }
//        insert 문 후에는 customer의 id 속성에 값이 저장된(<selectkey>)
        seqId = customer.getId();
        logger.info("seqID{}", seqId);
        return customerDao.findById(seqId);
    }

    @Override
    public boolean deleteById(Long id) {
        int queryResult =0;

        queryResult = customerDao.deleteCustomer(id);

        return (queryResult>=1)?true:false;
    }

    @Override
    public boolean deleteAll() {
        int queryResult =0;

        queryResult = customerDao.deleteAll();

        return (queryResult>=1)?true:false;
    }

}
