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
            :unique-opened="true"   
            :collapse="isCollapse"  
            :collapse-transition="false"
            :router="true"
          >
            <!-- 一级菜单 -->
            <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
              <!-- 一级菜单模板区域 -->
              <template slot="title">
                <!-- 图标  先不搞，最后处理-->
                <i class="el-icon-location"></i>
                <!-- 文本 -->
                <span>{{ item.authName }}</span>
              </template>
              
              <!-- 二级菜单  -->
              <el-menu-item :index="subItem.path" v-for="subItem in item.children" :key="subItem.id">
                <!-- 图标 -->
                <i class="el-icon-menu"></i>
                <!-- 文本 -->
                <span>{{subItem.nextName}}</span></el-menu-item
              >
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
export default {
  data() {
    return {
      //左侧菜单数据
      menuList: [],
      isCollapse:false
    };
  },
  created() {
    this.getMenuList();
  },
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
    //获取所有菜单
    getMenuList() {
      //根据用户信息，获得对应菜单
      var x = 1;
     //在path中填写对应路径
      this.menuList=[
        {
          id:0,
          authName: "用户界面",
          children:[
            { 
              id:0,
              path:"/0/0",
              nextName:"我的信息"
            }
          ]
        },
        {
          id:1,
          authName: "商店界面",
          children:[
            { 
              id:0,
              path:"/1/0",
              nextName:"已开放商店列表"
            },
            { 
              id:1,
              path:"/1/1",
              nextName:"当前商店"
            }
          ]
        },
        {
          id:2,
          authName: "我的商店",
          children:[
            { 
              id:0,
              path:"/2/0",
              nextName:"注册我的商店"
            },
            { 
              id:1,
              path:"/2/0",
              nextName:"我的商店信息"
            }
          ]
        },
        {
          id:3,
          authName: "管理员",
          children:[
            { 
              id:0,
              path:"/3/0",
              nextName:"待审核商店信息列表"
            },
            { 
              id:1,
              path:"/3/1",
              nextName:"待审核商店信息"
            }
          ]
        }
      ]


      switch (x) {
        case 1:
          // this.menuList=[];
          break;
        case 2:
          // this.menuList=[];
          break;
        case 3:
          // this.menuList=[];
          break;
      }
    },
    //点击按钮，切换菜单的折叠与展开
    toggleCollapse(){
      this.isCollapse=!this.isCollapse;
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
