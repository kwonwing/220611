package com.example.dongsungsi.service;
import com.example.dongsungsi.model.Customer;
import com.example.dongsungsi.model.Tutorial;
import com.example.dongsungsi.paging.Criteria;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.customerspring.service
 * fileName : CustomerServie
 * author : naraekwon
 * date : 2022/06/07
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/07         naraekwon          최초 생성
 */
public interface CustomerService {
        //      id로 회원 조회 메소드(결과 1건)
    Optional<Customer> findById(Long id);

        //    모든 회원 조회 메소드 (결과 여러건)
//    List<Customer> findAll();

    // TODO: findAll() 수정, findByEmailContaining
     //    회원 전체 조회하는 메소드
    List<Customer> findAll(Criteria criteria);

    List<Customer> findByEmailContaining(Criteria criteria);

    //    회원 저장, 수정 메소드(insert / update)
    Optional<Customer> save (Customer customer);

        //    id로 회원 삭제 메소드
    boolean deleteById(Long id);

        //    전체 회원 삭제 메소드
    boolean deleteAll();
}
