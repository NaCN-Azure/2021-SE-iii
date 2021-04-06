<template>
    <!-- 新建图谱对话框 -->
    <el-dialog
            :visible.sync="addDomainDialogVisible"
            title="新建图谱"
            class="dialog"
            :before-close="cancelAddDomain"
    >
        <el-tabs type="border-card" v-model="addDomainType">
            <el-tab-pane label="创建空白图谱" name="empty">
                <el-form>
                    <el-form-item label="图谱名称">
                        <el-input
                                placeholder="请填写图谱名称"
                                v-model="addDomainParams.name"
                        ></el-input>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane name="import" label="导入csv文件">
                csv文件格式：节点-节点-关系 三元组
                <el-upload
                        drag
                        class="uploading"
                        action="http://localhost:8002/coinservice/file/getCsv"
                        accept=".csv"
                        :auto-upload=false
                        ref="upload"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
        </el-tabs>
        <span slot="footer" class="dialog-footer">
            <el-button @click="cancelAddDomain">取消</el-button>
            <el-button @click="submitAddDomain" type="primary">确认</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {createDomainAPI} from "../../../api/domain";

    export default {
        name: "addDomainDialog",
        data(){
            return {
                addDomainType:'empty',
                addDomainParams: {
                    id:1,
                    name:'',
                },
            }
        },
        computed:{
            ...mapGetters([
                'addDomainDialogVisible',
            ])
        },
        methods:{
            ...mapMutations([
                'set_addDomainDialogVisible',
            ]),
            ...mapActions([
                'getAllDomains',
                'addDomain',
            ]),
            clearAddDomainParams(){
                this.addDomainParams = {
                    id:1,
                    name:'',
                }
            },
            cancelAddDomain(){
                this.set_addDomainDialogVisible(false);
                this.clearAddDomainParams();
            },
            submitAddDomain(){
                if(this.addDomainType=='empty'){
                    // 创建空白图谱
                    this.addDomain(this.addDomainParams);
                    this.clearAddDomainParams();
                }else{
                    // 导入文件创建图谱
                    this.set_addDomainDialogVisible(false);
                    this.$message({
                        message:'添加成功',
                        type:'success'
                    })
                    this.getAllDomains();
                }
            }
        }
    }
</script>

<style scoped>

</style>
