import * as types from './mutation-types'

const mutations = {
    [types.SET_TITLE](state, title) {
        state.title = title
    },
    [types.SET_BASE_DATA](state, data) {
        this.baseData = data
    },
    [types.SET_CACHE_DATA](state, data) {
        this.cacheData = data
    },
    [types.SET_REPLICATION_DATA](state, data) {
        this.replicationData = data
    }
}

export default mutations