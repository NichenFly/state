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

export function getOriginUrl() {
    let cookie = document.cookie
    let sessions = cookie.split(';')
    for (let i = 0; i < sessions.length; i++) {
        let session = sessions[i]
        if (session.startsWith('PLAY_FLASH=url=')) {
            return decodeURIComponent(session.replace('PLAY_FLASH=url=', ''))
        }
    }
    return ''
}