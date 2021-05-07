import {axios} from "../utils/request";
const api = {
    filePre: '/userservice/users'
}

// 注册
export function registerMember(formItem) {
    return axios({
        url: `${api.filePre}/register`,
        method: 'post',
        data: formItem
    })
}
