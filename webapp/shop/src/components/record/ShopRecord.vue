<template>
  <div>
     <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>申请记录</el-breadcrumb-item>
      <el-breadcrumb-item>商店申请记录</el-breadcrumb-item>
    </el-breadcrumb>
  <el-card> 
       <el-table :data="recordList" border stripe :row-style="{height: '49px'}" :cell-style="{padding: '0'}" >
        <el-table-column type="index"></el-table-column>
        <el-table-column
          label="商店状态"
          prop="shopRecordStateStr"
          width="200"
        ></el-table-column>
        <el-table-column
          label="日期"
          prop="shopRecordDate"
          width="250"
        ></el-table-column>
        <el-table-column
          label="审核意见"
          prop="shopRecordComment"
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
import {apiRecordShopList} from '@/api/api'
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
      apiRecordShopList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.recordList = response.object.records;
        
        for(let i=0;i<this.recordList.length;i++){
         switch(this.recordList[i].shopRecordState){
            case 0:this.recordList[i].shopRecordStateStr = "开店申请未处理";break;
            case 1:this.recordList[i].shopRecordStateStr = "开店申请通过";break;
            case 2:this.recordList[i].shopRecordStateStr = "开店申请拒绝";break;
            case 3:this.recordList[i].shopRecordStateStr = "删除申请未处理";break;
            case 4:this.recordList[i].shopRecordStateStr = "删除申请通过";break;
            case 5:this.recordList[i].shopRecordStateStr = "删除申请拒绝";break;
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