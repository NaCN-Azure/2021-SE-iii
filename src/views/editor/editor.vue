<template>
    <div class="container" style="position: absolute;left: 0;top:60px;bottom: 0; width: 100%">
    <!-- 左侧-图谱（domain）列表 -->
        <div class="domain-list" style="height: 100%;text-align: center;;border-right: 1px solid lightgray">
            <el-scrollbar style="height:100%;width: 300px;overflow-x: hidden">
                <div class="domain-list-head" style="margin-top: 15px;">
                    <span style="font-size: 16px;color: grey;">图谱列表</span>
                    <el-button
                            size="mini"
                            style="float: right;margin-top: 10px;margin-right: 20px"
                            circle
                            @click="addDomain"
                            icon="el-icon-plus"
                            type="primary"
                    ></el-button>
                </div>
                <!-- 添加搜索图谱功能 -->
<!--                <div class="search-domain">-->
<!--                </div>-->
            </el-scrollbar>
        </div>
        <!-- 左侧over -->
        <!-- 右侧-编辑知识图谱面板 -->
        <div class="edit-pane">
            <!--编辑面板头部，包括一些操作和节点信息的显示-->
            <div class="edit-tools">
                <!-- 显示当前图谱名称，可以在这里对图谱名称进行修改 TODO-->
                <div class="edit-tool" v-show="domain.name!=''" style="display: flex">
                    当前图谱：<el-input v-model="domain.name" size="small" style="width: 100px"></el-input>
                </div>
                <!-- 不知道是要搜索什么 -->
<!--                <div class="edit-tool" v-show="domain.name!=''">-->
<!--                    <el-input-->
<!--                            placeholder="请输入关键词"-->
<!--                            @keyup.native.enter="searchInCurrentDomain"-->
<!--                            clearable="true"-->
<!--                            size="small"-->
<!--                    ></el-input>-->
<!--                </div>-->
                <div class="edit-tool" v-show="domain.name!=''">
                    <span class="">选中节点：</span>
                </div>
                <!-- 固定的工具 -->
                <div class="fixed-tools">
                    <el-button class="edit-tool" size="small" type="primary" @click="createNode">新建节点</el-button>
                    <el-dropdown>
                        <el-button size="small" class="edit-tool">
                            导出<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>导出图片</el-dropdown-item>
                            <el-dropdown-item>导出CSV</el-dropdown-item>
                    </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
            <!-- 右边中间-图谱渲染区域 -->
            <div class="graph">
                <el-scrollbar style="width: 100%;height: 100%">

                </el-scrollbar>
            </div>
        </div>

<!--        &lt;!&ndash; 对话框 &ndash;&gt;-->
<!--        <el-dialog title="导出csv" :visible.sync="exportFormVisible">-->
<!--            <el-form>-->
<!--                <el-form-item label="选择图谱"-->
<!--                              label-width="">-->
<!--                    <el-autocomplete-->
<!--                              v-model="domain"-->
<!--                              :fetch-suggestion="domainSearch"-->
<!--                              placeholder="请输入图谱名称">-->
<!--                    </el-autocomplete>-->
<!--                </el-form-item>-->
<!--                <el-button @click="exportCSV" type="primary">确定</el-button>-->
<!--            </el-form>-->
<!--        </el-dialog>-->
        <create-node-dialog></create-node-dialog>
        <add-domain-dialog></add-domain-dialog>
    </div>
</template>

<script>
    import {mapGetters, mapMutations} from "vuex";
    import CreateNodeDialog from "./components/createNodeDialog";
    import AddDomainDialog from "./components/addDomainDialog";

    export default {
        name: "editor",
        components: {AddDomainDialog, CreateNodeDialog},
        data(){
            return{
                // keyName:'',
                // domain:'',
                // selectedNode:{
                //     id:'',
                //     name:'',
                // }
            }
        },
        computed:{
            ...mapGetters([
                'createNodeDialogVisible',
                'addDomainDialogVisible',
                'domain'
            ])
        },
        methods:{
            ...mapMutations([
                'set_createNodeDialogVisible',
                'set_addDomainDialogVisible'
            ]),
            createNode(){
                this.set_createNodeDialogVisible(true)
            },
            addDomain(){
                this.set_addDomainDialogVisible(true)
            }
        }
    }
</script>

<style scoped>
    .container{
        display: flex;
    }
    .domain-list-head{
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid lightgray;
    }
    .edit-pane{
        position: absolute;
        left: 300px;
        top: 0;
        width: 100%;
        height: 100%;
    }
    .edit-tools{
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid lightgray;
        width: 100%;
        display: flex;
    }
    .fixed-tools{
        width: 200px;
        position: absolute;
        right: 320px;
        /*很离谱啊这里，为什么浏览器在看不见的地方还多了300px*/
    }
    .edit-tool{
        margin-left: 20px;
    }
    .graph{
        position: absolute;
        top: 50px;
        bottom: 0;
        width: 100%;
    }

</style>