<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>审核员</el-breadcrumb-item>
      <el-breadcrumb-item>商品详细信息</el-breadcrumb-item>
    </el-breadcrumb>
      <template>
        <el-carousel :interval="4000" type="card"  v-loading="loading" height="450px" >
          <el-carousel-item v-for="item in productInfo.images" :key="item.productImageId">
              <el-image :src="item.productImageAddr" style="width:100%;height:100%" fit="fill">
            <div slot="error" class="image-slot">
            <i class="el-icon-picture-outline"></i>
          </div>
      </el-image>
          </el-carousel-item>
        </el-carousel>
      </template>
    <el-card v-loading="loading">
      <div style="padding: 1px;">
        <el-descriptions
        title="商品信息"
        :column="3"
        border>
        <el-descriptions-item label="商品名">{{
          productInfo.productName
        }}</el-descriptions-item>
        <el-descriptions-item label="价格" :span="2">{{
          productInfo.productPrice
        }}</el-descriptions-item>
         <el-descriptions-item label="商品简介">{{
          productInfo.productIntro
        }}</el-descriptions-item>
      </el-descriptions>
      </div>
      <div style="display: flex;justify-content:flex-end;">
         <el-button  type="success" round  @click="productPass">通过</el-button>
         <el-button  type="primary" round  @click="productReject">拒绝</el-button>
         <input v-model="reason" type="primary" placeholder="请输入拒绝理由" />
         <el-button @click="toAdminList">返回待审核商品列表</el-button>
        </div>
    </el-card>

  </div>
</template>
<script>
import {apiAdminProductPass,apiAdminProductReject,apiAdminProductInfo} from '@/api/api'
export default {
  data() {
    return {
        productInfo:{},
        loading:true,
        shopInfo:"",
        reason:"no specific reasons"
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    async getInfo() {
      var pid = window.sessionStorage.getItem("admin_productId");
      if (pid == null) {
        // 非法访问
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
      apiAdminProductInfo({productId:pid}).then(response =>{
            if(!response.success){
              return this.$message.error(response.message);
            }
            this.productInfo = response.object;
            for(let i=0;i<this.productInfo.images.length;i++){
              this.productInfo.images[i].productImageAddr="http://localhost:8080"+this.productInfo.images[i].productImageAddr;
            }
            this.loading=false;
        });
    },
    async toAdminList(){
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/product/list");
        this.$router.push("/admin/product/list");
    },
    async productPass(){
      apiAdminProductPass({productId:this.productInfo.productId}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        else{
          window.sessionStorage.removeItem("admin_productId");
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/product/list");
        this.$message({
            showClose: true,
            message: "通过成功",
            type: 'success'
        });
        this.$router.push("/admin/product/list");
        }
      });
      
    },
   async productReject(){
      apiAdminProductReject({productId:this.productInfo.productId,reason:this.reason}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        else{
          window.sessionStorage.removeItem("admin_productId");
        this.$parent.$parent.$parent.$parent.saveNaveState("/admin/product/list");
        this.$message({
            showClose: true,
            message: "删除成功",
            type: 'success'
        });
        this.$router.push("/admin/product/list");
        }
      });
    },
    

    
  },
};
</script>

<style lang="less" scoped>

</style>
