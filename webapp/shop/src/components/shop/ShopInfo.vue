<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商店界面</el-breadcrumb-item>
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
      </el-descriptions>
    </el-card>
      <el-table :data="productShopList" border stripe>
        <el-table-column type="index"></el-table-column>
         <el-table-column
          label="商品id"
          prop="productId"
          width="550"
        ></el-table-column>
        <el-table-column
          label="商品名"
          prop="productName"
          width="550"
        ></el-table-column>
      </el-table>
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
import {apiShopInfo,apiProductInfo,apiAdminShop,apiAdminProducts} from '@/api/api'
export default {
  data() {
    return {
      currentPage:1,
      pageSize:5,     //一页的数量
      total:100,
      shopInfo: {},
      productShopList:{}
    };
  },
  created() {
    this.getShopInfo();
  },
  methods: {
    async getShopInfo() {
      var t = window.sessionStorage.getItem('token');
      if(t!= "0" && t!="1" && t!="2"){
        this.$router.push("/login");
        return this.$message.error("非法访问");
      }
      var shopid = window.sessionStorage.getItem("shopId");
      var normal = window.sessionStorage.getItem("normalShopInfo");
      if (shopid == null || normal == null) {
        // 非法访问
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
      if(normal == true){
          apiShopInfo({shopId:shopid}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.shopInfo = response.object;
            apiProductInfo({shopId:shopid,page:this.currentPage}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.total = response.object.totalNumber;
            this.productShopList = response.object.products;
          });
        });
      }else if(t == "2"){
          apiAdminShop({shopId:shopid}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.shopInfo = response.object;
            apiAdminProducts({shopId:shopid,page:this.currentPage}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.total = response.object.totalNumber;
            this.productShopList = response.object.products;
          });
        });
      }else{
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
    },

    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       var shopid = window.sessionStorage.getItem("shopId");
        var normal = window.sessionStorage.getItem("normalShopInfo");
        if (shopid == null || normal == null) {
        // 非法访问
          this.$router.push("/home");
          return this.$message.error("非法访问");
        }
        if(normal == true){
          apiProductInfo({shopId:shopid,page:this.currentPage}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.total = response.object.totalNumber;
            this.productShopList = response.object.products;
      });
        }else if(t=="2"){
        apiAdminProducts({shopId:shopid,page:this.currentPage}).then(response =>{
        if(!response.success){
          this.$router.push("/home");
          return this.$message.error(response.message);
        }
        this.total = response.object.totalNumber;
        this.productShopList = response.object.products;
        })
      }else{
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
    },
  },
};
</script>

<style lang="less" scoped></style>
