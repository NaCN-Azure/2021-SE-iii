import {loginAPI, getUserInfoAPI} from "../../api/users";
import router from "../../router";
import {registerAPI} from "../../api/users";
import {Message} from "element-ui";
import cookie from 'js-cookie';


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
            // String sign;
            // 测试用数据
            id: 1,
            mobile: '10000000001',
            password: '123456',
            nickname: 'fcrrrr',
            avatar:'',
            sign: '别迷恋哥，哥只是个传说'
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
        },
    },
    actions: {
        login: async ({commit, dispatch}, loginData) => {
            const res = await loginAPI(loginData);
            if (res.data.code == 200) {
                commit('set_isLogin', true)
                Message({
                    type:'success',
                    message:'登录成功'
                })
                cookie.set('coin_token', res.data.data.token)
                dispatch('getUserInfo')
                router.push('/home')
            }
        },
        getUserInfo: async ({state, commit}) => {
            return new Promise((resolve, reject) => {
                getUserInfoAPI(state.userId).then(response => {
                    const data = response.data.data.userInfo
                    // console.log("userinfo",data);
                    if (!data) {
                        reject('登录已过期，请重新登录')
                    }
                    commit('set_userInfo', data)
                    commit('set_userId', data.id)
                    cookie.set('coin_user', this.userInfo)
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        register: async ({state, commit, dispatch}, registerData) => {
            const res = await registerAPI(registerData);
            if(res.data.code == 200){
                Message({
                    message: '注册成功',
                    type: 'success'
                })
                // 注册成功后直接登陆
                let loginData = {
                    mobile: registerData.mobile,
                    password: registerData.password,
                }
                // console.log('logindata', loginData);
                dispatch('login',loginData);
            }
        }
    }
}

export default user
