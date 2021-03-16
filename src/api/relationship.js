import { axios } from '@/utils/request'

const api = {
    relationshipPre: '/coinservice/relationship'
}

export function createLinkAPI(fromId, toId, name){
    return axios({
        url: `${api.relationshipPre}/createLink/${fromId}/${toId}/${name}`,
        method: 'post'
    })
}

export function updateLinkAPI(relationship){
    return axios({
        url: `${api.relationshipPre}/updateLink`,
        method: 'post',
        data: relationship
    })
}

export function deleteLinkAPI(relationship){
    return axios({
        url: `${api.relationshipPre}/deleteLink`,
        method: 'post',
        data: relationship
    })
}

export function getLinkByDomainIdAPI(domainId){
    return axios({
        url: `${api.relationshipPre}/getLinkByDomainId/${domainId}`,
        method: 'get'
    })
}