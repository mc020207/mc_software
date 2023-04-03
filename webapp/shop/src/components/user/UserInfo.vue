<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户界面</el-breadcrumb-item>
      <el-breadcrumb-item>我的信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
    <el-card> 
      <el-descriptions title="个人信息" direction="vertical" :column="3" border>
  <el-descriptions-item label="用户名">{{userInfo.userName}}</el-descriptions-item>
  <el-descriptions-item label="手机号">{{userInfo.userPhone}}</el-descriptions-item>
   <el-descriptions-item label="用户身份">{{userInfo.userRoleStr}}</el-descriptions-item>
  <el-descriptions-item label="身份证号" :span="2">{{userInfo.userIdCard}}</el-descriptions-item>
  
  <el-descriptions-item label="邮箱">{{userInfo.userEmail}}</el-descriptions-item>
</el-descriptions>
    </el-card>
  </div>
</template>

<script>
import {apiUserInfo} from '@/api/api'
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
      // var result = { success: true, message: "获取成功" };
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="0" && t!="1" && t!="2"){
        this.$message.error("非法访问");
        this.$router.push("/login");
      }
      apiUserInfo().then(response =>{
        if (!response.success){
          this.$router.push('/home');
          return this.$message.error(response.message);
        }
        this.userInfo = response.object;
        switch (this.userInfo.userRole){
        case 0:
          this.userInfo.userRoleStr='普通用户';
          break;
        case 1:
           this.userInfo.userRoleStr='商户';
          break;
        case 2:
           this.userInfo.userRoleStr='管理员';
          break;
        }
      })
    },
  },
};
</script>

<style lang="less" scoped></style>
