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
      <!-- 未注册或被拒绝时才有 -->
      <el-button type="primary" @click="addDialogVisible = true"  v-if="shopInfo.shopState==-1||shopInfo.shopState==1">{{shopStateRegister}}</el-button>
    
       <el-descriptions title="商店信息" direction="vertical" :column="4" border  v-if="shopInfo.shopState>-1">
         <template slot="extra">
      <el-button type="primary" @click="OwnerShopDelete" >删除商店</el-button>
    </template>
  <el-descriptions-item label="商店名">{{shopInfo.shopName}}</el-descriptions-item>
  <el-descriptions-item label="商店简介">{{shopInfo.shopIntro}}</el-descriptions-item>
   <el-descriptions-item label="商店地址">{{shopInfo.shopAddr}}</el-descriptions-item>
  <el-descriptions-item label="注册资金" :span="2">{{shopInfo.shopRegisterFund}}</el-descriptions-item>
  <el-descriptions-item label="注册日期">{{shopInfo.shopRegisterDate}}</el-descriptions-item>
   <el-descriptions-item label="状态">{{shopInfo.shopStateStr}}</el-descriptions-item>
  </el-descriptions>
      </el-card>
  
    <el-dialog
      :title="shopStateRegister"
      :visible.sync="addDialogVisible"
      width="50%">
      <el-form
        ref="shopRegisterFormRef"
        :model="shopRegisterForm"
        :rules="shopRegisterFormRules"
        label-width="0px"
        class="shopRegister_form"
      >
        
        <!-- 店名 -->
        <el-form-item prop="shopName">
          <el-input
            v-model="shopRegisterForm.shopName"
            prefix-icon="el-icon-shangdian"
          ></el-input>
        </el-form-item>
        <!-- 商店简介 -->
        <el-form-item prop="shopIntro">
          <el-input
            v-model="shopRegisterForm.shopIntro"
              prefix-icon="el-icon-jianjie"
          ></el-input>
        </el-form-item>
       
        <!-- 备案地址 -->
         <el-form-item prop="shopAddr">
          <el-input
            v-model="shopRegisterForm.shopAddr"
             prefix-icon="el-icon-location"
          ></el-input>
        </el-form-item>
        <!-- 注册资金 -->
        <el-form-item prop="shopRegisterFund">
          <el-input
            v-model="shopRegisterForm.shopRegisterFund"
            prefix-icon="el-icon-zijin"
          ></el-input>
        </el-form-item>
      </el-form>
          <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="shopRegister">提交</el-button>
        <el-button type="info" @click="resetshopRegisterForm">重置</el-button>
        <el-button @click="addDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
   
  </div>
</template>

<script>
import {apiOwnerShopRegister,apiOwnerShopInfo,apiOwnerShopDelete,apiOwnerProductList} from '@/api/api'
export default {
  data() {
    return {
      addDialogVisible: false,
      needCommit:false,
      currentPage:1,
      pageSize:9,     //一页的数量
      total:100,

      shopRegisterForm: {
        shopName: "",
        shopIntro:"",
        shopAddr:"",
        shopRegisterFund:""
      },
      shopInfo:{
        shopState: -1
      },
      shopStateRegister:"",
      //这是登录表单的验证规则对象
      shopRegisterFormRules: {
        shopName: [
          { required: true, message: "请输入商店名称", trigger: "blur" },
          {
            min: 1,
            max: 12,
            message: "长度在12个字符以内",
            trigger: "blur",
          }
        ],
        shopIntro:[
             { required: true, message: "请输入商店简介", trigger: "blur" },
             {
                min: 1,
                max: 128,
                message: "长度在128个字符以内",
                trigger: "blur",
            }
        ],
        shopAddr: [
          { required: true, message: "请输入备案地址", trigger: "blur" },
          {
             min: 1,
                max: 32,
                message: "长度在32个字符以内",
                trigger: "blur",
          }
        ],
        shopRegisterFund: [
          { required: true, message: "请输入注册资金", trigger: "blur" },
           { 
           pattern:/-?(?:[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0\.0+|0)/,
            message: `需要是浮点数`,
            trigger: "blur",
          },
          { 
            validator: (rules, value, callback) => {
              if (value<=1000) {
                return callback(new Error("需要是大于1000的浮点数"));
              }
              return callback();
            },
            trigger: "blur",
          },
        ],
      },
    };
  },
  created(){
    this.getShopInfo();
  },
  methods: {
    //重置表单
    resetshopRegisterForm() {
      this.$refs.shopRegisterFormRef.resetFields();
    },
    //提交表单
    shopRegister() {
      this.$refs.shopRegisterFormRef.validate(async (valid) => {
        if (!valid) return;
        this.addDialogVisible = false;
        this.getShopInfo();
        apiOwnerShopRegister(this.shopRegisterForm).then(response =>{
          if (!response.success) return this.$message.error(response.message);
           this.$message({
              showClose: true,
              message: "提交申请成功",
              type: 'success'
            });
          this.getShopInfo();
        })
      });
    },
    //获得商店信息
   async getShopInfo(){
      
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="1"){
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }

      apiOwnerShopInfo().then(response => {
        if(!response.success){
          this.shopInfo.shopState = -1;
          this.shopStateRegister="注册商店";
          window.sessionStorage.setItem("shopIsOpen","false");
        }
        else{
          this.shopInfo = response.object;
          switch(this.shopInfo.shopState){
            case 0:this.shopInfo.shopStateStr = "审核待通过";break;
            case 1:this.shopInfo.shopStateStr = "审核未通过";this.shopStateRegister="修改商店信息";break;
            case 2:this.shopInfo.shopStateStr = "商店开放";break;
            case 3:this.shopInfo.shopStateStr = "删除待通过";break;
            case 4:this.shopInfo.shopStateStr = "商店已删除";break;
          }
          if(this.shopInfo.shopState == 2 && window.sessionStorage.getItem("shopIsOpen") != "true"){
            window.sessionStorage.setItem("shopIsOpen","true");
            location.reload();
          }
          else if(this.shopInfo.shopState != 2 && window.sessionStorage.getItem("shopIsOpen") == "true"){
            window.sessionStorage.setItem("shopIsOpen","false");
            location.reload();
          }
          apiOwnerProductList({page:this.currentPage}).then(response =>{
            if(!response.success) return this.$message.error(response.message);
            this.productShopList = response.object.products;
            this.total = response.object.totalNumber;
          })
        }
        
      })
      
    },
    handleCurrentChange(newPage){
      this.currentPage=newPage;
      apiOwnerShopInfo({page:this.currentPage}).then(response =>{
            if(!response.success) return this.$message.error(response.message);
            this.productShopList = response.object.products;
            this.total = response.object.totalNumber;
          })
    },
    
    async OwnerShopDelete(){
      apiOwnerShopDelete().then(response =>{
        if(!response.success) return this.$message.error(response.message);
        this.$message({
              showClose: true,
              message: "删除已申请",
              type: 'success'
            });
        this.getShopInfo();
      })
    },

  },
};
</script>

<style lang="less" scoped>
#el-card2 {
    margin-top: 15px ;
}
</style>
