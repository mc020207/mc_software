<template>
  <div class="register_container">
    <div class="register_box">
      <!-- 头像区 -->
      <div class="avatar_box">
        <img src="../assets/logo.png" />
      </div>
      <!-- 注册表单区域 -->
      <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="registerFormRules"
        label-width="0px"
        class="register_form"
      >
        <!-- 角色 -->
        <el-form-item  class="register_form_Role">
                   <span prefix-icon="el-icon-password"></span>
          <el-radio-group v-model="registerForm.userRole" >
     
            <el-radio  label="0">普通用户</el-radio>
            <el-radio label="1">商家</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input
            v-model="registerForm.username"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <!-- 手机号 -->
        <el-form-item prop="userPhone">
          <el-input
            v-model="registerForm.userPhone"
              prefix-icon="el-icon-dianhua"
          ></el-input>
        </el-form-item>
        <!-- 身份证号 -->
        <el-form-item prop="userCard">
          <el-input
            v-model="registerForm.userCard"
            prefix-icon="el-icon-shenfenzheng"
          ></el-input>
        </el-form-item>
        <!-- 邮箱 -->
         <el-form-item prop="userEmail">
          <el-input
            v-model="registerForm.userEmail"
             prefix-icon="el-icon-youxiang"
          ></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input
            v-model="registerForm.password"
            prefix-icon="el-icon-password"
            show-password
          ></el-input>
        </el-form-item>
        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="register">提交</el-button>
          <el-button type="info" @click="resetregisterForm">重置</el-button>
          <el-button type="warning" @click="backLogin">返回</el-button>
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
      registerForm: {
        userRole: "0",
        username: "",
        userPhone: "",
        userCard: "",
        userEmail: "",
        password: "",
      },
      //这是登录表单的验证规则对象
      registerFormRules: {
        username: [
          { required: true, message: "请输入用户名称", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "长度在 3 到 10 个字符",
            trigger: "blur",
          },
          {
            pattern: /^[a-zA-Z\d_]*$/,
            message: `仅能出现英⽂字符、数字与下划线`,
            trigger: "blur",
          },
        ],
        userPhone:[
             { required: true, message: "请输入手机号", trigger: "blur" },
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
        userEmail: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            pattern: /^.+@.+$/,
            message: `邮箱格式错误`,
            trigger: "blur",
          }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 31,
            message: "长度在 6 到 32 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    //重置表单
    resetregisterForm() {
      this.$refs.registerFormRef.resetFields();
    },

    register() {
      this.$refs.registerFormRef.validate(async (valid) => {
        if (!valid) return;
        // 后端没部署，暂时注释
        // result=await this.$http.post('/register',this.registerForm);
          this.$message({
          showClose: true,
          message: '已注册',
          type: 'success'
        });
        this.$router.push("/login");
      });
    },
    backLogin() {
        this.$router.push("/login");
    },
  },
};
</script>

<style lang="less" scoped>
.register_container {
  background-color: #2b4b6b;
  height: 100%;
}
.register_box {
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

  .register_form {
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
