<template>
    <el-dialog
            title="会员充值"
            :visible="topUpVipDialogVisible"
            :before-close="cancelTopUp"
    >
        <el-row :gutter="9" style="margin-bottom: 20px">
            <el-col :span="8">
                <el-card shadow="hover" class="top-up-choice">
                    <div class="time">一个月</div>
                    <div class="money">￥15</div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="top-up-choice">
                    <div class="time">6个月</div>
                    <div class="money">￥80</div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="top-up-choice">
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
    import {mapGetters, mapMutations} from "vuex";

    export default {
        name: "topUpVipDialog",
        data(){
            return{

            }
        },
        computed:{
            ...mapGetters([
                'topUpVipDialogVisible'
            ])
        },
        methods:{
            ...mapMutations([
                'set_topUpVipDialogVisible'
            ]),
            cancelTopUp(){
                this.set_topUpVipDialogVisible(false);

            },
            submitTopUp(){
                this.set_topUpVipDialogVisible(false);
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
</style>
