
<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商店界面</el-breadcrumb-item>
      <el-breadcrumb-item>待审核商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
  <el-row v-for="(o,index_i) in 2" :key="o">
  <el-col :span="4" v-for="(o,index_j) in 5" :key="o" :offset="index_j?1:0">
    <el-card :body-style="{ padding: '0px' }"  style="width:195px;height:280px" v-if="index_i*5+index_j<productList.length">
      <!-- <el-image :src="productList[index_i*2+index_j].images.productImageAddr" style="width:200px;" fit="fill"> -->
          <el-image src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" style="width:100%;height:195" fit="fill">
         <div slot="error" class="image-slot">
        <i class="el-icon-picture-outline"></i>
      </div>
      </el-image>
      <div style="padding: 1px;">
        <span>{{productList[index_i*5+index_j].productName}}</span>
        <div class="bottom clearfix">{{productList[index_i*5+index_j].productIntro}}</div>
      </div>
         <!-- <el-button size="small" round @click="buyProduct(productList[index_i*5+index_j].productId)">{{productList[index_i*5+index_j].productPrice}}  购买</el-button> -->
         <el-button @click="productReject(productList[index_i*5+index_j].productId)">通过</el-button>
         <el-button @click="productPass(productList[index_i*5+index_j].productId)">拒绝</el-button>
         <el-button @click="productInspectInfo(productList[index_i*5+index_j].productId)">详情</el-button>
    </el-card>
  </el-col>
</el-row>
 <!-- 分页区域 -->
      <el-pagination 
       layout="total, prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import {apiAdminProductList,apiAdminProductPass,apiAdminProductReject} from '@/api/api'
export default {
  data() {
    return {
      productList: [],
      currentPage:1,
      pageSize:9,   //一页的数量  应改成10个
      total:0
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      apiVisitProductList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.productList = response.object.products;
      });
    },
    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getList();
    },
    //待处理事件逻辑

    async productInspectInfo(productId){
         window.sessionStorage.setItem("admin_productId", productId);
         var activePath='/admin/product/info';
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
        //不知道要不要加商店按钮
        //window.sessionStorage.removeItem("ShopProductList_shopId");
         this.$router.push(activePath);
    },
     async productPass(productid){
      // var result=await this.$http.get('/inspect/pass',shopId);
      apiAdminProductPass({productId:productid}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
      });
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="2"){
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
      this.getList();
    },
   async  productReject(productid){
      // var result=await this.$http.get('/inspect/pass',shopId);
      apiAdminProductReject({productId:productid}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
      });
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="2"){
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
      this.getList();
    },
  },
};
</script>

<style lang="less" scoped>
.el-row {
    margin-bottom: 15px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
</style>