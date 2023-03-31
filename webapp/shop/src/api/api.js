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

export function apiShopList(params) {
  return service({
    url: '/shop/list',
    method: 'get',
    params
  })
}

export function apiShopInfo(params) {
  return service({
    url: '/shop/info',
    method: 'get',
    params
  })
}

export function apiProductInfo(params) {
  return service({
    url: '/shop/product/list',
    method: 'get',
    params
  })
}

export function apiMyshopReg(params) {
  return service({
    url: '/myshop/register',
    method: 'post',
    data: params
  })
}


export function apiMyshopInfo() {
  return service({
    url: '/myshop/info',
    method: 'get',
  })
}

export function apiMyshopProducts(params) {
  return service({
    url: '/myshop/product/list',
    method: 'get',
    params
  })
}

export function apiMyshopAdd(params) {
  return service({
    url: '/myshop/product/add',
    method: 'post',
    data:params
  })
}

export function apiMyshopDelete(params) {
  return service({
    url: '/myshop/product/delete',
    method: 'get',
    params
  })
}

export function apiMyshopCommit() {
  return service({
    url: '/myshop/commit',
    method: 'get'
  })
}

export function apiAdminList(params) {
  return service({
    url: '/inspect/list',
    method: 'get',
    params
  })
}

export function apiAdminShop(params) {
  return service({
    url: '/inspect/info',
    method: 'get',
    params
  })
}

export function apiAdminProducts(params) {
  return service({
    url: '/inspect/product/list',
    method: 'get',
    params
  })
}

export function apiAdminPass(params) {
  return service({
    url: '/inspect/pass',
    method: 'get',
    params
  })
}

export function apiAdminReject(params) {
  return service({
    url: '/inspect/reject',
    method: 'get',
    params
  })
}
