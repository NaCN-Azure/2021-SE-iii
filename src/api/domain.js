import {axios} from "../utils/request";
const api = {
    domainPre: '/coinservice/domain'
}

export function createDomainAPI(domain) {
    return axios({
        url: `${api.domainPre}/createDomain`,
        method: 'POST',
        data: domain
    })
}

export function updateDomainAPI(domain) {
    return axios({
        url: `${api.domainPre}/updateDomain`,
        method: 'POST',
        data: domain
    })
}

export function deleteDomainAPI(domainId) {
    return axios({
        url: `${api.domainPre}/deleteDomain/${domainId}`,
        method: 'delete'
    })
}

export function selectDomainByIdAPI(domainId) {
    return axios({
        url: `${api.domainPre}/selectDomainByIdAPI/${domainId}`,
        method: 'get'
    })
}

export function selectAllDomainAPI(userId) {
    return axios({
        url: `${api.domainPre}/selectAllDomain/${userId}`,
        method: 'get'
    })
}
