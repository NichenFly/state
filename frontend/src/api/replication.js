import { replicationsUrl } from 'constants/constants'
import axios from 'axios'

export function getBases() {
    return axios(replicationsUrl, {
        params: {
            format: 'json',
            time: new Date().getTime()
        }
    })
    .then((res) => Promise.resolve(res))
}