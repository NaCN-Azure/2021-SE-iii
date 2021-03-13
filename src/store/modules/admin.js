import {
    getManagerListAPI,
    getMarketerListAPI,
    addManagerAPI,
    addMarketerAPI,
    deleteUserAPI,
    changeManagerAPI,
    reviseUserInfoAPI,
    saveFileAPI,
} from '@/api/admin'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        managerList: [

        ],
        marketerList:[

        ],
        addManagerModalVisible: false,
        addManagerParams: {
            email:'',
            password:'',
            username:'',
            phoneNumber: '',
        },
        addMarketerParams: {
            email:'',
            password:'',
            username:'',
            phoneNumber:'',
        },
        changeHotelManagerVisible : false,
        addMarketerModalVisible: false,
        curHotelId:'',
        selectedManagerId:'',
        reviseUsersInfoVisible: false,
        addHotelPhotoVisible : false,
        photoHotelId:0,
        selectedUserId:'',
        reviseUsersInfoParams: {
            id:'',
            username:'',
            phoneNumber:'',
            password:'',
        },
    },

    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_marketerList: function(state, data){
            state.marketerList = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addMarketerModalVisible: function(state, data){
            state.addMarketerModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        set_addMarketerParams: function(state, data) {
            state.addMarketerParams = {
                ...state.addMarketerParams,
                ...data,
            }
        },
        set_changeHotelManagerVisible:function(state,data){
            state.changeHotelManagerVisible = data
        },
        set_selectedUserId:function (state,data) {
            state.selectedUserId = data
        },
        set_selectedManagerId:function (state, data){
            state.selectedManagerId = data
        },
        set_reviseUsersInfoVisible:function(state,data){
            state.reviseUsersInfoVisible = data
        },
        set_reviseUsersInfoParams: function(state, data) {
            state.reviseUsersInfoParams = {
                ...state.reviseUsersInfoParams,
                ...data,
            }
        },
        set_curHotelId: function (state, data) {
            state.curHotelId = data
        },
        set_addHotelPhotoVisible: function(state, data) {
            state.addHotelPhotoVisible = data
        },
        set_photoHotelId:function(state,data){
            state.photoHotelId = data
        },
    },
    actions: {
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        getMarketerList: async({ commit }) => {
            const res = await getMarketerListAPI()
            if(res){
                commit('set_marketerList',res)
            }
        },
        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:'',
                    username:'',
                    phoneNumber:'',
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        },
        addMarketer: async({ state, commit, dispatch }) => {
            const res = await addMarketerAPI(state.addMarketerParams)
            if(res) {
                commit('set_addMarketerParams',{
                    email:'',
                    password:'',
                    username:'',
                    phoneNumber:'',
                })
                commit('set_addMarketerModalVisible', false)
                message.success('添加成功')
                dispatch('getMarketerList')
            }else{
                message.error('添加失败')
            }
        },
        deleteUser: async ({state,dispatch},userId)=>{
            const res=await deleteUserAPI(userId)
            if (res){
                dispatch("getManagerList")
                dispatch("getAllClients")
                dispatch("getMarketerList")
                message.success("删除成功")
            }else{
                message.error("删除失败")
            }
        },
        changeManager:async({state,commit,dispatch},data) => {
            const res = await changeManagerAPI(data)
            if(res){
                dispatch("getHotelList")
                message.success("修改成功")
                commit('set_changeHotelManagerVisible',false)
            }else{
                message.error("修改失败")
            }
        },
        reviseUserInfo: async({ state, commit, dispatch }, data) => {
            const params = {
                ...data,
            }
            const res = await reviseUserInfoAPI(params)
            if(res){
                commit('set_reviseUsersInfoParams',{
                    id:'',
                    username:'',
                    phoneNumber:'',
                    password:'',
                })
                commit('set_reviseUsersInfoVisible',false)
                message.success('修改成功')
                dispatch('getMarketerList')
                dispatch('getManagerList')
                dispatch('getAllClients')
            }else{
                message.error('修改失败')
            }
        },
    }
}
export default admin