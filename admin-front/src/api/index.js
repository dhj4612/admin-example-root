import {post, postFile} from "@/utils/request.js";

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
