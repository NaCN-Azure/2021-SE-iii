import {axios} from "../utils/request";
const api = {
    userPre: '/userservice/users'
}

//登录
export function loginAPI(userInfo) {
    return axios({
        url: `${api.userPre}/login`,
        method: 'post',
        data: userInfo
    })
}

//根据token获取用户信息
export function getUserInfoAPI() {
    return axios({
        url: `${api.userPre}/getMemberInfo`,
        method: 'get'
    })
}

// 注册
export function registerAPI(formItem) {
    return axios({
        url: `${api.userPre}/register`,
        method: 'post',
        data: formItem
    })
}

//更新用户信息
export function updateUserInfoAPI(userInfo) {
    return axios({
        url: `${api.userPre}/updateUserInfo`,
        method: 'post',
        data: userInfo
    })
}

//修改用户密码
export function updateUserPwdAPI(modifyPwdForm) {
    return axios({
        url: `${api.userPre}/updateUserPwd`,
        method: 'post',
        data: modifyPwdForm
    })
}
