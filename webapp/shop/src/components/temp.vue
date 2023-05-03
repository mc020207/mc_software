<template>
  <div>
     <!-- 测试图片上传 -->
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户界面</el-breadcrumb-item>
      <el-breadcrumb-item>我的信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 面包屑卡片视图 -->
 
<!-- <el-upload
  class="upload-demo"
  action="http://localhost:8081/front/owner/product/image/add"
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
</el-upload> -->

<el-upload
  class="avatar-uploader"
  action=""
  :http-request="uploader"
  :show-file-list="false"
  :on-success="handleAvatarSuccess"
  :before-upload="beforeAvatarUpload">
  <img v-if="imageUrl" :src="imageUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>

  </div>
</template>

<script>
import {apiUserInfo,apiUserPwdEdit,apiUserInfoEdit,apiOwnerProductImageAdd} from '@/api/api'
export default {
  data() {
    return {
      //修改信息
       fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
        headersOBJ:{
          Authorization:"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyUHdkIjoiODU0YjZjZWUxZjQzODdmYzFlYzg5NDI3ZWYxOWRkM2QiLCJ1c2VyUm9sZSI6IjEiLCJ1c2VyTmFtZSI6ImZrYmd1d2VscnUiLCJleHAiOjE2ODMwMTI3OTQsInVzZXJJZCI6IjMifQ.7DiTFGh88ELyZyEJsXQs0gp1_1OyLKNNWr7f0sVKU28"
        },
        imageUrl: '',
    }
  },
  
  methods: {
     handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      async uploader(params){
            
            let file = params.file;
            let productId;
            productId = 1;
            console.log("1");
            var formData = new FormData();
            formData.append('image', file);
            formData.append('productId',productId);
            apiOwnerProductImageAdd(formData).then(repsonse=>{
                console.log("ok");
            });
        },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
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
