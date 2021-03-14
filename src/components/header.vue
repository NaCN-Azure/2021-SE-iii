<template>
    <div class="header" style="border-bottom: 1px solid lightgray">
        <div class="label">
            <img src="../assets/logo.svg" class="logo" alt="logo" >
            <span class="title">Coinheap</span>
        </div>
        <el-menu
                default-active="activeIndex"
                mode="horizontal"
                text-color="#a9a9a9"
                active-text-color="#1e90ff"
                style="margin-left: 100px"
        >
            <el-menu-item class="nav-item" index="/home">
                <router-link to="/home">
                    <i class="el-icon-s-home"></i>
                    <span class="nav-name">首页</span>
                </router-link>
            </el-menu-item>
            <el-menu-item class="nav-item" index="/editor">
                <router-link to="/editor">
                    <i class="el-icon-edit-outline"></i>
                    <span class="nav-name">工作区</span>
                </router-link>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "header.vue",
        mounted() {

        },
    }
</script>

<style scoped>
    .header{
        display: flex;
        height: 60px;
        line-height: 60px;
    }
    .label{
        height: 60px;
        line-height: 60px;
        margin-left: 40px;
        vertical-align: middle;
    }
    .logo{
        height: 40px;
        vertical-align: top;
        padding-top: 10px;
        margin-right: 10px;
    }
    .title{
        font-size: 35px;
        font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
        color: #1e90ff;
        vertical-align: center;

    }
    .nav-name{
        margin-left: 0px;
        font-size: 18px;
    }
    .el-menu-item:hover{
        background-color: aliceblue !important;
    }
    a{
        text-decoration: none;
    }
</style>