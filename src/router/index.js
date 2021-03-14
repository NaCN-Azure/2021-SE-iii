import Vue from 'vue'
import VueRouter from 'vue-router'
import Create from '../views/create/index'
import Editor from '../views/editor/editor'

Vue.use(VueRouter)
const routes = [
  // {
  //   path: '/login',
  //   name: 'login',
  //   component: Login
  // },
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
    path: '/COINHEAP',
    name: 'layout',
    redirect: '/editor',
    component: () => import('@/views/layout'),
    children:[
      {
        path:'/editor',
        name:'editor',
        component: () => import('@/views/editor/editor')
      }
    ]
  },
  // {
  //   path: '/',
  //   redirect: '/NJUSE'
  // },
  // {
  //   path: '/NJUSE',
  //   name: 'layout',
  //   redirect: '/hotel/hotelList',
  //   component: ()=> import('@/views/layout'),
  //   children: [
  //     {
  //       path: '/admin/manageUser',
  //       name: 'manageUser',
  //       component: () => import('@/views/admin/manageUser')
  //     },
  //     {
  //       path: '/admin/manageAllHotel',
  //       name: 'manageAllHotel',
  //       component: ()=>import('@/views/admin/manageAllHotel')
  //     },
  //     {
  //       path: '/marketer/marketing',
  //       name: 'marketing',
  //       component: () => import('@/views/marketer/marketing')
  //     },
  //   ]
  // },
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
