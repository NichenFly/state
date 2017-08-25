import { basesUrl } from 'constants/constants'
import axios from 'axios'

export function getBases() {
    return axios(basesUrl, {
        params: {
            format: 'json',
            time: new Date().getTime()
        }
    })
    .then((res) => Promise.resolve(res))
}