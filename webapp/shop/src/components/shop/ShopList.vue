<template>
  <div>
    <!-- 功能就是    AdminList.vue的mini版，但为了不写过多的if esle，直接分成了两个界面 -->
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商店界面</el-breadcrumb-item>
      <el-breadcrumb-item>已开放商店列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
    <el-card>
      <!-- 商店列表区 -->
      <el-table :data="inspectShopList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="商店名"
          prop="shopName"
          width="150"
        ></el-table-column>
        <el-table-column
          label="简介"
          prop="shopIntro"
          width="450"
        ></el-table-column>
        <el-table-column
          label="地址"
          prop="shopAddr"
          width="300"
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
        <el-table-column label="详细信息">
            <template slot-scope="scope">
                  <!-- 详情 -->
                   <el-tooltip class="item" effect="dark" content="详细信息" placement="top" :enterable="false">
                  <el-button type="success" icon="el-icon-s-grid" size='mini' @click="shopInspectInfo(scope.row.shopId)"></el-button>
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
import {apiShopList} from '@/api/api'
export default {
  data() {
    return {
      inspectShopList: {},
      currentPage:1,
      pageSize:15,   //一页的数量
      total:100
    };
  },
  created() {
    this.getShopList();
  },
  methods: {
    async getShopList() {
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!= "0" && t!="1" && t!="2"){
        
        this.$router.push("/login");
        return this.$message.error("非法访问");
      }
    //   var result=await this.$http.get('/shop/list',{
    //     page:this.currentPage
    //   });
      apiShopList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.inspectShopList = response.object.shops;
      });
    },
    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getShopList();
    },
    //待处理事件逻辑

    async shopInspectInfo(shopId){
         window.sessionStorage.setItem("shopId", shopId);
         window.sessionStorage.setItem("normalShopInfo", true);
         var activePath="/shop/info";
         //这么写有点逆天，不过能跑
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         this.$router.push(activePath);
    },
  },
};
</script>

<style lang="less" scoped></style>
