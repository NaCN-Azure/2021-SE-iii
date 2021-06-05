import {loginAPI, getUserInfoAPI, updateAvatarAPI} from "../../api/users";
import router, {resetRouter} from "../../router";
import { getToken, setToken, removeToken } from '@/utils/auth'
import {registerAPI} from "../../api/users";
import {Message} from "element-ui";
import cookie from 'js-cookie'


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
        modifyUserInfoParams:{

        },
        resetPwdDialogVisible: false,

    },
    mutations: {
        reset_state:function(state){
            state.token = ''
            state.isLogin = false
            state.userId = ''
            state.userInfo = {

            }
        },
        set_token: function(state, token){
            state.token = token
        },
        set_isLogin: function (state, data) {
            state.isLogin = data;
        },
        set_userId: function (state, data){
            state.userId = data;
        },
        set_userInfo: function (state, data) {
            state.userInfo = data;
        },
        set_modifyUserInfoParams: function (state, data) {
            state.modifyUserInfoParams = data;
        },
        set_resetPwdDialogVisible: function (state, date) {
            state.resetPwdDialogVisible = date;
        }
    },
    actions: {
        login: async ({commit, dispatch}, loginData) => {
            const res = await loginAPI(loginData);
            console.log(res);
            if (res.data.code == 200) {
                setToken(res.data.data.id)
                commit('set_token',res.data.data.token)
                commit('set_isLogin', true)
                Message({
                    type: 'success',
                    message: '登录成功'
                })
                cookie.set('coin_token', res.data.data.token)
                dispatch('getUserInfo')
                router.push('/')
            }else{
                Message({
                    type:'error',
                    message:res.data.message
                })
            }
        },
        getUserInfo: async ({state, commit}) => {
            return new Promise((resolve, reject) => {
                getUserInfoAPI().then(response => {
                    const data = response.data.data.userInfo
                    console.log("userinfo",data);
                    if (!data) {
                        reject('登录已过期，请重新登录')
                        router.push('/login')
                    }
                    commit('set_userInfo', data)
                    commit('set_modifyUserInfoParams',data)
                    commit('set_userId', data.id)
                    cookie.set('coin_user', state.userInfo)
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        register: async ({state, commit, dispatch}, registerData) => {
            const res = await registerAPI(registerData);
            console.log(res);
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
            }else{
                Message({
                    message:'注册失败',
                    type:'error'
                })
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
    }
}

export default user
