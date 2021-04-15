import { axios } from '@/utils/request'

const api = {
    entityPre: '/coinservice/entity'
}

export function createNodeAPI(entity){
    return axios({
        url: `${api.entityPre}/createNode`,
        method: 'post',
        data: entity
    })
}

export function getNodesByDomainIdAPI(domainId){
    return axios({
        url: `${api.entityPre}/getNodesByDomainId/${domainId}`,
        method: 'get'
    })
}

export function updateNodeAPI(entity) {
    return axios({
        url: `${api.entityPre}/updateNode`,
        method: 'post',
        data: entity
    })
}

export function deleteNodeAPI(entity){
    return axios({
        url: `${api.entityPre}/deleteNode`,
        method: 'delete',
        data: entity
    })
}

export function countNodeAPI(domainId) {
    return axios({
        url: `${api.entityPre}/countNode/${domainId}`,
        method: 'get',
    })
}

export function updateXYAPI(entites) {
    return axios({
        url: `${api.entityPre}/updateXY`,
        method: 'post',
        data: entites
    })
}
