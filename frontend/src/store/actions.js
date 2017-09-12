import * as types from './mutation-types.js'
import { relayWarn, CODE_OK } from 'constants/constants'
import { getBases } from 'api/base'
import { getReplications } from 'api/replication'
import { expand } from 'common/js/expand'
import ReplicationExpand from '@/components/replication/replication-expand-row'
import { getMenus } from 'api/menu'

function _getWarns(data) {
    let num = 0
    data.forEach((item) => {
        if (item.hasError === 'yes') {
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

function _getReplicationData({commit, state}) {
    getReplications().then((res) => {
        if (res.code === CODE_OK) {
            // 加入展开的选项设置
            let data = res.data
            data.columns.unshift(expand(ReplicationExpand))

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
    _getReplicationData({commit, state})
}