export const menuDataToAntTree = menuData => {
    if (!menuData || menuData.length === 0) {
        return []
    }
    return JSON.parse(JSON.stringify(menuData)).map(({name, id, children}) => {
        return {
            label: name,
            value: id,
            children: menuDataToAntTree(children)
        }
    });
}
