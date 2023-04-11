import { createRouter, createWebHashHistory, createWebHistory } from "vue-router"




// 2. 定义路由配置
const routes = [
  {
    path: '/',
    component: () => import('./pages/Home.vue'),
    children: [
      {
        path: '',
        component: () => import('./pages/Visualization.vue'),
      },
      {
        path: 'video',
        component: () => import('./pages/Video.vue'),
      },
      {
        path: 'camera',
        component: () => import('./pages/Camera.vue'),
      },
      {
        path: 'detail',
        component: () => import('./pages/Detail.vue'),
      },
      {
        path: 'User',
        component: () => import('./pages/User.vue'),
      },


    ]
  },
  {

    path: '/login',
    component: () => import('./pages/Login.vue'),
  },


];

// 3. 创建路由实例
const router = createRouter({
  // 4. 采用hash 模式
  history: createWebHashHistory(),
  // 采用 history 模式
  // history: createWebHistory(),
  routes, //使用上方定义的路由配置
});



router.beforeEach((to, from, next) => {
  if (to.path == '/login') {
    next();
  } else if (localStorage.getItem('name') == null) {
    router.push("login");
  } else {
    next()
  }

});


export default router
//导出router
