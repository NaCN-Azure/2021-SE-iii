<template>
    <a-modal
        :visible="reviseUsersInfoVisible"
        title="修改用户信息"
        okText="保存"
        cancelText="取消"
        @ok="saveModify"
        @cancel="cancelModify"
    >
            <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
                    <a-form-item label="用户名" v-bind="formItemLayout">
                        <a-input
                            placeholder="请修改用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                        />
                    </a-form-item>
                    
                    <a-form-item label="手机号" v-bind="formItemLayout">
                        <a-input
                            placeholder="请修改手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请修改手机号' }] }]"
                        />
                    </a-form-item>

                    <a-form-item label="密码" v-bind="formItemLayout">
                        <a-input
                            placeholder="请修改密码"
                            v-decorator="['password', { rules: [{ required: true, message: '请修改密码' }] }]"
                        />
                    </a-form-item>
            </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
    name: 'reviseUsersInfo',//管理员修改酒店管理员或网站营销人员用户信息
    data(){
        return {
            formItemLayout:{
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            }
        }
    },
    computed: {
        ...mapGetters([
            'reviseUsersInfoVisible',
            'selectedUserId',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'reviseUsersInfo' });
    },
    methods: {
        ...mapMutations([
            'set_reviseUsersInfoVisible',
            'set_reviseUsersInfoParams',
        ]),
        ...mapActions([
            'reviseUserInfo',
        ]),
        saveModify(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        id:this.selectedUserId,
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password')
                    }
                    this.set_reviseUsersInfoParams(data)
                    this.reviseUserInfo(data)
                    this.form.resetFields()
                }
            });
        },
        cancelModify() {
            this.modify = false
            this.set_reviseUsersInfoVisible(false)
            this.form.resetFields()
        },
    }
}
</script>
<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>