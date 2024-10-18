/**
 * 防抖函数，固定时间段内，多次触发只执行最后一次
 * @param fnc
 * @param wait
 */
export const debounce = (fnc, wait = 1000) => {
    let timeout
    return function (...args) {
        const ctx = this
        clearTimeout(timeout)
        timeout = setTimeout(() => fnc.apply(ctx, args), wait)
    }
}

/**
 * 节流函数，固定时间段内，只执行一次
 * @param fnc
 * @param wait
 */
export const throttle = (fnc, wait = 1000) => {
    let inThrottle = false
    return function (...args) {
        const ctx = this
        if (!inThrottle) {
            fnc.apply(ctx, args)
            inThrottle = true
            setTimeout(() => inThrottle = false, wait)
        }
    }
}

/**
 * 路径转换为驼峰形式 /login/user => LoginUser
 * @param path
 * @returns {*}
 */
export const pathToCamel = (path) => {
    return path.replace(/\/(\w)/g, (all, letter) => letter.toUpperCase())
}

/**
 * 等待指定毫秒时间
 * @param delay
 * @return {Promise<unknown>}
 */
export function wait(delay = 1000) {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve()
        }, delay)
    })
}

/**
 * 获取 url 参数，转化为对象
 * @return {{[p: string]: string}}
 */
export function urlParam() {
    // 创建一个URLSearchParams实例
    const urlSearchParams = new URLSearchParams(window.location.search);
    // 把键值对列表转换为一个对象
    return Object.fromEntries(urlSearchParams.entries());
}

/**
 * 格式金额
 * @param money 值
 * @param maximumFractionDigits 使用的小数位数的最大数目，可能的值是从 0 到 20
 * @param minimumFractionDigits 使用的小数位数的最小数目，可能的值是从 0 到 20
 * @param minimumIntegerDigits 使用的整数数字的最小数目.可能的值是从 1 到 21,默认值是 1
 * @param useGrouping 是否使用分组分隔符，如千位分隔符或千/万/亿分隔符，默认为 true
 * @return {string}
 */
export const formatMoney = (money, minimumIntegerDigits = 1, minimumFractionDigits = 2, maximumFractionDigits = 2, useGrouping = true) => {
    return new Intl.NumberFormat("zh-CN", {
        style: 'currency',
        currency: 'CNY',
        currencyDisplay: 'symbol',
        useGrouping,
        minimumIntegerDigits,
        minimumFractionDigits,
        maximumFractionDigits
    }).format(money);
};

/**
 * 格式化金额（带￥符号）
 * @param money 数值金额
 * @param useGrouping 是否使用分组符号
 * @param prefix 前缀
 * @param suffix 后缀
 * @return string
 */
export function formatAmountSymbol({money, useGrouping = true, prefix = '', suffix = ''}) {
    if (Number.isNaN(Number(money))) {
        return ''
    }
    if (money || money === 0) {
        return `${prefix}${formatMoney(money, 1, 2, 2, useGrouping)}${suffix}`
    }
    return ''
}

/**
 * 格式化金额，无符号
 * @param money
 * @param useGrouping 是否使用分组符号
 * @param prefix 前缀
 * @param suffix 后缀
 * @return string
 */
export function formatAmount({money, useGrouping = true, prefix = '', suffix = ''}) {
    if (Number.isNaN(Number(money))) {
        return ''
    }
    if (money || money === 0) {
        return `${prefix}${formatMoney(money, 1, 2, 2, useGrouping)
            .replaceAll('¥', '')}${suffix}`
    }
    return ''
}

/**
 * 根据屏幕宽度和 ui 像素大小计算视口单位
 * 实际上是计算 px 单位占总视口单位的百分之几
 */
export const pxTvw = (px, viewport = import.meta.env.VITE_VIEWPORT_WIDTH) =>
    `${(px / Number.parseInt(viewport)) * 100}vw`
