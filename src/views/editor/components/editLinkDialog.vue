<template>
    <!-- 编辑关系对话框 -->
    <el-dialog
            :visible="editLinkDialogVisible"
            title="编辑关系"
            class="dialog"
            :show-close="false"
    >
        <el-form :model="editLinkParams">
            <el-form-item label="关系名称">
                <el-input v-model="editLinkParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="所属图谱">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select disabled v-model="editLinkParams.domainId" placeholder="请从现有图谱中选择">
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
                    <el-button @click="cancelEditLink">取消</el-button>
                    <el-button @click="submitEditLink" type="primary">确认</el-button>
                </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

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
                'set_editLinkDialogVisible'
            ]),
            ...mapActions([
                'editLink'
            ]),
            cancelEditLink(){
                this.set_editLinkDialogVisible(false);
            },
            submitEditLink(){
                this.editLink();
            }
        }
    }
</script>

<style scoped>

</style>
