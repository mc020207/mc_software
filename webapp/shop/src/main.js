import Vue from 'vue'
import App from './App.vue'
import router from './router'

import './plugins/element.js'
//导入字体图标
import './assets/font/iconfont.css'
//导入全局样式表
import './assets/css/global.css'

import axios from 'axios'

//配置请求的根路径
axios.defaults.baseURL='http://127.0.0.1:4523/m1/2461559-0-default'

Vue.prototype.$http=axios

Vue.config.productionTip = false

new Vue({
  router,

  render: h => h(App)
}).$mount('#app')
