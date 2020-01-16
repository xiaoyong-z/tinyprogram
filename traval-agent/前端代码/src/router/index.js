import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

let route = new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/bill_search',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/BillSearch.vue'),
                    meta: { title: '账单查询' }
                },
                {
                    path: '/tabs',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/Tabs.vue'),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/user_info',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/UserInfo.vue'),
                    meta: { title: '个人信息' }
                },
                {
                    path: '/TravellerAdmin2',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/TravellerAdmin.vue'),
                    meta: { title: '旅客管理' , requireAuth: true}
                },
                {
                    path: '/TravellerAdmin1',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/TravellerAdmin.vue'),
                    meta: { title: '旅客管理' }
                },
                {
                    path: '/FlightAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/FlightAdmin.vue'),
                    meta: { title: '航班管理' , requireAuth: true}
                },
                {
                    path: '/SalesmanAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/SalesmanAdmin.vue'),
                    meta: { title: '业务员管理' , requireAuth: true}
                },
                {
                    path: '/HotelAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/HotelAdmin.vue'),
                    meta: { title: '酒店管理' ,requireAuth: true}
                },
                {
                    path: '/RouteAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/RouteAdmin.vue'),
                    meta: { title: '路线管理' , requireAuth: true}
                },
                {
                    path: '/FoodAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/FoodAdmin.vue'),
                    meta: { title: '美食管理' , requireAuth: true}
                },
                {
                    path: '/ScenicAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/ScenicAdmin.vue'),
                    meta: { title: '景点管理' , requireAuth: true}
                },
                {
                    path: '/CompanyAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/CompanyAdmin.vue'),
                    meta: { title: '分公司管理' , requireAuth: true}
                },
                {
                    path: '/ActivityAdmin',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/ActivityAdmin.vue'),
                    meta: { title: '活动管理' , requireAuth: true}
                },
                {
                    path: '/business_handle',
                    component: () => import(/* webpackChunkName: "editor" */ '../components/page/BusinessHandle.vue'),
                    meta: { title: '业务办理' }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                },
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});


route.beforeEach((to, from, next) => {
    // 判断该路由是否需要登录权限
   if (to.matched.some(record => record.meta.requireAuth)) {
     // 判断当前的user是否登录
     let is_manager = localStorage.getItem("is_manager");
    //  console.log(is_manager == true);
     if (is_manager === "true") {
        next()
     } else {
       next({
         path: '/'
       })
     }
   } else {
     next()
   }
 })

 export default route;