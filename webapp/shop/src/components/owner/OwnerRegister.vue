<template>
  <div class="shopRegister_container">
   
    <div class="shopRegister_box">
      <!-- 头像区 -->
      <div class="avatar_box">
        <img src="../../assets/logo.png" />
      </div>
      <!-- 注册表单区域 -->
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
            prefix-icon="el-icon-user"
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
              prefix-icon="el-icon-dianhua"
          ></el-input>
        </el-form-item>
       
        <!-- 备案地址 -->
         <el-form-item prop="shopAddr">
          <el-input
            v-model="shopRegisterForm.shopAddr"
             prefix-icon="el-icon-youxiang"
          ></el-input>
        </el-form-item>
        <!-- 注册资金 -->
        <el-form-item prop="shopRegisterFund">
          <el-input
            v-model.number="shopRegisterForm.shopRegisterFund"
            prefix-icon="el-icon-password"
          ></el-input>
        </el-form-item>
        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="shopRegister">提交</el-button>
          <el-button type="info" @click="resetshopRegisterForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //这是登录表单的数据对象
      shopRegisterForm: {
        shopName: "",
        userCard: "",
        shopIntro:"",
        shopAddr:"",
        shopRegisterFund:""
      },
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
  methods: {
    //重置表单
    resetshopRegisterForm() {
      this.$refs.shopRegisterFormRef.resetFields();
    },

    shopRegister() {
      this.$refs.shopRegisterFormRef.validate(async (valid) => {
        if (!valid) return;
        // 后端没部署，暂时注释
        // result=await this.$http.post('/shopRegister',this.shopRegisterForm);
        this.$router.push("/owner/info");
      });
    }
  },
};
</script>

<style lang="less" scoped>
.shopRegister_container {
  background-color: #2b4b6b;
  height: 100%;
}
.shopRegister_box {
  width: 450px;
  height: 550px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;

    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }

  .shopRegister_form {
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }
  .btns {
    display: flex;
    justify-content: flex-end;
  }
  
}
</style>
