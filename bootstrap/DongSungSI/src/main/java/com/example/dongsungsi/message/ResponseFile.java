package com.example.dongsungsi.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * packageName : com.example.dongsunguploaddb.message
 * fileName : ResponseFile
 * author : naraekwon
 * date : 2022/05/31
 * description : vue에 이미지를 담에서 보낼 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/31         naraekwon          최초 생성
 */
@Setter
@Getter
//@AllArgsConstructor : Lombok에서 제공하는 모든 매개변수를 가진 생성자
@AllArgsConstructor
public class ResponseFile {
//    이미지 정보(TB_FILES_KNR 컬럼 정보)
    private String name; //첨부파일 이름
    private String url; //첨부파일 주소
    private String type; //첨부파일 유형(이미지, 텍스트 )
    private long size; //첨부파일 크기
    private String id; //첨부파일 id

}
