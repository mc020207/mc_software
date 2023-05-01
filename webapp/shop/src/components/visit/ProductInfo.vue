<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商店界面</el-breadcrumb-item>
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
        <span>{{productInfo.productName}}</span>
        <div class="bottom clearfix">{{productInfo.productIntro}}</div>
      </div>
      <div style="display: flex;justify-content:flex-end;">
         <el-button  type="success" round  @click="buyProduct(productInfo.productId)">{{productInfo.productPrice}}  购买</el-button>
         <el-button  type="primary" round  @click="cartAddProduct(productInfo.productId)">加入购物车</el-button>
         <el-button @click="toShopProductList">该商店全部商品</el-button>
        <el-button @click="getShopInfo">商店信息</el-button>
        </div>
    </el-card>

    <el-drawer
      :with-header="false"
      :visible.sync="drawer">
      <el-card>
      <el-descriptions
        title="商店信息"
        :column="1"
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
</el-drawer>
  </div>
</template>
<script>
import {apiVisitProductInfo,apiVisitShopInfo,apiOrderUserBuy,apiOrderUserAddCart} from '@/api/api'
export default {
  data() {
    return {
        productInfo:{},
        loading:true,
        drawer:false,

        shopInfo:""
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    async getInfo() {
      var pid = window.sessionStorage.getItem("productId");
      if (pid == null) {
        // 非法访问
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
      apiVisitProductInfo({productId:pid}).then(response =>{
            if(!response.success){
              return this.$message.error(response.message);
            }
            this.productInfo = response.object;
            this.productInfo.images=[
                {
                  productImageId: 0,
                  productImageAddr: "https://i.postimg.cc/Bbg0RNLz/70448487-p0.png"
                },
                 {
                  productImageId: 1,
                  productImageAddr: "https://i.postimg.cc/pLyH1f20/70467532-p0.png"
                },
                 {
                  productImageId: 2,
                  productImageAddr: "https://i.postimg.cc/5tCMffQL/70987206-p0.jpg"
                }
              ];
            this.loading=false;
        });
    },
    async getShopInfo() {
      var shopid = this.productInfo.shopId;
          apiVisitShopInfo({shopId:shopid}).then(response =>{
            if(!response.success){
              return this.$message.error(response.message);
            }
            this.shopInfo = response.object;
            this.drawer = true;
        });
    },
    async toShopProductList(){
         window.sessionStorage.setItem("ShopProductList_shopId", this.productInfo.shopId);
         var activePath='/visit/shop/product/list';
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         this.$router.push(activePath);
    },
      async buyProduct(productId){
      apiOrderUserBuy({productId:productId}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
           this.$message({
            showClose: true,
            message: "购买成功",
            type: 'success'
          });
     });
    },
       async cartAddProduct(productId){
      apiOrderUserAddCart({productId:productId}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
           this.$message({
            showClose: true,
            message: "加入购物车成功",
            type: 'success'
          });
     });
    }
    
  },
};
</script>

<style lang="less" scoped>

</style>
