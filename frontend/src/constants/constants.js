// 请求的返回状态
export const CODE_OK = 200

// 获取惨淡数据的url
// export const menusUrl = '/static/api/menus.json'
export const menusUrl = '/api/menuController/getMenus'

// 获取基础数据的url
// export const basesUrl = '/static/api/getbases.json'
export const basesUrl = '/api/mysqlController/getbases'

// 获取缓存数据的url
// export const cachesUrl = '/static/api/getcaches.json'
export const cachesUrl = '/api/mysqlController/getcaches'

// 获取复制信息的url
// export const replicationsUrl = '/static/api/getReplications.json'
export const replicationsUrl = '/api/mysqlController/getReplications'

// 轮询间隔 5000ms
export const intervalTime = 5000

// 展开单元格的默认宽度
export const expandWidth = 50

// 复制延迟的警告值
export const relayWarn = 10

// 标题显示内容
export const basePath = '/bases'
export const cachePath = '/caches'
export const replicationPath = '/replications'

export const showExcepts = [
    'host',
    'masterSlave',
    'state',
    'hasError'
]