import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'GET'
    })
}

export function getMarketerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllMarketers`,
        method: 'GET'
    })
}

export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}

export function addMarketerAPI(data){
    return axios({
        url: `${api.adminPre}/addMarketer`,
        method: 'POST',
        data
    })
}

export function deleteUserAPI(userId) {
    return axios({
        url:`${api.adminPre}/${userId}/moveUser`,
        method:'GET',
    })
}

export function chooseManagerAPI(hotelId) {
    return axios({
        url:`${api.adminPre}/${hotelId}/chooseManager`,
        method:'GET',
    })
}

export function changeManagerAPI(data) {
    return axios({
        url:`${api.adminPre}/${data.hotelId}/${data.managerID}/changeManager`,
        method:'POST',
        data
    })
}

export function reviseUserInfoAPI(data) {
    return axios({
        url:`${api.adminPre}/${data.id}/reviseUserInfo`,
        method:'POST',
        data
    })

}
