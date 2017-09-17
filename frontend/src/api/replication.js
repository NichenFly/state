import { replicationsUrl, replicationByHostUrl } from 'constants/constants'
import axios from 'axios'

export function getReplications() {
    return axios(replicationsUrl, {
        params: {
            format: 'json',
            time: new Date().getTime()
        }
    })
    .then((res) => Promise.resolve(res.data))
}

export function getReplicationByHost(host) {
    return axios(replicationByHostUrl, {
        params: {
            host: host,
            time: Date.now()
        }
    })
    .then((res) => Promise.resolve(res.data))
}