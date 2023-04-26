<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户界面</el-breadcrumb-item>
      <el-breadcrumb-item>我的信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
    <el-card> 
      <el-descriptions title="个人信息" direction="vertical" :column="3" border>
        <template slot="extra">
      <el-button type="primary" @click="infoDialogVisible = true" >修改信息</el-button>
    </template>
     <template slot="extra">
      <el-button type="primary" @click="pwdDialogVisible = true" >修改密码</el-button>
    </template>
  <el-descriptions-item label="用户名">{{userInfo.userName}}</el-descriptions-item>
  <el-descriptions-item label="手机号">{{userInfo.userPhone}}</el-descriptions-item>
   <el-descriptions-item label="用户身份">{{userInfo.userRoleStr}}</el-descriptions-item>
  <el-descriptions-item label="身份证号" :span="2">{{userInfo.userIdCard}}</el-descriptions-item>
  
  <el-descriptions-item label="邮箱">{{userInfo.userEmail}}</el-descriptions-item>
</el-descriptions>
    </el-card>
    
    <el-dialog
      title="修改信息"
      :visible.sync="infoDialogVisible"
      width="50%">
      <el-form
        ref="editInfoFormRef"
        :model="editInfoForm"
        :rules="editInfoFormRules"
        label-width="0px"
      >
        <!-- 用户名 -->
        <el-form-item prop="userName">
          <el-input
            v-model="editInfoForm.userName"
            prefix-icon="el-icon-user"
          ></el-input>
          </el-form-item>
          <!-- 手机号 -->
        <el-form-item prop="userPhone">
          <el-input
            v-model="editInfoForm.userPhone"
            prefix-icon="el-icon-dianhua"
          ></el-input>
          </el-form-item>
          <!-- 邮箱 -->
        <el-form-item prop="userEmail">
          <el-input
            v-model="editInfoForm.userEmail"
            prefix-icon="el-icon-youxiang"
          ></el-input>
        </el-form-item>
      </el-form>
          <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        
        <el-button type="primary" @click=" editInfo">提交</el-button>
        <el-button type="info" @click="reseteditInfoForm">重置</el-button>
        <el-button @click="infoDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
      <el-dialog
      title="修改密码"
      :visible.sync="pwdDialogVisible"
      width="50%">
      <el-form
        ref="editPwdFormRef"
        :model="editPwdForm"
        :rules="editPwdFormRules"
        label-width="0px"
      >
          <!-- 旧密码 -->
        <el-form-item prop="userOldPwd">
          <el-input
            v-model="editPwdForm.userOldPwd"
            prefix-icon="el-icon-password"
          ></el-input>
        </el-form-item>
          <!-- 新密码 -->
        <el-form-item prop="userNewPwd">
          <el-input
            v-model="editPwdForm.userNewPwd"
            prefix-icon="el-icon-password"
          ></el-input>
        </el-form-item>
      </el-form>
          <!-- 按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click=" editPwd()">提交</el-button>
        <el-button type="info" @click="reseteditPwdForm">重置</el-button>
        <el-button @click="pwdDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {apiUserInfo,apiUserPwdEdit,apiUserInfoEdit} from '@/api/api'
export default {
  data() {
    return {
      //修改信息
      userInfo: {},
      editInfoForm:{
        userName: "",
        userPhone: "",
        userEmail: "",
      },
      editInfoFormRules: {
        userName: [
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
        userEmail: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            pattern: /^.+@.+$/,
            message: `邮箱格式错误`,
            trigger: "blur",
          }
        ],
      },
      infoDialogVisible:false,
      pwdDialogVisible:false,
      editPwdForm:{
        userOldPwd: "",
        userNewPwd: "",
      },
      editPwdFormRules:{
        userOldPwd: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 31,
            message: "长度在 6 到 32 个字符",
            trigger: "blur",
          },
        ],
        userNewPwd: [
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
  created() {
    this.getUserInfo();
  },
  methods: {
    async getUserInfo() {
      // var result=await this.$http.get('/user/info');
      // var result = { success: true, message: "获取成功" };
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="0" && t!="1" && t!="2"){
        this.$message.error("非法访问");
        this.$router.push("/login");
      }
      apiUserInfo().then(response =>{
        if (!response.success){
          this.$router.push('/home');
          return this.$message.error(response.message);
        }
        this.userInfo = response.object;
        switch (this.userInfo.userRole){
        case 0:
          this.userInfo.userRoleStr='普通用户';
          break;
        case 1:
           this.userInfo.userRoleStr='商户';
          break;
        case 2:
           this.userInfo.userRoleStr='管理员';
          break;
        }
      })
    },
    editInfo(){
      apiUserInfoEdit(this.editInfoForm).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getUserInfo();
      })
    },
      //重置表单
    reseteditInfoForm() {
      this.$refs.editInfoFormRef.resetFields();
    },
    editPwd(){
      apiUserPwdEdit(this.editPwdForm).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getUserInfo();
      })
    },
       //重置表单
    reseteditPwdForm() {
      this.$refs.editPwdFormRef.resetFields();
    },
  },
};
</script>

<style lang="less" scoped></style>
