<template>
 <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>管理员</el-breadcrumb-item>
      <el-breadcrumb-item>商城账户流水</el-breadcrumb-item>
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

     <el-card>
     <el-table :data="flowList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="对方账户名"
          prop="accountName"
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
import {apiAccountInfo,apiAccountRecharge,apiAccountWithdraw} from '@/api/api'
import {apiFlowToList,apiFlowFromList,apiFlowAllList} from '@/api/api'
export default {
  data() {
    return {
      AccountType:{
        accountType:2
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
        accountType:2,
        money:""
      },
      withdrawForm:{
        accountType:2,
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
        if(this.accountInfo.accountType==1){
          this.accountInfo.accountTypeStr="商店账户";
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
        this.getAccountInfo().then(this.getFlowList());
        this.rechargeDialogVisible=false;
      })})
    },
    withdraw(){
      this.$refs.withdrawFormRef.validate(async (valid) => {
        if (!valid) return;
      apiAccountWithdraw(this.withdrawForm).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getAccountInfo().then(this.getFlowList());
        this.withdrawDialogVisible=false;
      })})
    },
    //flow
    async getFlowList() {
    switch (this.opValue){
      case 1:{
        apiFlowFromList({accountType:2,page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.flowList = response.object.flows;

         for(let i=0;i<this.flowList.length;i++){
            if(this.flowList[i].accountIdFrom==this.accountInfo.accountId){
              this.flowList[i].accountName=this.flowList[i].nameTo;
              this.flowList[i].flowMoney=-this.flowList[i].flowMoney;
            }
            else{
              this.flowList[i].accountName=this.flowList[i].nameFrom;
            }
      }
      })
        break;
      }
      case 2:{
        apiFlowToList({accountType:2,page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.flowList = response.object.flows;

         for(let i=0;i<this.flowList.length;i++){
            if(this.flowList[i].accountIdFrom==this.accountInfo.accountId){
              this.flowList[i].accountName=this.flowList[i].nameTo;
              this.flowList[i].flowMoney=-this.flowList[i].flowMoney;
            }
            else{
              this.flowList[i].accountName=this.flowList[i].nameFrom;
            }
      }
      })
        break;
      }
      case 3:{
        apiFlowAllList({accountType:2,page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.flowList = response.object.flows;

         for(let i=0;i<this.flowList.length;i++){
            if(this.flowList[i].accountIdFrom==this.accountInfo.accountId){
              this.flowList[i].accountName=this.flowList[i].nameTo;
              this.flowList[i].flowMoney=-this.flowList[i].flowMoney;
            }
            else{
              this.flowList[i].accountName=this.flowList[i].nameFrom;
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