import {useUserStore} from "@/stores/user.js";
import {useMenusStore} from "@/stores/menus.js";

export const AuthorizationKey = 'Authorization'
export const RefreshAuthorizationKey = 'RefreshAuthorization'

export const getAuthorization = _ => localStorage.getItem(AuthorizationKey)
export const getRefreshAuthorization = _ => localStorage.getItem(RefreshAuthorizationKey)

export const setAuthorization = (authorization) => localStorage.setItem(AuthorizationKey, authorization)
export const setRefreshAuthorization = (refreshAuthorization) => localStorage.setItem(RefreshAuthorizationKey, refreshAuthorization)

export const hasAuthorization = () => !!localStorage.getItem(AuthorizationKey)
export const hasRefreshAuthorization = () => !!localStorage.getItem(RefreshAuthorizationKey)

export const removeAuthorization = () => localStorage.removeItem(AuthorizationKey)
export const removeRefreshAuthorization = () => localStorage.removeItem(RefreshAuthorizationKey)

export const clearAll = _ => {
    removeAuthorization()
    removeRefreshAuthorization()
    useUserStore().clearUserInfo()
    useMenusStore().clearMenus()
}
