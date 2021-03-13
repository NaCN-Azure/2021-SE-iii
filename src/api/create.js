import {axios} from "../utils/request";
const api = {
    createPre: '/'
}

export function createFromFileAPI(data) {
    return axios({
        url: `${api.createPre}/kgeditor`,
        method: 'POST',
        data
    })

}