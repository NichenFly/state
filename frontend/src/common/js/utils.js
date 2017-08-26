// 处理list, 返回二维嵌套数组
export function resolveListTo2 (dataList) {
    let list = []
    let tmpList = []
    dataList.forEach((base, index) => {
        tmpList.push(base)
        if (tmpList.length === 2) {
            list.push(tmpList)
            tmpList = []
        }
        if (index === dataList.length - 1) {
            list.push(tmpList)
        }
    })
    return list
}