import * as types from './mutation-types'

const mutations = {
    [types.SET_TITLE](state, title) {
        state.title = title
    },
    [types.SET_WARNS_BASE](state, number) {
        state.warns.base = number
    },
    [types.SET_WARNS_CACHE](state, number) {
        state.warns.cache = number
    },
    [types.SET_WARNS_REPLICATION](state, number) {
        state.warns.replication = number
    },
    [types.SET_BASE_DATA](state, data) {
        state.baseData = data
    },
    [types.SET_CACHE_DATA](state, data) {
        state.cacheData = data
    },
    [types.SET_REPLICATION_DATA](state, data) {
        state.replicationData = data
    }
}

export default mutations