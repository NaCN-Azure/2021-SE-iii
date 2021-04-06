<template>
    <el-drawer
            :with-header="false"
            :visible.sync="nodeListVisible"
            size="20%"
            :before-close="handleClose"
    >
        <el-tabs stretch="true" type="card">
            <el-tab-pane label="节点列表"></el-tab-pane>
            <el-tab-pane label="关系列表"></el-tab-pane>
        </el-tabs>
    </el-drawer>
</template>

<script>
    import {mapGetters, mapMutations} from "vuex";

    export default {
        name: "nodeListDrawer",
        computed:{
            ...mapGetters([
                'nodeListVisible',
            ])
        },
        methods:{
            ...mapMutations([
                'set_nodeListVisible'
            ]),
            handleClose(){
                this.set_nodeListVisible(false);
            }
        }
    }
</script>

<style scoped>

</style>
