import {createNodeAPI, updateNodeAPI} from "../../api/entity";
import {  Message } from 'element-ui'
import {createDomainAPI, selectAllDomainAPI} from "../../api/domain";
import {createLinkAPI, getLinkByDomainIdAPI, updateLinkAPI} from "../../api/relationship";

const editor = {
    state: {
        // activeIndex是index和其他区域公共变量，表示导航栏当前位置
        activeIndex:'2',
        createNodeDialogVisible: false,
        createNodeParams: {
            name: '',
            shape: 0,
            domainId: '',
            type: '',
            description: '',
            r: 40,
            fontSize: 20,
        },
        editNodeDialogVisible: false,
        editNodeParams:{
            id:'',
            name:'',
            bgColor:'',
            shape:'',
            domainId:'',
            type: '',
            description: '',
            x:0.0,
            y:0.0,
            r: '',
            fontSize: '',
        },
        addDomainDialogVisible: false,
        createLinkDialogVisible: false,
        createLinkParams:{
            fromId:'',
            toId:'',
            name:'',
            domainId:'',
        },
        editLinkDialogVisible: false,
        editLinkParams:{
            id:'',
            name:'',
            type:'',
            fromId:'',
            toId:'',
        },
        selectedDomain:{
            name:'',
            id:'',
        },
        // 获取到的图谱数据！
        relationships:[],
        // 所有节点的位置数组
        nodePositions:[],
        shapes:[
            {key:0,label: '圆形'},
            {key:1,label: '矩形'},
            {key:2,label: '三角形'}
        ],
        domainList:[],
        // 要被渲染的nodes和links数组
        nodesData:[],
        linksData:[],
        nodeListVisible: false,
        mode: 0,  //两种模式，0代表力导图模式，1代表排版模式
        options: [],
        value: [],
    },
    mutations:{
        set_activeIndex: function(state, data){
            state.activeIndex = data;
        },
        set_createNodeDialogVisible:function (state,data) {
            state.createNodeDialogVisible = data;
        },
        set_createNodeParams: function (state, data) {
            state.createNodeParams = data;
        },
        set_selectedDomain: function (state, data) {
            state.selectedDomain = data;
        },
        set_domainList: function (state, data) {
            state.domainList = data;
        },
        set_editNodeDialogVisible: function (state, data) {
            state.editNodeDialogVisible = data;
        },
        set_editNodeParams: function (state, data) {
            state.editNodeParams = data;
        },
        set_relationships: function (state, data) {
            state.relationships = data;
        },
        set_addDomainDialogVisible: function (state, data) {
            state.addDomainDialogVisible = data;
        },
        set_createLinkDialogVisible: function (state, data) {
            state.createLinkDialogVisible = data;
        },
        set_createLinkParams: function (state, data) {
            state.createLinkParams = data;
        },
        set_editLinkDialogVisible: function (state, data) {
            state.editLinkDialogVisible = data
        },
        set_editLinkParams: function (state, data) {
            state.editLinkParams = data
        },
        set_nodesData: function (state, data) {
            state.nodesData = data
        },
        set_linksData: function (state, data) {
            state.linksData = data
        },
        set_nodePositions: function(state,data){
            state.nodePositions = data
        },
        set_nodeListVisible: function (state, data) {
            state.nodeListVisible  = data
        },
        set_mode: function (state, data) {
            state.mode  = data
        },
        set_options: function (state, data) {
            state.options  = data
        },
        set_value: function (state, data) {
            state.value  = data
        },
    },
    actions:{
        // 根据Id获取图谱，用于更新图谱
        getDomainById: async ({ state, commit }, domainId) => {
            const res = await getLinkByDomainIdAPI(domainId);
            if(res.data.code == 200) {
                commit('set_relationships',res.data.data.relationships);
            }
        },
        // 获取图谱列表
        getAllDomains: async ({state, commit}, userId) => {
            const res = await selectAllDomainAPI(userId);
            if(res.data.code == 200){
                commit('set_domainList',res.data.data.domain);
                console.log("domainlist",state.domainList)
            }
        },
        // 创建节点提交
        createNode: async ({state, commit, dispatch}) => {
            const res = await createNodeAPI(state.createNodeParams);
            // console.log(res)
            if(res.data.code == 200){
                Message({
                    message:'添加成功',
                    type:'success'
                })
                    // this.selectDomainById(this.createNodeParams.domainId);
                commit('set_createNodeDialogVisible',false);
                dispatch('getDomainById',state.createNodeParams.domainId);
            }
        },
        // 编辑节点提交
        editNode: async ({state, commit, dispatch}) => {
            const res = await updateNodeAPI(state.editNodeParams);
            if(res.data.code == 200){
                Message({
                    message: '修改成功',
                    type: 'success'
                });
                commit('set_editNodeDialogVisible',false);
                dispatch('getDomainById');
            }else{
                Message({
                    message: '修改失败',
                    type:'error'
                })
            }
        },
        // 添加关系提交
        createLink: async ({ state, commit, dispatch}) => {
            const res = await createLinkAPI(
                state.createLinkParams.fromId,
                state.createLinkParams.toId,
                state.createLinkParams.name
            );
            if(res.data.code == 200){
                Message({
                    message:'添加成功',
                    type:'success'
                })
                dispatch('getDomainById', state.createLinkParams.domainId);
                commit('set_createLinkDialogVisible', false);
            }
        },
        // 编辑关系提交
        editLink: async ({state, commit, dispatch}) => {
            const res = await updateLinkAPI(state.editLinkParams);
            if(res.data.code == 200){
                Message({
                    message: '修改成功',
                    type: 'success'
                })
                commit('set_editLinkDialogVisible',false);
                dispatch('getDomainById',state.editLinkParams.domainId);
            }else{
                Message({
                    message: '修改失败',
                    type:'error'
                })
            }
        },
        // 添加图谱提交
        addDomain: async ({ state, commit, dispatch}, data) => {
            console.log("adddomaindata",data);
            const res = await createDomainAPI(data);
            console.log("adddomain",res);
            if(res.data.code == 200){
                Message({
                    message:'添加成功',
                    type: 'success'
                })
                commit('set_addDomainDialogVisible', false);
                dispatch('getAllDomains',data.user_id);
            }else{
                Message({
                    message:'添加失败',
                    type: 'error'
                })
            }
        }
    }
}

export default editor
