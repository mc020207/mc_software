import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
import Home from "../components/Home.vue";
import Welcome from '../components/Welcome'
import UserInfo from '../components/user/UserInfo.vue'
import ShopRegister from '../components/owner/OwnerRegister.vue'
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Login,
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/register",
    component: Register,
  },
  {
    path: "/home",
    component: Home,
    redirect:'/welcome',
    children:[{path:'/Welcome',component:Welcome},
    {path:'/user/info',component:UserInfo},
    {path:'/owner/register',component:ShopRegister}
  ]
  },
];

const router = new VueRouter({
  routes,
});
//挂载路由导航守卫
// next()   next('/login') 强制跳转
router.beforeEach((to, from, next) => {
  if(to.path=='/login'||to.path=='/register') return next();
  //获取token
  const tokenStr=window.sessionStorage.getItem('token');
  if(!tokenStr) return next("/login");
  next();
});
export default router;
