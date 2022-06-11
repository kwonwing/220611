package com.example.dongsungsi.controller;

import com.example.dongsungsi.message.ResponseFile;
import com.example.dongsungsi.message.ResponseMessage;
import com.example.dongsungsi.model.FileDB;
import com.example.dongsungsi.service.FileDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.example.dongsunguploaddb.controller
 * fileName : FileDBController
 * author : naraekwon
 * date : 2022/05/31
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/31         naraekwon          최초 생성
 */
//웹 브라우저 보안 관련: 프론트 8080 포트로 오는 오청을 허용
    // ex 기본 요청: http://localhost:9000/board/list
    //             http://localhost:9000/api/list
@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/api")
public class FileDBController {

//    @Autowired : 스프링 객체를 받아옴
    @Autowired
    private FileDBService fileDBService; // 객체 정의(null)=>스프링 객체 저장

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    이미지 업로드를 위한 메뉴 (insert 호출)
    // Vue 에서 전송하는 형태(post)
    // @RequestParam : /upload
    // param = file
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file){
        String message = "";
//        매개변수 file 내용 출력
        logger.info("file {}" , file);
//        vue 에서 전송할 업로드 이미지를 db에 저장(insert 문 호출)
        try{
            fileDBService.store(file);
//              성공메세지 전송
            message = "uploaded the file successfully : "+ file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
//            실패메세지 전송
            message = "could not upload the file : "+ file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }

//    파일 id를 조회해서 다운로드 하는 메뉴
    // @PathVariable: /file/{id} 일 경우 사용하는 어노테이션
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id){
//        id로 조회하는 DB select 문 호출
        FileDB fileDB = fileDBService.getFile(id);
//        첨부파일 다운로드 형태로 전송 : attachment: filename = "image.jpg"
//        .header(상태정보 , 문서정보) .body(내용)
        return ResponseEntity.ok()
//                헤더정보(이미지이름):  attachment: filename fileDB.getName()
//                바디정보(이미지): fileDB.getName()
                .header(HttpHeaders.CONTENT_DISPOSITION,
//                                                                  "image.jpg"
                        "attachment; filename = \""+fileDB.getName()+"\"").body(fileDB.getData());
    }
//    모든 이미지 목록을 조회하는 메뉴
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles(){
      List<ResponseFile> files = fileDBService.getAllFile().map(dbFile->{
//        첨부파일을 다운로드 할 위치 url 만들어야함
//        vue 클릭스 이미지 다운로드 가능

//       =>  <img src ="http://localhost:9000/api/files/1"> : 요렇게 써야함
//        ServletUriComponentsBuilder:
            String fileDownloadURL = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getId())
                    .toUriString();

//            vue에 전송(파일 이름, url, 파일 유형, 파일 크기 )
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadURL,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getId());
//            stream => List
        }).collect(Collectors.toList());
//      vue 이미지 데이터 전송 (여러개 이미지 파일 전송)
        return ResponseEntity.status(HttpStatus.OK).body(files);

    }

}
