<template>
    <!-- 创建关系对话框 -->
    <el-dialog
            :visible="createLinkDialogVisible"
            title="添加关系"
            class="dialog"
            :show-close="false"
    >
        <el-form :model="createLinkParams">
            <el-form-item label="关系名称">
                <el-input v-model="createLinkParams.name" style="width: 330px"></el-input>
            </el-form-item>
            <el-form-item label="起始节点">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select v-model="createLinkParams.fromId" placeholder="请从现有图谱中选择关系源节点" style="width: 330px">
                    <el-option
                            v-for="item in nodesData"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="目标节点">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select v-model="createLinkParams.toId" placeholder="请从现有图谱中选择关系目标节点" style="width: 330px">
                    <el-option
                            v-for="item in nodesData"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="所属图谱">
                <!-- 默认只能在当前选中的图谱中添加节点 -->
                <el-select disabled v-model="createLinkParams.domainId" placeholder="请从现有图谱中选择">
                    <el-option
                            v-for="item in domainList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="cancelCreateLink">取消</el-button>
                <el-button @click="submitCreateLink" type="primary">确认</el-button>
            </span>
    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "createLinkDialog",
        computed: {
            ...mapGetters([
                'createLinkDialogVisible',
                'createLinkParams',
                'domainList',
                'nodesData'
            ])
        },
        methods:{
            ...mapMutations([
                'set_createLinkDialogVisible'
            ]),
            ...mapActions([
                'createLink'
            ]),
            cancelCreateLink(){
                this.set_createLinkDialogVisible(false);
            },
            submitCreateLink(){
                this.createLink();
            }
        }
    }
</script>

<style scoped>

</style>
