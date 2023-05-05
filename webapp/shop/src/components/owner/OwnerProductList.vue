
<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的商店</el-breadcrumb-item>
      <el-breadcrumb-item>我的商品列表</el-breadcrumb-item>
       <span class="shop-span">
        <el-button  size="mini" type="primary" @click="addDialogVisible = true">添加商品</el-button>
  </span>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
  <el-row v-for="(o,index_i) in 2" :key="o">
  <el-col :span="4" v-for="(o,index_j) in 5" :key="o" :offset="index_j?1:0">
    <el-card :body-style="{ padding: '0px' }"  style="width:195px;height:300px" v-if="index_i*5+index_j<productList.length">
       <el-image :src="productList[index_i*5+index_j].images[0].productImageAddr" style="width:100%;height:195px;" fit="fill">
         <div slot="error" class="image-slot">
        <i class="el-icon-picture-outline"></i>
      </div>
      </el-image>
       <div >
         <el-descriptions
        :column="1"
        size="mini"
        border>
        <el-descriptions-item label="商品名">{{
         productList[index_i*5+index_j].productName
        }}</el-descriptions-item>
         <el-descriptions-item label="商品简介">{{
          productList[index_i*5+index_j].productIntro
        }}</el-descriptions-item>
      </el-descriptions>
      </div>
         <el-button @click="productInspectInfo(productList[index_i*5+index_j].productId)">详情</el-button>
    </el-card>
  </el-col>
</el-row>
 <!-- 分页区域 -->
 <el-pagination 
       layout="total, prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total">
    </el-pagination>
 <el-dialog
      title="添加商品"
      :visible.sync="addDialogVisible"
      width="50%">
      <el-form
        ref="addProductRef"
        :model="productRegisterForm"
        :rules="productRegisterFormRules"
        label-width="0px"
      > 
      <el-form-item prop="productName">
      <el-input  placeholder="请输入商品名" v-model="productRegisterForm.productName" ></el-input>
      </el-form-item>
      <el-form-item prop="productIntro">
      <el-input  placeholder="请输入商品简介" v-model="productRegisterForm.productIntro" ></el-input>
      </el-form-item>
      <el-form-item prop="productPrice">
      <el-input  placeholder="请输入商品价格" v-model="productRegisterForm.productPrice" ></el-input>
      </el-form-item>
    </el-form>
      <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addProduct">提交</el-button>
        <el-button type="info" @click="resetProductForm">重置</el-button>
        <el-button @click="addDialogVisible = false">取 消</el-button>
      </span>
 </el-dialog>
  </div>
</template>

<script>
import {apiOwnerProductList,apiOwnerProductAdd} from '@/api/api'
export default {
  data() {
    return {
      productList: [],
      currentPage:1,
      pageSize:10,   //一页的数量  应改成10个
      total:0,

      addDialogVisible:false,
      productRegisterForm:{
         productName:"",
         productIntro:"",
         productPrice:"",
      },
      productRegisterFormRules:{
        //暂时没限制字数
        productName:[  { required: true, message: "请输入商品名称", trigger: "blur" }],
        productIntro:[
           { required: true, message: "请输入商品简介", trigger: "blur" },
             {
                min: 1,
                max: 128,
                message: "长度在128个字符以内",
                trigger: "blur",
            }
        ],
        productPrice:[
           { required: true, message: "请输入注册资金", trigger: "blur" },
           { 
           pattern:/-?(?:[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0\.0+|0)/,
            message: `需要是的浮点数`,
            trigger: "blur",
          },
          { 
            validator: (rules, value, callback) => {
              if (value<=0) {
                return callback(new Error("需要是大于0的浮点数"));
              }
              return callback();
            },
            trigger: "blur",
          }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      apiOwnerProductList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.productList = response.object.products;

              for(let i=0;i<this.productList.length;i++){
            if(this.productList[i].images.length){
                this.productList[i].images[0].productImageAddr="http://localhost:8080"+this.productList[i].images[0].productImageAddr;
            }
            else{
              this.productList[i].images[0]={
                productImageAddr:"https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
              }
            }
        }
      });
    },
    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getList();
    },
    async productInspectInfo(productId){
         window.sessionStorage.setItem("OwnerproductId", productId);
         var activePath='/owner/product/Info';
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         this.$router.push(activePath);
    },
     async addProduct(){
          this.$refs.addProductRef.validate(async (valid) => {
          if (!valid) return;
          apiOwnerProductAdd(this.productRegisterForm).then(response =>{
              if(!response.success) return this.$message.error(response.message);
              else{
                this.addDialogVisible = false;
              this.$message({
              showClose: true,
              message: "添加成功",
              type: 'success'
            });
              }
            this.getList();
          });
      });
    },
     //重置表单
    resetProductForm() {
      this.$refs.addProductRef.resetFields();
    }
  },
};
</script>

<style lang="less" scoped>
.el-row {
    margin-bottom: 15px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
.shop-span{

  display: flex;
  justify-content:flex-end;
 
}
</style>