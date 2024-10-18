import {ref} from 'vue'
import {defineStore} from 'pinia'
import {fetchUserMenuNavApi} from "@/api/index.js";

export const useMenusStore = defineStore('menus', () => {
    const menus = ref([])
    const asyncRoutes = ref([])

    async function fetchMenus() {
        const [res, err] = await fetchUserMenuNavApi()
        if (err) throw err
        menus.value = res || []
    }

    function setAsyncRoutes(routes) {
        asyncRoutes.value = routes
    }

    function clearMenus() {
        menus.value = []
        asyncRoutes.value = []
    }

    return {
        menus,
        asyncRoutes,
        fetchMenus,
        setAsyncRoutes,
        clearMenus
    }
})
