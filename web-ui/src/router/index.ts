import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

/**
 * https://router.vuejs.org/zh/guide/
 */
// 公共路由
export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: '/redirect',
    meta: { hidden: true },
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '',
    redirect: '/user',
    children: [
      {
        path: '/user',
        component: () => import('@/views/user/index.vue'),
        name: 'User',
        meta: {title: '用户管理', icon: 'dashboard', affix: true}
      },
    ]
  }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = []

// createWebHashHistory hash 路由
// createWebHistory history 路由
// createMemoryHistory 带缓存 history 路由
const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  // 刷新时，滚动条位置还原
  scrollBehavior: () => ({ left: 0, top: 0 })
})

export default router
