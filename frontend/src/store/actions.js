import * as types from './mutation-types.js'
import { relayWarn } from 'constants/constants'

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

export const setReplicationData = function({commit, state}, data) {
    commit(types.SET_REPLICATION_DATA, data)
    commit(types.SET_WARNS_REPLICATION, _getWarns(data.data))
}