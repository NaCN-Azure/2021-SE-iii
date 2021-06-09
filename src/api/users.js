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
export function updateUserInfoAPI(id, nickname, sign) {
    return axios({
        url: `${api.userPre}/updateUserInfo/${id}/${nickname}/${sign}`,
        method: 'post'
    })
}

//修改用户密码
export function updateUserPwdAPI(id, oldPwd, newPwd) {
    return axios({
        url: `${api.userPre}/updateUserPwd/${id}/${oldPwd}/${newPwd}`,
        method: 'post',
    })
}

export function resetPwdAPI(mobile,password) {
    return axios({
        url: `${api.userPre}/resetPwd/${mobile}/${password}`,
        method: 'post',
    })
}

export function updateAvatarAPI(avatarUpdater) {
    return axios({
        url: `${api.userPre}/updateAvatar`,
        method:'post',
        data: avatarUpdater
    })
}

export function closeAccountAPI(id){
    return axios({
        url:`${api.userPre}/deleteUser/${id}`,
        method: 'delete',
    })
}
