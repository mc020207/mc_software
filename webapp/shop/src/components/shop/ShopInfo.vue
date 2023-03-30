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
        :column="3"
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
        <el-descriptions-item label="状态">{{
          shopInfo.shopIsOpenStr
        }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
      <el-table :data="productShopList.object.products.products" border stripe>
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
export default {
  data() {
    return {
       currentPage:1,
      pageSize:5,     //一页的数量
      total:100,
      shopRegisterForm: {
        shopName: "",
        userCard: "",
        shopIntro: "",
        shopAddr: "",
        shopRegisterFund: "",
      },
      shopInfo: {},
      productShopList:{}
    };
  },
  created() {
    this.getShopInfo();
    
  },
  methods: {
    async getShopInfo() {
      var shopId = window.sessionStorage.getItem("shopId");
      if (shopId == null) {
        // token中有对应信息
        // var result1=await this.$http.get('/myshop/info');
        // var result2=await this.$http.get('/myshop/product/list');
      } else {
        window.sessionStorage.removeItem("shopId");
        // var result=await this.$http.get('/inspect/info',shopId);
        // var result2=await this.$http.get('/inspect/product/list',shopId);
      }      
        this.shopInfo = {
          shopId: 62,
          shopName: "积斯商龙层",
          shopIntro: "id do fugiat",
          shopAddr: "consequat ullamco ea in",
          shopRegisterFund: 86791089.96111247,
          shopRegisterDate: "1977-08-19 19:38:23",
          shopIsOpen: 1,
        };
        this.productShopList= {
            object: {
            products: {
              products: [
                  {
                      productId: 95,
                      productName: "际建斗数",
                      shopId: 58
                  },
                  {
                      productId: 94,
                      productName: "省造记两参",
                      shopId: 68
                  }
              ],
            totalNumber: 31
        }
        }
        };
      switch (this.shopInfo.shopIsOpen) {
        case 0:
          this.shopInfo.shopIsOpenStr = "未提交";
          break;
        case 1:
          this.shopInfo.shopIsOpenStr = "待审核";
          break;
        case 2:
          this.shopInfo.shopIsOpenStr = "未通过";
          break;
        case 3:
          this.shopInfo.shopIsOpenStr = "开发";
          break;
      }
    },
    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getShopList();
    },
  },
};
</script>

<style lang="less" scoped></style>
