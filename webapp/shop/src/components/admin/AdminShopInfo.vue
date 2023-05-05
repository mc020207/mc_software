<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>审核员</el-breadcrumb-item>
      <el-breadcrumb-item>当前商店</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
    <el-card>
      <el-descriptions
        title="商店信息"
        direction="vertical"
        :column="4"
        border>
        <el-descriptions-item label="商店名">{{
          shopInfo.shopName
        }}</el-descriptions-item>
        <el-descriptions-item label="商店简介">{{
          shopInfo.shopIntro
        }}</el-descriptions-item>
        <el-descriptions-item label="商店地址">{{
          shopInfo.shopAddr
        }}</el-descriptions-item>
        <el-descriptions-item label="注册资金" :span="2">{{
          shopInfo.shopRegisterFund
        }}</el-descriptions-item>
        <el-descriptions-item label="注册日期">{{
          shopInfo.shopRegisterDate
        }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">{{
          shopStateStr
        }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
    <div style="display: flex;justify-content:flex-end;">
         <el-button  type="success" round  @click="shopInspectPass">通过</el-button>
         <el-button  type="primary" round  @click="shopInspectReject">拒绝</el-button>
          <input v-model="reason" type="primary" placeholder="请输入拒绝理由" />
         <el-button @click="toAdminList">返回审核列表</el-button>
    </div>
  </div>
</template>

<script>
import {apiAdminShopInfo,apiAdminShopOpenPass,apiAdminShopOpenReject,apiAdminShopDeletePass,apiAdminShopDeleteReject} from '@/api/api'
export default {
  data() {
      return {
        shopInfo:{},
        reason:"no specific reasons",
        shopStateStr:""
    };
  },
  created() {
    this.getShopInfo();
  },
  methods: {
    async getShopInfo() {
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="2"){
        this.$router.push("/login");
        return this.$message.error("非法访问");
      }
      var shopid = window.sessionStorage.getItem("admin_shopId");
      // var normal = window.sessionStorage.getItem("normalShopInfo");
      if (shopid == null) {
        // 非法访问
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
          apiAdminShopInfo({shopId:shopid}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.shopInfo = response.object;
            if(this.shopInfo.shopState == 0){
              this.shopStateStr = "待审核";
            }
            else{
              this.shopStateStr = "待删除";
            }
        });
    },
    async toAdminList(){
      if(this.shopInfo.shopState == 0){
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/open/list");
        this.$router.push("/admin/open/list");
      }
      else{
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/delete/list");
        this.$router.push("/admin/delete/list");
      }
    },
    async shopInspectPass(){
      if(this.shopInfo.shopState == 0){
        apiAdminShopOpenPass({shopId:this.shopInfo.shopId}).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        else{
          window.sessionStorage.removeItem("admin_shopId");
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/open/list");
        this.$message({
            showClose: true,
            message: "通过成功",
            type: 'success'
        });
        this.$router.push("/admin/open/list");
        }
        });
        
      }
      else{
        apiAdminShopDeletePass({shopId:this.shopInfo.shopId}).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        else{
          window.sessionStorage.removeItem("admin_shopId");
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/delete/list");
        this.$message({
            showClose: true,
            message: "通过成功",
            type: 'success'
        });
        this.$router.push("/admin/delete/list");
        }
        });
      }
    },
    async shopInspectReject(){
      if(this.shopInfo.shopState == 0){
        apiAdminShopOpenReject({shopId:this.shopInfo.shopId,reason:this.reason}).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        else{
          window.sessionStorage.removeItem("admin_shopId");
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/open/list");
        this.$message({
            showClose: true,
            message: "拒绝成功",
            type: 'success'
        });
        this.$router.push("/admin/open/list");
        }
        });
      }
      else{
        apiAdminShopDeleteReject({shopId:this.shopInfo.shopId,reason:this.reason}).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        else{
          window.sessionStorage.removeItem("admin_shopId");
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/delete/list");
        this.$message({
            showClose: true,
            message: "拒绝成功",
            type: 'success'
        });
        this.$router.push("/admin/delete/list");
        }
        });
      }
    }
  },
};
</script>

<style lang="less" scoped></style>
