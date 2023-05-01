<script>
import {apiFlowToList,apiFlowFromList,apiFlowAllList} from '@/api/api'
export default {
  data() {
    return {
      flowList: [],
      currentPage:1,
      pageSize:9,   //一页的数量
      total:0
    };
  },
  created() {
    this.getFlowList();
  },
  methods: {
    async getFlowList() {
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="2"){
        this.$message.error("非法访问");
        this.$router.push("/login");
      }
      apiFlowAllList({accountType:3,page:this.currentPage}).then(response =>{
        if (!response.success) return this.$message.error(response.message);
        this.total = response.object.totalNumber;
        this.inspectShopList = response.object.flows;
      })
    },
  },
};
</script>