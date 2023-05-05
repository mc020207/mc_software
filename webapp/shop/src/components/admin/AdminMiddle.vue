<template>
 <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>管理员</el-breadcrumb-item>
      <el-breadcrumb-item>中间账户流水</el-breadcrumb-item>
    </el-breadcrumb>

     <!-- 面包屑卡片视图 -->
    <el-card> 
      <el-descriptions title="账户信息" direction="vertical" :column="3" border>
          <template slot="extra"  >
      <el-select v-model="opValue" style="margin-right: 10px;" placeholder="请选择" @change="getFlowList">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </template>
  <el-descriptions-item label="账户类型">{{accountInfo.accountTypeStr}}</el-descriptions-item>
  <el-descriptions-item label="余额">{{accountInfo.accountMoney}}</el-descriptions-item>
   <el-descriptions-item label="状态">{{accountInfo.accountStateStr}}</el-descriptions-item>
</el-descriptions>
    </el-card>

     <el-card>
     <el-table :data="flowList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="流水理由"
          prop="flowStr"
          width="200"
        ></el-table-column>
        <el-table-column
          label="资金"
          prop="flowMoney"
          width="150"
        ></el-table-column>
        <el-table-column
          label="转账日期"
          prop="flowDate"
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
    </el-card>
</div>
</template>

<script>
import {apiAccountInfo} from '@/api/api'
import {apiFlowToList,apiFlowFromList,apiFlowAllList} from '@/api/api'
export default {
  data() {
    return {
      AccountType:{
        accountType:3
      },
      accountInfo: {
        accountId:0,
        accountType:0,
        accountMoney:0,
        accountState:0,
        accountTypeStr:'',
        accountStateStr:''
      },
      //flow
      flowList: [],
      currentPage:1,
      pageSize:9,   //一页的数量
      total:0,
      
      opValue:3,
      options:[
        {
          value:1,
          label: '出账'
        },
         {
          value:2,
          label: '入账'
        },
        {
          value:3,
          label: '出入账'
        },
      ],
    };
  },
  created() {
    this.getAccountInfo().then(this.getFlowList());
  },
  methods: {
    async getAccountInfo() {
      apiAccountInfo(this.AccountType).then(response =>{
        if (!response.success){
          this.$router.push('/home');
          return this.$message.error(response.message);
        }
        this.accountInfo = response.object;
        //debug
        if(this.accountInfo==null){
          this.$message.error('空值错误')
        }
        if(this.accountInfo.accountType==3){
          this.accountInfo.accountTypeStr="中间账户";
        }
        else{
              this.$message.error("非法访问");
              this.$router.push("/home");
        }
        if(this.accountInfo.accountState==0){
          this.accountInfo.accountStateStr="有效";
        }
        else{
           this.accountInfo.accountStateStr="删除";
        }
      })
    },
    //flow
    async getFlowList() {
    switch (this.opValue){
      case 1:{
        apiFlowFromList({accountType:3,page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.flowList = response.object.flows;

         for(let i=0;i<this.flowList.length;i++){
            switch(this.flowList[i].accountTypeTo){
                case 4: this.flowList[i].flowStr = "返还开店资金";break;
                case 1: this.flowList[i].flowStr = "移交用户支付费用";break;
                case 2: this.flowList[i].flowStr = "移交开店资金";break;
                default:this.flowList[i].flowStr = "出账";break;
              }
              this.flowList[i].flowMoney=-this.flowList[i].flowMoney;
      }
      })
        break;
      }
      case 2:{
        apiFlowToList({accountType:3,page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.flowList = response.object.flows;

         for(let i=0;i<this.flowList.length;i++){
            switch(this.flowList[i].accountTypeFrom){
                case 0: this.flowList[i].flowStr = "暂存支付费用";break;
                case 4: this.flowList[i].flowStr = "暂存开店资金";break;
                default:this.flowList[i].flowStr = "入账";break;
              }
      }
      })
        break;
      }
      case 3:{
        apiFlowAllList({accountType:3,page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.flowList = response.object.flows;

         for(let i=0;i<this.flowList.length;i++){
            if(this.flowList[i].accountIdFrom==this.accountInfo.accountId){
              switch(this.flowList[i].accountTypeTo){
                case 4: this.flowList[i].flowStr = "返还开店资金";break;
                case 1: this.flowList[i].flowStr = "移交用户支付费用";break;
                case 2: this.flowList[i].flowStr = "移交开店资金";break;
                default:this.flowList[i].flowStr = "出账";break;
              }
              this.flowList[i].flowMoney=-this.flowList[i].flowMoney;
            }
            else{
              switch(this.flowList[i].accountTypeFrom){
                case 0: this.flowList[i].flowStr = "暂存支付费用";break;
                case 4: this.flowList[i].flowStr = "暂存开店资金";break;
                default:this.flowList[i].flowStr = "入账";break;
              }
            }
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
       this.getFlowList();
    },
  },
};
</script>