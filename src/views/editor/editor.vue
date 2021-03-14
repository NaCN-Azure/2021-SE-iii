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
                <div class="searchInKG" v-show="domain!=''">
                    <el-input
                            placeholder="请输入关键词"
                            v-model="keyName"
                            @keyup.native.enter="searchInCurrentDomain"
                            clearable="true"
                    ></el-input>
                </div>
                <span v-show="domain!=''">
                    <span class="">选中节点：<i>{{selectedNode.name}}</i></span>
                </span>
                <!-- 固定的工具 -->
                <div class="fixed-tools">
                    <el-dropdown>
                        <el-button size="small">
                            导出<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>导出图片</el-dropdown-item>
                            <el-dropdown-item>导出CSV</el-dropdown-item>
                    </el-dropdown-menu>
                    </el-dropdown>
                </div>
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

<!--        <el-dialog title="导出图片">-->

<!--        </el-dialog>-->

<!--        <el-dialog title="节点编辑" :visible.sync="nodeEditorVisible">-->
<!--&lt;!&ndash;            <el-tabs>&ndash;&gt;-->
<!--&lt;!&ndash;                <el-tab-pane label="属性编辑" name="propsEdit">&ndash;&gt;-->
<!--                    <el-form :model="graphEntity">-->
<!--                        <el-form-item label="节点名称">-->
<!--                            <el-input v-model="graphEntity.name"></el-input>-->
<!--                        </el-form-item>-->
<!--                        <el-form-item label="选择颜色">-->
<!--                            <el-color-picker v-model="graphEntity.color"-->
<!--                                             :predefine="predefineColor">-->
<!--                            </el-color-picker>-->
<!--                        </el-form-item>-->
<!--                        <el-form-item label="节点半径">-->
<!--                            <el-slider v-model="graphEntity.radius"-->
<!--                                       min="5"-->
<!--                                       max="15"-->
<!--                            ></el-slider>-->
<!--                        </el-form-item>-->
<!--                    </el-form>-->
<!--&lt;!&ndash;                </el-tab-pane>&ndash;&gt;-->
<!--&lt;!&ndash;            </el-tabs>&ndash;&gt;-->
<!--        </el-dialog>-->
    </div>
</template>

<script>
    export default {
        name: "editor",
        // eslint-disable-next-line vue/no-unused-components
        data(){
            return{
                keyName:'',
                domain:'',
                selectedNode:{
                    id:'',
                    name:'',
                }
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
    .edit-tools{
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid lightgray;
        position: absolute;
        left: 300px;
        top: 0;
        width: 100%;
        display: flex;
    }
    /*.fixed-tools{*/
    /*    float: right;*/
    /*}*/

</style>