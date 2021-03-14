import { axios } from '@/utils/request'

const api = {
    entityPre: '/coinservice/entity'
}

//TODO
export function createNode(name, color, type, domainId){
    return axios({
        url: `${api.entityPre}/createnode/${name}/${color}/${type}/${domainId}`,
        method: 'post'
    })
}

export function getNodesByDomainId(domainId){
    return axios({
        url: `${api.entityPre}/getNodesByDomainId/${domainId}`,
        method: 'get'
    })
}

export function updateNode(entity) {
    return axios({
        url: `${api.entityPre}/updateNode`,
        method: 'post',
        data: entity
    })
}

//TODO
export function deleteNode(entity){
    return axios({
        url: `${api.entityPre}/deleteNode`,
        method: 'post',
        data: entity
    })
}