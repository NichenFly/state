import { menusUrl } from 'constants/constants'
import axios from 'axios'

export function getMenus() {
    return axios(menusUrl, {
        params: {
            format: 'json',
            time: new Date().getTime()
        }
    })
    .then((res) => Promise.resolve(res.data))
}