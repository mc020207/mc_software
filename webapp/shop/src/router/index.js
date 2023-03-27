import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Home from "../components/Home.vue";
import Welcome from '../components/Welcome'
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
    path: "/home",
    component: Home,
    redirect:'/welcome',
    children:[{path:'/Welcome',component:Welcome}]
  },
];

const router = new VueRouter({
  routes,
});
//挂载路由导航守卫
// next()   next('/login') 强制跳转
router.beforeEach((to, from, next) => {
  if(to.path=='/login') return next();
  //获取token
  const tokenStr=window.sessionStorage.getItem('token');
  if(!tokenStr) return next("/login");
  next();
});
export default router;
