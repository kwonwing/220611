<template>
  <div  class="container mt-3 ">
        <!-- 페이징 추가 시작  -->
    <div class="mb-3">
      <div class="col-md-8">
        <div class="input-group mb-3">
          <!--        TODO : 수정 시작 #1-->
          <input
            type="text"
            class="form-control"
            placeholder="Search by email"
            v-model="searchEmail"
          />
          <div class="input-group-append">
            <button
              class="btn btn-outline-secondary"
              type="button"
              @click="
                page = 1;
                retrieveCustomers();
              "
            >
              Search
            </button>
          </div>
        </div>
      </div>

      <!--    TODO: 태그 추가 -->
      <div class="col-md-12">
        <div class="mb-3">
          Items per Page :
          <select v-model="pageSize" @change="handlePageSizeChange($event)">
            <option v-for="size in pageSize" :key="size" :value="size">
              {{ size }}
            </option>
          </select>
        </div>

        <!--      TODO: page bar 추가-->
        <b-pagination
          v-model="page"
          :total-rows="count"
          :per-page="pageSize"
          prev-text="Prev"
          next-text="Next"
          @change="handlePageChange"
        >
        </b-pagination>
      </div>
      <!--    TODO: page bar 끝-->
    </div>
    <table class="table">
      <thead>
      <tr>
        <th scope="col">First Name</th>
        <th scope="col">Last Name</th>
        <th scope="col">Email</th>
        <th scope="col">Phone</th>
        <th scope="col">Actions</th>
      </tr>
      </thead>
<!--      조회 데이터 생성 부분-->
      <tbody v-for="(customer, index) in customers"
             :key="index"
        >
      <tr>
        <td>{{ customer.firstName }}</td>
        <td>{{ customer.lastName }}</td>
        <td>{{ customer.email }}</td>
        <td>{{ customer.phone }}</td>
        <td>
          <a :href="'/customers/' + customer.id" class="btn btn-primary">
            Edit
          </a>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import CustomerDataService from "@/services/CustomerDataService";

/* eslint-disable */
export default {
  name: "customers",
  data() {
    return {
      customers: [],
      currentCustomer: null,
      currentIndex: -1,
      //TODO: title -> searchTitle
      searchEmail: "",
      //  TODO: 아래 변수 4개 추가
      page: 1,
      count: 0,
      //pageSize :페이지당 건수
      pageSize: 3,
      //페이지당 건수(3,6,9건수)
      pageSizes: [3, 6, 9],
    }
  },
  methods: {
    // 모든 회원 조회 서비스 호출
    //TODO: getRequestParams 추가 (springboot쪽으로 URL params 전송 )
    // http://localhost:8000/api/tutorials?title=''&page=1&size=3
    getRequestParams(searchEmail, page, pageSize) {
      let params = {};
      //searchTitle 값이 있으면 params객체에 title 로 저장
      if (searchEmail) {
        params["email"] = searchEmail;
      }
      //page 값이 있으면 params객체에 page 로 저장
      if (page) {
        params["page"] = page - 1;
      }
      //pageSize 값이 있으면 params객체에 size 로 저장
      if (pageSize) {
        params["size"] = pageSize;
      }
      return params;
    },
    // TODO : 아래 메소드 수정
    // TODO : getAll() => getAll(params)
    retrieveCustomers() {
      const params = this.getRequestParams(
        this.searchEmail,
        this.page,
        this.pageSize
      );
      //TODO : 백엔드 쪽으로 전체 데이터 요청 (페이징 처리)
      CustomerDataService.getAll(params)
        .then((response) => {
          // TODO: 아래 수정
          // 임시 변수 tutorials, totalItems (서버의 결과 데이터가 들어옴)
          // const { customers, totalItems } = response.data;
          // this.customers = customers; // 객체
          // this.count = totalItems; // 총건수
          this.customers = response.data

          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    // TODO: 메소드 2개 추가 handlePageChange , handlePageSizeChange
    //역할: 현재페이지 번호에 해당하는 데이터 다시 조회
    handlePageChange(value) {
      //페이지번호 저장
      this.page = value;
      //다시 조회
      this.retrieveCustomers();
    },
    //역할: 페이지당 건수가 변경되면 다시 조회하는 메소드
    handlePageSizeChange(event) {
      //select 박스 변경시 값 가져옴 (이벤트로 가져옴)
      //한 페이지당 건수 저장
      this.pageSize = event.target.value;
      //첫페이지 초기화?
      this.page = 1;
      //다시 조회
      this.retrieveCustomers();
    },
    // refreshList() {
    //   this.retrieveCustomers();
    //   this.currentCustomer = null;
    //   this.currentIndex = -1;
    // },

    // setActiveCustomers(customer, index) {
    //   this.currentCustomer = customer;
    //   this.currentIndex = index;
    // },

    // removeAllCustomers() {
    //   CustomerDataService.deleteAll()
    //     .then(response => {
    //       console.log(response.data);
    //       this.refreshList();
    //     })
    //     .catch(e => {
    //       console.log(e);
    //     });
    // },
  },
  //화면이 뜨자마자 실행되는 이벤트(모든 회원조회가 실행됨)
  mounted() {
    this.retrieveCustomers();
  }
}
</script>