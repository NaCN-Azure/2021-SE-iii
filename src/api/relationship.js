import { axios } from '@/utils/request'

const api = {
    relationshipPre: '/coinservice/relationship'
}

export function createLink(fromId, toId, name){
    return axios({
        url: `${api.relationshipPre}/createLink/${fromId}/${toId}/${name}`,
        method: 'post'
    })
}

export function updateLink(relationship){
    return axios({
        url: `${api.relationshipPre}/updateLink`,
        method: 'post',
        data: relationship
    })
}

//TODO
export function deleteLink(relationship){
    return axios({
        url: `${api.relationshipPre}/deleteLink`,
        method: 'post',
        data: relationship
    })
}