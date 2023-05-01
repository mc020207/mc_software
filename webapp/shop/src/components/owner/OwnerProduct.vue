<script>
import {apiOwnerProductInfo,apiOwnerProductDelete,apiOwnerProductInfoEdit,apiOwnerProductImageAdd,apiOwnerProductImageDelete} from '@/api/api'
export default {
  data() {
    return {
        productInfo:{
            /*
            productId:
            productName:
            productIntro:
            productPrice:
            productState:
            shopId:
            images:[]
            */
        },
        editForm:{
            productName:"",
            productIntro:"",
            productPrice:0
        },
        editRules:{
            productName:[  { required: true, message: "请输入商品名称", trigger: "blur" }],
            //rules todo
            productIntro:[],
            productPrice:[]
        },
        imageInfo:{
            //不知道用哪种方式传图
            url:"",
            stringfy:"",
        }
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    async getInfo() {
      var t = this.$decoder(window.sessionStorage.getItem('token')).userRole;
      if(t!="1"){
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
          apiOwnerProductInfo({productId:pid}).then(response =>{
            if(!response.success){
              this.$router.push("/home");
              return this.$message.error(response.message);
            }
            this.productInfo = response.object;
          });
    },
    deleteProduct(){
        apiOwnerProductDelete({productId:this.productInfo.productId}).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.getInfo();
        });
    },
    deleteProduct(){
        apiOwnerProductDelete({productId:this.productInfo.productId}).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.getInfo();
        });
    },
    editProduct(){
        apiOwnerProductInfoEdit(this.editForm).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.getInfo();
        });
    },
    addImage(){
        apiOwnerProductImageAdd({productId:this.productInfo.productId,image:this.imageInfo.stringfy}).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.getInfo();
        });
    },
    deleteImage(iid){
        apiOwnerProductImageDelete({productImgId:iid}).then(response=>{
             if (!response.success) return this.$message.error(response.message);
             this.getInfo();
        });
    }
    //监听页面值改变的事件
    // handleCurrentChange(newPage){
    //    this.currentPage=newPage;
    //    this.getInfo();
    // },
  },
};
</script>

<style lang="less" scoped></style>
