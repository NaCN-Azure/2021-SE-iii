<template>
    <a-modal
        :visible="changeHotelManagerVisible"
        title="修改管理员"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="现有管理员" v-bind="formItemLayout">
                <a-select style="width:120px" placeholder="请选择管理员" @change="changeManagerId">
                    <a-select-option v-for="manager in managerList" :key="manager.id">
                        {{manager.userName}}
                    </a-select-option>
                </a-select>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
import {mapActions,mapMutations,mapGetters} from 'vuex';

export default {
    name:'changeHotelManager',
    data(){
        return{
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
    computed:{
        ...mapGetters([
            'changeHotelManagerVisible',
            'managerList',
            'curHotelId',
            'selectedManagerId',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this,{name:'changeHotelManager'});
    },
    methods:{
        ...mapMutations([
            'set_changeHotelManagerVisible',
            'set_selectedManagerId',
        ]),
        ...mapActions([
            'changeManager',
        ]),
        cancel(){
            this.set_changeHotelManagerVisible(false)
            this.form.resetFields()
        },
        changeManagerId(value){
            this.set_selectedManagerId(value)
        },
        handleSubmit(e){
            e.preventDefault();
            this.form.validateFieldsAndScroll((err,values) => {
                if(!err){
                    const data = {
                        hotelId:this.curHotelId,
                        managerID:this.selectedManagerId,
                    }
                    this.changeManager(data)
                    this.form.resetFields()
                }
            });
        }
    }
}
</script>