import * as types from './mutation-types.js'
import { relayWarn, CODE_OK } from 'constants/constants'
import { getBases } from 'api/base'
import { getCaches } from 'api/cache'
import { getReplications } from 'api/replication'
import { expand } from 'common/js/expand'
import CacheExpand from '@/components/cache/cache-expand-row'
import ReplicationExpand from '@/components/replication/replication-expand-row'
import { resolveListTo2 } from 'common/js/utils'
import { getMenus } from 'api/menu'

function _getWarns(data) {
    let num = 0
    data.forEach((item) => {
        if (item.hasError) {
            num++
        } else if (item.relay > relayWarn) {
            num++
        }
    })
    return num
}

function _getBaseData({commit, state}) {
    getBases().then((res) => {
        if (res.code === CODE_OK) {
            // this.setBaseData(res.data)
            commit(types.SET_BASE_DATA, res.data)
        }
    })
}

function _getCacheData({commit, state}) {
    getCaches().then((res) => {
        if (res.code === CODE_OK) {
            // 加入展开的选项设置
            res.data.forEach(cache => {
                cache.columns.unshift(expand(CacheExpand))

                // 为保存展开状态做的变量保存,在传递事件时会带着,用于定位具体的某个数据项
                cache.data.forEach((d) => {
                    d.titleVarible = `${cache.title}_${d.variable}`
                    if (state.expand.caches[d.titleVarible]) {
                        d._expanded = true
                    }
                })
            })
            // this.setCacheData(resolveListTo2(res.data))
            commit(types.SET_CACHE_DATA, resolveListTo2(res.data))
        }
    })
}

function _getReplicationData({commit, state}) {
    getReplications().then((res) => {
        if (res.code === CODE_OK) {
            // 加入展开的选项设置
            let data = res.data
            data.columns.unshift(expand(ReplicationExpand))

            // expand的状态设置
            data.data.forEach((d) => {
                if (state.expand.replications[d.host]) {
                    d._expanded = true
                }
            })
            // this.setReplicationData(data)
            commit(types.SET_REPLICATION_DATA, data)
            commit(types.SET_WARNS_REPLICATION, _getWarns(data.data))
        }
    })
}

export const getMenu = function({commit, state}) {
    getMenus().then((res) => {
        if (res.code === CODE_OK) {
            let menuData = res.data
            if (menuData.length > 0) {
                menuData.sort((a, b) => a.orderNum - b.orderNum)
                let menuDesc = {}
                menuData.forEach((menu) => {
                    // 子菜单排序
                    menu.subMenus.sort((a, b) => a.orderNum - b.orderNum)
                    menu.subMenus.forEach((subMenu) => {
                        menuDesc[subMenu.path] = subMenu.desc
                    })
                })
                commit(types.SET_MENU_DESC, menuDesc)
                commit(types.SET_MENUS, menuData)
            }
        } else {
            this.$Notice.error({
                title: '获取菜单错误',
                desc: '请检查服务器连接是否正确'
            })
        }
    })
}

export const setData = function({commit, state}) {
    _getBaseData({commit, state})
    _getCacheData({commit, state})
    _getReplicationData({commit, state})
}