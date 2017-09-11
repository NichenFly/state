import { basesUrl, basesByHostUrl } from 'constants/constants'
import axios from 'axios'

export function getBases() {
    return axios(basesUrl, {
        params: {
            format: 'json',
            time: new Date().getTime()
        }
    })
    .then((res) => Promise.resolve(res.data))
}

export function getBasesByHost(host) {
    return axios(basesByHostUrl, {
        params: {
            format: 'json',
            host,
            time: new Date().getDate()
        }
    }).then((res) => Promise.resolve(res.data))
}