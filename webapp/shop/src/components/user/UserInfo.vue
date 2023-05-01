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
      width="30%">
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
      width="30%">
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
        <el-button type="primary" @click=" editPwd">提交</el-button>
        <el-button type="info" @click="reseteditPwdForm">重置</el-button>
        <el-button @click="pwdDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>

<el-upload
  class="upload-demo"
  action="http://localhost:8081/front/owner/product/add"
  :on-preview="handlePreview"
  :on-remove="handleRemove"
  :before-remove="beforeRemove"
  multiple
  :limit="3"
  :on-exceed="handleExceed"
  :headers="headersOBJ"
  :file-list="fileList">
  <el-button size="small" type="primary">点击上传</el-button>
  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
</el-upload>
  </div>
</template>

<script>
import {apiUserInfo,apiUserPwdEdit,apiUserInfoEdit} from '@/api/api'
export default {
  data() {
    return {
      //修改信息
       fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
        headersOBJ:{
          Authorization:"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyUHdkIjoiODU0YjZjZWUxZjQzODdmYzFlYzg5NDI3ZWYxOWRkM2QiLCJ1c2VyUm9sZSI6IjEiLCJ1c2VyTmFtZSI6ImZrYmd1d2VscnUiLCJleHAiOjE2ODMwMTI3OTQsInVzZXJJZCI6IjMifQ.7DiTFGh88ELyZyEJsXQs0gp1_1OyLKNNWr7f0sVKU28"
        },
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
       this.$refs.editInfoFormRef.validate(async (valid) => {
        if (!valid) return;
      apiUserInfoEdit(this.editInfoForm).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.$message({
            showClose: true,
            message: "修改成功",
            type: 'success'
          });
        this.infoDialogVisible=false;
        this.getUserInfo();
      })
       })
    },
      //重置表单
    reseteditInfoForm() {
      this.$refs.editInfoFormRef.resetFields();
    },
    editPwd(){
      // md5加密 密码
    this.$refs.editPwdFormRef.validate(async (valid) => {
        if (!valid) return;
          var editPwdForm={...this.editPwdForm};
        // editPwdForm.userOldPwd=this.$md5(editPwdForm.userOldPwd);
        // editPwdForm.userNewPwd=this.$md5(editPwdForm.userNewPwd);
      apiUserPwdEdit(editPwdForm).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.$message({
            showClose: true,
            message: "修改成功",
            type: 'success'
          });
        this.pwdDialogVisible=false;
        this.getUserInfo();
      })
    })
    },
       //重置表单
    reseteditPwdForm() {
      this.$refs.editPwdFormRef.resetFields();
    },
     handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      }
  },
};
</script>

<style lang="less" scoped>
 .edit_form {
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }
</style>
