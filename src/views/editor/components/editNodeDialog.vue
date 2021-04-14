<template>
    <!-- 编辑节点对话框 -->
    <el-dialog
            :visible.sync="editNodeDialogVisible"
            title="编辑节点"
            class="dialog"
            :before-close="cancelEditNode"
            width="600px"
    >
        <el-form>
            <el-form-item label="节点名称">
                <el-input v-model="editNodeParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="选择形状">
                <el-select v-model="editNodeParams.shape" placeholder="请选择">
                    <el-option
                            v-for="item in shapes"
                            :key="item.key"
                            :label="item.label"
                            :value="item.key">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="修改类型">
                <el-input v-model="editNodeParams.type" style="width: 450px" placeholder="请输入节点类型"></el-input>
            </el-form-item>
            <el-form-item label="修改描述">
                <el-input
                        type="textarea"
                        autosize
                        placeholder="请输入节点描述"
                        style="width: 450px"
                        v-model="editNodeParams.description"
                ></el-input>
            </el-form-item>
            <el-form-item label="修改半径">
                <el-input style="width: 330px" v-model="editNodeParams.r"></el-input>
            </el-form-item>
        </el-form>
        <!-- mark：dialog的footer一定要如下加span标签！不是div！ 按钮才可以靠右 -->
        <span slot="footer" class="dialog-footer">
            <el-button @click="cancelEditNode">取消</el-button>
            <el-button @click="submitEditNode" type="primary">确认</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {updateNodeAPI, deleteNodeAPI, updateXYAPI} from "../../../api/entity";
    import {createLinkAPI, getLinkByDomainIdAPI, updateLinkAPI} from "../../../api/relationship";
    import { Message } from 'element-ui'

    export default {
        name: "editNodeDialog",
        computed: {
            ...mapGetters([
                'editNodeDialogVisible',
                'editNodeParams',
                'shapes',
                'domainList',
                'relationships',
            ])
        },
        methods:{
            ...mapMutations([
                'set_editNodeDialogVisible',
                'set_relationships'
            ]),
            ...mapActions([
                'editNode',
            ]),
            cancelEditNode(){
                this.set_editNodeDialogVisible(false);
            },
            submitEditNode(){
                updateNodeAPI(this.editNodeParams)
                    .then(res => {
                        if(res.data.code == 200){
                            Message({
                                message: '修改成功',
                                type: 'success'
                            });
                            this.set_editNodeDialogVisible(false)
                            getLinkByDomainIdAPI(this.editNodeParams.domainId)
                                .then(res => {
                                    if(res.data.code == 200) {
                                        this.set_relationships(res.data.data.relationships)
                                        this.$parent.init()  //调用父组件的初始化图谱方法
                                    }
                                })
                        }else{
                            Message({
                                message: '修改失败',
                                type: 'error'
                            })
                        }
                    })
                    .catch(err => {
                        console.log(err)
                        Message({
                            message: '修改失败',
                            type: 'error'
                        })
                    })
            }
        }
    }
</script>

<style scoped>

</style>
