<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>管理员</el-breadcrumb-item>
      <el-breadcrumb-item>待审核商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
    <el-card>
      <!-- 商店列表区 -->
      <el-table :data="productList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="商品名"
          prop="productName"
          width="150"
        ></el-table-column>
        <el-table-column
          label="简介"
          prop="productIntro"
          width="400"
        ></el-table-column>
        <el-table-column
          label="商品价格"
          prop="productPrice"
          width="100"
        ></el-table-column>
        <el-table-column label="审批">
            <template slot-scope="scope">
                  <!-- 详情 -->
                   <el-tooltip class="item" effect="dark" content="详细信息" placement="top" :enterable="false">
                  <el-button type="success" icon="el-icon-s-grid" size='mini' @click="productInfo(scope.row.productId)"></el-button>
                </el-tooltip>
                          
                <!-- 同意 -->
                  <el-tooltip class="item" effect="dark" content="同意" placement="top" :enterable="false">
                   <el-button type="primary" icon="el-icon-check" size='mini' @click="productPass(scope.row.productId)"></el-button>
                </el-tooltip>
                <!-- 拒绝 -->
                  <el-tooltip class="item" effect="dark" content="拒绝" placement="top" :enterable="false">
                  <el-button type="danger" icon="el-icon-close" size='mini' @click="productReject(scope.row.productId)"></el-button>
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
import {apiAdminProductList,apiAdminProductPass,apiAdminProductReject} from '@/api/api'
export default {
  data() {
    return {
      productList: [],
      currentPage:1,
      pageSize:9,   //一页的数量
      total:0
    };
  },
  created() {
    this.getList();
    this.getList();
  },
  methods: {
    async getList() {
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="2"){
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
      apiAdminProductList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.productList = response.object.products;
      })
    },
      //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getList();
    },
    async productInfo(productId){
         window.sessionStorage.setItem("admin_productId", productId);
         var activePath="/admin/product/info";
         //这么写有点逆天，不过能跑
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         this.$router.push(activePath);
    },
   async productPass(productId){
      apiAdminProductPass({productId:productId}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
      });
      if(productId == parseInt(window.sessionStorage.getItem("admin_productId"))){
        window.sessionStorage.removeItem("admin_productId");
        location.reload();
      }
      this.getList();
      this.getList();
    },
   async productReject(productId){
      apiAdminProductReject({productId:productId,reason:"no specific reasons"}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
      });
      if(productId == parseInt(window.sessionStorage.getItem("admin_productId"))){
        window.sessionStorage.removeItem("admin_productId");
        location.reload();
      }
      this.getList();
      this.getList();
    },
  },
};
</script>