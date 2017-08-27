// 请求的返回状态
export const CODE_OK = 200

// 获取惨淡数据的url
// export const menusUrl = '/static/api/menus.json'
export const menusUrl = '/api/menuController/getMenus'

// 获取基础数据的url
// /api/getBases
export const basesUrl = '/static/api/bases.json'

// 获取缓存数据的url
// /api/getCaches
export const cachesUrl = '/static/api/caches.json'

// 获取复制信息的url
// /api/getreplications
export const replicationsUrl = '/static/api/replications.json'

// 轮询间隔 5000ms
export const intervalTime = 5000

// 展开单元格的默认宽度
export const expandWidth = 50

// 复制延迟的警告值
export const relayWarn = 10

// 标题显示内容
export const infoTitleBase = '展示数据库的基本信息'
export const infoTitleCache = '展示数据库的复制状态信息'
export const infoTitleReplication = '展示数据的主从复制信息'