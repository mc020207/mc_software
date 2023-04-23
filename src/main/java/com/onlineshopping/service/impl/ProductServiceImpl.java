package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.*;
import com.onlineshopping.model.dto.ProductDTO;
import com.onlineshopping.model.entity.*;
import com.onlineshopping.model.vo.*;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
// 注意！！！！！！！！！！！！！！！！
// 还没有考虑审核记录，后续需要加上
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductMapper productMapper;
    @Resource
    ShopMapper shopMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    ProductImgMapper productImgMapper;
    @Resource
    ProductRecordMapper productRecordMapper;
    private Shop getShop(HttpServletRequest request) {
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));
        Shop shop = shopMapper.selectShopByUserId(userId);
        if (shop==null){
            throw new ServiceException("该用户没有开店");
        }
        return shop;
    }
    @Override
    @Transactional
    public boolean productCanDelete(Integer productId){
        Order order = new Order();
        order.setProductId(productId);
        order.setOrderState(ConstantUtil.ORDER_NOT_RECEIVE);
        return orderMapper.countOrders(order)==0;
    }

    private boolean isMyProduct(Integer productId,HttpServletRequest request){
        Shop shop = getShop(request);
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        return Objects.equals(shop.getShopId(), product.getShopId());
    }
    private void addProductRecord(Integer productId){
        ProductRecord condition=new ProductRecord();
        condition.setProductId(productId);
        condition.setProductRecordState(ConstantUtil.RECORD_NOT_SOLVE);
        List<ProductRecord> productRecords = productRecordMapper.selectProductRecords(condition, 0, ConstantUtil.PAGE_SIZE);
        if (productRecords==null || productRecords.size()==0){
            ProductRecord productRecord = new ProductRecord(null,productId,new Timestamp(System.currentTimeMillis()),null,ConstantUtil.RECORD_NOT_SOLVE);
            productRecordMapper.insertProductRecord(productRecord);
        }else{
            ProductRecord productRecord = productRecords.get(0);
            productRecord.setProductRecordDate(new Timestamp(System.currentTimeMillis()));
            productRecordMapper.updateProductRecordById(productRecord);
        }
    }
     private ProductDisplayVO getProductDisplay(Product product){
        ProductDisplayVO productDisplayVO = new ProductDisplayVO(product);
        List<ProductImg> productImgs = productImgMapper.selectProductImgByProductId(product.getProductId());
        List<ProductImgVO> productImgVOList=new ArrayList<>();
        for (ProductImg productImg : productImgs){
            productImgVOList.add(new ProductImgVO(productImg));
        }
        productDisplayVO.setImages(productImgVOList);
        return productDisplayVO;
    }
    private ProductsDisplayVO getProductDisplayList(Product condition,Integer startRow,Integer num){
        Integer totalNum=productMapper.countProducts(condition);
        List<Product> products = productMapper.selectProducts(condition, startRow, num);
        List<ProductDisplayVO> productDisplayVOList=new ArrayList<>();
        for (Product product : products){
            productDisplayVOList.add(getProductDisplay(product));
        }
        return new ProductsDisplayVO(productDisplayVOList,totalNum);
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response) {
        String productName = productDTO.getProductName();
        FormatUtil.checkNotNull("productName", productName);
        Shop shop = getShop(request);
        if (!Objects.equals(shop.getShopState(), ConstantUtil.SHOP_OPEN)){
            throw new ServiceException("商店为开店状态时候才可以添加商品");
        }
        Product product = productDTO.changeToProduct();
        product.setShopId(shop.getShopId());
        productMapper.insertProduct(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId", productId);
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        if (!isMyProduct(productId,request)) {
            throw new ServiceException("不可以删除别人的商品");
        }
        if (!productCanDelete(productId)) {
            throw new ServiceException("还有未发货的订单");
        }
        productMapper.updateProductState(product,ConstantUtil.PRODUCT_DELETE);
    }

    @Override
    @Transactional
    public void updateProductInfo(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response) {
        Shop shop = getShop(request);
        if (!isMyProduct(productDTO.getProductId(),request)){
            throw new ServiceException("不得修改他人的商品");
        }
        Product product=productDTO.changeToProduct();
        product.setShopId(shop.getShopId());
        productMapper.updateProductInfo(product);
        addProductRecord(product.getProductId());
    }

    @Override
    @Transactional
    public void addProductImage(Integer productId, MultipartFile image, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkNotNull("image",image);
        if (!isMyProduct(productId,request)){
            throw new ServiceException("不得修改他人的商品");
        }
        //获取文件的后缀名
        int lastIndexOf = Objects.requireNonNull(image.getOriginalFilename()).lastIndexOf(".");
        String suffix = image.getOriginalFilename().substring(lastIndexOf);
        //根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
        String fileName = System.currentTimeMillis() + suffix;
        //获取当前项目的真实路径，然后拼接前面的文件
        String destFileName = request.getServletContext().getRealPath("") + "static\\productImage" + File.separator + fileName;
        //第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下static/user_source文件夹下）
        File destFile = new File(destFileName);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        //把浏览器上传的文件复制到希望的位置
        try {
            image.transferTo(destFile);
        } catch ( IOException e) {
            throw new ServiceException("添加图片失败");
        }
        String imagePath = "/static/article_image/" + fileName;
        productImgMapper.insertProductImg(new ProductImg(null,productId,imagePath));
        addProductRecord(productId);
    }

    @Override
    @Transactional
    public void deleteProductImage(Integer imageId, HttpServletRequest request, HttpServletResponse response) {
        ProductImg productImg = productImgMapper.selectProductImgByProductImgId(imageId);
        if (productImg==null){
            throw new ServiceException("没有这张图片");
        }
        if(!isMyProduct(productImg.getProductId(),request)){
            throw new ServiceException("不得修改他人的图片");
        }
        productImgMapper.deleteProductImgByProductImgId(imageId);
        addProductRecord(productImg.getProductId());
    }

    @Override
    @Transactional
    public ProductsDisplayVO displayAllProductsOnShelf(Integer page) {
        Product product = new Product();
        product.setProductState(ConstantUtil.PRODUCT_ON_SHELF);
        return getProductDisplayList(product,(page-1)*ConstantUtil.PAGE_SIZE,ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public ProductsDisplayVO displayProductsByShopId(Integer page, Integer shopId) {
        FormatUtil.checkPositive("shopId", shopId);
        FormatUtil.checkPositive("page", page);
        Shop shop = shopMapper.selectShopByShopId(shopId);
        if (shop==null){
            throw new ServiceException("没有这个商店");
        }
        if (!Objects.equals(shop.getShopState(), ConstantUtil.SHOP_OPEN)) {
            throw new ServiceException("该商店未开放");
        }
        Product product = new Product();
        product.setShopId(shopId);
        product.setProductState(ConstantUtil.PRODUCT_ON_SHELF);
        return getProductDisplayList(product,(page-1)*ConstantUtil.PAGE_SIZE,ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public ProductDisplayVO displayProductInfo(Integer productId) {
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_ON_SHELF)){
            throw new ServiceException("该商品没有上架");
        }
        return getProductDisplay(product);
    }

    @Override
    @Transactional
    public ProductsDisplayVO getMyProducts(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Shop shop = getShop(request);
        Product product = new Product();
        product.setShopId(shop.getShopId());
        return getProductDisplayList(product,(page-1)*ConstantUtil.PAGE_SIZE,ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public ProductDisplayVO getMyProductInfo(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        if (!isMyProduct(productId,request)){
            throw new ServiceException("不得查看他人商品");
        }
        Product product = productMapper.selectProductById(productId);
        return getProductDisplay(product);
    }

    @Override
    @Transactional
    public ProductsDisplayVO inspectAllProducts(Integer page) {
        FormatUtil.checkPositive("page", page);
        Product product = new Product();
        product.setProductState(ConstantUtil.PRODUCT_IN_INSPECTION);
        return getProductDisplayList(product,(page-1)*ConstantUtil.PAGE_SIZE,ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public ProductDisplayVO inspectProductInfo(Integer productId) {
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_IN_INSPECTION)){
            throw new ServiceException("该商品不是正在审核的商品");
        }
        return getProductDisplay(product);
    }

    @Override
    @Transactional
    public void passProduct(Integer productId) {
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_IN_INSPECTION)){
            throw new ServiceException("该商品不是正在审核的商品");
        }
        product.setProductState(ConstantUtil.PRODUCT_ON_SHELF);
        productMapper.updateProductInfo(product);
        ProductRecord productRecord = new ProductRecord();
        productRecord.setProductRecordState(ConstantUtil.RECORD_SOLVE);
        productRecord.setProductRecordDate(new Timestamp(System.currentTimeMillis()));
        productRecord.setProductId(productId);
        productRecord.setProductRecordComment("同意");
        productRecordMapper.updateProductRecordById(productRecord);
    }

    @Override
    @Transactional
    public void rejectProduct(Integer productId,String reason) {
        Product product = productMapper.selectProductById(productId);
        FormatUtil.checkNotNull("reason",reason);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_IN_INSPECTION)){
            throw new ServiceException("该商品不是正在审核的商品");
        }
        product.setProductState(ConstantUtil.PRODUCT_REJECTED);
        productMapper.updateProductInfo(product);
        ProductRecord productRecord = new ProductRecord();
        productRecord.setProductRecordComment("拒绝:"+reason);
        productRecord.setProductRecordState(ConstantUtil.RECORD_SOLVE);
        productRecord.setProductRecordDate(new Timestamp(System.currentTimeMillis()));
        productRecord.setProductId(productId);
        productRecordMapper.updateProductRecordById(productRecord);
    }
}
