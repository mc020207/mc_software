<script>
import {apiVisitProductList} from '@/api/api'
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
      if(t!= "0" && t!="1" && t!="2"){
        
        this.$router.push("/login");
        return this.$message.error("非法访问");
      }
    //   var result=await this.$http.get('/shop/list',{
    //     page:this.currentPage
    //   });
      apiVisitProductList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.inspectShopList = response.object.products;
      });
    },
    //监听页面值改变的事件
    handleCurrentChange(newPage){
       this.currentPage=newPage;
       this.getList();
    },
    //待处理事件逻辑

    // async shopInspectInfo(shopId){
    //      window.sessionStorage.setItem("shopId", shopId);
    //      window.sessionStorage.setItem("normalShopInfo", true);
    //      var activePath="/shop/info";
    //      //这么写有点逆天，不过能跑
    //      this.$parent.$parent.$parent.$parent.saveNaveState(activePath);
    //      this.$router.push(activePath);
    // },
  },
};
</script>