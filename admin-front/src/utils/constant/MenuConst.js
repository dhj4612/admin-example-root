export const OpenStyleConst = {
    Inner: {
        code: 0,
        name: '内部'
    },
    Extern: {
        code: 1,
        name: '外部'
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
