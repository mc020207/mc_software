<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的商店</el-breadcrumb-item>
      <el-breadcrumb-item>我的商店信息</el-breadcrumb-item>
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      shopRegisterForm: {
        shopName: "",
        userCard: "",
        shopIntro: "",
        shopAddr: "",
        shopRegisterFund: "",
      },
      shopInfo: {},
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
        // var result=await this.$http.get('/myshop/info');
      } else {
        window.sessionStorage.removeItem("shopId");
        // var result=await this.$http.get('/inspect/info',shopId);
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
  },
};
</script>

<style lang="less" scoped></style>
