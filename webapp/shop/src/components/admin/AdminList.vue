<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>管理员</el-breadcrumb-item>
      <el-breadcrumb-item>待审核商店信息列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
    <el-card>
      <!-- 商店列表区 -->
      <el-table :data="inspectShopList.object.shops" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="商店名"
          prop="shopName"
          width="150"
        ></el-table-column>
        <el-table-column
          label="简介"
          prop="shopIntro"
          width="400"
        ></el-table-column>
        <el-table-column
          label="地址"
          prop="shopAddr"
          width="250"
        ></el-table-column>
        <el-table-column
          label="注册资金"
          prop="shopRegisterFund"
          width="100"
        ></el-table-column>
        <el-table-column
          label="注册日期"
          prop="shopRegisterDate"
          width="100"
        ></el-table-column>
        <el-table-column label="审批">
            <template slot-scope="scope">
                  <!-- 详情 -->
                   <el-tooltip class="item" effect="dark" content="详细信息" placement="top" :enterable="false">
                  <el-button type="success" icon="el-icon-s-grid" size='mini' @click="shopInspectInfo(scope.row.shopId)"></el-button>
                </el-tooltip>
                          
                <!-- 同意 -->
                  <el-tooltip class="item" effect="dark" content="同意" placement="top" :enterable="false">
                   <el-button type="primary" icon="el-icon-check" size='mini' @click="shopInspectPass(scope.row.shopId)"></el-button>
                </el-tooltip>
                <!-- 拒绝 -->
                  <el-tooltip class="item" effect="dark" content="拒绝" placement="top" :enterable="false">
                  <el-button type="danger" icon="el-icon-close" size='mini' @click="shopInspectReject(scope.row.shopId)"></el-button>
                </el-tooltip>
               
            </template>
             </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination 
       layout="total, prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total">
    </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      inspectShopList: {},
      currentPage:1,
      pageSize:15,     //一页的数量
      total:100
    };
  },
  created() {
    this.getShopList();
  },
  methods: {
    async getShopList() {
    //   var result=await this.$http.get('/user/info',{
    //     page:this.currentPage
    //   });
      var result = { success: true, message: "获取成功" };
      if (!result.success) return this.$message.error(result.message);
      this.inspectShopList = {
        success: true,
        object: {
          shops: [
            {
              shopName: "所新分等性全越",
              userIdCard: "62",
              shopIntro: "officia fugiat in esse",
              shopAddr: "mollit Ut ipsum in officia",
              shopRegisterFund: 24,
              shopRegisterDate: "1989-06-07",
              shopId: 77,
            },
            {
              shopName: "比林合青",
              userIdCard: "72",
              shopIntro: "ad deserunt",
              shopAddr: "commodo amet",
              shopRegisterFund: 47,
              shopRegisterDate: "1989-10-23",
              shopId: 98,
            },
            {
              shopName: "正以温养包",
              userIdCard: "62",
              shopIntro: "Lorem commodo culpa consectetur velit",
              shopAddr: "tempor do velit",
              shopRegisterFund: 4,
              shopRegisterDate: "1980-12-29",
              shopId: 72,
            },
          ],
        },
        message: "维矿二代题往于己无书上界。",
      };
    },
    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getShopList();
    },
    //待处理事件逻辑

    async shopInspectInfo(shopId){
         window.sessionStorage.setItem("shopId", shopId);
         var activePath="/shop/info";
         //这么写有点逆天，不过能跑
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         this.$router.push(activePath);
    },
   async shopInspectPass(shopId){
      // var result=await this.$http.get('/inspect/pass',shopId);
      this.getShopList();
    },
   async shopInspectReject(shopId){
    //  var result=await this.$http.get('/inspect/reject',shopId);
    this.getShopList();
    }
  },
};
</script>

<style lang="less" scoped></style>
