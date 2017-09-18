import * as types from './mutation-types.js'
import { relayWarn, CODE_OK, STATE_YES_STRING } from 'constants/constants'
import { getBases } from 'api/base'
import { getReplications } from 'api/replication'
// import { expand } from 'common/js/expand'
// import ReplicationExpand from '@/components/replication/replication-expand-row'
import { getMenus } from 'api/menu'

function _getReplicationWarns(data) {
    let num = 0
    data.forEach((item) => {
        if (item.hasError === STATE_YES_STRING) {
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
            if (JSON.stringify(state.baseData) === JSON.stringify(res.data)) {
                return
            }
            let data = res.data
            let errors = 0
            data.forEach((item) => {
                if (item.hasError) {
                    errors++
                }
            })
            commit(types.SET_WARNS_BASE, errors)
            commit(types.SET_BASE_DATA, res.data)
        }
    })
}

function _getNodes(data) {
    let nodes = {}
    data.forEach((node) => {
        nodes[node.name] = node.hasError
    })
    return nodes
}

function _addMasters(data) {
    let masters = []
    data.forEach(function(item) {
        var masterHosts = item.masters
        masterHosts.forEach(function(host) {
            if (!~masters.indexOf(host)) {
                masters.push(host)
            }
        })
    })

    let masterObjs = {}
    masters.forEach(function(item) {
        masterObjs[item] = []
    })

    data.forEach(function(item) {
        let masterHosts = item.masters
        masterHosts.forEach(function(master) {
            masterObjs[master].push({
                name: item.name
            })
        })
    })

    let masterArrays = []
    for (let key in masterObjs) {
        masterArrays.push({
            name: key,
            slaves: masterObjs[key]
        })
    }
    return masterArrays
}

function _addGroups(masterArrays) {
    let groups = []
    for (let i = 0; i < masterArrays.length; i++) {
        let prevMaster = masterArrays[i]
        let j = i + 1
        for (; j < masterArrays.length; j++) {
            let thisMaster = masterArrays[j]
            let k = 0
            for (; k < prevMaster.slaves.length; k++) {
                let prevMasterSlave = prevMaster.slaves[k]
                let isFound = thisMaster.slaves.find(function(thisMasterSlave) {
                    return thisMasterSlave.name === prevMasterSlave.name
                })

                // 如果找到了, 这两个都加入到同一个组的master里, 并且其对应的slave加入到组里的slaves
                if (isFound) {
                    let foundInGroups = groups.find(function(group) {
                        return ~group.masters.indexOf(prevMaster.name) || ~group.masters.indexOf(thisMaster)
                    })
                    if (foundInGroups) {
                        if (!~foundInGroups.master.indexOf(prevMaster.name)) {
                            foundInGroups.masters.push(prevMaster.name)
                        } else {
                            foundInGroups.masters.push(thisMaster.name)
                        }
                    } else {
                        let group = {}
                        group.masters = []
                        group.masters.push(prevMaster.name)
                        group.masters.push(thisMaster.name)
                        group.slaves = []
                        prevMaster.slaves.forEach(function(slave) {
                            if (!~group.slaves.indexOf(slave.name) && !~group.masters.indexOf(slave.name)) {
                                group.slaves.push(slave.name)
                            }
                        })
                        thisMaster.slaves.forEach(function(slave) {
                            if (!~group.slaves.indexOf(slave.name) && !~group.masters.indexOf(slave.name)) {
                                group.slaves.push(slave.name)
                            }
                        })
                        groups.push(group)
                    }
                    break
                }
            }
            if (k < prevMaster.slaves.length) {
                break
            }
        }
        if (j === masterArrays.length) {
            let foundInGroups = groups.find(function(group) {
                return ~group.masters.indexOf(prevMaster.name)
            })
            if (!foundInGroups) {
                groups.push({
                    masters: [prevMaster.name],
                    slaves: prevMaster.slaves.map(function(item) {
                        return item.name
                    })
                })
            }
        }
    }
    return groups
}

function _getReplicationData({commit, state}) {
    getReplications().then((res) => {
        if (res.code === CODE_OK) {
            // 加入展开的选项设置
            let data = res.data

            let nodes = _getNodes(data)

            if (JSON.stringify(state.replicationData.nodes) === JSON.stringify(nodes)) {
                return
            }

            let masters = _addMasters(data)
            let groups = _addGroups(masters)

            commit(types.SET_REPLICATION_NODES, nodes)
            commit(types.SET_REPLICATION_MASTERS, masters)
            commit(types.SET_REPLICATION_GROUPS, groups)
            commit(types.SET_WARNS_REPLICATION, _getReplicationWarns(data))
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