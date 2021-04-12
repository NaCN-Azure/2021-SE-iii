<template>
    <div class="whole">
        <div class="search">
            <el-col :span="8" class="center">
                <el-input
                        v-model="search"
                        @focus="focus"
                        @blur="blur"
                        @keyup.enter.native="searchHandler"
                        placeholder="搜索想要的关系..."
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
                        <dt class="remove-history" v-show="history" @click="removeAllLinkHistory">
                            <i class="el-icon-delete"></i>清空历史记录
                        </dt>
                        <el-tag
                                v-for="search in historySearchLinkList"
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
        <div class="result" id="resultShow">
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
                historySearchLinkList: [], //历史搜索数据
                searchList: ["暂无数据"], //搜索返回数据,
                history: false,
                types: ["", "success", "info", "warning", "danger"], //搜索历史tag式样
                searchLinksResult: [],
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
            console.log("tab2组件");
            console.log(this.linksData);
            this.showSearchRelations(this.linksData);

        },
        methods: {
            focus() {
                this.isFocus = true;
                this.historySearchLinkList =
                    Store.loadLinkHistory() == null ? [] : Store.loadLinkHistory();
                this.history = this.historySearchLinkList.length == 0 ? false : true;
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
                this.searchLinks(this.search);
                this.showSearchRelations(this.searchLinksResult)
                //随机生成搜索历史tag式样
                let n = RandomUtil.getRandomNumber(0, 5);
                let exist =
                    this.historySearchLinkList.filter(value => {
                        return value.name == this.search;
                    }).length == 0
                        ? false
                        : true;
                if (!exist) {
                    this.historySearchLinkList.push({ name: this.search, type: this.types[n] });
                    Store.saveLinkHistory(this.historySearchLinkList);
                }
                this.history = this.historySearchLinkList.length == 0 ? false : true;
            },
            closeHandler(search){
                this.historySearchLinkList.splice(this.historySearchLinkList.indexOf(search), 1);
                Store.saveLinkHistory(this.historySearchLinkList);
                clearTimeout(this.searchBoxTimeout);
                if (this.historySearchLinkList.length == 0) {
                    this.history = false;
                }
            },
            removeAllLinkHistory() {
                Store.removeAllLinkHistory();
            },
            searchLinks(content) {
                this.searchLinksResult = []
                for(var i = 0; i < this.linksData.length; i++) {
                    if(this.linksData[i].name.search(content) != -1) {
                        this.searchLinksResult.push(this.linksData[i])
                    }
                }
                //searchLinksResult就是结果数组
            },
            createRelationHtml(name,start,end){
                return "            <div style='display:block;margin-top: 10px;margin-bottom: 15px;margin-left:20px;width: 250px;height: 110px;-webkit-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);-moz-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);box-shadow:0 0 10px rgba(52, 56, 66, 0.2);border-radius: 10px 10px;'>\n" +
                    "                <svg t=\"1618218774209\" class=\"icon\" style=\"margin-top: 27px;margin-left: 35px\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2192\" width=\"50\" height=\"50\"><path d=\"M824.888889 682.666667c-28.444444 0-56.888889 11.377778-79.644445 22.755555l-45.511111-45.511111c22.755556-34.133333 39.822222-73.955556 39.822223-119.466667 0-62.577778-28.444444-119.466667-73.955556-153.6l22.755556-45.511111h22.755555C790.755556 341.333333 853.333333 278.755556 853.333333 199.111111S790.755556 56.888889 711.111111 56.888889 568.888889 119.466667 568.888889 199.111111c0 51.2 28.444444 91.022222 62.577778 119.466667l-17.066667 39.822222c-22.755556-11.377778-45.511111-17.066667-73.955556-17.066667C432.355556 341.333333 341.333333 432.355556 341.333333 540.444444c0 28.444444 5.688889 51.2 17.066667 73.955556l-39.822222 17.066667c-28.444444-34.133333-68.266667-62.577778-119.466667-62.577778C119.466667 568.888889 56.888889 631.466667 56.888889 711.111111S119.466667 853.333333 199.111111 853.333333 341.333333 790.755556 341.333333 711.111111v-22.755555l45.511111-22.755556c34.133333 45.511111 91.022222 73.955556 153.6 73.955556 45.511111 0 85.333333-17.066667 119.466667-39.822223l45.511111 45.511111c-11.377778 22.755556-22.755556 51.2-22.755555 79.644445 0 79.644444 62.577778 142.222222 142.222222 142.222222s142.222222-62.577778 142.222222-142.222222-62.577778-142.222222-142.222222-142.222222z m-113.777778-568.888889c45.511111 0 85.333333 39.822222 85.333333 85.333333S756.622222 284.444444 711.111111 284.444444 625.777778 244.622222 625.777778 199.111111 665.6 113.777778 711.111111 113.777778z m-512 682.666666c-45.511111 0-85.333333-39.822222-85.333333-85.333333S153.6 625.777778 199.111111 625.777778s85.333333 39.822222 85.333333 85.333333S244.622222 796.444444 199.111111 796.444444zM398.222222 540.444444C398.222222 460.8 460.8 398.222222 540.444444 398.222222S682.666667 460.8 682.666667 540.444444 620.088889 682.666667 540.444444 682.666667 398.222222 620.088889 398.222222 540.444444z m426.666667 369.777778c-45.511111 0-85.333333-39.822222-85.333333-85.333333s39.822222-85.333333 85.333333-85.333333 85.333333 39.822222 85.333333 85.333333-39.822222 85.333333-85.333333 85.333333z\" fill=\"#1296DB\" p-id=\"2193\"></path></svg>\n" +
                    "                <div style=\"font-size: 12px;width: 200px;height: 15px;margin-left: 120px;margin-top: -55px\">关系名称："+name+"</div>\n" +
                    "                <div style=\"font-size: 12px;width: 200px;height: 15px;margin-left: 120px;margin-top: 5px\">起始节点："+start+"</div>\n" +
                    "                <div style=\"font-size: 12px;width: 200px;height: 15px;margin-left: 120px;margin-top: 5px\">目标节点："+end+"</div>\n" +
                    "            </div>";
            },
            showSearchRelations(relations){
                document.getElementById("resultShow").innerHTML="";
                const relationsData=relations;
                for(var i = 0; i < relationsData.length; i++) {
                    document.getElementById("resultShow").innerHTML+=this.createRelationHtml(relationsData[i].name,relationsData[i].source.name,relationsData[i].target.name);
                }
            },
        }
    }
</script>

<style scoped>
    .showElement{
        margin: 20px;
        width: 260px;
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