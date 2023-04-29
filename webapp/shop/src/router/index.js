import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
import Home from "../components/Home.vue";
import Welcome from '../components/Welcome'
import UserInfo from '../components/user/UserInfo.vue'
import UserAccount from "../components/user/UserAccount.vue";
// import ShopList from '../components/visit/ShopList.vue'
// import ShopInfo from '../components/visit/ShopInfo.vue'
// import MyShopInfo from '../components/owner/OwnerInfo.vue';
import OwnerAccount from '../components/owner/OwnerAccount.vue';
import AdminShopOpen from '../components/admin/AdminShopOpen.vue';
import AdminProfit from '../components/admin/AdminProfit.vue';
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
    //user_routes
    {path:'/user/info',component:UserInfo},
    {path:'/user/account',component:UserAccount},
    // shop_routes
    // {path:'/shop/list',component:ShopList},
    // {path:'/shop/info',component:ShopInfo},
    // owner_routes
    // {path:'/owner/info',component:MyShopInfo},
    {path:'/owner/account',component:OwnerAccount},
    // admin_routes
    {path:'/admin/list',component: AdminShopOpen},
    { path:'/admin/profit',component:AdminProfit}
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
  //先开发，注释路由守卫
  return next();
  // let valid = ["/","/login","/register","/home",'/welcome','/admin/list','/shop/list','/shop/info','/owner/info','/user/info'];
  // for(let i = 0; i < valid.length; i++) {
  //   if(to.path == valid[i]) return next();
  // }
  // next("/home");
});
export default router;
