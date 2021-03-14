<template>
    <el-dialog
            :visible="createNodeDialogVisible"
            title="创建节点"
            :show-close="false"
    >
        <el-form :model="createNodeParams">
            <el-form-item label="节点名称">
                <el-input v-model="createNodeParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="选择颜色">
                <el-color-picker v-model="createNodeParams.color">
                </el-color-picker>
            </el-form-item>
            <el-form-item label="选择形状">
                <el-select v-model="createNodeParams.shape" placeholder="请选择">
                    <el-option
                        v-for="item in shapes"
                        :key="item.key"
                        :label="item.label"
                        :value="item.key">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="所属图谱">
                <el-select v-model="createNodeParams.domainId" placeholder="请从现有图谱中选择">
                    <el-option
                        v-for="item in domainList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <!-- mark：dialog的footer一定要如下加span标签！不是div！ 按钮才可以靠右 -->
            <span slot="footer" class="el-dialog__footer">
                <el-button @click="cancelCreateNode">取消</el-button>
                <el-button @click="submitCreateNode" type="primary">确认</el-button>
            </span>
        </el-form>

    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "createNodeDialog",
        computed: {
            ...mapGetters([
                'createNodeDialogVisible',
                'createNodeParams',
                'shapes',
                'domainList'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'createNodeDialog' });
        },
        methods:{
            ...mapMutations([
                'set_createNodeDialogVisible'
            ]),
            ...mapActions([
                'createNode'
            ]),
            cancelCreateNode(){
                this.set_createNodeDialogVisible(false);
                this.form.resetFields();
            },
            submitCreateNode(e){
                this.createNode();
                this.form.resetFields()
            }
        }
    }
</script>

<style scoped>
    .el-dialog__footer{
        float: right;
    }
</style>