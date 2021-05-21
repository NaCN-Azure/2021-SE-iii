<template>
    <div class="container" style="position: absolute;left: 0;top:60px;bottom: 0; width: 100%">
    <!-- 左侧-图谱（domain）列表 -->
        <div class="domain-list" style="height: 100%;text-align: center;;border-right: 1px solid lightgray">
            <el-scrollbar style="height:100%;width: 300px;overflow-x: hidden">
                <div class="domain-list-head" style="margin-top: 15px;">
                    <span style="font-size: 16px;color: grey;">图谱列表</span>
                    <el-tooltip content="添加图谱" placement="right">
                    <el-button
                            size="mini"
                            style="float: right;margin-top: 10px;margin-right: 20px"
                            circle
                            @click="addDomain"
                            icon="el-icon-plus"
                            type="primary"
                    ></el-button></el-tooltip>
                </div>
                <div class="domain-table" style="margin-top: 10px">
                    <el-table
                            :data="domainList"
                            style="width:100%"
                            :show-header="false"
                            :row-style="{height:'30px'}"
                            :cell-style="{padding:'8px'}"
                    >
                        <el-table-column>
                            <template slot-scope="scope">
                                <el-tag
                                        closable
                                        effect="light"
                                        style="margin-left:3px;width: 98%; font-size: 15px;cursor: pointer"
                                        @close="deleteDomain(scope.row.id)"
                                        @click="selectDomain(scope.row)"
                                >{{scope.row.name}}</el-tag>
                            </template>
                        </el-table-column>

                    </el-table>
                </div>
                <!-- 添加搜索图谱功能 -->
<!--                <div class="search-domain">-->
<!--                </div>-->
            </el-scrollbar>
        </div>
        <!-- 左侧over -->
        <!-- 右侧-编辑知识图谱面板 -->
        <div class="edit-pane">
            <!--编辑面板头部，包括一些操作和节点信息的显示-->
            <div class="edit-tools">
                <!-- 显示当前图谱名称，可以在这里对图谱名称进行修改 TODO-->
                <div class="edit-tool" v-show="selectedDomain.name!=''" style="display: flex">
                    当前图谱：<el-input v-model="selectedDomain.name" size="small" style="width: 100px"></el-input>
                </div>
                <div class="edit-tool" v-show="selectedDomain.name!=''">
                    <span class="">选中节点：</span>
                    <span>{{selectedNode.name}}</span>
                </div>
                <div class="multi-mode">
                    <el-radio-group v-model="isCollapse" @change="buttonChange" size="small">
                        <el-radio-button :label="true" class="mode-button" id="mode-button-first"  size="small">力导模式</el-radio-button>
                        <el-radio-button :label="false" class="mode-button" id="mode-button-second"  size="small">排版模式</el-radio-button>
                    </el-radio-group>
                </div>
                <div v-show="selectedDomain.name!=''" style="margin-left: 650px;position: absolute">
                    <span class="">节点个数：</span>
                    <el-input v-model="nodesData.length" size="small" style="width: 50px"></el-input>
                    <span class="" style="margin-left: 20px">关系个数：</span>
                    <el-input v-model="linksData.length" size="small" style="width: 50px"></el-input>
                </div>
                <!-- 固定的工具 -->
                <div class="fixed-tools">

                    <el-button class="edit-tool" size="small" type="primary" @click="showCreateNodeDialog">添加节点</el-button>
                    <el-button class="edit-tool" size="small" type="primary" @click="showCreateLinkDialog">添加关系</el-button>
                    <el-dropdown>
                        <el-button size="small" class="edit-tool">
                            导出<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="exportPic">导出图片</el-dropdown-item>
                            <el-dropdown-item @click.native="exportXml">导出xml</el-dropdown-item>
                            <!-- <el-dropdown-item @click.native="test">过滤</el-dropdown-item> -->
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
            <!-- 右边中间-图谱渲染区域 -->
            <div class="graph">
                <el-scrollbar style="width: 100%;height: 100%">
                    <svg id="kgGraph" width="1300" height="650"></svg>
                </el-scrollbar>
            </div>
            <div class="left-side-bar">
                <el-button @click="cancelZoom" size="mini" type="primary" title="全屏" plain icon="el-icon-full-screen" style="margin-left: 10px;margin-top: 10px" circle></el-button>
                <el-button @click="vanishAllRelationships" size="mini" type="primary" title="隐藏关系" plain icon="el-icon-view" style="margin-left: 10px;margin-top: 10px" circle></el-button>
                <el-button @click="saveGraph" size="mini" type="primary" title="保存" plain icon="el-icon-document-checked" style="margin-left: 10px;margin-top: 10px" circle></el-button>
            </div>
            <div class="right-side-bar">
                <el-button
                    @click="showNodeList"
                    size="mini"
                    style="margin-right: 15px;margin-top: 10px;width: 90px"
                    type="primary" plain
                >节点与关系</el-button>
                <node-list-drawer/>
            </div>

            <div class="center-side-bar">
                <el-select
                    v-model="typeValue"
                    multiple
                    collapse-tags
                    style="margin-left: 70px;margin-top: 5px;width: 155px"
                    placeholder="类型筛选...">
                <el-option
                        v-for="item in this.options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <search-local-cache style="margin-left: 35%;margin-top: -55px;"></search-local-cache>

            </div>



            <!-- 节点操作 -->
            <ul class="el-dropdown-menu el-popper" id="node-custom-menu" style="display: none">
                <li class="el-dropdown-menu__item" @click="editNode">
                    <span>编辑</span>
                </li>
                <li  class="el-dropdown-menu__item" @click="addLinkFromNode">
                    <span class="pl-15">添加关系</span>
                </li>
                <li class="el-dropdown-menu__item" @click="deleteNode">
                    <span class="pl-15">删除</span>
                </li>
            </ul>

            <!-- 关系操作 -->
            <ul class="el-dropdown-menu el-popper" id="link-custom-menu" style="display: none">
                <li class="el-dropdown-menu__item" @click="editLink">
                    <span>编辑</span>
                </li>
                <li class="el-dropdown-menu__item" @click="deleteLink">
                    <span class="pl-15">删除</span>
                </li>
            </ul>

            <!-- 节点富文本展示 -->
            <div id="rich-container" style="display: none;">
                <div class="mind-fj-box" v-show="popoverContent!=''">
                    <el-scrollbar v-show="popoverContent!=''" class="mind-fj-p">
                        <p v-html="popoverContent"></p>
                    </el-scrollbar>
                </div>
            </div>
        </div>

        <create-node-dialog/>
        <edit-node-dialog/>
        <add-domain-dialog/>
        <create-link-dialog/>
        <edit-link-dialog/>
    </div>
</template>

<script>
    import * as d3 from 'd3';
    import $ from 'jquery';
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {updateNodeAPI, deleteNodeAPI, updateXYAPI} from "../../api/entity";
    import {deleteDomainAPI} from "../../api/domain";
    import {deleteLinkAPI, getLinkByDomainIdAPI} from "../../api/relationship";
    import {downloadAPI, exportGraphXMLAPI} from "../../api/file";
    import CreateNodeDialog from "./components/createNodeDialog";
    import EditNodeDialog from "./components/editNodeDialog";
    import AddDomainDialog from "./components/addDomainDialog";
    import CreateLinkDialog from "./components/createLinkDialog";
    import EditLinkDialog from "./components/editLinkDialog";
    import NodeListDrawer from "./components/nodeListDrawer";
    import searchLocalCache from "./components/searchLocalCache";
    import { Message } from 'element-ui'


    export default {
        name: "editor",
        components: {
            searchLocalCache,
            NodeListDrawer, EditLinkDialog, CreateLinkDialog, AddDomainDialog, EditNodeDialog, CreateNodeDialog},
        inject:['reload'],
        data(){
            return{
                width: 1300,
                height: 650,
                // 选中要进行操作的节点
                selectedNode:{
                    id:'',
                    name:'',
                    bgColor:'',
                    shape:'',
                    domainId:'',
                    nodeType:'',
                    description:'',
                    r: '',
                    fontSize: 20,  //字体大小
                },
                selectedLink:{
                    id:'',
                    name:'',
                    type:1, // 能被选中的关系一定type是1
                    fromId:'',
                    toId:'',
                    domainId:'',
                },
                popoverContent:'',
                simulation: null,
                markedSimulation: null,
                circleNodes: [],
                rectNodes: [],
                triangleNodes: [],
                links: [],
                nodeText: [],
                nodeTextWithColor: [],
                linkText: [],
                svg: null,
                timer: null,

                touchedNodes: [],  //被动过的节点集合

                markedNodes: [],    //搜索节点结果，即标记的节点
                circleMarkedNodes: [],
                rectMarkedNodes: [],
                triangleMarkedNodes: [],
                ummarkedNodes: [],  //未标记的节点

                isCollapse: true,
                typeValue: [],
            }
        },

        directives: {
            focus: {
                inserted: function(el) {
                    el.focus();
                }
            }
        },

        created(){
            this.getAllDomains(this.userInfo.id);
        },

        computed:{
            ...mapGetters([
                'selectedDomain',
                'createNodeParams',
                'domainList',
                'relationships',
                'nodesData',
                'linksData',
                'nodeListVisible',
                'mode',
                'value',
                'options',
                'userInfo',
                'isLogin',
            ])
        },

        methods:{
            ...mapMutations([
                'set_selectedDomain',
                'set_createNodeDialogVisible',
                'set_createNodeParams',
                'set_editNodeDialogVisible',
                'set_editNodeParams',
                'set_addDomainDialogVisible',
                'set_createLinkDialogVisible',
                'set_createLinkParams',
                'set_editLinkDialogVisible',
                'set_editLinkParams',
                'set_nodesData',
                'set_linksData',
                'set_nodeListVisible',
                'set_relationships',
                'set_mode',
                'set_options',
                'set_value',
            ]),
            ...mapActions([
                'getAllDomains',
                'getDomainById',
            ]),

            //初始化图谱
            init() {
                this.set_nodesData(this.getNodesFromRelationships(this.relationships))
                this.set_linksData(this.getLinksFromRelationships(this.relationships))
                console.log(this.nodesData)
                if (this.mode == 0) {
                    console.log('力导图初始化开始')
                    this.initGraph(this.nodesData, this.linksData, [], 0.3, -100, 'black', 1, 1)
                }
                else {
                    console.log('排版模式初始化开始')
                    this.composeModeSet(this.nodesData);
                    this.initGraph(this.nodesData, this.linksData, [], 0, 0, 'black', 1, 1)
                }
            },

            initGraph(nodesData, linksData, colorNodes, elasticForce, electromagneticForce, nodeTextColor, nodesDataOpacity, colorNodesOpacity){
                //simulation需要的是所有节点
                this.simulation = d3.forceSimulation(this.nodesData)
                    .force("link", d3.forceLink(linksData).id(d => d.id).distance(200).strength(elasticForce))
                    .force("collide", d3.forceCollide().radius(()=>50))
                    .force("charge", d3.forceManyBody().strength(electromagneticForce))
                    .force("center", d3.forceCenter(this.width / 2, this.height / 2))

                this.svg = d3.select('svg')
                    .attr("viewBox", [0, 0, this.width, this.height])
                    .attr("class", "svgCanvas")

                //清空svg内容!!!!!!
                this.svg.selectAll("*").remove()

                this.addMarker()

                const g = this.svg.append('g').attr("class", "content")

                //保存this
                var _this = this

                //关系初始化
                this.links = this.drawLinks(g, linksData, _this)

                //关系内容初始化
                this.linkText = this.drawLinkText(g, linksData)

                //圆形节点初始化
                this.circleNodes = this.drawCircleNodes(g, nodesData, _this, nodesDataOpacity)
                    .call(this.drag(this.simulation))
                //圆形被标记节点初始化
                this.circleMarkedNodes = this.drawCircleNodes(g, colorNodes, _this, colorNodesOpacity)
                    .call(this.drag(this.simulation))

                //矩形节点初始化
                this.rectNodes = this.drawRectNodes(g, nodesData, _this, nodesDataOpacity)
                    .call(this.drag(this.simulation))
                //矩形被标记节点初始化
                this.rectMarkedNodes = this.drawRectNodes(g, colorNodes, _this, colorNodesOpacity)
                    .call(this.drag(this.simulation))

                //三角形节点初始化
                this.triangleNodes = this.drawTriangleNodes(g, nodesData, _this, nodesDataOpacity)
                    .call(this.drag(this.simulation))
                //三角形被标记节点初始化
                this.triangleMarkedNodes = this.drawTriangleNodes(g, colorNodes, _this, colorNodesOpacity)
                    .call(this.drag(this.simulation))

                //节点内容初始化
                //分两步走，首先将没有标记的节点渲染，再将标记的节点渲染
                this.nodeText = this.drawNodeText(g, nodesData, _this, 'black', 20, false)
                this.nodeTextWithColor = this.drawNodeText(g, colorNodes, _this, nodeTextColor, 20, true)

                this.simulation.on("tick", () => {
                    this.links.attr("d", function(d) {
                        if(d.source.x < d.target.x && d.source.y < d.target.y){
                            return( "M " +
                            (d.source.x - (d.source.x - d.target.x)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            +" "+
                            (d.source.y-(d.source.y-d.target.y)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            +" L "+
                            (d.target.x-(d.target.x-d.source.x)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            +" "+
                            (d.target.y-(d.target.y-d.source.y)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            )
                        }else if(d.source.x<d.target.x&&d.source.y>=d.target.y){
                            return( "M "+
                            (d.source.x-(d.source.x-d.target.x)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            +" "+
                            (d.source.y-(d.source.y-d.target.y)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            +" L "+
                            (d.target.x-(d.target.x-d.source.x)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            +" "+
                            (d.target.y-(d.target.y-d.source.y)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            )
                        }else if(d.source.x>d.target.x&&d.source.y<d.target.y){
                            return( "M "+
                            (d.target.x-(d.target.x-d.source.x)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            +" "+
                            (d.target.y-(d.target.y-d.source.y)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            +" L "+
                            (d.source.x-(d.source.x-d.target.x)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            +" "+
                            (d.source.y-(d.source.y-d.target.y)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            )
                        }else{
                            return ("M " +
                            (d.target.x-(d.target.x-d.source.x)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            +" "+
                            (d.target.y-(d.target.y-d.source.y)*(d.target.r)/(Math.sqrt(Math.pow(d.target.y-d.source.y,2)+Math.pow(d.target.x-d.source.x,2))))
                            +" L "+
                            (d.source.x-(d.source.x-d.target.x)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            +" "+
                            (d.source.y-(d.source.y-d.target.y)*(d.source.r)/(Math.sqrt(Math.pow(d.source.y-d.target.y,2)+Math.pow(d.source.x-d.target.x,2))))
                            )
                        }
                        })
                        .attr("marker-end", function (d) {
                            if(d.source.x < d.target.x){
                                return "url(#positiveMarker)"
                            }
                            else {
                                return null
                            }
                        })
                        .attr("marker-start",function (d) {
                            if(d.source.x < d.target.x){
                                return null
                            }
                            else{
                                return "url(#negativeMarker)"
                            }
                        })

                    this.circleNodes
                        .attr('cx', (d) => { return d.x })
                        .attr('cy', (d) => { return d.y })
                    this.circleMarkedNodes
                        .attr('cx', (d) => { return d.x })
                        .attr('cy', (d) => { return d.y })

                    this.rectNodes
                        .attr("x", d => d.x - d.r * 0.707) //方形中心点
                        .attr("y", d => d.y - d.r * 0.707)
                    this.rectMarkedNodes
                        .attr("x", d => d.x - d.r * 0.707)
                        .attr("y", d => d.y - d.r * 0.707)

                    this.triangleNodes
                        .attr("d", function(d){
                            return "M "+(d.x-0.87*d.r)+" "+ (d.y+0.5*d.r)+" L "+(d.x+0.87*d.r-0.87*d.r)+" "+(d.y-1.5*d.r+0.5*d.r)+" L "+(d.x+d.r*1.73-0.87*d.r)+" "+(d.y+0.5*d.r)+" L "+(d.x-0.87*d.r)+" "+(d.y+0.5*d.r)
                        })
                    this.triangleMarkedNodes
                        .attr("d", function(d){
                            return "M "+(d.x-0.87*d.r)+" "+ (d.y+0.5*d.r)+" L "+(d.x+0.87*d.r-0.87*d.r)+" "+(d.y-1.5*d.r+0.5*d.r)+" L "+(d.x+d.r*1.73-0.87*d.r)+" "+(d.y+0.5*d.r)+" L "+(d.x-0.87*d.r)+" "+(d.y+0.5*d.r)
                        })

                    this.nodeText
                        .attr("x", d => d.x)
                        .attr("y", d => d.y)

                    this.nodeTextWithColor
                        .attr("x", d => d.x)
                        .attr("y", d => d.y)

                    })

                //缩放
                this.svg.call(d3.zoom().scaleExtent([0.3, 3]).on("zoom", function (event) {
                        g.attr("transform", event.transform)
                    }))

                // 点击空白处，关闭点开的菜单
                this.svg.on("click", function () {
                    $('#link-custom-menu').hide()
                    $('#node-custom-menu').hide()
                })
            },

            drawLinks(g, linksData, that) {
                return g.append("g")
                    .selectAll("path")
                    .data(linksData, function (d) {
                        if(typeof (d.source) === 'object'){
                            return d.source.id + "_"+d.name+"_"+d.target.id
                        }
                        else{
                            return d.source+"_"+d.name+"_"+d.target
                        }
                    })
                    .enter()
                    .append('path')
                    .attr("stroke", "#999")
                    .attr("stroke-opacity", 0.8)
                    .attr("marker-end", "url(#arrowMarker)")
                    .attr("fill-opacity", 0)
                    .attr("stroke-width", 2)
                    .attr("class", "link")
                    .on("contextmenu",function(d, i){
                        var cc = $(this).offset()
                        that.selectedLink.id = i.id
                        that.selectedLink.name = i.name
                        that.selectedLink.fromId = i.source.id
                        that.selectedLink.toId = i.target.id
                        that.selectedLink.domainId = i.domainId
                        d3.select('#link-custom-menu')
                            .style('position','absolute')
                            .style('left',cc.left-300+"px")
                            .style('top', cc.top-80+"px")
                            .style('display','block')
                        event.preventDefault()
                        event.stopPropagation()
                    })
                    .on('mouseenter',function (d) {
                        d3.select(this).style("stroke-width", "10").style("stroke", "#C6C1C5")
                    })
                    .on('mouseleave',function (d) {
                        d3.select(this).style("stroke-width", "3")
                    })
                    //虚线   stroke-dasharray 5, 5
                    .attr("id", function (d) {
                        if(typeof (d.source) === 'object'){
                            return d.source.id+"_"+d.name+"_"+d.target.id
                        }
                        else{
                            return d.source+"_"+d.name+"_"+d.target
                        }
                    })
            },

            drawLinkText(g, linksData) {
                return g.append("g")
                    .selectAll("text")
                    .data(linksData, function (d) {
                        if(typeof (d.source) === 'object'){
                            return d.source.id+"_"+d.name+"_"+d.target.id
                        }
                        else{
                            return d.source+"_"+d.name+"_"+d.target
                        }
                    })
                    .enter()
                    .append('text')
                    .style('text-anchor', 'middle')
                    .style('font-size', '20px')
                    .append('textPath')
                    .attr('xlink:href', function (d) {
                        if(typeof (d.source) === 'object'){
                            return "#" + d.source.id+"_"+d.name+"_"+d.target.id
                        }
                        else{
                            return "#" + d.source+"_"+d.name+"_"+d.target
                        }
                    })
                    .attr('startOffset', '50%')
                    // .style("font-size",function(d){
                    //     return d.font_size
                    // })
                    .text(d=>d.name)
            },

            drawCircleNodes(g, nodesData, that, opacity) {
                return g.append("g")
                    .selectAll("circle")
                    .data(nodesData)
                    .enter()
                    .append('circle')
                    .filter(d => d.shape == 0)
                    .attr('r', function (d) {
                        if(typeof(d.r) != "undefined" && d.r != ''){
                            return d.r
                        }
                        return 40   //默认半径是40
                    })
                    .attr("class","node")
                    .attr("fill-opacity", opacity)  //透明度
                    .attr("fill", function(d){
                        if(typeof(d.bgColor) != "undefined" && d.bgColor != ''){
                            return d.bgColor
                        }
                        return "#5290F2"  //一种蓝色
                    })
                    .on("contextmenu", function(d, i){
                        var cc = $(this).offset()
                        that.selectedNode = i
                        d3.select('#node-custom-menu')
                            .style('position', 'absolute')
                            .style('left', cc.left -250 + "px")
                            .style('top', cc.top -130 + "px")
                            .style('display', 'block')
                        event.preventDefault() // 禁止系统默认右键
                        event.stopPropagation() // 禁止空白处右键
                    })
                    .on('mouseenter', function (d, i) {
                        d3.select(this).style("stroke-width", "2").style("stroke","#999")
                        that.selectedNode = i
                    })
                    // 鼠标在节点上停留2s时，显示节点描述信息  （此功能暂时禁用，后面再讨论具体使用细节）
                    // .on('mouseover',function (d, i){
                    //     that.timer = setTimeout(function (d) {
                    //         that.popoverContent = i.description
                    //         d3.select("#rich-container")
                    //             .style('position', 'absolute')
                    //             .style('left', i.x + "px")
                    //             .style('top', i.y + "px")
                    //             .style('display', 'block')
                    //     }, 2000)
                    // })
                    // .on('mouseout',function (d, i) {
                    //     clearTimeout(that.timer)
                    // })
                    .on('mouseleave', function (d) {
                        d3.select(this).style("stroke-width", "0")
                    })
            },

            drawRectNodes(g, nodesData, that, opacity) {
                return g.append("g")
                    .selectAll("rect")
                    .data(nodesData)
                    .enter()
                    .filter(d => d.shape == 1)
                    .append("rect")
                    .attr("width", function (d) {  //方形为对应size原型的内切方形
                        if(typeof(d.r) != "undefined" && d.r != ''){
                            return d.r * 1.41
                        }
                        return 40 * 1.41
                    })
                    .attr("height", function (d) {
                        if(typeof(d.r) != "undefined" && d.r != ''){
                            return d.r * 1.41
                        }
                        return 40 * 1.41
                    })
                    .attr("class", "node")
                    .attr("fill-opacity", opacity)  //透明度
                    .attr("fill", function(d){
                        if(typeof(d.bgColor) != "undefined" && d.bgColor != ''){
                            return d.bgColor
                        }
                        return "#5290F2"  //一种蓝色
                    })
                    .on("contextmenu", function(d, i){
                        var cc = $(this).offset()
                        that.selectedNode = i
                        d3.select('#node-custom-menu')
                            .style('position', 'absolute')
                            .style('left', cc.left -250 + "px")
                            .style('top', cc.top -130 + "px")
                            .style('display', 'block');
                        event.preventDefault() // 禁止系统默认右键
                        event.stopPropagation() // 禁止空白处右键
                    })
                    .on('mouseenter',function (d, i) {
                        d3.select(this).style("stroke-width", "2").style("stroke","#999")
                        that.selectedNode = i
                    })
                    // 鼠标在节点上停留2s时，显示节点描述信息
                    // .on('mouseover',function (d, i){
                    //     this.timer = setTimeout(function (d) {
                    //         that.popoverContent = d.description
                    //         d3.select("#rich-container")
                    //             .style('position', 'absolute')
                    //             .style('left', d.x + "px")
                    //             .style('top', d.y + "px")
                    //             .style('display', 'block')
                    //     }, 2000)
                    // })
                    // .on('mouseout',function (d, i) {
                    //     clearTimeout(this.timer)
                    // })
                    .on('mouseleave',function (d) {
                        d3.select(this).style("stroke-width","0")
                    })
            },

            drawTriangleNodes(g, nodesData, that, opacity) {
                return g.append("g")
                    .selectAll("triangle-up")
                    .data(nodesData)
                    .enter()
                    .filter(d => d.shape == 2)
                    .append('path')
                    .attr("class", "node")
                    .attr("fill-opacity", opacity)  //透明度
                    .attr("fill", function(d){
                        if(typeof(d.bgColor) != "undefined" && d.bgColor != ''){
                            return d.bgColor
                        }
                        return "#5290F2"  //一种蓝色
                    })
                    .on("contextmenu", function(d, i){
                        var cc = $(this).offset()
                        that.selectedNode = i
                        d3.select('#node-custom-menu')
                            .style('position', 'absolute')
                            .style('left', cc.left -250 + "px")
                            .style('top', cc.top -130 + "px")
                            .style('display', 'block');
                        event.preventDefault() // 禁止系统默认右键
                        event.stopPropagation() // 禁止空白处右键
                    })
                    .on('mouseenter',function (d, i) {
                        d3.select(this).style("stroke-width", "2").style("stroke","#999")
                        that.selectedNode = i
                    })
                    // 鼠标在节点上停留2s时，显示节点描述信息
                    // .on('mouseover',function (d, i){
                    //     this.timer = setTimeout(function (d) {
                    //         that.popoverContent = d.description
                    //         d3.select("#rich-container")
                    //             .style('position', 'absolute')
                    //             .style('left', d.x + "px")
                    //             .style('top', d.y + "px")
                    //             .style('display', 'block')
                    //     }, 2000)
                    // })
                    // .on('mouseout',function (d, i) {
                    //     clearTimeout(this.timer)
                    // })
                    .on('mouseleave',function (d) {
                        d3.select(this).style("stroke-width","0")
                    })
            },

            drawNodeText(g, nodesData, that, textColor, fontSize, bold) {
                const nodeText =  g.append("g")
                                    .selectAll("text")
                                    .data(nodesData)
                                    .enter()
                                    .append('text')
                                    .text(function(d){
                                        // if(d.name.length > 4){
                                        //     var s = d.name.slice(0,4) + "..."
                                        //     return s
                                        // }
                                        return d.name
                                    })
                                    .attr("dx", function (d) {
                                        //现在后端没有fontSize，前端就先给出来，到迭代三再加入
                                        // d.fontSize = 20
                                        return (d.r/2 + fontSize)/2*(-1)
                                    })
                                    .attr("dy", function (d) {
                                        return d.r + fontSize - 5
                                    })
                                    .style("font-size", fontSize)
                                    .attr("class", "node-name")
                                    .attr("fill", textColor)
                                    .on("contextmenu", function (d, i) {
                                        var cc = $(this).offset()
                                        that.selectedNode = i
                                        d3.select('#node-custom-menu')
                                            .style('position','absolute')
                                            .style('left', cc.left -250 + "px")
                                            .style('top', cc.top -130+ "px")
                                            .style('display','block')
                                        event.preventDefault()
                                        event.stopPropagation()
                                    })
                if(bold) {
                    return nodeText.style('font-weight', 'bold')
                }
                else {
                    return nodeText
                }
            },

            //显示创建节点对话框
            showCreateNodeDialog(){
                if(this.selectedDomain.id==''){
                    this.$message({
                        message: '请先选择要添加节点的图谱哦',
                        type: 'warning'
                    })
                }else {
                    this.set_createNodeParams({
                        name: '',
                        bgColor: '',
                        shape: 0,
                        domainId: this.selectedDomain.id,
                        type:'',
                        description:'',
                        r: 40,
                        x: 200,
                        y: 100,
                        fontSize: 20,
                    })
                    this.set_createNodeDialogVisible(true);
                }
            },

            showCreateLinkDialog(){
                if(this.selectedDomain.id==''){
                    this.$message({
                        message:'请先选择要添加关系的图谱哦',
                        type:'warning'
                    })
                }else{
                    this.set_createLinkParams({
                        fromId:'',
                        toId:'',
                        name:'',
                        domainId: this.selectedDomain.id,
                    })
                    this.set_createLinkDialogVisible(true);
                }
            },

            addDomain(){
                if(!this.isLogin){
                    this.$message({
                        message:'请先登录哦',
                        type:'warning',
                    })
                }else{
                    this.set_addDomainDialogVisible(true);
                }
            },

            addLinkFromNode(){
                this.set_createLinkParams({
                    fromId: this.selectedNode.id,
                    toId: '',
                    name:'',
                    domainId: this.selectedDomain.id,
                })
                $('#node-custom-menu').hide()
                this.set_createLinkDialogVisible(true)
                this.init()
            },

            // 选择domain，展示它的图谱
            selectDomain(domain){
                this.set_selectedDomain(domain)
                getLinkByDomainIdAPI(this.selectedDomain.id)
                    .then(res => {
                        if(res.data.code == 200) {
                            this.set_relationships(res.data.data.relationships)
                            this.init()
                            this.set_options([])
                            this.typeValue=[]
                            let count=1;
                            for(let i=0;i<this.nodesData.length;i++){
                                let judge=true
                                for(let j=0;j<this.options.length;j++){
                                    if(this.nodesData[i].type==this.options[j].label)
                                        judge=false;
                                }
                                if(judge) {
                                    this.options.push(
                                        {
                                            value: this.nodesData[i].type,
                                            label: this.nodesData[i].type
                                        }
                                    )
                                    count+=1;
                                }
                            }
                        console.log(this.options)
                        }
                    })

            },

            // 其他方法更新图谱时使用
            selectDomainById(domainId){
                getLinkByDomainIdAPI(domainId)
                    .then(res => {
                        if(res.data.code == 200) {
                            this.set_relationships(res.data.data.relationships)
                            this.init()
                        }
                    })
            },

            deleteDomain(domainId){
                this.$confirm('此操作将删除图谱及其中所有节点和关系（不可恢复），是否继续？',{
                    confirmButtonText:'确认',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    deleteDomainAPI(domainId).then(res => {
                        if (res.data.code == 200) {
                            this.$message({
                                message: '删除成功',
                                type: 'success',
                            });
                            // 删除正在显示的domain，刷新
                            if (domainId == this.domain.id) {
                                this.relationships = [];
                                this.updateGraph();
                            }
                        } else {
                            this.$message({
                                message: '删除失败',
                                type: 'error'
                            })
                        }
                        this.getAllDomains();
                    });
                }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        })
                    }
                )
            },

            showNodeList (){
                this.set_nodeListVisible(true);
            },

            // 从前端返回的relationships数组中获取nodes
            getNodesFromRelationships (relationships) {
                /*
                nodes格式示例：{"id": 114,
                        "name": "JKL",
                        "bgColor": "1",
                        "shape": 1,
                        "domainId": 1,
                        "r": 1}
                 */
                var nodes = [];
                var node_ids =[]; //去重用
                relationships.forEach(function (relationship) {
                    if(relationship.type == 1){
                        // 正常关系
                        // 去重！！！
                        if(!node_ids.includes(relationship.startEntity.id)){
                            nodes.push(relationship.startEntity);
                            node_ids.push(relationship.startEntity.id);
                        }
                        if(!node_ids.includes(relationship.endEntity.id)){
                            nodes.push(relationship.endEntity);
                            node_ids.push(relationship.endEntity.id);
                        }
                    }else{
                        // 孤立节点
                        if(!node_ids.includes(relationship.startEntity.id)){
                            nodes.push(relationship.startEntity);
                            node_ids.push(relationship.startEntity.id);
                        }
                    }
                });
                return nodes;
            },

            // 从前端返回的relationships数组中获取links
            getLinksFromRelationships(relationships){
                /*
                links格式示例：{"id": null,
                    "name": null,
                    "source": 114,
                    "target": 114,
                    "domainId": 1,}
                 */
                var links = [];
                relationships.forEach(function (relationship) {
                    if(relationship.type == 1){
                        var link = {}
                        link.id = relationship.id;
                        link.name = relationship.name;
                        link.source = relationship.fromId;
                        link.target = relationship.toId;
                        link.domainId = relationship.domainId;
                        links.push(link);
                    }else{
                        // -1表示孤立点，不用加入links数组
                        // do nothing
                    }
                });
                return links;
            },

            editNode(){
                // 编辑节点表单初始值即为选中节点属性值
                this.set_editNodeParams(this.selectedNode)
                this.set_editNodeDialogVisible(true)
                $('#node-custom-menu').hide()
            },

            deleteNode(){
                this.$confirm('此操作将删除节点及与其关联的所有关系（不可恢复），是否继续？',{
                    confirmButtonText:'确认',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    deleteNodeAPI(this.selectedNode).then(res => {
                        if (res.data.code == 200 ) {
                            this.$message({
                                message: '删除成功',
                                type: 'success'
                            })
                            this.selectDomainById(this.selectedNode.domainId)
                        } else {
                            this.$message({
                                message: '删除失败',
                                type: 'error'
                            })
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type:'info',
                        message:'已取消删除'
                    })
                })
                $('#node-custom-menu').hide()
            },

            editLink(){
                this.set_editLinkParams(this.selectedLink);
                this.set_editLinkDialogVisible(true);
                $('#link-custom-menu').hide();
            },

            deleteLink(){
                this.$confirm('此操作将删除该关系（不可恢复），是否继续？',{
                    confirmButtonText:'确认',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    deleteLinkAPI(this.selectedLink).then(res => {
                        if (res.data.code == 200) {
                            this.$message({
                                message: '删除成功',
                                type: 'success'
                            })
                            this.selectDomainById(this.selectedLink.domainId);
                        } else {
                            this.$message({
                                message: '删除失败',
                                type: 'error'
                            })
                        }
                    });
                }).catch(()=>{
                    this.$message({
                        type:'info',
                        message:'已取消删除'
                    })
                })
                $('#link-custom-menu').hide();
            },

            drag(simulation){
                var that = this
                function dragstarted(event) {
                    if (!event.active) {
                        simulation.alphaTarget(0.3).restart()
                    }
                    event.subject.fx = event.subject.x
                    event.subject.fy = event.subject.y
                }
                function dragged(event) {
                    event.subject.fx = event.x
                    event.subject.fy = event.y
                }
                function dragended(event) {
                    //拖拽结束，将该节点加入touchedNodes，同时检测是否已经存在该节点
                    var flag = false  //判断是否节点已经存在于数组中
                    for(let i = 0; i < that.touchedNodes.length; i++) {
                        if(event.subject.id === that.touchedNodes[i].id) {
                            that.touchedNodes[i] = event.subject
                            flag = true
                        }
                    }
                    if(!flag) {
                        that.touchedNodes.push(event.subject)
                    }
                    if (!event.active) {
                        simulation.alphaTarget(0)
                    }
                    event.subject.fx = null
                    event.subject.fy = null
                }
                return d3.drag()
                    .on("start", dragstarted)
                    .on("drag", dragged)
                    .on("end", dragended)
            },

            addMarker(){
                const positiveMarker = this.svg.append("marker")
                    .attr("id", "positiveMarker")
                    .attr("orient", "auto")
                    .attr("stroke-width", 2)
                    .attr("markerUnits", "strokeWidth")
                    .attr("markerUnits", "userSpaceOnUse")
                    .attr("viewBox", "0 -5 10 10")
                    .attr("refX", 11)
                    .attr("refY", 0)
                    .attr("markerWidth", 12)
                    .attr("markerHeight", 12)
                    .append("path")
                    .attr("d", "M 0 -5 L 10 0 L 0 5")
                    .attr('fill', '#999')
                    .attr("stroke-opacity", 0.6)

                const negativeMarker = this.svg.append("marker")
                    .attr("id", "negativeMarker")
                    .attr("orient","auto")
                    .attr("stroke-width",2)
                    .attr("markerUnits", "strokeWidth")
                    .attr("markerUnits", "userSpaceOnUse")
                    .attr("viewBox", "0 -5 10 10")
                    .attr("refX", 0)
                    .attr("refY", 0)
                    .attr("markerWidth", 12)
                    .attr("markerHeight", 12)
                    .append("path")
                    .attr("d", "M 10 -5 L 0 0 L 10 5")
                    .attr('fill', '#999')
                    .attr("stroke-opacity", 0.6)

                var arrowMarker = this.svg.append("marker")
                    .attr("id", "arrow")
                    .attr("markerUnits", "strokeWidth")
                    .attr("markerWidth", "10")//
                    .attr("markerHeight", "10")
                    .attr("viewBox", "0 0 12 12")
                    .attr("refX", "25")// 13
                    .attr("refY", "6")
                    .attr("orient", "auto")
                var arrow_path = "M2,2 L10,6 L2,10 L6,6 L2,2"// 定义箭头形状
                arrowMarker.append("path").attr("d", arrow_path).attr("fill", "grey") // 应该是箭头颜色，后面再改
            },

            // 将用户对图谱做出的改动进行保存，主要是更新位置
            saveGraph(){
                updateXYAPI(this.nodesData)
                    .then(res => {
                        if(res.data.code == 200) {
                            Message({
                                message: '保存成功',
                                type: 'success'
                            })
                            getLinkByDomainIdAPI(this.selectedDomain.id)
                                .then(res => {
                                    if(res.data.code == 200) {
                                        this.set_relationships(res.data.data.relationships)
                                    }
                                })
                        }
                    })
            },

            //恢复用户对图谱的改动
            cancelZoom() {
                this.init()
            },

            //=================================导出图片部分=============================
            exportPic() {
                var svg = document.getElementById("kgGraph");
                var img = this.export2Base64Img(svg, null, {
                    background: '#fff'
                });
                img.then(function (base64src) {
                    var oA = document.createElement("a");
                    oA.download = Math.floor(Math.random() * 10000) + '.png';
                    oA.style.display = 'none'
                    oA.href = base64src;
                    document.body.appendChild(oA);
                    oA.click();
                    oA.remove();
                })
            },
            reEncode(data) {
                return decodeURIComponent(
                    encodeURIComponent(data).replace(/%([0-9A-F]{2})/g, (match, p1) => {
                        const c = String.fromCharCode(`0x${p1}`);
                        return c === '%' ? '%25' : c;
                    })
                )
            },
            export2Base64Img(svgDom, MIMEType, option) {
                var serializer = new XMLSerializer();
                var source = serializer.serializeToString(svgDom);
                var path = "data:image/svg+xml;base64," + window.btoa(this.reEncode(source));
                var canvas = document.createElement("canvas"),
                    context = canvas.getContext("2d"),
                    img = new Image(),
                    pixelRatio = window.devicePixelRatio || 1,
                    _exportPath, handler
                option = option || {};

                canvas.width = parseFloat(svgDom.getAttribute('width')); //  * pixelRatio
                canvas.height = parseFloat(svgDom.getAttribute('height')); //  * pixelRatio
                img.src = path;
                img.onload = function () {
                    // 增加底色
                    if (option.background) {
                        context.beginPath();
                        context.rect(0, 0, canvas.width, canvas.height);
                        context.fillStyle = option.background;
                        context.fill();
                        context.closePath();
                    }
                    //
                    context.drawImage(img, 0, 0);

                    var marker = option.watermark || "";

                    if (marker) {
                        context.font = "18px 微软雅黑";
                        context.fillStyle = "rgba(12, 0, 70, 0.5)";

                        var textWidth = context.measureText(marker).width,
                            textHegith = 50,
                            pk = 1.2,
                            rotate = (option.rotation || -45) * Math.PI / 180,
                            sinReg = Math.sin(rotate),
                            cosReg = Math.cos(rotate),
                            width = Math.abs(canvas.width * cosReg) + Math.abs(canvas.height * sinReg),
                            height = Math.abs(canvas.height * cosReg) + Math.abs(canvas.width * sinReg);

                        var xf = Math.ceil(width / textWidth * pk);
                        var yf = Math.ceil(height / textHegith);

                        context.rotate(rotate);

                        for (var i = 0; i < yf; i++) {
                            for (var k = 0; k < xf; k++) {
                                context.fillText(marker, textWidth * k * pk - canvas.height * cosReg, textHegith * i)
                            }
                        }
                    }
                    document.body.appendChild(canvas);
                    _exportPath = canvas.toDataURL(MIMEType || 'image/png', 1)
                    typeof handler === 'function' && handler(_exportPath)
                    document.body.removeChild(canvas)
                }

                return new Promise(function (resolve, reject) {
                    handler = resolve
                })
            },
            //=================================导出图片结束=============================

            //=================================导出xml部分=============================
            exportXml(){
                exportGraphXMLAPI(this.selectedDomain.id).then(res => {
                    if(res.data.code == 200){
                        this.download();
                    }else{
                    }
                })
            },
            download(){
                downloadAPI(this.selectedDomain.name,1).then(res => {
                    if(res.status == 200){
                        let blob = new Blob([res.data], {type: "application/x-download"}), // 此处为生成doc
                            link = document.createElement("a"),
                            href = window.URL.createObjectURL(blob);
                        link.href = href;
                        link.download = this.selectedDomain.name+".xml";
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link);
                        window.URL.revokeObjectURL(href); // 释放掉blob对象
                        this.$message({
                            message:'导出成功',
                            type:'success'
                        })
                    }else{
                        this.$message({
                            message:'导出失败',
                            type:'error'
                        })
                    }
                    }
                )
            },
            //=================================导出xml结束=============================

            buttonChange:function (val) {
                if(val)
                    this.set_mode(0)
                else
                    this.set_mode(1)

                this.init()

            },

            compareArr(arr1,arr2){
                return arr1.length-arr2.length;
            },
            composeModeSet(nodesData){
                let newData = JSON.parse(JSON.stringify(nodesData))

                let returnData=[[]];
                returnData[0].push(newData[0]);
                for(let i=1;i<newData.length;i++){
                    let judge=true; //是否需要新增类
                    let tempj=0;
                    for(let j=0;j<returnData.length;j++){
                        if(newData[i].type==returnData[j][0].type){
                            judge=false;
                            tempj=j;
                        }
                    }
                    if(judge){
                        returnData.push([]);
                        returnData[returnData.length-1].push(newData[i]);
                    }else{
                        returnData[tempj].push(newData[i]);
                    }
                }
                returnData.sort(this.compareArr);
                returnData.reverse();

                let collision=100+200;
                let startx =this.width/4;
                let starty =this.height/4;

                for(let i=0;i<returnData.length;i++){
                    for(let j=0;j<returnData[i].length;j++){
                        for(let z=0;z<newData.length;z++){
                            if(newData[z].id==returnData[i][j].id){
                                newData[z].x=startx;
                                newData[z].y=starty;
                            }
                        }
                        starty+=collision;
                    }
                    starty=this.height/4;
                    startx+=collision;
                }
                for(let i=0;i<newData.length;i++){
                    nodesData[i].x=newData[i].x;
                    nodesData[i].y=newData[i].y;
                }
            },

            //让所有关系消失
            vanishAllRelationships() {
                if(this.mode == 0) {
                    this.initGraph(this.nodesData, [], [], 0.3, -100, 'black')
                }
                else {
                    this.initGraph(this.nodesData, [], [], 0, 0, 'black')
                }
            },

            //选择特定类型节点进行显示
            filterNodeType(types) {
                var selectedNodes = []
                for(let i = 0; i < this.nodesData.length; i++) {
                    for(let j = 0; j < types.length; j++) {
                        if(this.nodesData[i].type === types[j]) {
                            selectedNodes.push(this.nodesData[i])
                        }
                    }
                }
                if(this.mode == 0) {
                    this.initGraph(this.nodesData, this.linksData, selectedNodes, 0.3, -100, 'black', 0.2, 1)
                }
                else {
                    this.initGraph(this.nodesData, this.linksData, selectedNodes, 0, 0, 'black', 0.2, 1)
                }
            },


        }
    }
</script>

<style scoped>
    .container{
        display: flex;
    }
    .domain-list-head{
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid lightgray;
    }
    .edit-pane{
        position: absolute;
        left: 300px;
        top: 0;
        width: 100%;
        height: 100%;
    }
    .edit-tools{
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid lightgray;
        width: 100%;
        display: flex;
    }
    .multi-mode{
        width: 300px;
        position: absolute;
        right: 820px;
    }
    .fixed-tools{
        width: 300px;
        position: absolute;
        right: 300px;
        /*很离谱啊这里，为什么浏览器在看不见的地方还多了300px*/
    }
    .edit-tool{
        margin-left: 20px;
    }
    .graph{
        position: absolute;
        top: 50px;
        bottom: 0;
        width: 100%;
    }
    .left-side-bar{
        display: table-row;
        position: absolute;
        left: 0px;
        /*width: 20px;*/
        height: 100%;
        vertical-align:center
    }
    .right-side-bar{
        display: table-row;
        position: absolute;
        right: 300px;
        /*width: 20px;*/
        height: 100%;
        vertical-align:center
    }
    .center-side-bar{
        margin-left: 15%;
        display: table-row;
        position: absolute;
        width: 40%;
        height: 7%;
        /*border: 1px solid black;*/
        vertical-align:center
    }

    .domain-table >>> .el-table__row > td{
        /* 去除表格线 */
        border: none;
    }
    .domain-table >>> .el-table::before{
        /* 去除下边框 */
        height: 0;
    }

</style>
