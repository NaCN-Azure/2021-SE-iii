import {loginAPI, getUserInfoAPI} from "../../api/login";
import router from "../../router";
import {registerAPI} from "../../api/register";
import {Message} from "element-ui";

const user = {
    state: {
        isLogin: false,
        userId: '',
        userInfo:{
            // String id;
            // String mobile;
            // String password;
            // String nickname;
            // String avatar;
            // Boolean isDisabled;
            // Boolean isDeleted;
            // String sign;
        },

    },
    mutations: {
        set_isLogin: function (state, data) {
            state.isLogin = data;
        },
        set_userId: function (state, data){
            state.userId = data;
        },
        set_userInfo: function (state, data) {
            state.userInfo = data;
        }
    },
    actions: {
        login: async ({commit, dispatch}, loginData) => {
            const res = await loginAPI(loginData);
            if (res.data.code == 200) {
                commit('set_isLogin', true)
                dispatch('getUserInfo')
                router.push('/home')
            }
        },
        getUserInfo: async ({state, commit}) => {
            return new Promise((resolve, reject) => {
                getUserInfoAPI(state.userId).then(response => {
                    const data = response
                    if (!data) {
                        reject('登录已过期，请重新登录')
                    }
                    commit('set_userInfo', data)
                    commit('set_userId', data.id)
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        register: async ({state, commit}, registerData) => {
            const res = await registerAPI(registerData);
            if(res.data.code == 200){
                Message({
                    message: '注册成功',
                    type: 'success'
                })
                router.push('/login')
            }
        }
    }
}

export default user
