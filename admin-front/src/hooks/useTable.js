import {reactive, ref, toRaw} from 'vue'
import {message} from 'ant-design-vue';

/**
 * 默认表格数据处理器
 * @param sourceData
 * @constructor
 */
export const DefaultDataHandler = async (sourceData) => sourceData

/**
 * 默认请求失败处理器
 * @param e
 * @return {Promise<void>}
 * @constructor
 */
export const DefaultErrHandler = async (e) => {
    console.log(e)
    message.warn(e?.msg || '未知错误')
}

/**
 * ant-vue 表格操作的 hooks，返回与表格操作相关的一系列响应式状态
 * @param fetchApi 数据获取的异步函数
 * @param dataHandler 自定义的数据处理函数
 * @param searchParams 数据查询参数
 * @param option 其他选项，包括自定义 pagination 参数
 * @param handlerErr 自定义错误处理逻辑
 */
export function useTable({
                             fetchApi = () => Promise.resolve([[], null]),
                             dataHandler = DefaultDataHandler,
                             searchParams = {},
                             option = {},
                             handlerErr = DefaultErrHandler
                         }) {
    const dataSource = ref([])
    const searchFormState = reactive(searchParams)
    const searchFormRef = ref()
    let loading = ref(false)
    let pagination = reactive({
        current: option['current'] ?? 1,
        pageSize: option['pageSize'] ?? 10,
        showSizeChanger: option['sizeChanger'] || false,
        total: 0,
        showTotal(total) {
            return `共 ${total} 项`;
        }
    })

    async function handlerSubmit() {
        pagination.current = 1
        await fetchTableData()
    }

    async function handleTableChange(pag, filters, sorter) {
        pagination.current = pag.current
        if (pagination.showSizeChanger) {
            pagination.pageSize = pag.pageSize
        }
        return fetchTableData()
    }

    async function fetchTableData() {
        dataSource.value = []
        loading.value = true
        const searchParam = toRaw(searchFormState)
        const [res, err] = await fetchApi({
            ...searchParam, pn: pagination.current, ps: pagination.pageSize
        })
        if (err) {
            return DefaultErrHandler(err)
        }
        const items = res['records']
        const total = res['total']
        let handlerResult = await dataHandler(items)
        dataSource.value = handlerResult ?? items
        pagination.total = total ?? 0
        loading.value = false
    }

    async function resetForm() {
        searchFormRef.value.resetFields();
        pagination.current = 1
        await fetchTableData()
    }

    return {
        dataSource,
        loading,
        pagination,
        searchFormState,
        searchFormRef,
        handlerSubmit,
        handleTableChange,
        resetForm,
        fetchTableData
    }
}
