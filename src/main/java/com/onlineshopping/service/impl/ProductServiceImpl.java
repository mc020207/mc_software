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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    /**
     * @Description: 获取用户的商店，并且检查商店是否开放
     * @Author: Ma-Cheng
     */
    private Shop getShop(HttpServletRequest request) {
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));
        Shop shop = shopMapper.selectShopByUserId(userId);
        if (shop==null|| !Objects.equals(shop.getShopState(), ConstantUtil.SHOP_OPEN)){
            throw new ServiceException("该用户没有已经开放的商店");
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
    /**
     * @Description: 检查商品是不是该用户商店的商品
     * @Author: Ma-Cheng
     */
    private void isMyProduct(Integer productId,HttpServletRequest request){
        Shop shop = getShop(request);
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        if (!Objects.equals(shop.getShopId(), product.getShopId())){
            throw new ServiceException("该商品不是当前用户的商品");
        }
    }
    /**
     * @Description: 添加或更改商品未审核信息
     * @Author: Ma-Cheng
     */
    private void addProductRecord(Integer productId){
        ProductRecord condition=new ProductRecord();
        condition.setProductId(productId);
        condition.setProductRecordState(ConstantUtil.PRODUCT_RECORD_NOT_SOLVE);
        List<ProductRecord> productRecords = productRecordMapper.selectProductRecords(condition, 0, ConstantUtil.PAGE_SIZE);
        if (productRecords==null || productRecords.size()==0){
            // 如果没有未处理的记录则增加一条
            ProductRecord productRecord = new ProductRecord(null,productId,new Timestamp(System.currentTimeMillis()),"",ConstantUtil.PRODUCT_RECORD_NOT_SOLVE);
            productRecordMapper.insertProductRecord(productRecord);
        }else{
            // 如果有未处理的记录就更改这个记录
            ProductRecord productRecord = productRecords.get(0);
            productRecord.setProductRecordDate(new Timestamp(System.currentTimeMillis()));
            productRecordMapper.updateProductRecordById(productRecord);
        }
    }
    /**
     * @Description: 将Product包装成ProductDisplayVO
     * @Author: Ma-Cheng
     */
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
    /**
     * @Description: 根据Condition包装ProductsDisplayVO
     * @Author: Ma-Cheng
     */
    private ProductsDisplayVO getProductDisplayList(Product condition,Integer startRow,Integer num){
        Integer totalNum=productMapper.countProducts(condition);
        List<Product> products = productMapper.selectProducts(condition, startRow, num);
        List<ProductDisplayVO> productDisplayVOList=new ArrayList<>();
        for (Product product : products){
            productDisplayVOList.add(getProductDisplay(product));
        }
        return new ProductsDisplayVO(productDisplayVOList,totalNum);
    }
    /**
     * @Description: 更改商品属性
     * @Author: Ma-Cheng
     */
    private void changeProductState(Integer productId,Integer newState){
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        product.setProductState(newState);
        productMapper.updateProductInfo(product);
    }

    /**
     * @Description: 检查商品状态并根据ID返回商品
     * @Author: Ma-Cheng
     */
    private Product checkProductState(Integer productId,Integer productState) {
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("没有这个商品");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_IN_INSPECTION)){
            if (Objects.equals(productState, ConstantUtil.PRODUCT_IN_INSPECTION)) {
                throw new ServiceException("该商品不是正在审核的商品");
            }
            else if (Objects.equals(productState, ConstantUtil.PRODUCT_ON_SHELF)) {
                throw new ServiceException("该商品没有上架");
            }else{
                throw new ServiceException("商品和状态不符合");
            }
        }
        return product;
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkNotNull("productName", productDTO.getProductName());
        FormatUtil.checkNotNull("productPriceName", productDTO.getProductName());
        FormatUtil.checkNotNull("productIntro", productDTO.getProductName());
        Shop shop = getShop(request);
        Product product = productDTO.changeToProduct();
        product.setShopId(shop.getShopId());
        productMapper.insertProduct(product);
        Integer productId = productMapper.selectProducts(product, 0, 1).get(0).getProductId();
        addProductRecord(productId);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId", productId);
        if (!productCanDelete(productId)) {
            throw new ServiceException("还有未发货的订单");
        }
        changeProductState(productId,ConstantUtil.PRODUCT_DELETE);
    }

    @Override
    @Transactional
    public void updateProductInfo(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId",productDTO.getProductId());
        Shop shop = getShop(request);
        isMyProduct(productDTO.getProductId(),request);
        if (!productCanDelete(productDTO.getProductId())) {
            throw new ServiceException("还有未发货的订单");
        }
        Product product=productDTO.changeToProduct();
        product.setShopId(shop.getShopId());
        productMapper.updateProductInfo(product);
        addProductRecord(product.getProductId());
        changeProductState(product.getProductId(), ConstantUtil.PRODUCT_IN_INSPECTION);
    }

    @Override
    @Transactional
    public void addProductImage(Integer productId, MultipartFile image, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkNotNull("image",image);
        isMyProduct(productId,request);
        if (!productCanDelete(productId)) {
            throw new ServiceException("还有未发货的订单");
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
        changeProductState(productId, ConstantUtil.PRODUCT_IN_INSPECTION);
    }

    @Override
    @Transactional
    public void deleteProductImage(Integer imageId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("imageId",imageId);
        ProductImg productImg = productImgMapper.selectProductImgByProductImgId(imageId);
        if (productImg==null){
            throw new ServiceException("没有这张图片");
        }
        isMyProduct(productImg.getProductId(),request);
        if (!productCanDelete(productImg.getProductId())) {
            throw new ServiceException("还有未发货的订单");
        }
        productImgMapper.deleteProductImgByProductImgId(imageId);
        addProductRecord(productImg.getProductId());
        changeProductState(productImg.getProductId(), ConstantUtil.PRODUCT_IN_INSPECTION);
    }

    @Override
    @Transactional
    public ProductsDisplayVO displayAllProductsOnShelf(Integer page) {
        FormatUtil.checkPositive("page",page);
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
        FormatUtil.checkPositive("productId",productId);
        Product product = checkProductState(productId,ConstantUtil.PRODUCT_ON_SHELF);
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
        FormatUtil.checkPositive("productId",productId);
        isMyProduct(productId,request);
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
        FormatUtil.checkPositive("productId",productId);
        Product product = checkProductState(productId,ConstantUtil.PRODUCT_IN_INSPECTION);
        return getProductDisplay(product);
    }

    @Override
    @Transactional
    public void passProduct(Integer productId) {
        FormatUtil.checkPositive("productId",productId);
        // 更改商品状态
        Product product = checkProductState(productId,ConstantUtil.PRODUCT_IN_INSPECTION);
        product.setProductState(ConstantUtil.PRODUCT_ON_SHELF);
        productMapper.updateProductInfo(product);
        // 更改商品纪录
        ProductRecord condition = new ProductRecord();
        condition.setProductId(productId);
        condition.setProductRecordState(ConstantUtil.PRODUCT_RECORD_NOT_SOLVE);
        ProductRecord productRecord = productRecordMapper.selectProductRecords(condition, 0, ConstantUtil.PAGE_SIZE).get(0);
        productRecord.setProductRecordState(ConstantUtil.PRODUCT_RECORD_PASS);
        productRecord.setProductRecordDate(new Timestamp(System.currentTimeMillis()));
        productRecord.setProductId(productId);
        productRecord.setProductRecordComment("同意");
        productRecordMapper.updateProductRecordById(productRecord);
    }

    @Override
    @Transactional
    public void rejectProduct(Integer productId,String reason) {
        FormatUtil.checkPositive("productId",productId);
        FormatUtil.checkNotNull("reason",reason);
        // 更改商品状态
        Product product = checkProductState(productId, ConstantUtil.PRODUCT_IN_INSPECTION);
        product.setProductState(ConstantUtil.PRODUCT_REJECTED);
        productMapper.updateProductInfo(product);
        // 更改商品纪录
        ProductRecord condition = new ProductRecord();
        condition.setProductId(productId);
        condition.setProductRecordState(ConstantUtil.PRODUCT_RECORD_NOT_SOLVE);
        ProductRecord productRecord = productRecordMapper.selectProductRecords(condition, 0, ConstantUtil.PAGE_SIZE).get(0);
        productRecord.setProductRecordComment("拒绝:"+reason);
        productRecord.setProductRecordState(ConstantUtil.PRODUCT_RECORD_REJECT);
        productRecord.setProductRecordDate(new Timestamp(System.currentTimeMillis()));
        productRecord.setProductId(productId);
        productRecordMapper.updateProductRecordById(productRecord);
    }
}
