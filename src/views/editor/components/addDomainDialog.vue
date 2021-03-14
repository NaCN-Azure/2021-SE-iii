<template>
    <el-dialog
            :visible="addDomainDialogVisible"
            title="新建图谱"
            :show-close="false"
    >
        <el-tabs type="border-card" v-model="addDomainType">
            <el-tab-pane label="创建空白图谱" name="empty">
                <el-form>
                    <el-form-item label="图谱名称">
                        <el-input v-model="addDomainParams.name"></el-input>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane name="import" label="导入文件">
                csv文件格式：节点-节点-关系 三元组
                <el-upload
                        drag
                        class="uploading"
                        on-success="csvsuccess"
                        accept=".csv"
                        auto-upload="false"
                        ref="upload"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
        </el-tabs>
        <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取消</el-button>
            <el-button @click="submit" type="primary">确认</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import {mapGetters, mapMutations} from "vuex";

    export default {
        name: "addDomainDialog",
        data(){
            return {
                addDomainType:'empty'
            }
        },
        computed:{
            ...mapGetters([
                'addDomainDialogVisible',
                'addDomainParams',
            ])
        },
        methods:{
            ...mapMutations([
                'set_addDomainDialogVisible',
            ]),
            cancel(){
                this.set_addDomainDialogVisible(false)
            },
            submit(){
                if(this.addDomainType=='empty'){
                    // 创建空白图谱

                }else{
                    // 导入文件创建图谱
                }
                console.log("提交新建图谱表单")
            }
        }
    }
</script>

<style scoped>

</style>