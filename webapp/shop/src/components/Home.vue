<template>
  <div class="home-div">
    <el-container class="home-container">
      <!-- 头部区域 -->
      <el-header>
        <div style="height: 100%">
          <img
            src="../assets/logo.png"
            style="height: 100%; object-fit: contain"
            alt=""
          />
          <span>购物系统</span>
        </div>
        <el-button type="info" @click="logout">退出</el-button>
      </el-header>
      <!-- 主体区域 -->
      <el-container>
        <!-- 侧边栏 -->
        <el-aside :width="isCollapse?'64px':'200px'">
          <div class="toggle-button" @click="toggleCollapse">|||</div>
          <!-- 侧边栏菜单区-->
          <el-menu
            background-color="#333744"
            text-color="#fff"
            active-text-color="#409eff"
            :unique-opened="false"   
            :collapse="isCollapse"  
            :collapse-transition="false"
            :router="true"
            :default-active="activePath"
          >
            <!-- 一级菜单 -->
            <el-submenu :index="item.id" v-for="item in menuList" :key="item.id" >
              <!-- 一级菜单模板区域 -->
              <template slot="title">
                <!-- 图标 -->
                <i class="el-icon-location"></i>
                <!-- 文本 -->
                <span>{{ item.authName }}</span>
              </template>
              
              <!-- 二级菜单  -->
               <template v-for="subItem in item.children">
              <el-menu-item :index="subItem.path"  :key="subItem.path"
               @click="saveNaveState(subItem.path)" v-if="getActivepath(subItem)"
              >
                <!-- 图标 -->
                <i class="el-icon-menu"></i>
                <!-- 文本 -->
                <span>{{subItem.nextName}}</span>
              
                </el-menu-item>
              </template>
            </el-submenu>
          </el-menu>
        </el-aside>
        <!-- 右侧内容主体 -->
        <el-main>
          <!-- 路由占位符 -->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {apiLogout} from '@/api/api'
export default {
  data() {
    return {
      //左侧菜单数据
      menuList: [],
      isCollapse:false,
      //激活的链接地址
       activePath:''
    };
  },
  created() {
    this.getMenuList();
    this.activePath=window.sessionStorage.getItem('activePath');
  },
  methods: {
    logout() {
      apiLogout().then(response =>{
        if(!response.success) return this.$message.error(response.message);
        window.sessionStorage.clear();
        this.$router.push("/login");
        this.$message({
          showClose: true,
          message: '已退出',
        });
      });
    },
    //获取所有菜单
    getMenuList() {
    //根据用户信息，获得对应菜单
    //解析JWT，等后端实装后再说
    //在path中填写对应路径
    var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      switch (t) {

        case "0":
          this.menuList=[
        {
          id:"user",
          authName: "用户界面",
          children:[
            { 
              path:"/user/info",
              nextName:"我的信息"
            },
            { 
              path:"/user/account",
              nextName:"账户流水"
            }
          ]
        },
        {
          id:"shop",
          authName: "商店界面",
          children:[
            { 
              path:"/visit/product/list",
              nextName:"开放商品列表"
            },
            {
              path:'/visit/product/info',
              nextName:"商品详细信息"
            },
            {
              path:'/visit/shop/product/list',
              nextName:"该商店商品"
            }
          ]
        }
      ]
          break;
        case "1":
          this.menuList=[
        {
          id:"user",
          authName: "用户界面",
          children:[
            { 
              path:"/user/info",
              nextName:"我的信息"
            },
             { 
              path:"/user/account",
              nextName:"账户流水"
            }
          ]
        },
        {
          id:"shop",
          authName: "商店界面",
          children:[
            { 
              path:"/visit/product/list",
              nextName:"开放商品列表"
            },
             {
              path:'/visit/product/info',
              nextName:"商品详细信息"
            },
              {
              path:'/visit/shop/product/list',
              nextName:"该商店商品"
            }
          ]
        },
        {
          id:"owner",
          authName: "我的商店",
          children:[
            // { 
            //   path:"/owner/info",
            //   nextName:"我的商店信息"
            // },
             { 
              path:"/owner/account",
              nextName:"账户流水"
            }
          ]
        }
      ]
          break;
        case "2":
          this.menuList=[
        {
          id:"user",
          authName: "用户界面",
          children:[
            { 
              path:"/user/info",
              nextName:"我的信息"
            },
            { 
              path:"/user/account",
              nextName:"账户流水"
            }
          ]
        },
        {
          id:"shop",
          authName: "商店界面",
          children:[
            { 
              path:"/visit/product/list",
              nextName:"开放商品列表"
            },
             {
              path:'/visit/product/info',
              nextName:"商品详细信息"
            },
              {
              path:'/visit/shop/product/list',
              nextName:"该商店商品"
            }
          ]
        },
        {
          id:"admin",
          authName: "管理员",
          children:[
            { 
              
              path:"/admin/list",
              nextName:"待审核商店信息列表"
            },
             { 
              path:"/admin/profit",
              nextName:"账户流水"
            }
          ]
        }
      ]
          break;
        default:
          this.$message.error("非法访问");
          this.$router.push("/login");
      }
    },
    //点击按钮，切换菜单的折叠与展开
    toggleCollapse(){
      this.isCollapse=!this.isCollapse;
    },
    //保持链接的激活状态
    saveNaveState(activePath){
        window.sessionStorage.setItem('activePath',activePath);
        this.activePath=activePath;
    },
    getActivepath(subItem){
      var ifshow=true;
      if(subItem.path=='/visit/product/info'){
        let productId = window.sessionStorage.getItem("productId");
        if(productId==null){
          ifshow=false;
        }
      }
      else if(subItem.path=='/visit/shop/product/list'){
          let shopId = window.sessionStorage.getItem("ShopProductList_shopId");
        if(shopId==null){
          ifshow=false;
        }
      }
      return ifshow;
    }
  },
};
</script>

<style lang="less" scoped>
.home-div {
  height: 100%;
}
.home-container {
  height: 100%;
}
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0%;
  align-items: center;
  color: #ffff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}
.el-aside {
  background-color: #333744;
  .el-menu{
    border-right: none;
  }
}
.el-main {
  background-color: #eaedf1;
}
.toggle-button{
  background-color:#4A5064 ;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
