package com.example.dongsungsi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName : com.example.dongsunguploaddb.model
 * fileName : FileDB
 * author : naraekwon
 * date : 2022/05/31
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/31         naraekwon          최초 생성
 */
@Setter
@Getter
@ToString
//매개변수없는 생성자만들어주는 어노테이션
@NoArgsConstructor
public class FileDB {
    private String id; //첨부파일 id
    private String name; //첨부파일 이름
    private String type; //첨부파일 유형
    private byte[] data; // oaracle 이미지 담는 자료형(data) : byte[] = DATA BLOB

//    매개변수 3개 생성자

    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}


