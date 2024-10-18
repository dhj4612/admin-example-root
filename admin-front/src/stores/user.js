import {reactive} from 'vue'
import {defineStore} from 'pinia'
import {fetchAuthoritiesApi, fetchUserInfoApi} from "@/api/index.js";

export const useUserStore = defineStore('user', () => {
    const user = reactive({
        username: '',
        superAdmin: false,
        avatar: '',
        authorities: []
    })

    function clearUserInfo() {
        user.username = ''
        user.superAdmin = false
        user.avatar = ''
        user.authorities = []
    }

    function setUser({username, superAdmin, avatar}) {
        user.username = username
        user.superAdmin = superAdmin
        user.avatar = avatar
    }

    async function fetchAuthorities() {
        const [res, err] = await fetchAuthoritiesApi()
        if (err) throw err
        user.authorities = res || []
    }

    function hasUserInfo() {
        return !!user.username
    }

    function hasAuthorities(auth) {
        if (user.authorities.includes('*')) {
            return true;
        }
        if (!Array.isArray(auth)) {
            auth = [auth]
        }
        return auth.every(item => user.authorities.includes(item))
    }

    async function fetchUserInfo() {
        const [res, err] = await fetchUserInfoApi()
        if (err) throw err
        setUser(res)
    }

    return {
        user,
        setUser,
        fetchUserInfo,
        fetchAuthorities,
        hasUserInfo,
        clearUserInfo,
        hasAuthorities
    }
})
