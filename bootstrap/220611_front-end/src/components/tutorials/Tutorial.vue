<template>
  <!-- currentTutorial != null 이면 -->
  <div v-if="currentTutorial" class="edit-form">
    <h4>Tutorial</h4>
    <!-- form 시작 -->
    <form>
        <div class="form-group">
            <label for="title"></label>
            <input type="text" class="form-controll" id="title" v-model="currentTutorial.title"/>
        </div>
        <div class="form-group">
            <label for="description"></label>
            <input type="description" class="form-controll" id="description" v-model="currentTutorial.description"/>
        </div>
        <div class="form-group">
            <label><strong>Status: </strong></label>
            {{(currentTutorial =='Y')?"published":"Pending"}}
        </div>
    </form>
    <!-- form 끝  -->
        <!-- 클릭시 published =='Y'  -->
        <button class="badge badge-primary mr-2" 
                v-if='currentTutorial.published=="Y"'
                @click="updatePublished(false)"
                >
         Unpublished
         </button>
        <button v-else class="badge badge-primary mr-2" 
                @click="updatePublished(true)">
         published
         </button>
         <!-- delete 버튼 -->
         <button class="badge badge-primary mr-2" @click="deleteTutorial">
         Delete
         </button>
         <!-- update 버튼 -->
        <button type="submit" class="badge badge-success" @click="updateTutorial">
         Update
         </button>
         <p>{{message}}</p>


  </div>
  <!-- currentTutorial == null 이면 -->
  <div v-else>
      <br>
      <p>Please click on a Tutorial...</p>
  </div>
</template>

<script>
import TutorialDataService from "@/services/TutorialDataService";
//eslint : vue 감시 
 //  eslint-disable : eslint 비활성화 (name 두단어여야하는데 한단어라서 에러남)
//  아래 eslint 적용 예외 처리 
/* eslint-disable*/
export default {
    name: "tutorial",
    data(){
        return{
            currentTutorial: null,
            message: ''
        }
    },
    methods:{
        // id로tutorial데이터 상세 조회
        // 백엔드쪽에 요청 
        getTutorial(id){
            // id로 검색을 백엔드에 요청하는 메소드
            TutorialDataService.get(id)
            // 성공하면 then으로 들어옴
            .then(response =>{
                // response.data : 백엔드쪽에서온 데이터 
                this.currentTutorial = response.data;
                console.log(response.data);
            })
            // 실패하면 catch로 들어옴
            .catch(e=>{
                console.log(e)
            })
        },
        // tutorial 데이터 수정 메소드 호출
        updateTutorial(){
            // 백엔드쪽에 id로 검색해서 데이터 수정 요청 
            TutorialDataService.update(this.currentTutorial.id, this.currentTutorial)
            // 성공하면 then으로 들어옴
            .then(response=>{
                console.log(response.data)
                this.message = 'The tutorial was updated successfully!'
            })
            // 실패하면 catch로 들어옴 
            .catch(e=>{
                console.log(e)
            })
        },
        // 백엔드쪽에 데이터 삭제 요청 메소드
        deleteTutorial(){
            // 백엔드쪽에 id로 데이터 삭제 요청
            TutorialDataService.delete(this.currentTutorial.id)
            // 성공하면 then 으로 응답 들어옴 
            .then(response=>{
                console.log(response.data)
            })
            // 실패하면 catch 로 응답 들어옴 
            .catch(e=>{
                console.log(e)
                // 실패 후 새로고침(tutorialList 첫화면으로 새로고침 )
                // this.$router.push(이동할 페이지)
                this.$router.push({name: "tutorials"})
            })
        },
        // tutorial테이블 published 컬럼 : 'Y'<->'N'
        // published컬럼을 수정하는 메소드 
        updatePublished(status){
//TODO
        }
    },
    // vue 화면이 로딩될때(뜰때) 자동으로 실행되는 이벤트
    mounted(){
        this.message = '';
        // router/index.js =>path: 'tutorials/:id'
        this.getTutorial(this.$route.params.id);
    }
}
</script>

<style>
    .edit-form{
        max-width: 300px;
        margin: auto;
    }
</style>