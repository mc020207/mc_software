<template>
  <div>
     <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的商店</el-breadcrumb-item>
      <el-breadcrumb-item>商店订单</el-breadcrumb-item>
    </el-breadcrumb>
  <el-card> 
    <el-descriptions :title="options[opValue].label" direction="vertical" :column="3" border>
          <!-- 流水表区 -->
      <template slot="extra"  >
      <el-select v-model="opValue" style="margin-right: 10px;" placeholder="请选择" @change="getOrderList">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </template>
      </el-descriptions>
       <el-table :data="orderList" border stripe :row-style="{height: '49px'}" :cell-style="{padding: '0'}" >
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="商品名"
          prop="productName"
          width="200"
        ></el-table-column>
        <el-table-column
          label="日期"
          prop="orderDate"
          width="250"
        ></el-table-column>
        <el-table-column
          label="金额"
          prop="orderMoney"
          width="250"
        ></el-table-column>
        <el-table-column
          label="操作"
        >
         <template slot-scope="scope">
          <div>
           <el-button type="success" icon="el-icon-check" size='mini' v-if="opValue==0" @click="OrderOwnerSend(scope.row.orderId)">发货</el-button>
            <el-button type="primary" icon="el-icon-s-grid" size='mini' @click="orderProductInfo(scope.row.productId)">详细信息</el-button>
            </div>
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
import {apiOrderOwnerSendList,apiOrderOwnerSend,apiOrderOwnerFinishList} from '@/api/api'
export default {
  data() {
    return {
      orderList: [],
      currentPage:1,
      pageSize:9,   //一页的数量
      total:0,

      opValue:0,
      options:[
        {
          value:0,
          label: '待发货订单列表'
        },
         {
          value:1,
          label: '已发货商品列表'
        },
      ],
    };
  },
  created() {
    this.getOrderList();
  },
  methods: {
    async getOrderList() {
       switch (this.opValue){
      case 0:{
          apiOrderOwnerSendList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.orderList = response.object.orders;
      })
         break;
      }
       case 1:{
           apiOrderOwnerFinishList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.orderList = response.object.orders;
      })
         break;
      }
      default:
          this.$message.error("非法访问");
          this.$router.push("/login");
      }
    
    },
      //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getOrderList();
    },
  async  OrderOwnerSend(orderId){
      apiOrderOwnerSend({orderId:orderId}).then(response =>{
        if(!response.success) return this.$message.error(response.message);
         this.$message({
          showClose: true,
          message: '发货成功',
          type: 'success'
        });
        this.getOrderList();
    });
    },
  async  orderProductInfo(productId){
         window.sessionStorage.setItem("productId", productId);
         var activePath='/visit/product/info';
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         window.sessionStorage.removeItem("ShopProductList_shopId");
         this.$router.push(activePath);
    },
  },
};
</script>