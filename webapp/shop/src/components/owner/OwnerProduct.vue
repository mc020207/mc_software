<template>
    <div>
      <!-- 导航区 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>我的商店</el-breadcrumb-item>
          <el-breadcrumb-item>商品详细信息</el-breadcrumb-item>
        </el-breadcrumb>
      <template>
        <el-carousel :interval="4000" type="card"  v-loading="loading" height="400px" >
          <el-carousel-item v-for="item in productInfo.images" :key="item.productImageId">
              <el-image :src="item.productImageAddr" style="width:100%;height:100%" fit="fill">
            <div slot="error" class="image-slot">
            <i class="el-icon-picture-outline"></i>
          </div>
      </el-image>
          </el-carousel-item>
        </el-carousel>
      </template>
    <el-card v-loading="loading">
      <el-descriptions
        title="商品信息"
        :column="3"
        border>
        <el-descriptions-item label="商品名">{{
          productInfo.productName
        }}</el-descriptions-item>
        <el-descriptions-item label="价格" :span="2">{{
          productInfo.productPrice
        }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{
          productInfo.productStateStr
        }}</el-descriptions-item>
         <el-descriptions-item label="商品简介">{{
          productInfo.productIntro
        }}</el-descriptions-item>
      </el-descriptions>
      <div style="display: flex;justify-content:flex-end;">
        <el-button  type="primary" round  @click="editDialogVisible=true">修改文字信息</el-button>
         <el-button  type="danger" round  @click="deleteProduct">删除</el-button>
        </div>
    </el-card>

     <el-dialog
      title="修改商品信息"
      :visible.sync="editDialogVisible"
      width="50%">
      <el-form
        ref="editProductRef"
        :model="editForm"
        :rules="editFormRules"
        label-width="0px"
      > 
      <el-form-item prop="productName">
      <el-input  placeholder="请输入商品名" v-model="editForm.productName" ></el-input>
      </el-form-item>
      <el-form-item prop="productIntro">
      <el-input  placeholder="请输入商品简介" v-model="editForm.productIntro" ></el-input>
      </el-form-item>
      <el-form-item prop="productPrice">
      <el-input  placeholder="请输入商品价格" v-model.number="editForm.productPrice" ></el-input>
      </el-form-item>
    </el-form>
      <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editProduct">提交</el-button>
        <el-button type="info" @click="resetProductForm">重置</el-button>
        <el-button @click="editDialogVisible = false">取 消</el-button>
      </span>
 </el-dialog>
  </div>
</template>
<script>
import {apiOwnerProductInfo,apiOwnerProductDelete,apiOwnerProductInfoEdit,apiOwnerProductImageAdd,apiOwnerProductImageDelete} from '@/api/api'
export default {
  data() {
    return {
        productInfo:{
            /*
            productId:
            productName:
            productIntro:
            productPrice:
            productState:
            shopId:
            images:[]
            */
        },
        loading:true,
        editDialogVisible:false,
        editForm:{
          productId:"",
            productName:"",
            productIntro:"",
            productPrice:""
        },
        editFormRules:{
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
           type:'number',
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
        },
        imageInfo:{
            //不知道用哪种方式传图
            url:"",
            stringfy:"",
        }
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    async getInfo() {
      var pid = window.sessionStorage.getItem("OwnerproductId");
      if (pid == null) {
        // 非法访问
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
          apiOwnerProductInfo({productId:pid}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.productInfo = response.object;
            this.productInfo.images=[
                {
                  productImageId: 0,
                  productImageAddr: "https://i.postimg.cc/Bbg0RNLz/70448487-p0.png"
                },
                 {
                  productImageId: 1,
                  productImageAddr: "https://i.postimg.cc/pLyH1f20/70467532-p0.png"
                },
                 {
                  productImageId: 2,
                  productImageAddr: "https://i.postimg.cc/5tCMffQL/70987206-p0.jpg"
                }
              ];
            this.loading=false;
            switch (this.productInfo.productState){
              case 0:{
                this.productInfo.productStateStr="审核待通过";
                  break;
              };
              case 1:{
                this.productInfo.productStateStr="审核未通过";
                  break;
              };
              case 2:{
                this.productInfo.productStateStr="已上架";
                  break;
              };
              case 3:{
                this.productInfo.productStateStr="已下架";
                  break;
              };
              case 4:{
                this.productInfo.productStateStr="已删除";
                  break;
              };
            }
          });
    },
    deleteProduct(){
        apiOwnerProductDelete({productId:this.productInfo.productId}).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.$message({
              showClose: true,
              message: "删除已申请",
              type: 'success'
            });
            var activePath='/owner/product/list';
            this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
            this.$router.push(activePath);
            window.sessionStorage.removeItem("OwnerproductId");
        });
    },
    editProduct(){
      this.$refs.editProductRef.validate(async (valid) => {
          if (!valid) return;
          this.editForm.productId=this.productInfo.productId;
        apiOwnerProductInfoEdit(this.editForm).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.$message({
              showClose: true,
              message: "提交申请成功",
              type: 'success'
            });
            this.editDialogVisible=false;
             this.getInfo();
        });
        });
    },
    addImage(){
        apiOwnerProductImageAdd({productId:this.productInfo.productId,image:this.imageInfo.stringfy}).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.getInfo();
        });
    },
    deleteImage(iid){
        apiOwnerProductImageDelete({productImgId:iid}).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.getInfo();
        });
    },
      //重置表单
    resetProductForm() {
      this.$refs.editProductRef.resetFields();
    }
  },
};
</script>

<style lang="less" scoped></style>
