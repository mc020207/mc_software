module.exports = {
  transpileDependencies: true,
  devServer: {
        proxy: {
          '/front': {
              // 此处的写法，目的是为了 将 /api 替换成 https://www.baidu.com/
              target: 'http://localhost:8080/api/',
              // 允许跨域
              changeOrigin: true,
              ws: true,
              pathRewrite: {
                  '^/front': ''
              }
          }
      }
  }
}
