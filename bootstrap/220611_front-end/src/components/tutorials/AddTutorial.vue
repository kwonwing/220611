<template>
  <div class="submit-form">
      <div v-if="!submitted">
          <div class="form-group">
              <label for="title">Title</label>
              <input 
                type="text" 
                class="form-control" 
                id="title" 
                required 
                v-model="tutorial.title" 
                name="title"
                />
          </div>

          <div class="form-group">
              <label for="description">Description</label>
              <input 
                class="form-control" 
                id="description" 
                required 
                v-model="tutorial.description" 
                name="description"
                />
          </div>

          <button @click="saveTutorial" class="btn btn-success">
              Submit
          </button>
      </div>
          
      <div v-else>
          <h4>You submitted successfully!</h4>
          <button class="btn btn-success" @click="newTutorial">
              Add
          </button>
      </div>
  </div>
</template>

<script>
// 화면 : tutorial 데이터 추가하는 화면 
import TutorialDataService from "@/services/TutorialDataService"
export default {
    name:"add-tutorial",
    data(){
        return {
            // tutorial 객체 초기화
            tutorial:{
                id: null,
                title: "",
                description: "",
                published: "N"
            },
            submitted: false
        }
    },
    methods:{
        saveTutorial(){
            // 전송용 데이터: springboot 로 
            var data = {
                // this 는 tutorial객체에서 가져오는 거 
                title: this.tutorial.title,
                description: this.tutorial.description,
            };
            // springboot(api서버) 데이터 추가 요청 
            TutorialDataService.create(data)
            .then(response=>{
                // 백엔드에 요청이 성공하면 결과가 들어옴 
                this.tutorial.id = response.data.id;
                this.submitted = true;
                console.log(response.data);
            })
            .catch(e=>{
                // 실패하면 (프론트로)결과가 들어옴 
                console.log(e);
            })
        },
        newTutorial(){
            this.submitted = false;
            this.tutorial = {};
        },
    }
}
</script>

<style>
    .submit-form{
        max-width: 300px;
        margin: auto;
    }
</style>