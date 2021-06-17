<template>
    <!-- 添加节点对话框 -->
    <el-dialog
            :visible="createNodeDialogVisible"
            title="创建节点"
            :before-close="cancelCreateNode"
            width="600px"
    >
        <el-form :model="createNodeParams">
            <el-form-item label="节点名称">
                <el-input v-model="createNodeParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="选择形状">
                <el-select v-model="createNodeParams.shape" placeholder="请选择">
                    <el-option
                        v-for="item in shapes"
                        :key="item.key"
                        :label="item.label"
                        :value="item.key"/>
                </el-select>
            </el-form-item>
            <el-form-item label="选择类型">
                <el-input v-model="createNodeParams.type" style="width: 450px" placeholder="请输入节点类型"/>
            </el-form-item>
            <el-form-item label="添加描述">
                <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入节点描述"
                    style="width: 450px"
                    v-model="createNodeParams.description"
                />
            </el-form-item>
            <!-- <el-form-item label="输入半径">
                <el-input v-model="createNodeParams.r" style="width: 330px"></el-input>
            </el-form-item> -->
            <el-form-item label="所属图谱">
                <el-select
                    v-model="createNodeParams.domainId"
                    disabled
                    placeholder="">
                    <el-option
                        v-for="item in domainList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <!-- mark：dialog的footer一定要如下加span标签！不是div！ 按钮才可以靠右 -->
        <span slot="footer" class="dialog-footer">
                <el-button @click="cancelCreateNode">取消</el-button>
                <el-button @click="submitCreateNode" type="primary">确认</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {createNodeAPI} from "../../../api/entity";
    import {createLinkAPI, getLinkByDomainIdAPI, updateLinkAPI} from "../../../api/relationship";
    import { Message } from 'element-ui'
    import {addNodeNameAPI, addTypeNameAPI} from "../../../api/robot";

    export default {
        name: "createNodeDialog",
        computed: {
            ...mapGetters([
                'createNodeDialogVisible',
                'createNodeParams',
                'shapes',
                'domainList',
            ])
        },
        methods:{
            ...mapMutations([
                'set_createNodeDialogVisible',
                'set_relationships',
            ]),
            ...mapActions([
                'createNode'
            ]),
            cancelCreateNode(){
                this.set_createNodeDialogVisible(false);
            },
            submitCreateNode(){
                console.log("creating node",this.createNodeParams)
                createNodeAPI(this.createNodeParams)
                    .then(res => {
                        if(res.data.code == 200){
                            Message({
                                message:'添加成功',
                                type:'success'
                            })
                            this.set_createNodeDialogVisible(false)
                            // 传到机器人字典
                            addNodeNameAPI(this.createNodeParams.name)
                                .then(res=>{
                                    console.log("addNodeNameToDir",res)
                            })
                            addTypeNameAPI(this.createNodeParams.type)
                                .then(res=>{
                                    console.log("addTypeNameToDir",res)
                            })
                            getLinkByDomainIdAPI(this.createNodeParams.domainId)
                                .then(res => {
                                    this.set_relationships(res.data.data.relationships)
                                    this.$parent.init()
                                })
                        }
                        else {
                            Message({
                                message: '添加失败',
                                type: 'error'
                            })
                        }
                    })
                    .catch(err => {
                        console.log(err)
                        Message({
                            message: '添加失败',
                            type: 'error'
                        })
                    })
            }
        }
    }
</script>

<style scoped>
</style>
