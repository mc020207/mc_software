import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'

Vue.use(VueRouter)

const routes = [
    {
      path:'/',component:Login
    },
    {
      
      path:'/user/login',component:Login
    },
   
]

const router = new VueRouter({
  routes
})

export default router
