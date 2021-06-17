import {axios} from "../utils/request";
const api = {
    questionPre: '/coinservice/question'
}

export function askQuestionAPI(domainId, question) {
    return axios({
        url: `${api.questionPre}/ask/${domainId}/${question}`,
        method: 'get',
    })
}
