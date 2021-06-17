<template>
    <!-- 新建图谱对话框 -->
    <el-dialog
            :visible.sync="addDomainDialogVisible"
            title="新建图谱"
            class="dialog"
            :before-close="cancelAddDomain"
            width="30%"
    >
        <el-tabs type="border-card" v-model="addDomainType">
            <el-tab-pane label="创建空白图谱" name="empty">
                <el-form>
                    <el-form-item label="图谱名称">
                        <el-input
                                placeholder="请填写图谱名称"
                                v-model="addDomainName"
                        ></el-input>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane name="import" label="导入csv文件">
                csv文件格式：节点-节点-关系 三元组
                <el-upload
                        drag
                        :action="uploadUrl"
                        class="uploading"
                        :on-success="handleCsvSuccess"
                        accept=".csv"
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
                addDomainName:'',
                uploadParam: {},
            }
        },
        computed:{
            ...mapGetters([
                'addDomainDialogVisible',
                'userInfo',
            ]),
            uploadUrl(){
                // return "http://106.15.93.81:8002/coinservice/file/getCsv/"+this.userInfo.id
                return "http://localhost:8002/coinservice/file/getCsv/"+this.userInfo.id
            }
        },
        methods:{
            ...mapMutations([
                'set_addDomainDialogVisible',
            ]),
            ...mapActions([
                'getAllDomains',
                'addDomain',
            ]),
            // clearAddDomainParams(){
            //     this.addDomainParams = {
            //         id:1,
            //         name:'',
            //         user_id: this.userInfo.id,
            //     }
            // },
            handleCsvSuccess(res) {
                console.log(res);
                this.$message({
                    message: '导入完成',
                    type: 'success'
                })
            },
            cancelAddDomain(){
                this.set_addDomainDialogVisible(false);
                this.addDomainName = '';
            },
            submitAddDomain(){
                if(this.addDomainType=='empty'){
                    // 创建空白图谱
                    const addDomainParams = {
                        id:1,
                        name: this.addDomainName,
                        user_id: this.userInfo.id,
                    }
                    this.addDomain(addDomainParams);
                    this.addDomainName = '';
                }else{
                    // 导入文件创建图谱
                    this.set_addDomainDialogVisible(false);
                    console.log('submiting');
                    this.$refs.upload.submit();
                    this.getAllDomains(this.userInfo.id)
                }
            }
        }
    }
</script>

<style scoped>

</style>
