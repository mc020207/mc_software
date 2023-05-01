import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
import Home from "../components/Home.vue";
import Welcome from '../components/Welcome'
import UserInfo from '../components/user/UserInfo.vue'
import UserAccount from "../components/user/UserAccount.vue";
import UserCart from "../components/user/UserCart.vue";

import ProductList from '../components/visit/ProductList.vue';
import ProductInfo from '../components/visit/ProductInfo.vue';
import ShopProductList from '../components/visit/ShopProductList.vue'; 
import OwnerShopInfo from '../components/owner/OwnerInfo.vue';
import OwnerProductList from '../components/owner/OwnerProductList.vue';
import OwnerProductInfo from '../components/owner/OwnerProduct.vue';
import OwnerAccount from '../components/owner/OwnerAccount.vue';
import OwnerOrder from  '../components/owner/OwnerOrder.vue';
import ShopRecord from '../components/record/ShopRecord.vue';
import ProductRecord from '../components/record/ProductRecord.vue';
import AdminShopOpen from '../components/admin/AdminShopOpen.vue';
import AdminProfit from '../components/admin/AdminProfit.vue';

import temp from "../components/temp.vue";
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
    {path:'/user/cart',component:UserCart},
    // visit_routes
    {path:'/visit/product/list',component:ProductList},
    {path:'/visit/product/info',component:ProductInfo},
    {path:'/visit/shop/product/list',component:ShopProductList},
    // owner_routes
    {path:'/owner/info',component:OwnerShopInfo},
    {path:'/owner/account',component:OwnerAccount},
    {path:'/owner/order',component:OwnerOrder},
    {path:'/owner/product/list',component:OwnerProductList},
    {path:'/owner/product/Info',component:OwnerProductInfo},
    // admin_routes
    // {path:'/admin/list',component: AdminShopOpen},
    // { path:'/admin/profit',component:AdminProfit},
    { path:'/temp',component:temp},
    // { path:'/admin/product/info',component:AdminProductInfo},
    // { path:'/admin/shop/info',component:AdminShopInfo},
    // record_routes
    {path:'/record/shop/list',component:ShopRecord},
    {path:'/record/product/list',component:ProductRecord},
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
