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
      <el-button type="primary" @click="addDialogVisible = true"  v-show="shopInfo.shopIsOpen==0?true:false">注册商店</el-button>
    
       <el-descriptions title="商店信息" direction="vertical" :column="4" border  v-show="shopInfo.shopIsOpen>0?true:false">
  <el-descriptions-item label="商店名">{{shopInfo.shopName}}</el-descriptions-item>
  <el-descriptions-item label="商店简介">{{shopInfo.shopIntro}}</el-descriptions-item>
   <el-descriptions-item label="商店地址">{{shopInfo.shopAddr}}</el-descriptions-item>
  <el-descriptions-item label="注册资金" :span="2">{{shopInfo.shopRegisterFund}}</el-descriptions-item>
  <el-descriptions-item label="注册日期">{{shopInfo.shopRegisterDate}}</el-descriptions-item>
   <el-descriptions-item label="状态">{{shopInfo.shopIsOpenStr}}</el-descriptions-item>
  </el-descriptions>
   
      </el-card>
        
         <el-card id="el-card2">
         <el-row  :gutter="20">
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
       
        <el-table :data="productShopList.object.products.products" border stripe>
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
                  <el-button type="danger" icon="el-icon-close" size='mini' @click="productDelete(scope.row.shopId)"></el-button>
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
      :total="total">
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
        <el-form-item prop="userCard">
          <el-input
            v-model="shopRegisterForm.userCard"
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
        userCard: "",
        shopIntro:"",
        shopAddr:"",
        shopRegisterFund:""
      },
      shopInfo:{

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
        userCard: [
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
        // 后端没部署，暂时注释
        // result=await this.$http.post('/shopRegister',this.shopRegisterForm);
        this.addDialogVisible = false;
        this.getShopInfo();
        //理论上至少为1
        this.shopInfo.shopIsOpen=this.shopInfo.shopIsOpen==0?1:this.shopInfo.shopIsOpen;

         var result = { success: true, message: "提交成功" };
         
        if (!result.success) return this.$message.error(result.message);
         this.$message({
          showClose: true,
          message: result.message,
          type: 'success'
        });
      });
    },
    //获得商店信息
   async getShopInfo(){
      // 判断从token中的逻辑，待补全
      if(false){
         this.shopInfo.shopIsOpen=0;
          // token中有对应信息
        
      }
      else{
       // var result1=await this.$http.get('/myshop/info');
        // var result2=await this.$http.get('/myshop/product/list');
        this.shopInfo={
        shopId: 62,
        shopName: "积斯商龙层",
        shopIntro: "id do fugiat",
        shopAddr: "consequat ullamco ea in",
        shopRegisterFund: 86791089.96111247,
        shopRegisterDate: "1977-08-19 19:38:23",
        shopIsOpen: 1
        };
        this.productShopList= {
            object: {
            products: {
              products: [
                  {
                      productId: 95,
                      productName: "际建斗数",
                      shopId: 58
                  },
                  {
                      productId: 94,
                      productName: "省造记两参",
                      shopId: 68
                  }
              ],
            totalNumber: 31
        }
        }
        };
      }
      switch (this.shopInfo.shopIsOpen){
        case 0:
          this.shopInfo.shopIsOpenStr="未提交";
          break;
        case 1:
          this.shopInfo.shopIsOpenStr="待审核";
          break;
        case 2:
          this.shopInfo.shopIsOpenStr="未通过";
          break;
        case 3:
          this.shopInfo.shopIsOpenStr="开发";
          break;
      }
    },
    handleCurrentChange(newPage){
       this.currentPage=newPage;
      this.getShopInfo();
    },
    async productDelete(){
       //  var result=await this.$http.get('/myshop/product/delete',shopId);
   this.getShopInfo();
    },
    async productAdd(){
          //  var result=await this.$http.post('/myshop/product/add',this.productName);
             this.getShopInfo();
            this.needCommit=true;
             this.$message({
          showClose: true,
          message: "添加成功",
          type: 'success'
        });
    },
    async productCommit(){
       //  var result=await this.$http.get('/myshop/commit');
            this.getShopInfo();
            this.needCommit=false;
              this.$message({
          showClose: true,
          message: "添加提交成功",
          type: 'success'
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
