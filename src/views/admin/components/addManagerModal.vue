<template>
    <a-modal
        :visible="addManagerModalVisible"
        title="添加管理员"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
    <a-Form :form="form">
        <a-form-item v-bind="formItemLayout" label="用户邮箱">
                <a-input
                    v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入用户邮箱', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                    v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码', }] }
                    ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input
                    v-decorator="[
                        'username',
                        { rules: [{required: true, message: '请输入用户名(请保持格式为 xx酒店管理员)', }] }
                    ]"
            />
        </a-form-item >
        <a-form-item v-bind="formItemLayout" label="手机号码">
            <a-input
                    v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请输入手机号码', }] }
                    ]"
            />
        </a-form-item >
    </a-Form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addManagerModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
        }
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'managerList',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addManagerModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_addManagerParams',
        ]),
        ...mapActions([
            'getManagerList',
            'addManager',
        ]),
        cancel() {
            this.set_addManagerModalVisible(false)
            this.form.resetFields()
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        email: this.form.getFieldValue('email'),
                        password: this.form.getFieldValue('password'),
                        username:this.form.getFieldValue('username'),
                        phoneNumber:this.form.getFieldValue('phoneNumber'),
                    }
                    this.set_addManagerParams(data)
                    this.addManager()
                    this.form.resetFields()
                }
            });
        },
    }
}
</script>