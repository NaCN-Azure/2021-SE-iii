<template>
    <!-- 添加节点对话框 -->
    <el-dialog
            :visible="createNodeDialogVisible"
            title="创建节点"
            :before-close="cancelCreateNode"
    >
        <el-form :model="createNodeParams">
            <el-form-item label="节点名称">
                <el-input v-model="createNodeParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="选择颜色">
                <el-color-picker v-model="createNodeParams.bgColor">
                </el-color-picker>
            </el-form-item>
            <el-form-item label="选择形状">
                <el-select disabled v-model="createNodeParams.shape" placeholder="请选择">
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
                <el-select disabled v-model="createNodeParams.domainId" placeholder="请从现有图谱中选择">
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
                <el-button @click="cancelCreateNode">取消</el-button>
                <el-button @click="submitCreateNode" type="primary">确认</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {createNodeAPI} from "../../../api/entity";

    export default {
        name: "createNodeDialog",
        computed: {
            ...mapGetters([
                'createNodeDialogVisible',
                'createNodeParams',
                'shapes',
                'domainList',
            ])
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
            },
            submitCreateNode(){
                this.createNode();
            }
        }
    }
</script>

<style scoped>
</style>
