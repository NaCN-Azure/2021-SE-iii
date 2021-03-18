import { axios } from '@/utils/request'

const api = {
    entityPre: '/coinservice/entity'
}

//TODO，前端可不可以直接传一个对象回去？和后端讨论ing
export function createNodeAPI(name, color, type, domainId){
    return axios({
        url: `${api.entityPre}/createNode/${name}/${color}/${type}/${domainId}`,
        method: 'post'
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

export function testAPI() {
    return axios({
        url:`${api.entityPre}/test`,
        method:'get',
    })

}
