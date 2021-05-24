import Vue from 'vue'
import VueRouter from 'vue-router'
import Create from '../views/create/index'
import Editor from '../views/editor/editor'
import Login from '../views/login'
import Register from '../views/register'
import UserCenter from '../views/user/userCenter'

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err)
// }

Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: '首页',
    component: Create
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/COINHEAP',
    name: 'layout',
    redirect: '/editor',
    component: () => import('@/views/layout'),
    children:[
      {
        path:'/editor',
        name:'editor',
        component: Editor
      },
      {
        path: '/userCenter',
        name: 'userCenter',
        component: UserCenter
      }
    ]
  },
]
const createRouter = () => new VueRouter({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes
})
const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
