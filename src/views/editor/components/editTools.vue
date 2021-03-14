<template>
    <div class="editTools">
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
    </div>
</template>

<script>
    export default {
        name: "editTools"
    }
</script>

<style scoped>

</style>