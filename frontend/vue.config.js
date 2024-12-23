const { defineConfig } = require('@vue/cli-service')

module.exports = {
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端服务的地址
        changeOrigin: true, // 开启跨域代理
      }
    }
  }
}