<template>
    <el-dialog
            title="会员充值"
            :visible="topUpVipDialogVisible"
            :before-close="cancelTopUp"
    >
        <el-row :gutter="9" style="margin-bottom: 20px">
            <el-col :span="8">
                <el-card shadow="hover" class="top-up-choice" @click.native="setDays(30)">
                    <div class="time">一个月</div>
                    <div class="money">￥15</div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="top-up-choice" @click.native="setDays(180)">
                    <div class="time">6个月</div>
                    <div class="money">￥80</div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="top-up-choice" @click.native="setDays(365)">
                    <div class="time">一年</div>
                    <div class="money">￥150</div>
                </el-card>
            </el-col>

        </el-row>
        <el-row>
            <el-col :span="24">
            <el-button round
                       style="background-color:rgb(240,213,157);color: rgb(51,51,51);border: none;width: 100%"
                       @click="submitTopUp"
            >确认充值</el-button>
            </el-col>
        </el-row>

    </el-dialog>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {setVipUserAPI} from "../../api/users";
    import {Message} from "element-ui";

    export default {
        name: "topUpVipDialog",
        data(){
            return{
                days:''
            }
        },
        computed:{
            ...mapGetters([
                'topUpVipDialogVisible',
                'userInfo'
            ])
        },
        methods:{
            ...mapMutations([
                'set_topUpVipDialogVisible'
            ]),
            ...mapActions([
                'getUserInfo'
            ]),
            setDays(days){
                this.days = days;
            },
            cancelTopUp(){
                this.set_topUpVipDialogVisible(false);

            },
            submitTopUp(){
                setVipUserAPI(this.userInfo.id,this.days).then(res => {
                    console.log(res)
                    if(res.data.code == 200){
                        Message({
                            type:'success',
                            message:'充值成功'
                        })
                    }else{
                        Message({
                            type:'error',
                            message:'充值失败'
                        })
                    }
                })
                this.set_topUpVipDialogVisible(false);
                this.getUserInfo()
            }
        }
    }
</script>

<style scoped>
    .top-up-choice{
        background-color: rgba(240,213,157);
        color: rgb(51,51,51);
        text-align: center;
    }
    .time{
        font-size: 20px;
        margin-bottom: 20px;
    }
    .money{
        font-size: 15px;
    }
    .el-card{
        cursor: pointer;
    }
</style>
