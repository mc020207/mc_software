<script>
import {apiAdminProductList,apiAdminProductPass,apiAdminProductReject} from '@/api/api'
export default {
  data() {
    return {
      productList: [],
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
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="2"){ 
        this.$router.push("/login");
        return this.$message.error("非法访问");
      }
      apiAdminProductList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.productList = response.object.products;
      });
    },
    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getList();
    },
    //待处理事件逻辑

    async productInspectInfo(productId){
         window.sessionStorage.setItem("productId", productId);
        //  window.sessionStorage.setItem("normalShopInfo", true);
         var activePath="/shop/info";
         //这么写有点逆天，不过能跑
         this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
         this.$router.push(activePath);
    },
    async productInspectPass(pId){
      // var result=await this.$http.get('/inspect/pass',shopId);
      apiAdminProductPass({productId:pId}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
      });
      this.getList();
    },
   async productInspectReject(pId){
      //var result=await this.$http.get('/inspect/reject',shopId);
      apiAdminProductReject({productId:pId}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
      });
      this.getShopList();
    }
  },
};
</script>