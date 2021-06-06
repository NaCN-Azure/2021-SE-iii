import router from './router'
import store from './store'
import { getToken } from '@/utils/auth'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
const whiteList = ['/login','/home','/register'] // no redirect whitelist， 首页可以不用登录直接访问
router.beforeEach(async(to, from, next) => {
    // start progress bar
    NProgress.start()
    // determine whether the user has logged in
    const hasToken = getToken()
    if (hasToken) {
        // console.log(hasToken)
        store.commit('set_isLogin',true)
        // 有存入cookie的登录状态，直接getUserInfo
        await store.dispatch('getUserInfo')
      if (to.path === '/login') {
        // if is logged in, redirect to the home page
        next({path: '/'})
        NProgress.done()
      }else if (to.path === '/userCenter'){
          // 不这样的话，每次刷新导航按又会跳到默认工作区去
          store.commit('set_activeIndex','3')
          next()
          NProgress.done()
      }
      else {
        next()
        NProgress.done()
      }
    } else {
      /* has no token*/
      if (whiteList.indexOf(to.path) !== -1) {
        // in the free login whitelist, go directly
        next()
      } else {
        // other pages that do not have permission to access are redirected to the login page.
        next(`/login?redirect=${to.path}`)
        NProgress.done()
      }
    }
  })

  router.afterEach(() => {
    // finish progress bar
    NProgress.done()
  })
