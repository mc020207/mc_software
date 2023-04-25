<script>
import {apiVisitProductInfo} from '@/api/api'
export default {
  data() {
    return {
        productInfo:{}
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    async getInfo() {
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!= "0" && t!="1" && t!="2"){
        this.$router.push("/login");
        return this.$message.error("非法访问");
      }
      var pid = window.sessionStorage.getItem("productId");
      // var normal = window.sessionStorage.getItem("normalShopInfo");
      if (pid == null) {
        // 非法访问
        this.$router.push("/home");
        return this.$message.error("非法访问");
      }
          apiVisitProductInfo({productId:pid}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.productInfo = response.object;
          });
    },

    //监听页面值改变的事件
    // handleCurrentChange(newPage){
    //    this.currentPage=newPage;
    //    this.getInfo();
    // },
  },
};
</script>

<style lang="less" scoped></style>
