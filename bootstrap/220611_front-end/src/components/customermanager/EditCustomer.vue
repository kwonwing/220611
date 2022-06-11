<template>
  <div  class="container mt-3 ">
    <!--    기존회원이 있을 경우  currentCustomer != null -->
    <div v-if="currentCustomer">
      <div class="mb-3">
        <label for="firstName" class="form-label">First Name</label>
        <input type="text" class="form-control" id="firstName" required name="firstName" v-model="currentCustomer.firstName">
      </div>
      <div class="mb-3">
        <label for="lastName" class="form-label">Last Name</label>
        <input type="text" class="form-control" id="lastName" required name="lastName" v-model="currentCustomer.lastName">
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" required name="email" v-model="currentCustomer.email">
      </div>
      <div class="mb-3">
        <label for="phone" class="form-label">Phone</label>
        <input type="text" class="form-control" id="phone" required name="phone" v-model="currentCustomer.phone">
      </div>
      <div class="mb-3">
        <button @click="updateCustomer" class="btn btn-primary me-3">Update</button>
        <button @click="deleteCustomer" class="btn btn-danger">Delete</button>
      </div>
<!--      수정 버튼을 클릭후 성공시 아래가 보임 -->
      <div class="alert alert-success" role="alert" v-if="message">
        {{message}}
      </div>
    </div>
  </div>
</template>

<script>
// 화면 설명: 회원정보 수정/삭제 화면
import CustomerDataService  from "@/services/CustomerDataService";
export default {
  name: "edit-customer",
  data(){
    return {
      //객체 (회원정보)
      currentCustomer: null,
      message:''
    }
  },
  methods:{
    getCustomer(id){
    //  id에 해당하는 회원정보를 조회하는 서비스를 요청(springboot)
    //  axios 통신이용 서비스 호출(springboot)
      CustomerDataService.get(id)
          //성공 -> 결과데이터 들어옴
          .then(response=>{
            this.currentCustomer=response.data;
          })
    //  실패 -> 에러데이터 들어옴
        .catch(e=>{
          alert(e)
        })
    },
    // 회원정보 수정 서비스 요청(springboot)
    updateCustomer(){
      CustomerDataService.update(this.currentCustomer.id, this.currentCustomer)
          .then(()=>{
            this.message = 'The customer was update successfully!'
          })
          .catch(e=>{
            alert(e)
          })
    },
  //  회원 삭제 서비스 요청
    deleteCustomer(){
      CustomerDataService.delete(this.currentCustomer.id)
          .then(()=>{
            //화면이동  (이동할 페이지 연결) : customers 화면으로 이동됨
            //  router/index.js 안에 name
            this.$router.push({name:'customers'})
          })
          .catch(e=>{
            alert(e)
          })
    },

  },
//  화면이 뜨면 실행되는 이벤트
  mounted() {
    //http://localhost:8080/:id(param정보)
    //$route : route/index.js param.id
    this.getCustomer(this.$route.params.id)
  }
}
</script>