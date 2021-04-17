<template>
    <div class="whole">
        <div class="search">
            <el-col :span="8" class="center">
                <el-input
                        v-model="search"
                        @focus="focus"
                        @blur="blur"
                        @keyup.enter.native="searchHandler"
                        placeholder="搜索感兴趣的内容..."
                        style="width: 350px;text-align: center"
                >
                    <el-button slot="append" icon="el-icon-search" style="color:white;" id="search" @click="searchHandler"></el-button>
                </el-input>
                <!---设置z-index优先级,防止被走马灯效果遮挡-->
                <el-card
                        @mouseenter="enterSearchBoxHanlder"
                        v-if="isSearch"
                        class="box-card"
                        id="search-box"
                        style="position:relative;z-index:15;width: 347px"
                >
                    <dl v-if="isHistorySearch">
                        <dt class="search-title" v-show="history">历史搜索</dt>
                        <dt class="remove-history" v-show="history" @click="removeAllHeadHistory">
                            <i class="el-icon-delete"></i>清空历史记录
                        </dt>
                        <el-tag
                                v-for="search in historySearchNodeList"
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
    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import RandomUtil from "../../../utils/randomUtil"
    import Store from "../../../utils/store";
    export default {
        name: "searchLocalCache",
        components: {},
        data() {
            return {
                search: "", //当前输入框的值
                isFocus: false, //是否聚焦
                hotSearchList: ["暂无热门搜索"], //热门搜索数据
                historySearchNodeList: [], //历史搜索数据
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
        },
        methods: {
            focus() {
                this.isFocus = true;
                this.historySearchNodeList =
                    Store.loadHeadHistory() == null ? [] : Store.loadHeadHistory();
                this.history = this.historySearchNodeList.length == 0 ? false : true;
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
                this.highlight(this.search);
                //随机生成搜索历史tag式样
                let n = RandomUtil.getRandomNumber(0, 5);
                let exist =
                    this.historySearchNodeList.filter(value => {
                        return value.name == this.search;
                    }).length == 0
                        ? false
                        : true;
                if (!exist) {
                    this.historySearchNodeList.push({ name: this.search, type: this.types[n] });
                    Store.saveHeadHistory(this.historySearchNodeList);
                }
                this.history = this.historySearchNodeList.length == 0 ? false : true;
            },
            closeHandler(search){
                this.historySearchNodeList.splice(this.historySearchNodeList.indexOf(search), 1);
                Store.saveHeadHistory(this.historySearchNodeList);
                clearTimeout(this.searchBoxTimeout);
                if (this.historySearchNodeList.length == 0) {
                    this.history = false;
                }
            },
            removeAllHeadHistory() {
                Store.removeAllHeadHistory();
            },
            //对指定的节点文本进行标记
            highlight(content) {
                console.log(content)
                this.searchNodes(content)
                if(this.mode==0)
                    this.$parent.initGraph(this.ummarkedNodes, this.linksData, this.markedNodes, 0.3, -100, 'red', 0.1, 1);
                else
                    this.$parent.initGraph(this.ummarkedNodes, this.linksData, this.markedNodes, 0, 0, 'red', 0.1, 1);
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
    .center {
        margin-top: 15px;
        margin-left: 20px;
    }
    .whole{
        display: block;
        /*border: solid 1px black;*/
    }
    .search{
        /*border: solid 1px black;*/
        height: 70px;
        width: 300px;
        padding-right: 20px;
    }
    .result{
        margin-top: 0;
        margin-left: 2px;
        padding-top: -10px;
        margin-bottom: 10px;
        width: 300px;
        height: 600px;
        overflow-y: auto;
        overflow-x: hidden;
        /*border: solid 1px black;*/
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