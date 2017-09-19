const debug = process.env.NODE_ENV !== 'production'

// 请求的返回状态
export const CODE_OK = 200

// 获取惨淡数据的url
export const menusUrl = debug ? '/static/api/menus.json' : '/api/menuController/getMenus'

// 获取基础数据的url
export const basesUrl = debug ? '/static/api/bases.json' : '/api/mysqlController/getbases'

export const basesByHostUrl = debug ? '/static/api/getbases.json' : '/api/mysqlController/getBasesByHost'

// 获取复制信息的url
export const replicationsUrl = debug ? '/static/api/replications.json' : '/api/mysqlController/getReplications'

export const replicationByHostUrl = debug ? '/static/api/replication-items.json' : '/api/mysqlController/getReplicationsByHost'

// 轮询间隔 5000ms
export const intervalTime = 5000

// 展开单元格的默认宽度
export const expandWidth = 50

// 复制延迟的警告值
export const relayWarn = 10

export const STATE_HAS_ERROR = true

// 标题显示内容
export const basePath = '/bases'
export const replicationPath = '/replications'

export const showExcepts = [
    'host',
    'masterSlave',
    'state',
    'hasError'
]

// 基本信息界面弹出框的展开按钮的宽度
export const baseExpandWidth = 50