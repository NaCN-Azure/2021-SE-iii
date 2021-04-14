<template>
    <!-- 创建关系对话框 -->
    <el-dialog
            :visible="createLinkDialogVisible"
            title="添加关系"
            class="dialog"
            :before-close="cancelCreateLink"
    >
        <el-form :model="createLinkParams">
            <el-form-item label="关系名称">
                <el-input v-model="createLinkParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="起始节点">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select v-model="createLinkParams.fromId" placeholder="请从现有图谱中选择关系源节点" style="width: 330px">
                    <el-option
                            v-for="item in nodesData"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="目标节点">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select v-model="createLinkParams.toId" placeholder="请从现有图谱中选择关系目标节点" style="width: 330px">
                    <el-option
                            v-for="item in nodesData"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="所属图谱">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select disabled v-model="createLinkParams.domainId" placeholder="请从现有图谱中选择">
                    <el-option
                            v-for="item in domainList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="cancelCreateLink">取消</el-button>
                <el-button @click="submitCreateLink" type="primary">确认</el-button>
            </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {createLinkAPI, getLinkByDomainIdAPI, updateLinkAPI} from "../../../api/relationship";
    import { Message } from 'element-ui'

    export default {
        name: "createLinkDialog",
        computed: {
            ...mapGetters([
                'createLinkDialogVisible',
                'createLinkParams',
                'domainList',
                'nodesData'
            ])
        },
        methods:{
            ...mapMutations([
                'set_createLinkDialogVisible',
                'set_relationships'
            ]),
            ...mapActions([
                'createLink'
            ]),
            cancelCreateLink(){
                this.set_createLinkDialogVisible(false);
            },
            submitCreateLink(){
                createLinkAPI(this.createLinkParams.fromId, this.createLinkParams.toId, this.createLinkParams.name)
                    .then(res => {
                        if(res.data.code == 200){
                            Message({
                                message:'添加成功',
                                type:'success'
                            })
                            this.set_createLinkDialogVisible(false)
                            getLinkByDomainIdAPI(this.createLinkParams.domainId)
                                .then(res => {
                                    this.set_relationships(res.data.data.relationships)
                                    this.$parent.init()
                                })
                        }
                        else {
                            Message({
                                message:'添加失败',
                                type:'error'
                            })
                        }
                    })
                    .catch(err => {
                        console.log(err)
                        Message({
                            message:'添加失败',
                            type:'error'
                        })
                    })
            }
        }
    }
</script>

<style scoped>

</style>
