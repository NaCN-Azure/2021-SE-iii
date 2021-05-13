import {axios} from "../utils/request";
const api = {
    registerPre: '/userservice/users'
}

// 注册
export function registerAPI(formItem) {
    return axios({
        url: `${api.registerPre}/register`,
        method: 'post',
        data: formItem
    })
}
