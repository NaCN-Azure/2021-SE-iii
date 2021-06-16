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
                        style="width: 260px"
                        size="small"
                >
                    <el-button slot="append" icon="el-icon-search" style="color:white;" id="search" @click="searchHandler" size="small"></el-button>
                </el-input>
                <!---设置z-index优先级,防止被走马灯效果遮挡-->
                <el-card
                        @mouseenter="enterSearchBoxHanlder"
                        v-if="isSearch"
                        class="box-card"
                        id="search-box"
                        style="position:relative;z-index:15;width: 265px;height: 200px"
                >
                    <dl v-if="isHistorySearch">
                        <dt class="search-title" v-show="history" style="margin-top: -30px">历史搜索</dt>
                        <dt class="remove-history" v-show="history" @click="removeAllNodeHistory">
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
        <div id="sort">
            <el-radio v-model="sortBy" label="type" @change="sortSearchNodes">按节点类型排序</el-radio>
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
                historySearchNodeList: [], //历史搜索数据
                searchList: ["暂无数据"], //搜索返回数据,
                history: false,
                types: ["", "success", "info", "warning", "danger"], //搜索历史tag式样
                searchNodesResult: [],  //搜索节点结果
                sortBy:'createTime'
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
            console.log("tab1组件");
            console.log(this.nodesData)
            this.searchNodesResult = this.nodesData
            this.showSearchNodes(this.searchNodesResult);
        },
        methods: {
            focus() {
                this.isFocus = true;
                this.historySearchNodeList =
                    Store.loadNodeHistory() == null ? [] : Store.loadNodeHistory();
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
                this.searchNodes(this.search);
                // console.log(this.searchNodesResult)
                this.showSearchNodes(this.searchNodesResult);
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
                    Store.saveNodeHistory(this.historySearchNodeList);
                }
                this.history = this.historySearchNodeList.length == 0 ? false : true;
            },
            closeHandler(search){
                this.historySearchNodeList.splice(this.historySearchNodeList.indexOf(search), 1);
                Store.saveNodeHistory(this.historySearchNodeList);
                clearTimeout(this.searchBoxTimeout);
                if (this.historySearchNodeList.length == 0) {
                    this.history = false;
                }
            },
            removeAllNodeHistory() {
                Store.removeAllNodeHistory();
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
            // 节点排序
            sortSearchNodes(sortWay){
                console.log("sorting by", sortWay)
                // 按类型名排序
                if(sortWay==='type'){
                    this.searchNodesResult.sort(function (a,b) {
                        if(a.type==b.type){
                            return 0;
                        }else if(a.type<b.type){
                            return -1;
                        }else{
                            return 1;
                        }
                    })
                }
                this.showSearchNodes(this.searchNodesResult)
            },
            createCircleHtml(name,type,description,color){
                return "            <div class=\"showElement\" style='margin-top: 10px;margin-bottom: 15px;margin-left:20px;width: 250px;height: 110px;-webkit-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);-moz-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);box-shadow:0 0 10px rgba(52, 56, 66, 0.2);border-radius: 10px 10px;'>\n" +
                    "                <svg width='250px' height='110px' xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" +
                    "                    <circle cx=\"60\" cy=\"55\" r=\"25\" stroke=\"#999\" stroke-width=\"0\" fill="+color+" />\n" +
                    "                    <text x=\"120\" y=\"40\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点名称："+name+"</text>\n" +
                    "                    <text x=\"120\" y=\"60\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点类型："+type+"</text>\n" +
                    "                    <text x=\"120\" y=\"80\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点描述："+description+"</text>\n" +
                    "                </svg>\n" +
                    "            </div>\n"
            },
            createRectangleHtml(name,type,description,color){
                return "            <div class=\"showElement\" style='margin-top: 10px;margin-bottom: 15px;margin-left:20px;width: 250px;height: 110px;-webkit-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);-moz-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);box-shadow:0 0 10px rgba(52, 56, 66, 0.2);border-radius: 10px 10px;'>\n" +
                    "                <svg width='250px' height='110px' xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" +
                    "                    <rect  x=\"38\" y=\"34\" width=\"44\" height=\"44\" style=\"fill:"+color+";stroke-width:0;stroke:#999\" />\n" +
                    "                    <text x=\"120\" y=\"40\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点名称："+name+"</text>\n" +
                    "                    <text x=\"120\" y=\"60\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点类型："+type+"</text>\n" +
                    "                    <text x=\"120\" y=\"80\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点描述："+description+"</text>\n" +
                    "                </svg>\n" +
                    "            </div>"
            },
            createTriangleHtml(name,type,description,color){
                return "            <div class=\"showElement\" style='margin-top: 10px;margin-bottom: 15px;margin-left:20px;width: 250px;height: 110px;-webkit-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);-moz-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);box-shadow:0 0 10px rgba(52, 56, 66, 0.2);border-radius: 10px 10px;'>\n" +
                    "                <svg width='250px' height='110px' xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" +
                    "                    <polygon points=\"60,28 35,78 85,78\"\n" +
                    "                             style=\"fill:"+color+";stroke:#999;stroke-width:0\"/>\n" +
                    "                    <text x=\"120\" y=\"40\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点名称："+name+"</text>\n" +
                    "                    <text x=\"120\" y=\"60\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点类型："+type+"</text>\n" +
                    "                    <text x=\"120\" y=\"80\" fill=\"black\" text-anchor=\"start\" font-size=\"12px\">节点描述："+description+"</text>\n" +
                    "                </svg>\n" +
                    "        </div>"
            },
            showSearchNodes(nodes){
                document.getElementById("resultShow").innerHTML="";
                const nodesData=nodes;
                // console.log(nodesData);
                for(var i = 0; i < nodesData.length; i++) {
                    switch (nodesData[i].shape) {
                        case 0:{
                            document.getElementById("resultShow").innerHTML+=this.createCircleHtml(nodesData[i].name,nodesData[i].type,nodesData[i].description,nodesData[i].bgColor);
                            break;
                        }
                        case 1:{
                            document.getElementById("resultShow").innerHTML+=this.createRectangleHtml(nodesData[i].name,nodesData[i].type,nodesData[i].description,nodesData[i].bgColor);
                            break;
                        }
                        case 2:{
                            document.getElementById("resultShow").innerHTML+=this.createTriangleHtml(nodesData[i].name,nodesData[i].type,nodesData[i].description,nodesData[i].bgColor);
                            break;
                        }
                    }
                    // console.log("name:"+nodesData[i].name+"\ndescription: "+nodesData[i].description+"\nshape: "+nodesData[i].shape+"\ntype: "+nodesData[i].type);
                }
            },
        }
    }
</script>

<style scoped>
    .showElement{
        margin-top: 20px;
        width: 260px;
        height: 110px;
        display: block;
        -webkit-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);
        -moz-box-shadow:0 0 10px rgba(52, 56, 66, 0.2);
        box-shadow:0 0 15px rgba(52, 56, 66, 0.2);
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
    #sort{
        float: right;
        padding-right: 20px;
        margin-bottom: 10px;
        font-size: 13px;
        color: gray;
    }
</style>
