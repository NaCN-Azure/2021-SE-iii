import Vue from 'vue'
import VueRouter from 'vue-router'
import Create from '../views/create/index'
import Editor from '../views/editor/editor'
import Login from '../views/login'
import Register from '../views/register'
import UserCenter from '../views/user/userCenter'

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
