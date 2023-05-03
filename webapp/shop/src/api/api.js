import service from './http' // 使用实例

export function apiLogin(params) {
  return service({
    url: '/user/login',
    method: 'post',
    data: params
  })
}

export function apiRegister(params) {
    return service({
      url: '/user/register',
      method: 'post',
      data: params
    })
  }

export function apiLogout() {
  return service({
    url: '/user/logout',
    method: 'get',
  })
}

export function apiUserInfo() {
  return service({
    url: '/user/info',
    method: 'get',
  })
}

export function apiUserInfoEdit(params) {
  return service({
    url: '/user/info/edit',
    method: 'post',
    data: params
  })
}

export function apiUserPwdEdit(params) {
  return service({
    url: '/user/pwd/edit',
    method: 'post',
    data:params
  })
}

export function apiAccountInfo(params) {
  return service({
    url: '/account/info',
    method: 'get',
    params
  })
}

export function apiAccountRecharge(params) {
  return service({
    url: '/account/recharge',
    method: 'get',
    params
  })
}

export function apiAccountWithdraw(params) {
  return service({
    url: '/account/withdraw',
    method: 'get',
    params
  })
}

export function apiFlowToList(params) {
  return service({
    url: '/flow/to/list',
    method: 'get',
    params
  })
}

export function apiFlowFromList(params) {
  return service({
    url: '/flow/from/list',
    method: 'get',
    params
  })
}

export function apiFlowAllList(params) {
  return service({
    url: '/flow/all/list',
    method: 'get',
    params
  })
}

export function apiVisitShopInfo(params) {
  return service({
    url: '/visit/shop/info',
    method: 'get',
    params
  })
}

export function apiVisitShopProductList(params) {
  return service({
    url: '/visit/shop/product/list',
    method: 'get',
    params
  })
}

export function apiVisitProductList(params) {
  return service({
    url: '/visit/product/list',
    method: 'get',
    params
  })
}

export function apiVisitProductInfo(params) {
  return service({
    url: '/visit/product/info',
    method: 'get',
    params
  })
}

export function apiOrderUserBuy(params) {
  return service({
    url: '/order/user/buy',
    method: 'get',
    params
  })
}

export function apiOrderUserAddCart(params) {
  return service({
    url: '/order/user/cart/add',
    method: 'get',
    params
  })
}

export function apiOrderUserCartList(params) {
  return service({
    url: '/order/user/cart/list',
    method: 'get',
    params
  })
}

export function apiOrderUserCartBuy(params) {
  return service({
    url: '/order/user/cart/buy',
    method: 'get',
    params
  })
}

export function apiOrderUserCartDelete(params) {
  return service({
    url: '/order/user/cart/delete',
    method: 'get',
    params
  })
}

export function apiOrderUserWaitList(params) {
  return service({
    url: '/order/user/wait/list',
    method: 'get',
    params
  })
}

export function apiOrderUserReceiveList(params) {
  return service({
    url: '/order/user/receive/list',
    method: 'get',
    params
  })
}

export function apiOrderOwnerSendList(params) {
  return service({
    url: '/order/owner/send/list',
    method: 'get',
    params
  })
}

export function apiOrderOwnerSend(params) {
  return service({
    url: '/order/owner/send',
    method: 'get',
    params
  })
}

export function apiOrderOwnerFinishList(params) {
  return service({
    url: '/order/owner/finish/list',
    method: 'get',
    params
  })
}

export function apiOwnerShopRegister(params) {
  return service({
    url: '/owner/shop/register',
    method: 'post',
    data: params
  })
}

export function apiOwnerShopInfo() {
  return service({
    url: '/owner/shop/info',
    method: 'get',
  })
}

export function apiOwnerShopDelete() {
  return service({
    url: '/owner/shop/delete',
    method: 'get'
  })
}

export function apiOwnerProductList(params) {
  return service({
    url: '/owner/product/list',
    method: 'get',
    params
  })
}

export function apiOwnerProductAdd(params) {
  return service({
    url: '/owner/product/add',
    method: 'post',
    data:params
  })
}

export function apiOwnerProductDelete(params) {
  return service({
    url: '/owner/product/delete',
    method: 'get',
    params
  })
}

export function apiOwnerProductInfo(params) {
  return service({
    url: '/owner/product/info',
    method: 'get',
    params
  })
}

export function apiOwnerProductInfoEdit(params) {
  return service({
    url: '/owner/product/info/edit',
    method: 'post',
    data: params
  })
}

export function apiOwnerProductImageAdd(params) {
  return service({
    url: '/owner/product/image/add',
    method: 'post',
    data: params
  })
}

export function apiOwnerProductImageDelete(params) {
  return service({
    url: '/owner/product/image/delete',
    method: 'get',
    params
  })
}

export function apiAdminShopInfo(params) {
  return service({
    url: '/admin/shop/info',
    method: 'get',
    params
  })
}

export function apiAdminShopOpenList(params) {
  return service({
    url: '/admin/shop/open/list',
    method: 'get',
    params
  })
}

export function apiAdminShopOpenPass(params) {
  return service({
    url: '/admin/shop/open/pass',
    method: 'get',
    params
  })
}

export function apiAdminShopOpenReject(params) {
  return service({
    url: '/admin/shop/open/reject',
    method: 'get',
    params
  })
}

export function apiAdminShopDeleteList(params) {
  return service({
    url: '/admin/shop/delete/list',
    method: 'get',
    params
  })
}

export function apiAdminShopDeletePass(params) {
  return service({
    url: '/admin/shop/delete/pass',
    method: 'get',
    params
  })
}

export function apiAdminShopDeleteReject(params) {
  return service({
    url: '/admin/shop/delete/reject',
    method: 'get',
    params
  })
}

export function apiAdminProductInfo(params) {
  return service({
    url: '/admin/product/info',
    method: 'get',
    params
  })
}

export function apiAdminProductList(params) {
  return service({
    url: '/admin/product/list',
    method: 'get',
    params
  })
}

export function apiAdminProductPass(params) {
  return service({
    url: '/admin/product/pass',
    method: 'get',
    params
  })
}

export function apiAdminProductReject(params) {
  return service({
    url: '/admin/product/reject',
    method: 'get',
    params
  })
}

export function apiRecordShopList(params) {
  return service({
    url: '/record/shop/list',
    method: 'get',
    params
  })
}

export function apiRecordProductList(params) {
  return service({
    url: '/record/product/list',
    method: 'get',
    params
  })
}






