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
