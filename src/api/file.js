import {axios} from "../utils/request";
const api = {
    filePre: '/coinservice/file'
}

export function getCsvFileAPI(file) {
    return axios({
        url: `${api.filePre}/getCsv`,
        method: 'POST',
        data: file
    })
}

export function exportGraphXMLAPI(domainId) {
    return axios({
        url: `${api.filePre}/exportXml/${domainId}`,
        method: 'get'
    })
}

export function downloadAPI(domainName,type) {
    return axios({
        url: `${api.filePre}/download/${domainName}/${type}`,
        method: 'get'
    })
}
