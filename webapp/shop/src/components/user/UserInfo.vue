<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户界面</el-breadcrumb-item>
      <el-breadcrumb-item>我的信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
    <el-card> 123 </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInfo: {},
    };
  },
  created() {
    this.getUserInfo();
  },
  methods: {
    async getUserInfo() {
      // var result=await this.$http.get('/user/info');
      var result = { success: true, message: "获取成功" };
      if (!result.success) return this.$message.error(result.message);
      this.userInfo = {
        userRole: 0,
        userName: "曹洋",
        userPhone: "18182933222",
        userIdCard: "26179520881022153X",
        userEmail: "u.qdn@epfl.md"
      };
    },
  },
};
</script>

<style lang="less" scoped></style>
