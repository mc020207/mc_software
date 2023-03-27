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
        <el-aside width="200px">
          <!-- 侧边栏菜单区-->
          <el-menu
            background-color="#333744"
            text-color="#fff"
            active-text-color="#ffd04b"
          >
            <!-- 一级菜单 -->
            <el-submenu index="1">
              <!-- 一级菜单模板区域 -->
              <template slot="title">
                <!-- 图标 -->
                <i class="el-icon-location"></i>
                <!-- 文本 -->
                <span>导航一</span>
              </template>
              <!-- 二级菜单 -->
              <el-menu-item index="1-4-1">
                <!-- 图标 -->
                <i class="el-icon-location"></i>
                <!-- 文本 -->
                <span>导航一</span></el-menu-item
              >
            </el-submenu>
          </el-menu>
        </el-aside>
        <!-- 右侧内容主体 -->
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
 
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/user/login");
    },
    //获取所有菜单
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
}
.el-main {
  background-color: #eaedf1;
}
</style>
