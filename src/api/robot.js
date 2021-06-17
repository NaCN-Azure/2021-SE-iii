import { axios } from '@/utils/request'

const api = {
    robotPre: '106.15.93.81:5000/'
}

export function addNodeNameAPI(name){
    return axios({
        url: `${api.robotPre}/addNodeName/${name}`,
        method: 'get'
    })
}

export function addTypeNameAPI(type){
    return axios({
        url: `${api.robotPre}/addTypeName/${type}`,
        method: 'get'
    })
}

export function addLinkNameAPI(name){
    return axios({
        url: `${api.robotPre}/addLinkName/${name}`,
        method: 'get'
    })
}

