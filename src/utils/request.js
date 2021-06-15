import Vue from 'vue'
import axios from 'axios'
import { VueAxios } from './axios'
import {notification, message} from 'ant-design-vue'
import store from '@/store'
import { getToken } from './auth'
import router from '../router'
import cookie from 'js-cookie'

// 创建 axios 实例
const service = axios.create({
  // baseURL: 'http://106.15.93.81:9001',  //api的base_url
  baseURL: 'http://localhost:9001',
  withCredentials: false,
  // timeout: 20000 // 请求超时时间
})

// 创建拦截器
service.interceptors.request.use( // 每次发送请求中，使用拦截器，用于传递token信息
  config => {
    // 判断cookie中是否有名称为coin_token的数据
    if (cookie.get('coin_token')) {
      // 将获取的cookie值放到header中
      config.headers['token'] = cookie.get('coin_token')
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

// console.log(process.env.NODE_ENV)
//  const err = (error) => {
//   if (error.response) {
//     const data = error.response.data
//     const token = Vue.ls.get('ACCESS_TOKEN')
//     if (error.response.status === 403) {
//       notification.error({
//         message: 'Forbidden',
//         description: data.message
//       })
//     }
//     if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
//       notification.error({
//         message: 'Unauthorized',
//         description: 'Authorization verification failed'
//       })
//       if (token) {
//         store.dispatch('Logout').then(() => {
//           setTimeout(() => {
//             window.location.reload()
//           }, 1500)
//         })
//       }
//     }
//   }
//   return Promise.reject(error)
//  }

//request incerceptor
// service.interceptors.request.use((config) => {
//   const requestConfig = {
//     ...config,
//     url: `${config.url}`,
//   }
//   return requestConfig
// }, err)
//
// service.interceptors.response.use((response) => {
//   switch (response.status) {
//     case 200:
//       if(response.data.success && response.data.success){
//         return response.data.content
//       }
//       message.error(response.data.message)
//       break
//     case 404:
//       return false
//     default:
//       message.error(response.data.message)
//   }
// })

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}
