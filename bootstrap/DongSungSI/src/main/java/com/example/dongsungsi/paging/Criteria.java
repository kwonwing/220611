package com.example.dongsungsi.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName : com.example.dongsungsi.paging
 * fileName : criteria
 * author : naraekwon
 * date : 2022/06/10
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/10         naraekwon          최초 생성
 */
@Getter
@Setter
@ToString
public class Criteria {
//    현재 페이지 번호
//    Long 객체 long 기본형
//    Integer 객체 int 기본형
    private Integer page; // 현재 페이지 번
    private Integer size; //페이지당 출력할 데이터 개수
    private Integer totalItems; //테이블 총 데이터 개수
//    계산 공식: 테이블 총 데이터 개수 /1 페이지당 출력할 데이터 개수 (totalItems/size)
    private Integer totalPages; // 총 페이지수
    private String title;// 검색 제목
//    TODO: customer 추가
    private String email;// 메일 검색

//    기본 페이지 정보 저장
    public Criteria(){
        this.page =0;//현재 페이지 번호
        this.size=3;//페이지당 데이터 건수

    }
}
