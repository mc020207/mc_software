<template>
  <div>
     <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>申请记录</el-breadcrumb-item>
      <el-breadcrumb-item>商品申请记录</el-breadcrumb-item>
    </el-breadcrumb>
  <el-card> 
       <el-table :data="recordList" border stripe :row-style="{height: '49px'}" :cell-style="{padding: '0'}" >
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="商品状态"
          prop="productRecordStateStr"
          width="200"
        ></el-table-column>
        <el-table-column
          label="日期"
          prop="productRecordDate"
          width="250"
        ></el-table-column>
        <el-table-column
          label="审核意见"
          prop="productRecordComment"
          width="250"
        ></el-table-column>
      </el-table>

       <!-- 分页区域 -->
      <el-pagination 
       layout="total, prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total">
    </el-pagination>
    </el-card>
  </div>
</template>
<script>
import {apiRecordProductList} from '@/api/api'
export default {
  data() {
    return {
      recordList: [],
      currentPage:1,
      pageSize:9,   //一页的数量
      total:0
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      apiRecordProductList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.recordList = response.object.records;
        
        for(let i=0;i<this.recordList.length;i++){
         switch(this.recordList[i].productRecordState){
            case 0:this.recordList[i].productRecordStateStr = "未处理";break;
            case 1:this.recordList[i].productRecordStateStr = "上架申请拒绝";break;
            case 2:this.recordList[i].productRecordStateStr = "上架申请通过";break;
          }
        }
      })
    },
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getList();
    },
  },
};
</script>