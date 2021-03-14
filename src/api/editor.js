import {axios} from "../utils/request";
const api = {
    editorPre: '/editor'
}

// 从文件创建图谱
export function createFromFileAPI(data) {
    return axios({
        url: `${api.editorPre}/`,
        method: 'POST',
        data
    })

}