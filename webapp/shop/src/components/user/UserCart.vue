<script>
import {apiOrderUserCartBuy,apiOrderUserCartDelete,apiOrderUserCartList} from '@/api/api'
export default {
  data() {
    return {
      orderList: [],
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
      if(t!="0" && t!="1" && t!="2"){
        this.$message.error("非法访问");
        this.$router.push("/login");
      }
      apiOrderUserCartList({page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.inspectShopList = response.object.orders;
      })
    },
  },
};
</script>