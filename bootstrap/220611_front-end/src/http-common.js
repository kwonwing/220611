// 전역 공통 함수 
import axios from "axios";
export default axios.create({
   // 스프링서버와 연결 
   baseURL: "http://localhost:8000/api",
   headers:{
       "Content-type":"application/json"
    }
});