import * as types from './mutation-types'

const mutations = {
    [types.SET_TITLE](state, title) {
        state.title = title
    },
    [types.SET_WARNS_BASE](state, number) {
        state.warns.base = number
    },
    [types.SET_WARNS_REPLICATION](state, number) {
        state.warns.replication = number
    },
    [types.SET_BASE_DATA](state, data) {
        state.baseData = data
    },
    [types.SET_REPLICATION_DATA](state, data) {
        state.replicationData = data
    },
    [types.SET_MENUS](state, data) {
        state.menus = data
    },
    [types.SET_MENU_DESC](state, data) {
        state.menuDesc = data
    },
    [types.SET_EXPAND_REPLICATION](state, data) {
        state.expand.replications[data.host] = data.status
    }
}

export default mutations