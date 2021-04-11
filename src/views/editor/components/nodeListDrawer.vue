<template>
    <el-drawer
            :with-header="false"
            :visible.sync="nodeListVisible"
            size="20%"
            :before-close="handleClose"
    >
        <el-tabs stretch="true" type="card" v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="节点列表" name="nodeList">
                <tab-node-list v-if="isChildUpdate1"></tab-node-list>
            </el-tab-pane>
            <el-tab-pane label="关系列表" name="relationList">
                <tab-relation-list v-if="isChildUpdate2"></tab-relation-list>
            </el-tab-pane>
        </el-tabs>
    </el-drawer>
</template>

<script>
    import {mapGetters, mapMutations} from "vuex";
    import TabNodeList from "./tabComponents/tabNodeList";
    import TabRelationList from "./tabComponents/tabRelationList";

    export default {
        name: "nodeListDrawer",
        components: {TabRelationList, TabNodeList},
        data() {
            return {
                //默认第一个选项卡
                activeName: "nodeList",
                isChildUpdate1:true,
                isChildUpdate2:false
            }
        },
        computed:{
            ...mapGetters([
                'nodeListVisible',
                'nodesData',
                'linksData',
            ])
        },
        methods:{
            ...mapMutations([
                'set_nodeListVisible'
            ]),
            handleClose(){
                this.set_nodeListVisible(false);
            },
            handleClick(tab) {
                if(tab.name == "nodeList") {
                    this.isChildUpdate1 = true;
                    this.isChildUpdate2 = false;
                } else if(tab.name == "relationList") {
                    this.isChildUpdate1 = false;
                    this.isChildUpdate2 = true;
                }
            }
        }
    }
</script>

<style scoped>

</style>
