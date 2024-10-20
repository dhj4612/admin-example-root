import NProgress from 'nprogress'
import {useUserStore} from "@/stores/user.js";
import {useMenusStore} from "@/stores/menus.js";
import {createRouter, createWebHistory} from 'vue-router'
import {clearAllAuthorized, hasAuthorization} from "@/utils/authorization.js";
import {pathToCamel} from "@/utils/tools.js";
import 'nprogress/nprogress.css'

const asyncRoutes = {
    path: '/',
    component: _ => import('@/views/Index.vue'),
    children: []
}

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'Login',
            component: _ => import('@/views/Login.vue')
        }
    ]
})

const humpComponentName = (path) => {
    const parts = path.split('/');
    const lastPart = parts.pop();

    const convertedLastPart = lastPart
        .split('-')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join('');

    return [...parts, convertedLastPart].join('/');
}

// 根据路径，动态获取vue组件
const getDynamicComponent = (path) => {
    return import.meta.glob('/src/views/**/*.vue')[`/src/views/${path}.vue`]
}

const menuToRoute = (menu) => {
    const {url, name} = menu
    return {
        path: url,
        name: pathToCamel(name),
        component: getDynamicComponent(humpComponentName(url)),
        meta: {...menu}
    }
}

const flatteningMenuToRoute = (menus) => {
    const routes = []
    menus.forEach(menu => {
        if (menu.url) {
            routes.push(menuToRoute(menu))
        }
        if (menu.children && menu.children.length) {
            routes.push(...flatteningMenuToRoute(menu.children))
        }
    })
    return routes
}

// 路由白名单（取名称）
const whiteList = [
    'Login'
]

router.beforeEach(async (to, from) => {
    NProgress.start()

    const userStore = useUserStore()
    const menusStore = useMenusStore()

    if (hasAuthorization()) {
        if (userStore.hasUserInfo()) {
            return true
        }

        // 加载用户信息，菜单权限...
        try {
            await Promise.all([
                userStore.fetchUserInfo(),
                userStore.fetchAuthorities(),
                menusStore.fetchMenus()
            ])

            // 扁平化菜单，生成异步路由添加
            const asyncChildrenRoutes = flatteningMenuToRoute([...menusStore.menus])
            if (asyncChildrenRoutes && asyncChildrenRoutes.length > 0) {
                asyncRoutes.children.push(...asyncChildrenRoutes)
            }
            router.addRoute(asyncRoutes)

            // 404路由最后添加，不优先匹配
            router.addRoute({
                path: '/:pathMatch(.*)*',
                name: 'NotFound',
                component: () => import('@/views/404.vue')
            })
            menusStore.setAsyncRoutes(asyncRoutes)

            // 继续导航到目标路由
            return {...to, replace: true}
        } catch (e) {
            clearAllAuthorized()
            return {name: 'Login'}
        }
    }

    if (whiteList.includes(to.name)) {
        return true
    }

    return {name: 'Login'}
})

router.afterEach((to) => {
    NProgress.done()
});

export default router
