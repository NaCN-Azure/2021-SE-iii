<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="hotelList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small"
                                  @click="changeHotelManager(record.id)"
                                  v-if="record.managerId != NULL">修改管理员</a-button>
                        <a-divider type="vertical" v-if="record.managerId != NULL"></a-divider>
                        <a-button type="primary"
                                  size="small"
                                  @click="changeHotelManager(record.id)" v-if="record.managerId == NULL">添加管理员</a-button>
                        <a-divider type="vertical" v-if="record.managerId == NULL"></a-divider>
                        <a-popconfirm
                                title="确定删除该酒店吗？"
                                @confirm="confirmDeleteHotel(record.id)"
                                @cancel="cancelDeleteHotel"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

        </a-tabs>
        <AddHotelModal></AddHotelModal>
        <AddManagerModal></AddManagerModal>
        <change-hotel-manager></change-hotel-manager>
    </div>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import AddHotelModal from './components/addHotelModal'
    import AddManagerModal from './components/addManagerModal'
    import changeHotelManager from "./components/changeHotelManager";
    const moment = require('moment')
    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'name',
        },
        {
            title: '城市',
            dataIndex: 'city',
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar'
        },
        {
            title: '评分',
            dataIndex: 'rate',
        },
        {
            title: '简介',
            dataIndex: 'description',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: 'manageAllHotel',//酒店管理界面
        data(){
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns1,
                form: this.$form.createForm(this, { name: 'manageAllHotel' }),
            }
        },
        components: {
            AddHotelModal,
            AddManagerModal,
            changeHotelManager,
        },
        computed: {
            ...mapGetters([
                'hotelList',
                'addHotelModalVisible',
                'addManagerModalVisible',
                'activeHotelId',
            ]),
        },
        async mounted() {
            await this.getHotelList()
            await this.getManagerList()
        },
        methods: {
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_activeHotelId',
                'set_addManagerModalVisible',
                'set_changeHotelManagerVisible',
                'set_curHotelId',
            ]),
            ...mapActions([
                'getHotelList',
                'getManagerList',
                'deleteHotel',
            ]),
            addHotel() {
                this.set_addHotelModalVisible(true)
            },
            changeHotelManager(hotelId){
                this.set_curHotelId(hotelId)
                this.set_changeHotelManagerVisible(true)
            },
            confirmDeleteHotel(hotelId){
                this.deleteHotel(hotelId);
            },
            cancelDeleteHotel(){

            },
            deleteOrder(){

            },
        }
    }
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
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
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>