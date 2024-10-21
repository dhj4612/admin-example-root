import {get, post} from "@/utils/request.js";

export const codeKey = 'code'
export const messageKey = 'msg'
export const dataKey = 'data'

const def_options = {showError: true, handlerError: e => console.log(e)}

const ApiWrapper = (apiAction, options = def_options) => {
    return new Promise(resolve => apiAction
        .then(result => {
            console.log(result)
            resolve([result, null])
        })
        .catch(e => {
            if (options?.showError) {
                // showToast(e[messageKey] || '未知错误')
            }
            if (options?.handlerError) {
                options.handlerError(e)
            }
            resolve([null, e])
        }))
}

export const exampleApi = (data = {}) => ApiWrapper(post('/example', data))

export const loginApi = (data = {}) => ApiWrapper(post('/sys/user/phone/login', data))

export const logoutApi = _ => ApiWrapper(post('/sys/user/logout'))

export const fetchAuthoritiesApi = _ => ApiWrapper(get('/sys/user/authorities'))

export const fetchUserInfoApi = _ => ApiWrapper(get('/sys/user/info'))

export const fetchUserMenuNavApi = _ => ApiWrapper(get('/sys/user/menu/nav'))

export const fetchMenuListApi = (params = {}) => ApiWrapper(get('/sys/manager/menu/list', params))

export const fetchMenuInfoApi = (data = {}) => ApiWrapper(post('/sys/manager/menu/info', data))

export const menuAddOrUpdateApi = (data = {}) => ApiWrapper(post('/sys/manager/menu-add-or-update', data))

export const menuDelApi = (data = {}) => ApiWrapper(post('/sys/manager/menu/del', data))

export const fetchRoleListApi = (data = {}) => ApiWrapper(post('/sys/manager/role/list', data))
