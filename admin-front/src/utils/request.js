import axios from 'axios'
import {clearAllAuthorized, getAuthorization, hasAuthorization} from '@/utils/authorization.js'
import {AuthorizationKey} from "./authorization.js";
import {useRouter} from "vue-router";

const instance = axios.create({
    baseURL: '/api', timeout: 30000,
})

instance.interceptors.request.use(
    config => {
        if (hasAuthorization()) {
            config.headers[AuthorizationKey] = `Bearer ${getAuthorization()}`
        }
        return config
    },
    e => Promise.reject(e))

instance.interceptors.response.use(
    response => {
        console.log('axios response=>', response)
        const {status, data} = response
        if (status === 200 && (data?.code.toString() === '200')) {
            return Promise.resolve(data?.data || {})
        }
        if (status === 401 || data?.code === '401') {
            clearAllAuthorized()
            useRouter().replace('/login')
            return Promise.reject()
        }
        return Promise.reject(response?.data)
    },
    e => Promise.reject(e?.response?.data))

export const post = (url, data, config) => instance.post(url, data, config)
export const get = (url, params, config) => instance.get(url, {params, ...config})
export const postForm = (url, data, config) => instance.post(url, data, {
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    }, ...config,
})

export function postFile(url, data, config) {
    const formData = new FormData();
    Object.keys(data || {}).forEach(key => formData.append(key, data[key]));
    return post(url, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }, ...config
    });
}
