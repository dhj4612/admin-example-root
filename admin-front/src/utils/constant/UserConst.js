export const Gender = {
    Man: {
        code: 0,
        name: '男'
    },
    Woman: {
        code: 1,
        name: '女'
    },
    Unknown: {
        code: 2,
        name: '未知'
    },
    excludeCodes: function (codes) {
        if (!Array.isArray(codes)) {
            codes = [codes]
        }
        return this.toList().filter(item => codes.indexOf(item['code']) === -1)
    },
    toList: function () {
        return Object.keys(this).map(key => this[key]).filter(item => typeof item !== 'function')
    },
    getNameByCode: function (code) {
        const stateList = this.toList()
        if ((code || code === 0)) {
            return stateList.find(item => item.code === code)?.name ?? ''
        }
        return ''
    }
}

export const Status = {
    // 0-禁用 1-正常
    Disabled: {
        code: 0,
        name: '禁用'
    },
    Normal: {
        code: 1,
        name: '正常'
    },
    excludeCodes: function (codes) {
        if (!Array.isArray(codes)) {
            codes = [codes]
        }
        return this.toList().filter(item => codes.indexOf(item['code']) === -1)
    },
    toList: function () {
        return Object.keys(this).map(key => this[key]).filter(item => typeof item !== 'function')
    },
    getNameByCode: function (code) {
        const stateList = this.toList()
        if ((code || code === 0)) {
            return stateList.find(item => item.code === code)?.name ?? ''
        }
        return ''
    }
}
