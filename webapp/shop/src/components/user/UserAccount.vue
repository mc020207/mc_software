<template>
 <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户界面</el-breadcrumb-item>
      <el-breadcrumb-item>账户流水</el-breadcrumb-item>
    </el-breadcrumb>

     <!-- 面包屑卡片视图 -->
    <el-card> 
      <el-descriptions title="账户信息" direction="vertical" :column="3" border>
        <template slot="extra">
      <el-button type="primary" @click="rechargeDialogVisible = true" >充值</el-button>
    </template>
     <template slot="extra">
      <el-button type="primary" @click="withdrawDialogVisible = true" >提现</el-button>
    </template>
  <el-descriptions-item label="账户类型">{{accountInfo.accountTypeStr}}</el-descriptions-item>
  <el-descriptions-item label="余额">{{accountInfo.accountMoney}}</el-descriptions-item>
   <el-descriptions-item label="状态">{{accountInfo.accountStateStr}}</el-descriptions-item>
</el-descriptions>
    </el-card>
</div>
</template>

<script>
import {apiAccountInfo,apiAccountRecharge,apiAccountWithdraw} from '@/api/api'
export default {
  data() {
    return {
      accountType:0,
      accountInfo: {
        accountId:0,
        accountType:0,
        accountMoney:0,
        accountState:0,
        accountTypeStr:'',
        accountStateStr:''
      },
      flowInfo:{
          accountType:0,
          money:0
      },
      rechargeDialogVisible:false,
      withdrawDialogVisible:false,
    };
  },
  created() {
    this.getAccountInfo();
  },
  methods: {
    async getAccountInfo() {
      apiAccountInfo(this.accountType).then(response =>{
        // if (!response.success){
        //   this.$router.push('/home');
        //   return this.$message.error(response.message);
        // }
        // this.accountInfo = response.object;
        if(this.accountType==0){
          this.accountInfo.accountTypeStr="用户账户";
        }
        else{
              this.$message.error("非法访问");
              this.$router.push("/home");
        }
        if(this.accountInfo.accountState==1){
          this.accountInfo.accountStateStr="有效";
        }
        else{
           this.accountInfo.accountStateStr="删除";
        }
      })
    },
    recharge(){
      apiAccountRecharge(this.flowInfo).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getAccountInfo();
      })
    },
    withdraw(){
      apiAccountWithdraw(this.flowInfo).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getAccountInfo();
      })
    },
  },
};
</script>