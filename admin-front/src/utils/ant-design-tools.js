export const treeDataConvertByNameKeys = (treeData, {
    keyName = 'key',
    valueName = 'value',
    childrenName = 'children'
}) => {
    if (!treeData || treeData.length === 0) {
        return []
    }
    return JSON.parse(JSON.stringify(treeData)).map(item => {
        const {name, id, children} = item
        return {
            ...item,
            [keyName]: id,
            [valueName]: name,
            [childrenName]: treeDataConvertByNameKeys(children,
                {keyName, valueName, childrenName}),
        }
    });
}

export const deepFindNodeById = (id, src) => {
    for (let i = 0; i < src.length; i++) {
        if (src[i].id === id) {
            return src[i]
        }
        if (src[i].children && src[i].children.length) {
            return deepFindNodeById(id, src[i].children)
        }
    }
}

export const collectFullPathTreeIds = (id, src) => {
    const fullIds = [id]
    const currentNode = deepFindNodeById(id, src)
    if (currentNode) {
        if (currentNode.pid !== 0) {
            fullIds.push(...collectFullPathTreeIds(currentNode.pid, src))
        }
    }
    return fullIds
}
