import {axios} from "../utils/request";
const api = {
    filePre: '/userservice/users'
}

//登录
export function loginAPI(userInfo) {
    return axios({
        url: `${api.filePre}/login`,
        method: 'post',
        data: userInfo
    })
}

//根据token获取用户信息
export function getUserInfoAPI() {
    return axios({
        url: `${api.filePre}/getMemberInfo`,
        method: 'get'
    })
}
