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
                <!-- 不知道是要搜索什么 -->
<!--                <div class="edit-tool" v-show="domain.name!=''">-->
<!--                    <el-input-->
<!--                            placeholder="请输入关键词"-->
<!--                            @keyup.native.enter="searchInCurrentDomain"-->
<!--                            clearable="true"-->
<!--                            size="small"-->
<!--                    ></el-input>-->
<!--                </div>-->
                <div class="edit-tool" v-show="selectedDomain.name!=''">
                    <span class="">选中节点：</span>
                    <span>{{selectedNode.name}}</span>
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
                    </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
            <!-- 右边中间-图谱渲染区域 -->
            <div class="graph">
                <el-scrollbar style="width: 100%;height: 100%">
<!--                    <show-graph v-bind:relationships="relationships"></show-graph>-->
                    <svg id="kgGraph" width="1200" height="600"></svg>
                </el-scrollbar>
            </div>
            <div class="right-side-bar">
                <el-button
                    @click="showNodeList"
                    size="mini"
                    style="display: block"
                >节点与关系</el-button>
            <node-list-drawer></node-list-drawer>
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
        </div>

        <create-node-dialog></create-node-dialog>
        <edit-node-dialog></edit-node-dialog>
        <add-domain-dialog></add-domain-dialog>
        <create-link-dialog></create-link-dialog>
        <edit-link-dialog></edit-link-dialog>
    </div>
</template>

<script>
    import * as d3 from 'd3';
    import $ from 'jquery';
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {deleteNodeAPI} from "../../api/entity";
    import {deleteDomainAPI} from "../../api/domain";
    import {deleteLinkAPI} from "../../api/relationship";
    import {downloadAPI, exportGraphXMLAPI} from "../../api/file";
    import CreateNodeDialog from "./components/createNodeDialog";
    import EditNodeDialog from "./components/editNodeDialog";
    import AddDomainDialog from "./components/addDomainDialog";
    import CreateLinkDialog from "./components/createLinkDialog";
    import EditLinkDialog from "./components/editLinkDialog";
    import NodeListDrawer from "./components/nodeListDrawer";

    var simulation;
    var nodes;
    var links;
    var nodeText;
    var linkText;
    var svg;

    export default {
        name: "editor",
        components: {NodeListDrawer, EditLinkDialog, CreateLinkDialog, AddDomainDialog, EditNodeDialog, CreateNodeDialog},
        inject:['reload'],
        data(){
            return{
                // 选中要进行操作的节点
                selectedNode:{
                    id:'',
                    name:'',
                    bgColor:'',
                    shape:'',
                    domainId:'',
                },
                selectedLink:{
                    id:'',
                    name:'',
                    type:1, // 能被选中的关系一定type是1
                    fromId:'',
                    toId:'',
                    domainId:'',
                },
            }
        },

        created(){
            this.getAllDomains();
        },

        mounted(){
            svg = d3.select('svg')
            var width = +svg.attr('width')
            var height = +svg.attr('height')
            const g = svg.append('g') // svg中创建g元素，node和link放在g中

            simulation = d3.forceSimulation()
                .force('charge', d3.forceManyBody().strength(-100))
                .force('collide', d3.forceCollide().strength(-30))
                .force('center', d3.forceCenter(width / 2, height / 2))
                .force('link', d3.forceLink().distance(300).id(function (d) {
                    return d.id;
                }));

            links = g.append('g').attr("class","link");
            linkText = g.append('g').attr("class","linkText");
            nodes = g.append('g').attr("class","node");
            nodeText = g.append("g").attr("class","nodeText");

            this.addMarker();
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
            ]),
            ...mapActions([
                'getAllDomains',
                'getDomainById',
            ]),
            showCreateNodeDialog(){
                if(this.selectedDomain.id==''){
                    this.$message({
                        message:'请先选择要添加节点的图谱哦',
                        type:'warning'
                    })
                }else {
                    this.set_createNodeParams({
                        name: '',
                        bgColor: '',
                        shape: 0,
                        domainId: this.selectedDomain.id,
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
                this.set_addDomainDialogVisible(true);
            },
            addLinkFromNode(){
                this.set_createLinkParams({
                    fromId: this.selectedNode.id,
                    toId: '',
                    name:'',
                    domainId: this.selectedDomain.id,
                })
                $('#node-custom-menu').hide();
                this.set_createLinkDialogVisible(true);
            },
            // 选择domain，展示它的图谱
            selectDomain(domain){
                this.set_selectedDomain(domain);
                this.getDomainById(this.selectedDomain.id);
                this.updateGraph()
            },
            // 其他方法更新图谱时使用
            selectDomainById(domainId){
                this.getDomainById(domainId);
                this.updateGraph();
            },
            deleteDomain(domainId){
                this.$confirm('此操作将删除图谱及其中所有节点和关系（不可恢复），是否继续？',{
                    confirmButtonText:'确认',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    deleteDomainAPI(domainId).then(res => {
                        console.log(res);
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
            /* ==========================showGraph================================== */
            // 从前端返回的relationships数组中获取nodes
            getNodesFromRelationships (relationships) {
                /*
                nodes格式示例：{"id": 114,
                        "name": "JKL",
                        "bgColor": "1",
                        "shape": 1,
                        "domainId": 1}
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
                this.set_editNodeParams(this.selectedNode);
                this.set_editNodeDialogVisible(true);
                $('#node-custom-menu').hide();
            },
            deleteNode(){
                this.$confirm('此操作将删除节点及与其关联的所有关系（不可恢复），是否继续？',{
                    confirmButtonText:'确认',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    deleteNodeAPI(this.selectedNode).then(res => {
                        if (res.data.code == 200) {
                            this.$message({
                                message: '删除成功',
                                type: 'success'
                            })
                            this.selectDomainById(this.selectedNode.domainId);
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
                $('#node-custom-menu').hide();
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
            // 监听拖拽开始
            dragStarted(d) {
                if(!d3.event.active)
                    simulation.alphaTarget(0.3).restart()
                d.fx = d.x;
                d.fy = d.y;
            },

            //监听拖拽中
            dragged(d) {
                d.fx = d3.event.x;  //fevent.x为拖拽移动时的坐标
                d.fy = d3.event.y;
            },

            //监听拖拽结束
            dragended(d) {
                if (!d3.event.active) simulation.alphaTarget(0);
                d.fx = null;        //固定坐标清空
                d.fy = null;
            },

            addMarker(){
                var arrowMarker = svg.append("marker")
                    .attr("id","arrow")
                    .attr("markerUnits","strokeWidth")
                    .attr("markerWidth","10")//
                    .attr("markerHeight","10")
                    .attr("viewBox","0 0 12 12")
                    .attr("refX","25")// 13
                    .attr("refY","6")
                    .attr("orient","auto");
                var arrow_path = "M2,2 L10,6 L2,10 L6,6 L2,2";// 定义箭头形状
                arrowMarker.append("path").attr("d",arrow_path).attr("fill","grey"); // 应该是箭头颜色，后面再改
            },

            drawNode(nodes){
                var _this = this;
                var nodeEnter = nodes.enter().append('circle');
                nodeEnter.on("contextmenu", function(d){
                    console.log(d);
                    var cc = $(this).offset();
                    _this.selectedNode.id = d.id;
                    _this.selectedNode.name = d.name;
                    _this.selectedNode.bgColor = d.bgColor;
                    _this.selectedNode.shape = d.shape;
                    _this.selectedNode.domainId = d.domainId;
                    d3.select('#node-custom-menu')
                        .style('position','absolute')
                        .style('left', cc.left -250 + "px")
                        .style('top', cc.top -130+ "px")
                        .style('display','block');
                    d3.event.preventDefault(); // 禁止系统默认右键
                    d3.event.stopPropagation(); // 禁止空白处右键
                });
                nodeEnter.on('mouseenter',function (d) {
                    d3.select(this).style("stroke-width","10")
                        .style("stroke","#C6C1C5")
                    _this.selectedNode = d;
                })
                nodeEnter.on('mouseleave',function (d) {
                    d3.select(this).style("stroke-width","0")
                })
                nodeEnter.call(d3.drag()
                    .on("start",this.dragStarted)
                    .on("drag",this.dragged)
                    .on("end",this.dragended)
                );
                return nodeEnter;
            },
            drawLink(links){
                var _this = this;
                var linkEnter = links.enter().append("line")
                    .attr("stroke-width",3)
                    .attr("stroke","#A8BFC2")
                    .attr("marker-end","url(#arrow)");
                linkEnter.on("contextmenu",function(d){
                    var cc = $(this).offset();
                    _this.selectedLink.id = d.id;
                    _this.selectedLink.name = d.name;
                    _this.selectedLink.fromId = d.source.id;
                    _this.selectedLink.toId = d.target.id;
                    _this.selectedLink.domainId = d.domainId;
                    console.log("selectedLink");
                    console.log(_this.selectedLink);
                    d3.select('#link-custom-menu')
                        .style('position','absolute')
                        .style('left',cc.left-300+"px")
                        .style('top',cc.top-80+"px")
                        .style('display','block')
                    d3.event.preventDefault(); // 禁止系统默认右键
                    d3.event.stopPropagation(); // 禁止空白处右键
                })
                linkEnter.on('mouseenter',function (d) {
                    d3.select(this).style("stroke-width","10")
                        .style("stroke","#C6C1C5")
                })
                linkEnter.on('mouseleave',function (d) {
                    d3.select(this).style("stroke-width","3")
                })
                return linkEnter;
            },
            drawNodeText(nodetext){
                var _this = this;
                var nodeTextEnter = nodetext.enter().append("text")
                    .style("fill",function (d) {
                        if(d.bgColor == "#ffffff" || d.bgColor == "#FFFFFF"){
                            return "#000000"
                        }
                        else{
                            return "#ffffff"
                        }
                    })
                    .attr("class","nodeText")
                    .attr("dy",4)
                    .attr("text-anchor","middle")
                    .text(function(d){
                        var length=d.name.length;
                        if(d.name.length>4){
                            var s= d.name.slice(0,4)+"...";
                            return s;
                        }
                        return d.name;
                    });
                nodeTextEnter.on("contextmenu",function (d) {
                        console.log(d);
                        var cc = $(this).offset();
                        _this.selectedNode.id = d.id;
                        _this.selectedNode.name = d.name;
                        _this.selectedNode.bgColor = d.bgColor;
                        _this.selectedNode.shape = d.shape;
                        _this.selectedNode.domainId = d.domainId;
                        d3.select('#node-custom-menu')
                            .style('position','absolute')
                            .style('left', cc.left -250 + "px")
                            .style('top', cc.top -130+ "px")
                            .style('display','block');
                        d3.event.preventDefault(); // 禁止系统默认右键
                        d3.event.stopPropagation(); // 禁止空白处右键
                });
                return nodeTextEnter;
            },
            drawLinkText(linktext){
                var linkTextEnter = linktext.enter().append('text')
                    .attr("class","linkText")
                    .style('fill','#827980')
                    .style('font-size','20px')
                    .text(function(d){
                        return d.name;
                    })
                return linkTextEnter
            },
            updateGraph(){
                this.set_nodesData(this.getNodesFromRelationships(this.relationships));
                this.set_linksData(this.getLinksFromRelationships(this.relationships));
                var _this = this;
                var link = links.selectAll('line').data(this.linksData,function(d){ return d.id});
                link.exit().remove();
                var linkEnter = this.drawLink(link);
                link = linkEnter.merge(link);

                var linktext = linkText.selectAll("text").data(this.linksData,function(d){return d.id})
                linktext.exit().remove();
                var linkTextEnter = this.drawLinkText(linktext);
                linktext = linkTextEnter.merge(linktext).text(function(d){ return d.name});

                var node = nodes.selectAll("circle").data(this.nodesData);
                node.exit().remove();
                var nodeEnter = this.drawNode(node);
                node = nodeEnter.merge(node).text(function(d){ return d.name;});
                node.attr("fill",function(d){
                    if(typeof(d.bgColor)!="undefined" && d.bgColor != ''){
                        return d.bgColor;
                    }
                    return "#5290F2"
                });
                node.attr('r', 40);
                node.style("opacity",0.8);
                node.append("title")
                    .text(function (d) {
                        return d.name;
                    });

                var nodetext = nodeText.selectAll("text").data(this.nodesData);
                nodetext.exit().remove();
                var nodeTextEnter = this.drawNodeText(nodetext);
                nodetext = nodeTextEnter.merge(nodetext).text(function(d){return d.name});
                nodetext.append("title")
                    .text(function (d) {
                        return d.name;
                    });

                simulation.nodes(this.nodesData).alphaTarget(0).alphaDecay(0.05).on("tick",tickAction);
                simulation.force("link").links(this.linksData);
                simulation.alpha(1).restart();
                // 每tick更新图谱
                function tickAction () {
                    node
                        .attr('cx', (d) => { return d.x })
                        .attr('cy', (d) => { return d.y })

                    link
                        .attr('x1', (d) => { return d.source.x })
                        .attr('y1', (d) => { return d.source.y })
                        .attr('x2', (d) => { return d.target.x })
                        .attr('y2', (d) => { return d.target.y })
                    nodetext
                        .attr('x', (d) => { return d.x })
                        .attr('y', (d) => { return d.y })
                    linktext
                        .attr('x', (d) => { return (d.source.x + d.target.x) / 2})
                        .attr('y', (d) => { return (d.source.y + d.target.y) / 2})
                }

                svg.call(d3.zoom().on("zoom", function() {
                    svg.selectAll("g").attr("transform", d3.event.transform);
                }));

                // 点击空白处，关闭点开的菜单
                svg.on("click",function () {
                    $('#link-custom-menu').hide();
                    $('#node-custom-menu').hide();
                })

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
            //==========导出图片部分结束============

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
                    console.log(res);
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
            }

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
    .right-side-bar{
        position: absolute;
        right: 300px;
        /*width: 20px;*/
        height: 100%;
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
