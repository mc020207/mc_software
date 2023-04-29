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

      <el-dialog
      title="请输入充值金额"
      :visible.sync="rechargeDialogVisible"
      width="30%">
      <el-form
        ref="rechargeFormRef"
        :model="rechargeForm"
        :rules="rechargeFormRules"
        label-width="0px"
      >
          <!-- 金额-->
        <el-form-item prop="money">
          <el-input
            v-model.number="rechargeForm.money"
            prefix-icon="el-icon-zijin"
          ></el-input>
        </el-form-item>
      </el-form>
          <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="recharge">充值</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="请输入提现金额"
      :visible.sync="withdrawDialogVisible"
      width="30%">
      <el-form
        ref="withdrawFormRef"
        :model="withdrawForm"
        :rules="withdrawFormRules"
        label-width="0px"
      >
          <!-- 金额-->
        <el-form-item prop="money">
          <el-input
            v-model.number="withdrawForm.money"
            prefix-icon="el-icon-zijin"
          ></el-input>
        </el-form-item>
      </el-form>
          <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="withdraw">充值</el-button>
      </span>
    </el-dialog>
</div>
</template>

<script>
import {apiAccountInfo,apiAccountRecharge,apiAccountWithdraw} from '@/api/api'
export default {
  data() {
    return {
      AccountType:{
        accountType:0
      },
      accountInfo: {
        accountId:0,
        accountType:0,
        accountMoney:0,
        accountState:0,
        accountTypeStr:'',
        accountStateStr:''
      },
      rechargeDialogVisible:false,
      withdrawDialogVisible:false,
      rechargeForm:{
        accountType:0,
        money:""
      },
      withdrawForm:{
        accountType:0,
        money:""
      },
       rechargeFormRules:{
         money: [
          { required: true, message: "请输入金额", trigger: "blur" },
           { 
           type:'number',
            message: `需要是浮点数`,
            trigger: "blur",
          },
          { 
            validator: (rules, value, callback) => {
              if (value<=0) {
                return callback(new Error("需要是正浮点数"));
              }
              return callback();
            },
            trigger: "blur",
          },
        ]
       },
      withdrawFormRules:{
         money: [
          { required: true, message: "请输入金额", trigger: "blur" },
           { 
           type:'number',
            message: `需要是浮点数`,
            trigger: "blur",
          },
          { 
            validator: (rules, value, callback) => {
              if (value<=0) {
                return callback(new Error("需要是正浮点数"));
              }
              return callback();
            },
            trigger: "blur",
          },
        ]
       }
    };
  },
  created() {
    this.getAccountInfo();
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
        if(this.accountInfo.accountType==0){
          this.accountInfo.accountTypeStr="用户账户";
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
    recharge(){
       this.$refs.rechargeFormRef.validate(async (valid) => {
        if (!valid) return;
      apiAccountRecharge(this.rechargeForm).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getAccountInfo();
        this.rechargeDialogVisible=false;
      })})
    },
    withdraw(){
      this.$refs.withdrawFormRef.validate(async (valid) => {
        if (!valid) return;
      apiAccountWithdraw(this.withdrawForm).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getAccountInfo();
        this.withdrawDialogVisible=false;
      })})
    },
  },
};
</script>