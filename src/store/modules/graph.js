import { message } from "element-ui";
import {createNodeAPI, getNodesByDomainIdAPI, updateNodeAPI} from "../../api/entity";
import {createLinkAPI, updateLinkAPI} from "../../api/relationship";

const graph = {
    state: {
        // 当前选中的图谱
        domain:{
            name:'hhh',// 测试用，正常化初始化为空
            id:'',
        },
        domainList:[],// 所有已创建的图谱
        nodeList:[], // 当前需要渲染的图谱中的节点列表
        linkList:[], // 当前需要渲染的图谱中的关系列表
        // 节点可选图形
        shapes:[
            {key:0,label:'圆形'}
        ],
        // 鼠标选中的节点
        // selectedNode:{
        //     name:'',
        //     description:'',
        //
        // },
        createNodeParams:{
            name:'',
            color:'',
            shape:0,
            // description:'', //好像没看到后端的方法可以添加描述的
            domainId:''
        },
        updateNodeParams:{
            id:'',
            name:'',
            bgColor:'',
            shape:0,
        },
        createLinkParams:{
            fromId:'',
            toId:'',
            name:'',
        },
        updateLinkParams: {
            id:'',
            name:'',
            type:'',
            fromId:'',
            toId:'',
        },
        addDomainParams: {
            name:'',
        },
        updateNodeDialogVisible: false,
        updateLinkDialogVisible: false,
        createNodeDialogVisible: false,
        createLinkDialogVisible: false,
        addDomainDialogVisible:false,

    },

    mutations: {
        set_domain: function( state, data ) {
            state.domain = data
        },
        set_nodeList: function ( state, data ) {
            state.nodeList = data
        },
        set_linkList: function ( state, data ){
            state.linkList = data
        },
        set_createNodeParams: function ( state, data ) {
            state.createNodeParams = data
        },
        set_updateNodeParams: function ( state, data ) {
            state.updateNodeParams = data
        },
        set_updateNodeDialogVisible: function ( state, data) {
            state.updateNodeDialogVisible = data
        },
        set_updateLinkDialogVisible: function ( state, data) {
            state.updateLinkDialogVisible = data
        },
        set_createNodeDialogVisible: function ( state, data) {
            state.createNodeDialogVisible = data
        },
        set_createLinkDialogVisible: function ( state, data) {
            state.createLinkDialogVisible = data
        },
        set_createLinkParams: function ( state, data ) {
            state.createLinkParams = data
        },
        set_updateLinkParams: function ( state, data ) {
            state.updateLinkParams = data
        },
        set_addDomainDialogVisible: function ( state, data ) {
            state.addDomainDialogVisible = data
        }
    },
    actions: {
        getNodesByDomainId: async({ state, commit }) => {
            const res = await getNodesByDomainIdAPI(state.domain.id)
            if(res) {
                commit('set_nodeList',res)
                // 节点获取成功，下一步更新图谱
            }else{
                message.error('获取图谱节点失败')
            }
        },
        createNode: async ({ state, commit, dispatch }) => {
            const res = await createNodeAPI(
                state.createNodeParams.name,
                state.createNodeParams.color,
                state.createNodeParams.shape,
                state.createNodeParams.domainId,
            )
            if(res){
                commit('set_createNodeParams',{
                    name:'',
                    color:'',
                    shape:0,
                    domainId: state.domain.id,
                })
                commit('set_createNodeDialogVisible',false)
                message.success('添加成功')
                dispatch('updateGraph')
            }else{
                message.error('添加失败')
            }
        },
        // getLinksByDomainId:
        updateNode: async ({ state, commit, dispatch }) => {
            const res = await updateNodeAPI(state.updateNodeParams)
            if(res) {
                commit('set_updataNodeParams',{
                    id:'',
                    name:'',
                    bgColor:'',
                    shape:0,
                })
                commit('set_updateNodeDialogVisible',false)
                message.success('修改成功')
                dispatch('updateGraph')
            }else{
                message.error('修改失败')
            }
        },
        createLink: async ({ state, commit, dispatch }) => {
            const res = await createLinkAPI(
                state.createLinkParams.fromId,
                state.createLinkParams.toId,
                state.createLinkParams.name
            )
            if(res){
                commit('set_createLinkParams',{
                    fromId:'',
                    toId:'',
                    name:'',
                })
                commit('set_createLinkDialogVisible',false)
                message.success('添加成功')
                dispatch('updateGraph')
            }else{
                message.error('添加失败')
            }
        },
        updateLink: async ({ state, commit, dispatch }) => {
            const res = await updateLinkAPI(state.updateLinkParams)
            if(res) {
                commit('set_updataLinkParams',{
                    id:'',
                    name:'',
                    type:'',
                    fromId:'',
                    toId:'',
                })
                commit('set_updateLinkDialogVisible',false)
                message.success('修改成功')
                dispatch('updateGraph')
            }else{
                message.error('修改失败')
            }
        },
        // updateGraph

    }
}
export default graph