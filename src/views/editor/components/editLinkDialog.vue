<template>
    <!-- 编辑关系对话框 -->
    <el-dialog
            :visible="editLinkDialogVisible"
            title="编辑关系"
            class="dialog"
            :before-close="cancelEditLink"
    >
        <el-form :model="editLinkParams">
            <el-form-item label="关系名称">
                <el-input v-model="editLinkParams.name" style="width: 330px"></el-input>
            </el-form-item>
        </el-form>
        <!-- mark：dialog的footer一定要如下加span标签！不是div！ 按钮才可以靠右 -->
        <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelEditLink">取消</el-button>
                    <el-button @click="submitEditLink" type="primary">确认</el-button>
                </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {createLinkAPI, getLinkByDomainIdAPI, updateLinkAPI} from "../../../api/relationship";
    import { Message } from 'element-ui'

    export default {
        name: "editLinkDialog",
        computed: {
            ...mapGetters([
                'editLinkDialogVisible',
                'editLinkParams',
                'domainList',
            ])
        },
        data(){
            return{
                nodesData:[],
            }
        },
        methods:{
            ...mapMutations([
                'set_editLinkDialogVisible',
                'set_relationships'
            ]),
            ...mapActions([
                'editLink'
            ]),
            cancelEditLink(){
                this.set_editLinkDialogVisible(false);
            },
            submitEditLink(){
                updateLinkAPI(this.editLinkParams)
                    .then(res => {
                        if(res.data.code == 200){
                            Message({
                                message: '修改成功',
                                type: 'success'
                            })
                            this.set_editLinkDialogVisible(false)
                            getLinkByDomainIdAPI(this.editLinkParams.domainId)
                                .then(res => {
                                    this.set_relationships(res.data.data.relationships)
                                    this.$parent.init()
                                })
                        }else{
                            Message({
                                message: '修改失败',
                                type:'error'
                            })
                        }
                    })
                    .catch(err => {
                        console.log(err)
                        Message({
                            message: '修改失败',
                            type:'error'
                        })
                    })
            }
        }
    }
</script>

<style scoped>

</style>
