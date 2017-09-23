import { loginUrl } from 'constants/constants'
import axios from 'axios'
import qs from 'querystring'

export function login(params) {
    return axios.post(loginUrl, qs.stringify(params))
    .then((res) => Promise.resolve(res.data))
    .catch((error) => {
        console.log(error)
    })
}