import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  //  /, /tutorials 메뉴를 클릭하면 TutorialsList.vue 로딩
  // tutorialsList.vue : Tutorial 목록 화면 
  {
    path: '/',
    alias: '/home',
    name: '/home',
    component:  ()=> import('@/views/HomeView.vue')
  },
  {
    path:"/tutorials",
    name: 'tutorials',
    component: ()=> import('@/components/tutorials/TutorialsList')
  },
  // tutorial.vue : Tutorial 상세 화면 
  {
    path: '/tutorials/:id',
    name: '/tutorial-details',

    component:  ()=> import('@/components/tutorials/Tutorial')
  },
  // //  Addtutorial.vue : Tutorial 에 목록 추가 화면 
  {
    path: '/add',
    name: '/add',
    component:  ()=> import('@/components/tutorials/AddTutorial')
  },
  {
    path: '/complain',
    name: '/complain',
    component:  ()=> import('@/views/ComplainView')
  },
  {
    path: '/upload',
    name: '/upload',
    component:  ()=> import('@/components/UploadImage')
  },
  {
    path: '/customers',
    name: '/customers',
    component:  ()=> import('@/components/customermanager/AddCustomer')
  },
  {
    path: '/costomerlist',
    name: '/costomerlist',
    component:  ()=> import('@/components/customermanager/CustomerList')
  },
  {
    path: '/customers/:id',
    name: '/edit-costomer',
    component:  ()=> import('@/components/customermanager/EditCustomer')
  },
  {
    path: '/shop',
    name: '/shop',
    component:  ()=> import('@/views/ShopView')
  },

  {
    path: '/creative',
    name: '/creative',
    component:  ()=> import('@/views/CreativeView')
  },


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
