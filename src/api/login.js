import {axios} from "../utils/request";
const api = {
    loginPre: '/userservice/users'
}

//登录
export function loginAPI(userInfo) {
    return axios({
        url: `${api.loginPre}/login`,
        method: 'post',
        data: userInfo
    })
}

//根据token获取用户信息
export function getUserInfoAPI() {
    return axios({
        url: `${api.loginPre}/getMemberInfo`,
        method: 'get'
    })
}
