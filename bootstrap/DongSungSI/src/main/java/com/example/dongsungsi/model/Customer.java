package com.example.dongsungsi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName : com.example.customerspring.model
 * fileName : Customer
 * author : naraekwon
 * date : 2022/06/07
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/07         naraekwon          최초 생성
 */
@Getter
@Setter
@ToString
public class Customer {
//    Long : 객체 ,long 일반 자료형
    private Long id; //회원 아이디
    private String firstName;//회원 이름
    private String lastName;//회원 tjd
    private String phone;//회원 전화번호
    private String email;//회원 이메일
    private String deleteYn;//삭제 여부
    private String insertTime;// 등록일
    private String updateTime;// 수정일
    private String deleteTime;// 삭제일
}

