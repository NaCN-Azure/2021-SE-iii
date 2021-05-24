<template>
    <div class="header" style="border-bottom: 1px solid lightgray">
        <div class="label">
            <img src="../assets/logo.svg" class="logo" alt="logo" >
            <span class="title">Coinheap</span>
        </div>
        <el-menu
                :default-active="activeIndex"
                mode="horizontal"
                text-color="#a9a9a9"
                active-text-color="#1e90ff"
        >
            <el-menu-item class="nav-item" index="1">
                <router-link to="/home">
                    <i class="el-icon-s-home"></i>
                    <span class="nav-name">首页</span>
                </router-link>
            </el-menu-item>
            <el-menu-item class="nav-item" index="2">
                <router-link to="/editor">
                    <i class="el-icon-edit-outline"></i>
                    <span class="nav-name">工作区</span>
                </router-link>
            </el-menu-item>
        </el-menu>
<!--        用户信息-->
        <div class="right-info">
            <el-dropdown @command="handleCommand" v-if="isLogin">
                <div class="user">
                    <el-avatar :src=userInfo.avatar alt="user" :size="45" v-if="isLogin"></el-avatar>
                    <span class="username">{{userInfo.nickname}}</span>
                </div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
                    <el-dropdown-item command="logout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button type="primary" size="mini" round="true" @click="goLogin" v-else>登录</el-button>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapMutations} from "vuex";
    import cookie from "js-cookie";

    export default {
        name: "header.vue",
        mounted() {

        },
        data(){
            return{
                activeIndex:'2'
            }
        },
        computed:{
            ...mapGetters([
                'userInfo',
                'isLogin'
            ])
        },
        methods:{
            ...mapMutations([
                'set_userInfo',
                'set_isLogin'
            ]),
            goLogin(){
                this.$router.push('/login');
            },
            handleCommand(command){
                if(command=='userCenter'){
                    // 个人中心
                    this.$router.push('/userCenter');
                }else{
                    // logout
                    cookie.set('coin_token', '')
                    cookie.set('coin_user', '')
                    this.set_userInfo('')
                    this.set_isLogin(false)
                    this.$router.push('/home')
                }
            },
        }
    }
</script>

<style scoped>
    .header{
        display: flex;
        width: 100%;
        height: 60px;
        line-height: 60px;
        align-items: center;
        justify-content: space-between;
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
    .right-info{
        height: 100%;
        margin-right: 30px;
    }
    .user{
        display: flex;
        align-items: center;
        cursor: pointer;
    }
    .username{
        margin-left: 15px;
        font-size: 15px;
    }
</style>
