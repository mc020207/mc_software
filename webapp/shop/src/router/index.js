import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
import Home from "../components/Home.vue";
import Welcome from '../components/Welcome'
import UserInfo from '../components/user/UserInfo.vue'
import ShopList from '../components/shop/ShopList.vue'
import ShopInfo from '../components/shop/ShopInfo.vue'
import MyShopInfo from '../components/owner/OwnerInfo.vue'
import AdminList from '../components/admin/AdminList.vue'
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
    children:[{path:'/welcome',component:Welcome},
    // admin_routes
    {path:'/admin/list',component:AdminList},
    // shop_routes
    {path:'/shop/list',component:ShopList},
    {path:'/shop/info',component:ShopInfo},
    // owner_routes
    {path:'/owner/info',component:MyShopInfo},
    //user_routes
    {path:'/user/info',component:UserInfo},
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
