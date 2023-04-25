<script>
import {apiAccountInfo,apiAccountRecharge,apiAccountWithdraw} from '@/api/api'
export default {
  data() {
    return {
      accountInfo: {},
      flowInfo:{
          accountType:1,
          money:0
      }
    };
  },
  created() {
    this.getAccountInfo();
  },
  methods: {
    async getAccountInfo() {
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="1"){
        this.$message.error("非法访问");
        this.$router.push("/login");
      }
      apiAccountInfo().then(response =>{
        if (!response.success){
          this.$router.push('/home');
          return this.$message.error(response.message);
        }
        this.accountInfo = response.object;
      })
    },
    recharge(){
      apiAccountRecharge(this.flowInfo).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getAccountInfo();
      })
    },
    withdraw(){
      apiAccountWithdraw(this.flowInfo).then(response=>{
        if (!response.success) return this.$message.error(response.message);
        this.getAccountInfo();
      })
    },
  },
};
</script>