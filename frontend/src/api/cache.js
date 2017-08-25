import { cachesUrl } from 'constants/constants'
import axios from 'axios'

export function getBases() {
    return axios(cachesUrl, {
        params: {
            format: 'json',
            time: new Date().getTime()
        }
    })
    .then((res) => Promise.resolve(res))
}