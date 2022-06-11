package com.example.dongsungsi.dao;

import com.example.dongsungsi.model.Customer;
import com.example.dongsungsi.model.Tutorial;
import com.example.dongsungsi.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.customerspring.dao
 * fileName : CustomerDoa
 * author : naraekwon
 * date : 2022/06/07
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/07         naraekwon          최초 생성
 */
@Mapper
public interface CustomerDao {
//    TODO: findAll수정 ,findByEmailContaining,selectTotalCount 추가
//    전체 회원 조회 메소드
    List<Customer> findAll(Criteria criteria);
    List<Customer> findByEmailContaining(Criteria criteria);
    //    제목에 다른 데이터 건수를 세는 메소드
    int selectTotalCount(String title);
//      id로 회원 조회 메소드
    Optional<Customer> findById(Long id);
//    회원 생성 메소드
    long insertCustomer(Customer customer);
//    회원 수정 메소드
    long updateCustomer(Customer customer);
//    회원 삭제 메소드
    int deleteCustomer(Long id);
//    전체 회원 삭제 메소드
    int deleteAll();

}
