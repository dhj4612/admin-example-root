import {createRouter, createWebHistory} from 'vue-router'
import {hasAuthorization} from "@/utils/authorization.js";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'index',
            component: _ => import('@/views/Index.vue')
        },
        {
            path: '/login',
            name: 'login',
            component: _ => import('@/views/Login.vue')
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'notFound',
            component: _ => import('@/views/404.vue')
        },
    ]
})

// 路由白名单（取名称）
const whiteList = [
    'login'
]

router.beforeEach(async (to, from, next) => {
    if (hasAuthorization() || whiteList.includes(to.name)) {
        next()
        return
    }
    next('login')
})

router.afterEach((to) => {

});

export default router
