<template>
    <div class="whole">
        <div class="search">
            <el-col :span="8" class="center">
                <el-input
                        v-model="search"
                        @focus="focus"
                        @blur="blur"
                        @keyup.enter.native="searchHandler"
                        placeholder="搜索想要的节点..."
                        style="width: 270px"
                >
                    <el-button slot="append" icon="el-icon-search" style="color:white;" id="search" @click="searchHandler"></el-button>
                </el-input>
                <!---设置z-index优先级,防止被走马灯效果遮挡-->
                <el-card
                        @mouseenter="enterSearchBoxHanlder"
                        v-if="isSearch"
                        class="box-card"
                        id="search-box"
                        style="position:relative;z-index:15;width: 267px"
                >
                    <dl v-if="isHistorySearch">
                        <dt class="search-title" v-show="history">历史搜索</dt>
                        <dt class="remove-history" v-show="history" @click="removeAllHistory">
                            <i class="el-icon-delete"></i>清空历史记录
                        </dt>
                        <el-tag
                                v-for="search in historySearchList"
                                :key="search.id"
                                closable
                                :type="search.type"
                                @close="closeHandler(search)"
                                style="margin-right:5px; margin-bottom:5px;"
                        >{{search.name}}</el-tag>
                        <dt class="search-title">热门搜索</dt>
                        <dd v-for="search in hotSearchList" :key="search.id">{{search}}</dd>
                    </dl>
                    <dl v-if="isSearchList">
                        <dd v-for="search in searchList" :key="search.id">{{search}}</dd>
                    </dl>
                </el-card>
            </el-col>
        </div>
        <div class="result">

        </div>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import RandomUtil from "../../../../utils/randomUtil"
    import Store from "../../../../utils/store";
    export default {
        data() {
            return {
                search: "", //当前输入框的值
                isFocus: false, //是否聚焦
                hotSearchList: ["暂无热门搜索"], //热门搜索数据
                historySearchList: [], //历史搜索数据
                searchList: ["暂无数据"], //搜索返回数据,
                history: false,
                types: ["", "success", "info", "warning", "danger"], //搜索历史tag式样
                searchNodesResult: [],  //搜索节点结果
            };
        },
        computed:{
            ...mapGetters([
                'nodeListVisible',
                'nodesData',
                'linksData',
            ]),
            isHistorySearch() {
                return this.isFocus && !this.search;
            },
            isSearchList() {
                return this.isFocus && this.search;
            },
            isSearch() {
                return this.isFocus;
            }
        },
        mounted(){
            const nodesData=this.nodesData;
            console.log("tab1组件");
            console.log(nodesData);
            for(var i = 0; i < nodesData.length; i++) {
                console.log("name:"+nodesData[i].name+"\ndescription: "+nodesData[i].description+"\nshape: "+nodesData[i].shape+"\ntype: "+nodesData[i].type);
            }

        },
        methods: {
            focus() {
                this.isFocus = true;
                this.historySearchList =
                    Store.loadHistory() == null ? [] : Store.loadHistory();
                this.history = this.historySearchList.length == 0 ? false : true;
            },
            blur() {
                var self = this;
                this.searchBoxTimeout = setTimeout(function() {
                    self.isFocus = false;
                }, 300);
            },
            enterSearchBoxHanlder() {
                clearTimeout(this.searchBoxTimeout);
            },
            searchHandler() {
                this.searchNodes(this.search)
                console.log(this.searchNodesResult)
                //随机生成搜索历史tag式样
                let n = RandomUtil.getRandomNumber(0, 5);
                let exist =
                    this.historySearchList.filter(value => {
                        return value.name == this.search;
                    }).length == 0
                        ? false
                        : true;
                if (!exist) {
                    this.historySearchList.push({ name: this.search, type: this.types[n] });
                    Store.saveHistory(this.historySearchList);
                }
                this.history = this.historySearchList.length == 0 ? false : true;
            },
            closeHandler(search){
                this.historySearchList.splice(this.historySearchList.indexOf(search), 1);
                Store.saveHistory(this.historySearchList);
                clearTimeout(this.searchBoxTimeout);
                if (this.historySearchList.length == 0) {
                    this.history = false;
                }
            },
            removeAllHistory() {
                Store.removeAllHistory();
            },
            //搜索节点
            searchNodes(content) {
                this.searchNodesResult = []
                for(var i = 0; i < this.nodesData.length; i++) {
                    if(this.nodesData[i].name.search(content) != -1) {
                        this.searchNodesResult.push(this.nodesData[i])
                    }
                }
                //searchNodesResult就是结果数组
            },
        }
    }
</script>

<style scoped>
    .showElement{
        margin: 20px;
        width: 120px;
        height: 120px;
        display: block;
        -webkit-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);
        -moz-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);
        box-shadow:0 0 10px rgba(52, 56, 66, 0.2);
        border-radius: 10px 10px;
    }
    .center {
        margin-top: 15px;
        margin-left: 20px;
    }
    .whole{
        display: block;
        border: solid 1px black;
    }
    .search{
        border: solid 1px black;
        height: 70px;
        width: 300px;
        padding-right: 20px;
    }
    .result{
        margin-left: 2px;
        width: 300px;
        height: 600px;
        border: solid 1px black;
    }
    #search {
        background-color: rgb(64,158,255);
        border-radius: 0%;
    }
    .search-title {
        color: #bdbaba;
        font-size: 15px;
        margin-bottom: 5px;
    }
    .remove-history {
        color: #bdbaba;
        font-size: 15px;
        float: right;
        margin-top: -22px;
    }
    #search-box {
        width: 555px;
        height: 300px;
        margin-top: 0px;
        padding-bottom: 20px;
    }
</style>