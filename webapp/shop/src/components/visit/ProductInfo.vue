<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商店界面</el-breadcrumb-item>
      <el-breadcrumb-item>商品详细信息</el-breadcrumb-item>
    </el-breadcrumb>
      <template>
        <el-carousel :interval="4000" type="card"  v-loading="loading" height="400px" >
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
        :column="1"
        border>
        <el-descriptions-item label="商品名">{{
          productInfo.productName
        }}</el-descriptions-item>
         <el-descriptions-item label="商品简介">{{
          productInfo.productIntro
        }}</el-descriptions-item>
      </el-descriptions>
      </div>
      <div style="display: flex;justify-content:flex-end;">
         <el-button  type="success" round  @click="ClickBuy(0)">{{productInfo.productPrice}}  购买</el-button>
         <el-button  type="primary" round  @click="ClickBuy(1)">加入购物车</el-button>
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
     <!-- 购买件数 -->
    <el-dialog
      title="购买件数"
      :visible.sync="NumDialogVisible"
      width="30%">
    <el-descriptions
        :column="1"
        size="mini"
        border>
        <el-descriptions-item label="总价格" >{{
         this.productTotalPrice
        }}</el-descriptions-item>
         <el-descriptions-item label="件数">{{
         this.productNum
        }}</el-descriptions-item>
      </el-descriptions>
      
          <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="danger" v-show="productNum>0" @click="subNum">-</el-button>
        <el-button type="success" @click="addNum">+</el-button>
        <el-button type="primary" @click="BuyProduct">提交</el-button>
      </span>
    </el-dialog>
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

        shopInfo:"",
        NumDialogVisible:false,
        productNum:1,
        productTotalPrice:0,
        buyOrCart:0,
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
            for(let i=0;i<this.productInfo.images.length;i++){
              this.productInfo.images[i].productImageAddr="http://localhost:8080"+this.productInfo.images[i].productImageAddr;
            }
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
     ClickBuy(buyOrCart){
      this.buyOrCart=buyOrCart;
      this.NumDialogVisible=true;
      this.productTotalPrice=this.productInfo.productPrice*this.productNum;
    },
    async BuyProduct(){
      if(this.buyOrCart){
    apiOrderUserAddCart({productId:this.productInfo.productId,productNum:this.productNum}).then(response =>{
            if (!response.success) return this.$message.error(response.message);
              this.$message({
                showClose: true,
                message: "加入购物车成功",
                type: 'success'
              });
        });
      }
      else{
           apiOrderUserBuy({productId:this.productInfo.productId,productNum:this.productNum}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
           this.$message({
            showClose: true,
            message: "购买成功",
            type: 'success'
          });
     });
      }
       this.productNum=1;
          this.NumDialogVisible=false;
    },
    subNum(){
      this.productNum--;
      this.productTotalPrice=this.productInfo.productPrice*this.productNum;
    },
    addNum(){
    this.productNum++;
    this.productTotalPrice=this.productInfo.productPrice*this.productNum;
    }
    
  },
};
</script>

<style lang="less" scoped>

</style>
