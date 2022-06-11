package com.example.dongsungsi.service;

import com.example.dongsungsi.dao.FileDBDao;
import com.example.dongsungsi.model.FileDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * packageName : com.example.dongsunguploaddb.service
 * fileName : FileDBService
 * author : naraekwon
 * date : 2022/05/31
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/31         naraekwon          최초 생성
 */
//@Service FileDBService클래스도 스프링의 객체로 생성됨
@Service
public class FileDBService {

    @Autowired
    private FileDBDao fileDBDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    insert 문 호출하는 서비스(업로드 이미지 db저장됨)
    public int store(MultipartFile file)throws IOException {
//        매개변수로 들어오는 이미지 파일의 경로를 제거해서 순수 파일이름만 얻기
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

//        이미지파일 생성됨
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

//        DB에 insert문 호출
        return fileDBDao.save(fileDB);
    }
//    이미지 정보 가져오기 서비스(결과 1)
    public FileDB getFile(String id){
        return fileDBDao.findById(id);
    }

//    모든 이미지 목록을 가져오는 서비스(결과: 여러건)
    public Stream<FileDB> getAllFile(){
//        list, map, set => 인터페이스 <=> Stream변환 가능 (양쪽 가능 )
//        ArrayList, Hashmap, HashSet =>구현 클래스
//        Stream에 있는 편한 함수를 쓰기위해 (map, foreach, filter)
        Stream<FileDB> resFileDB = fileDBDao.findAll().stream();

        return resFileDB;
    }
}
