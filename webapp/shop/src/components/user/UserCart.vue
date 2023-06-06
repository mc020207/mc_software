<template>
  <div>
     <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户界面</el-breadcrumb-item>
      <el-breadcrumb-item>我的订单</el-breadcrumb-item>
    </el-breadcrumb>
  <el-card> 
    <el-descriptions :title="options[opValue].label" direction="vertical" :column="3" border>
          <!-- 流水表区 -->
      <template slot="extra"  >
      <el-button type="primary"  style="margin-right: 10px;" @click="orderCartListBuy">下单</el-button>
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
          width="200"
        ></el-table-column>
        <el-table-column
          label="金额"
          prop="orderMoney"
          width="100"
        ></el-table-column>
        <el-table-column
          label="数量"
          prop="productNum"
          width="50"
        ></el-table-column>
         <el-table-column
          label="总金额"
          prop="productTotalPrice"
          width="100"
        ></el-table-column>
        <el-table-column
          label="操作"
        >
         <template slot-scope="scope">
          <div>
           <el-button type="success" icon="el-icon-check" size='mini' v-if="opValue==0&&!scope.row.buyCartOrder" @click="orderCartBuy(scope.row.orderId)">购买</el-button>
            <el-button type="primary" icon="el-icon-s-grid" size='mini' @click="orderCartInfo(scope.row.productId)">详细信息</el-button>
            <el-button type="danger" icon="el-icon-close" size='mini' v-if="opValue==0&&!scope.row.buyCartOrder" @click="orderCartDelete(scope.row.orderId)">删除</el-button>
            <el-button type="danger" size='mini' v-if="opValue==0&&!scope.row.buyCartOrder"  @click="subNum(scope.row.orderId)">-</el-button>
            <el-button type="success" size='mini' v-if="opValue==0&&!scope.row.buyCartOrder" @click="addNum(scope.row.orderId)">+</el-button>
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
import {apiOrderUserCartBuy,apiOrderUserCartEdit,apiOrderUserCartList,apiOrderUserReceiveList,apiOrderUserWaitList} from '@/api/api'

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
          label: '购物车商品列表'
        },
         {
          value:1,
          label: '已购买待收货商品列表'
        },
        {
          value:2,
          label: '已收货商品列表'
        },
        {
          value:3,
          label: '未发货商品列表'
        }
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
          apiOrderUserCartList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.orderList = response.object.orders;
        for(let i=0;i<this.orderList.length;i++){
        this.orderList[i].productTotalPrice=this.orderList[i].orderMoney*this.orderList[i].productNum;
        this.orderList[i].buyCartOrder=0;
      }
      })
         break;
      }
       case 1:{
           apiOrderUserWaitList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.orderList = response.object.orders;
        for(let i=0;i<this.orderList.length;i++){
        this.orderList[i].productTotalPrice=this.orderList[i].orderMoney*this.orderList[i].productNum;
      }
      })
         break;
      }
       case 2:{
           apiOrderUserReceiveList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.orderList = response.object.orders;
        for(let i=0;i<this.orderList.length;i++){
        this.orderList[i].productTotalPrice=this.orderList[i].orderMoney*this.orderList[i].productNum;
      }
      })
         break;
      }
      case 3:{
           apiOrderUserReceiveList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.orderList = response.object.orders;
        for(let i=0;i<this.orderList.length;i++){
        this.orderList[i].productTotalPrice=this.orderList[i].orderMoney*this.orderList[i].productNum;
      }
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
  async  orderCartBuy(orderId){
      var i=this.find_index(orderId);
      this.orderList[i].buyCartOrder=1;
      this.$forceUpdate();
    },
     async  orderCartListBuy(){
      var orderIds=[];
       for(let i=0;i<this.orderList.length;i++){
        if(this.orderList[i].buyCartOrder){
            orderIds.push(this.orderList[i].orderId);
        }
      }
      console.log(orderIds);
      var str='';
      for(let i=0;i<orderIds.length;i++){
        if(i==0){
          str+=orderIds[i];
        }
        else{
          str+=" "+orderIds[i];
        }
      }
      apiOrderUserCartBuy({orderIds:str}).then(response =>{
        if(!response.success) return this.$message.error(response.message);
         this.$message({
          showClose: true,
          message: '下单成功',
          type: 'success'
        });
        this.total = response.object.totalNumber;
        this.orderList = response.object.orders;
    });
    },
  async  orderCartInfo(productId){
         window.sessionStorage.setItem("productId", productId);
         var activePath='/visit/product/info';
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         window.sessionStorage.removeItem("ShopProductList_shopId");
         this.$router.push(activePath);
    },
   async orderCartDelete(orderId){
       apiOrderUserCartEdit({orderId:orderId,productNum:0}).then(response =>{
        if(!response.success) return this.$message.error(response.message);
          this.$message({
          showClose: true,
          message: '删除成功',
           type: 'warning'
        });
        this.getOrderList();
    });
    },
    find_index(orderId){
      for(let i=0;i<this.orderList.length;i++){
          if(this.orderList[i].orderId==orderId){
            return i;
          }
      }
    },
   async subNum(orderId){
      var i=this.find_index(orderId);
      this.orderList[i].productNum--;
     this.orderList[i].productTotalPrice=this.orderList[i].orderMoney*this.orderList[i].productNum;
     if(this.orderList[i].productNum==0){
       await this.orderCartDelete(orderId);
     }
     else{
       apiOrderUserCartEdit({orderId:orderId,productNum:this.orderList[i].productNum}).then(response =>{
        if(!response.success) return this.$message.error(response.message);
          this.$message({
          showClose: true,
          message: '修改成功',
           type: 'success'
        });
        this.getOrderList();
    })
     }
    },
   async addNum(orderId){
     var i=this.find_index(orderId);
      this.orderList[i].productNum++;
     this.orderList[i].productTotalPrice=this.orderList[i].orderMoney*this.orderList[i].productNum;
       apiOrderUserCartEdit({orderId:orderId,productNum:this.orderList[i].productNum}).then(response =>{
        if(!response.success) return this.$message.error(response.message);
          this.$message({
          showClose: true,
          message: '修改成功',
           type: 'success'
        });
        this.getOrderList();
    })
      
    }
  },
};
</script>