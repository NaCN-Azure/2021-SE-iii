<template>
    <!-- 新建图谱对话框 -->
    <el-dialog
            :visible.sync="addDomainDialogVisible"
            title="新建图谱"
            class="dialog"
            :before-close="cancelAddDomain"
            width="30%"
    >
        <el-tabs type="border-card" v-model="addDomainType"
                 :stretch="true"
                 style="text-align: center"
        >
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
            <el-tab-pane label="使用推荐模板" name="useTemplate">
                欢迎使用我们的推荐模板：
                <div style="margin-top: 15px">
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(1)">宝可梦图谱</el-button>
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(2)">化学图谱</el-button>
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(3)">金融图谱</el-button>
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(4)">电影图谱</el-button>
                </div>
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
    import {createDomainAPI, getTemplateAPI} from "../../../api/domain";
    import {Message} from "element-ui";

    export default {
        name: "addDomainDialog",
        data(){
            return {
                addDomainType:'empty',
                addDomainName:'',
                uploadParam: {},
                templates:[
                    {
                        id:1,
                        name:'宝可梦图谱'
                    },
                    {
                        id:2,
                        name:'化学图谱'
                    },
                    {
                        id:3,
                        name:'金融图谱'
                    },
                    {
                        id:4,
                        name:'电影图谱'
                    }
                ],
                selectedTemplate:'',
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
            handleCurrentChange(val) {
                // console.log(val);
                // console.log(val.id);
                this.selectedTemplate = val.id
            },
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
                }else if(this.addDomainName=='import'){
                    // 导入文件创建图谱
                    this.set_addDomainDialogVisible(false);
                    console.log('submiting');
                    this.$refs.upload.submit();
                    this.getAllDomains(this.userInfo.id)
                }else{
                    getTemplateAPI(this.selectedTemplate,this.userInfo.id).then(res=>{
                        console.log(res);
                        if(res.data.code == 200){
                            Message({
                                type:'success',
                                message:'创建成功'
                            })
                            this.$router.push('/editor')
                        }else{
                            Message({
                                type:'error',
                                message:'创建失败'
                            })
                        }
                    })
                }
            }
        }
    }
</script>

<style scoped>

</style>
