<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店工作人员管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店管理员</a-button>
                </div>
                <a-table
                    :columns="columns_man"
                    :dataSource="managerList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small"
                                  @click="reviseUsersInfo(record.id)">修改用户信息</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定删除该用户吗？"
                            @confirm="confirmDeleteUser(record.id)"
                            @cancel="cancelDeleteUser"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="网站营销人员管理" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addMarketer"><a-icon type="plus" />添加营销人员</a-button>
                </div>
                <a-table
                        :columns="columns_mar"
                        :dataSource="marketerList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small"
                                  @click="reviseUsersInfo(record.id)">修改用户信息</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定删除该用户吗？"
                                @confirm="confirmDeleteUser(record.id)"
                                @cancel="cancelDeleteUser"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="客户管理" key="3">
                <a-table
                        :columns="columns_cli"
                        :dataSource="clientList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small"
                                  @click="reviseUsersInfo(record.id)">修改用户信息</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定删除该用户吗？"
                                @confirm="confirmDeleteUser(record.id)"
                                @cancel="cancelDeleteUser"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <addMarketerModal></addMarketerModal>
        <reviseUsersInfo></reviseUsersInfo>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import addMarketerModal from "./components/addMarketerModal";
import reviseUsersInfo from "./components/reviseUsersInfo";
const moment = require('moment')
const columns_man = [
    {  
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {  
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns_mar = [
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const columns_cli = [
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '信用值',
        dataIndex: 'credit',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name: 'manageUser',//账户管理界面
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns_man,
            columns_mar,
            columns_cli,
            data: [],
            // form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        reviseUsersInfo,
        AddManagerModal,
        addMarketerModal,
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'addManagerModalVisible',
            'managerList',
            'marketerList',
            'clientList',
            'reviseUsersInfoVisible',
        ])
    },
    mounted() {
      this.getManagerList()
      this.getAllClients()
      this.getMarketerList()
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'getAllClients',
            'getMarketerList',
            'deleteUser',
            'reviseUserInfo',
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_addMarketerModalVisible',
            'set_selectedUserId',
            'set_reviseUsersInfoVisible',
        ]),
        addManager(){
            this.set_addManagerModalVisible(true)
        },
        addMarketer(){
            this.set_addMarketerModalVisible(true)
        },
        reviseUsersInfo(userId){
            this.set_selectedUserId(userId)
            this.set_reviseUsersInfoVisible(true)
        },
        confirmDeleteUser(userId){
            this.deleteUser(userId)
        },
        cancelDeleteUser(){

        },
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
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
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>