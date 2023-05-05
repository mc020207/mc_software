import Vue from "vue";
import App from "./App.vue";
import router from "./router";

import "./plugins/element.js";
//导入字体图标
import "./assets/font/iconfont.css";
//导入全局样式表
import "./assets/css/global.css";

import jwt_decode from "jwt-decode";
import md5 from "js-md5";
import axios from "axios";
// import  VueCookies  from "vue-cookies";

//配置请求的根路径  记得加上"api"
// axios.defaults.baseURL = "http://localhost:8080/api";    
//请问均要cookie
// axios.defaults.withCredentials=true;
// axios.withCredentials=true;
// axios.defaults.crossDomain=true;
// Vue.use(VueCookies);
//所有请问均要token

// axios.interceptors.request.use(config=>{
//   config.headers.Authorization=window.sessionStorage.getItem('token');
//   return config;
// });

// Vue.prototype.$http = axios;

Vue.config.productionTip = false;

Vue.prototype.$decoder = jwt_decode;
Vue.prototype.$md5 = md5;
new Vue({
  router,

  render: (h) => h(App),
}).$mount("#app");
