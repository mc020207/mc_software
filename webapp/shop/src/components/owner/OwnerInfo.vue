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
      <!-- 未注册时才有 -->
      <el-button type="primary" @click="addDialogVisible = true"  v-show="shopInfo.shopIsOpen > -1 && shopInfo.shopIsOpen != 2 ?false:true">注册商店</el-button>
    
       <el-descriptions title="商店信息" direction="vertical" :column="4" border  v-show="shopInfo.shopIsOpen>-1?true:false">
  <el-descriptions-item label="商店名">{{shopInfo.shopName}}</el-descriptions-item>
  <el-descriptions-item label="商店简介">{{shopInfo.shopIntro}}</el-descriptions-item>
   <el-descriptions-item label="商店地址">{{shopInfo.shopAddr}}</el-descriptions-item>
  <el-descriptions-item label="注册资金" :span="2">{{shopInfo.shopRegisterFund}}</el-descriptions-item>
  <el-descriptions-item label="注册日期">{{shopInfo.shopRegisterDate}}</el-descriptions-item>
   <el-descriptions-item label="状态">{{shopInfo.shopIsOpenStr}}</el-descriptions-item>
  </el-descriptions>
   
      </el-card>
        
         <el-card id="el-card2" v-show="shopInfo.shopIsOpen > -1">
         <el-row  :gutter="20" v-show="shopInfo.shopIsOpen != 2">
           <el-col :span="7">
             <el-input  placeholder="请输入商品名" v-model="productName"></el-input>
             </el-col>
              <!-- 添加商品按钮 -->
            <el-col :span="2.5">
               <el-button type="primary" @click="productAdd"  >添加商品</el-button>
            </el-col>
            <el-col :span="2.5">
              <el-button type="success"  icon="el-icon-s-promotion" @click="productCommit" >提交修改</el-button>
            </el-col>
         </el-row>
       
        <el-table :data="productShopList" border stripe>
        <el-table-column type="index"></el-table-column>
         <el-table-column
          label="商品id"
          prop="productId"
          width="500"
        ></el-table-column>
        <el-table-column
          label="商品名"
          prop="productName"
          width="500"
        ></el-table-column>
        <el-table-column label="审批">
            <template slot-scope="scope">
                <!-- 删除 -->
                  <el-tooltip class="item" effect="dark" content="删除" placement="top" :enterable="false">
                  <el-button type="danger" icon="el-icon-close" size='mini' @click="productDelete(scope.row.productId)"></el-button>
                </el-tooltip>
               
            </template>
             </el-table-column>
      </el-table>
      </el-card>
       <!-- 分页区域 -->
      <el-pagination 
       layout="total, prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      v-show="shopInfo.shopIsOpen > -1">
    </el-pagination>

    <el-dialog
      title="注册商店"
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
         <!-- 身份证号 -->
        <el-form-item prop="userIdCard">
          <el-input
            v-model="shopRegisterForm.userIdCard"
            prefix-icon="el-icon-shenfenzheng"
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
            v-model.number="shopRegisterForm.shopRegisterFund"
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
import {apiMyshopReg,apiMyshopInfo,apiMyshopProducts,apiMyshopAdd,apiMyshopDelete,apiMyshopCommit} from '@/api/api'
export default {
  data() {
    return {
      addDialogVisible: false,
      needCommit:false,
      currentPage:1,
      pageSize:5,     //一页的数量
      total:100,
      //添加的商品名
      productName:"",
      shopRegisterForm: {
        shopName: "",
        userIdCard: "",
        shopIntro:"",
        shopAddr:"",
        shopRegisterFund:""
      },
      shopInfo:{
        shopIsOpen: -1
      },
     productShopList:{},
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
        userIdCard: [
          { required: true, message: "请输入身份证号", trigger: "blur" },
          {
            min: 18,
            max: 18,
            message: "长度为18字符",
            trigger: "blur",
          },
        ],
        shopIntro:[
             { required: true, message: "请输入商品简介", trigger: "blur" },
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
          { //这里1000暂时没办法处理
           type:'number',
            min: 1000 , 
            message: `需要是大于1000的浮点数`,
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
        apiMyshopReg(this.shopRegisterForm).then(response =>{
          if (!response.success) return this.$message.error(response.message);
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

      apiMyshopInfo().then(response => {
        if(!response.success){
          this.shopInfo.shopIsOpen = -1;
        }
        else{
          this.shopInfo = response.object;
          switch(this.shopInfo.shopIsOpen){
            case 0:this.shopInfo.shopIsOpenStr = "待提交";break;
            case 1:this.shopInfo.shopIsOpenStr = "待审核";break;
            case 2:this.shopInfo.shopIsOpenStr = "驳回";break;
            case 3:this.shopInfo.shopIsOpenStr = "上线";break;
          }
          apiMyshopProducts({page:this.currentPage}).then(response =>{
            if(!response.success) return this.$message.error(response.message);
            this.productShopList = response.object.products;
            this.total = response.object.totalNumber;
          })
        }
        
      })
      
    },
    handleCurrentChange(newPage){
      this.currentPage=newPage;
      apiMyshopProducts({page:this.currentPage}).then(response =>{
            if(!response.success) return this.$message.error(response.message);
            this.productShopList = response.object.products;
            this.total = response.object.totalNumber;
          })
    },
    async productDelete(pid){
      //  var result=await this.$http.get('/myshop/product/delete',shopId);
      apiMyshopDelete({productId:pid}).then(response =>{
        if(!response.success) return this.$message.error(response.message);
        this.$message({
              showClose: true,
              message: "删除成功",
              type: 'success'
            });
        this.getShopInfo();
      })
    },
    async productAdd(){
          //  var result=await this.$http.post('/myshop/product/add',this.productName);
          apiMyshopAdd({productName:this.productName}).then(response =>{
              if(!response.success) return this.$message.error(response.message);
              this.$message({
              showClose: true,
              message: "添加成功",
              type: 'success'
            });
            this.getShopInfo();
          });
            
    },
    async productCommit(){
       //  var result=await this.$http.get('/myshop/commit');
        apiMyshopCommit().then(response =>{
            if(!response.success) return this.$message.error(response.message);
            this.getShopInfo();
          });
    }
  },
};
</script>

<style lang="less" scoped>
#el-card2 {
    margin-top: 15px ;
}
</style>
