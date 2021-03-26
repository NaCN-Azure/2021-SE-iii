<template>
    <!-- 编辑节点对话框 -->
    <el-dialog
            :visible.sync="editNodeDialogVisible"
            title="编辑节点"
            class="dialog"
    >
        <el-form>
            <el-form-item label="节点名称">
                <el-input v-model="editNodeParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="选择颜色">
                <el-color-picker v-model="editNodeParams.bgColor">
                </el-color-picker>
            </el-form-item>
            <el-form-item label="选择形状">
                <el-select disabled v-model="editNodeParams.shape" placeholder="请选择">
                    <el-option
                            v-for="item in shapes"
                            :key="item.key"
                            :label="item.label"
                            :value="item.key">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="所属图谱">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select disabled v-model="editNodeParams.domainId" placeholder="请从现有图谱中选择">
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
                    <el-button @click="cancelEditNode">取消</el-button>
                    <el-button @click="submitEditNode" type="primary">确认</el-button>
                </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "editNodeDialog",
        computed: {
            ...mapGetters([
                'editNodeDialogVisible',
                'editNodeParams',
                'shapes',
                'domainList',
            ])
        },
        methods:{
            ...mapMutations([
                'set_editNodeDialogVisible'
            ]),
            ...mapActions([
                'editNode',
            ]),
            cancelEditNode(){
                this.set_editNodeDialogVisible(false);
            },
            submitEditNode(){
                this.editNode();
            }
        }
    }
</script>

<style scoped>

</style>
