import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Home from "../components/Home.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Login,
  },
  {
    path: "/user/login",
    component: Login,
  },
  {
    path: "/home",
    component: Home,
  },
];

const router = new VueRouter({
  routes,
});
//挂载路由导航守卫
// next()   next('/user/login') 强制跳转
router.beforeEach((to, from, next) => {
  if (to.path == "/user/login") return next();
  //获取token
  const tokenStr = window.sessionStorage.getItem("token");
  if (!tokenStr) return next("/user/login");
  next();
});
export default router;
