<template>
    <div>
    <div class="graph-container">
        <svg width="960" height="600"></svg>
    </div>

        <!-- 节点操作 -->
        <ul class="el-dropdown-menu el-popper" id="node-custom-menu" style="display: none">
            <li class="el-dropdown-menu__item" @click="editNode">
                <span>编辑</span>
            </li>
            <li  class="el-dropdown-menu__item" @click="addLink">
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
        <!-- 编辑节点对话框 -->
        <el-dialog
                :visible.sync="editNodeFormVisible"
                title="编辑节点"
        >
            <el-form :model="this.editNodeParams">
                <el-form-item label="节点名称">
                    <el-input v-model="this.editNodeParams.name" style="width: 330px"></el-input>
                </el-form-item>
                <el-form-item label="选择颜色">
                    <el-color-picker v-model="this.editNodeParams.bgColor">
                    </el-color-picker>
                </el-form-item>
                <el-form-item label="选择形状">
                    <el-select disabled v-model="this.editNodeParams.shape" placeholder="请选择">
                        <el-option
                                v-for="item in shapes"
                                :key="item.key"
                                :label="item.label"
                                :value="item.key">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所属图谱">
                    <!-- 默认只能在当前选中的图谱中添加节点 -->
                    <el-select disabled v-model="this.editNodeParams.domainId" placeholder="请从现有图谱中选择">
                        <el-option
                                v-for="item in domainList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <!-- mark：dialog的footer一定要如下加span标签！不是div！ 按钮才可以靠右 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancelEditNode">取消</el-button>
                <el-button @click="submitEditNode" type="primary">确认</el-button>
        </span>
        </el-dialog>
    </div>
</template>

<script>
    import * as d3 from 'd3';
    import $ from 'jquery';
    import {mapGetters} from "vuex";
    import {deleteNodeAPI, updateNodeAPI} from "../../../api/entity";


    export default {
        name: "showGraph",
        props: [
            "relationships"
        ],
        data(){
            return{
                selectedNode:{
                    id:'',
                    name:'',
                    bgColor:'',
                    shape:'',
                    domainId:'',
                },
                editNodeFormVisible:false,
                addLinkFormVisible:false,
                editNodeParams:{
                    id:'',
                    name:'',
                    bgColor:'',
                    shape:'',
                    domainId:'',
                },

            }
        },
        mounted() {
            //     updateGraph() {
            //         var _this = this;
            //         var nodes = this.nodeList;
            //         var links = this.linkList;
            //         var showedLinks = [];
            //         // tick表示运动中每更新一帧时
            //         links.forEach(function (m) {
            //             var sourceNode = nodes.filter(function (n) {
            //                 return n.id === m.fromId;
            //             })[0];
            //             if(typeof(sourceNode) == 'undefined') return;
            //             var targetNode = nodes.filter(function (n) {
            //                 return n.id === m.toId;
            //             })[0];
            //             if(typeof(targetNode) == 'undefined') return;
            //             showedLinks.push({source:sourceNode.id,target:targetNode.id,link:m});
            //         });
            //         var link =
            //         function ticked() {
            //
            //         }
            //
            //     }

            var _this = this;
            let svg = d3.select('svg')
            let width = +svg.attr('width')
            let height = +svg.attr('height')
            const g = svg.append('g') // svg中创建g元素，node和link放在g中

            svg.call(d3.zoom().on('zoom',function () {
                g.attr('transform',d3.event.transform)
            }))

            var relationships = [
                {
                    "id": 1,
                    "name": 'XXX',
                    "type": 1,
                    "fromId": 93,
                    "toId": 113,
                    "domainId": 1,
                    "startEntity": {
                        "id": 93,
                        "name": "AAA",
                        "bgColor": "blue",
                        "shape": 1,
                        "domainId": 1
                    },
                    "endEntity": {
                        "id": 113,
                        "name": "BBB",
                        "bgColor": "red",
                        "shape": 1,
                        "domainId": 1
                    }
                },
                {
                    "id": null,
                    "name": null,
                    "type": -1,
                    "fromId": 114,
                    "toId": 114,
                    "domainId": 1,
                    "startEntity": {
                        "id": 114,
                        "name": "JKL",
                        "bgColor": "#9a9abe", // 返回的颜色必须是16进制的，colorpicker才能解析
                        "shape": 1,
                        "domainId": 1
                    },
                    "endEntity": {
                        "id": 114,
                        "name": "JKL",
                        "bgColor": "yellow",
                        "shape": 1,
                        "domainId": 1
                    }
                }
            ]

            // 提取出d3可以解析的节点和关系数组
            var nodesData = this.getNodesFromRelationships(relationships);
            var linksData = this.getLinksFromRelationships(relationships);

            console.log(nodesData);
            console.log(linksData);

            var simulation = d3.forceSimulation()
                .nodes(nodesData)
                .force('charge', d3.forceManyBody().strength(-100))
                .force('collide', d3.forceCollide().strength(-30))
                .force('center', d3.forceCenter(width / 2, height / 2))

            var node = g.append('g')
                .attr('class', 'nodes')
                .selectAll('circle')
                .data(nodesData)
                .enter()
                .append('circle')
                .attr('r', 20)
                .attr('fill', (d) => {return d.bgColor})

            // node.on("mouseover",function (d,i) {
            //
            // });
            // 右键节点，弹出菜单
            node.on("contextmenu",function (d) {
                // var position = d3.mouse(this)
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

            var link = g.append('g')
                .attr('class', 'links')
                .selectAll('line')
                .data(linksData)
                .enter()
                .append('line')
                .attr('stroke-width', 2)
                .attr('marker-end',"url(#arrow)")
                .style('stroke', this.linkColor)

            link.on("contextmenu",function(d){
                var cc = $(this).offset();
                d3.select('#link-custom-menu')
                    .style('position','absolute')
                    .style('left',cc.left-250+"px")
                    .style('top',cc.top-130+"px")
                    .style('display','block')
                d3.event.preventDefault(); // 禁止系统默认右键
                d3.event.stopPropagation(); // 禁止空白处右键
            })

            var nodeText = g.append('g')
                .attr("class", "nodeText")
                .selectAll("text")
                .data(nodesData)
                .enter()
                .append("text")
                .style("fill","black")
                .attr("dx",5)
                .attr("dy",20) // 在节点右下角显示文字
                .text((d) => { return d.name });

            var linkText = g.append('g')
                .attr("class","linkText")
                .selectAll("text")
                .data(linksData)
                .enter()
                .append("text")
                .text((d) => {return d.name})

            simulation.on('tick', tickAction)

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
                nodeText
                    .attr('x', (d) => { return d.x })
                    .attr('y', (d) => { return d.y })
                linkText
                    .attr('x', (d) => { return (d.source.x + d.target.x) / 2})
                    .attr('y', (d) => { return (d.source.y + d.target.y) / 2})
            }

            // 监听拖拽开始
            function dragStarted(d) {
                if(!d3.event.active)
                    simulation.alphaTarget(0.3).restart()
                d.fx = d.x;
                d.fy = d.y;
            }

            //监听拖拽中
            function dragged(d) {
                d.fx = d3.event.x;  //fevent.x为拖拽移动时的坐标
                d.fy = d3.event.y;
            }

            //监听拖拽结束
            function dragended(d) {
                if (!d3.event.active) simulation.alphaTarget(0);
                d.fx = null;        //固定坐标清空
                d.fy = null;
            }

            node.call(d3.drag().on("start",dragStarted)
                .on("drag",dragged)
                .on("end",dragended)
            )

            var linkForce = d3.forceLink(linksData)
                .id((d) => { return d.id })
                .distance(150)

            simulation.force('links', linkForce)

            // 为关系添加箭头
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
        methods: {
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
                relationships.forEach(function (relationship) {
                    if(relationship.type == 1){
                        // 正常关系
                        nodes.push(relationship.startEntity);
                        nodes.push(relationship.endEntity);
                    }else{
                        // 孤立节点
                        nodes.push(relationship.startEntity);
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
            circleColor (d) {
                return d.bgColor;
            },
            linkColor (d) {
                return 'grey' // 目前连线颜色都设为灰色
            },
            editNode(){
                console.log(this.selectedNode);
                this.editNodeParams = this.selectedNode; // 编辑节点表单初始值即为选中节点属性值
                console.log(this.editNodeParams);
                this.editNodeFormVisible = true;
                $('#node-custom-menu').hide();
            },
            addLink(){

            },
            deleteNode(){
                deleteNodeAPI(this.selectedNode).then(res => {
                    if(res){
                        this.$message({
                            message:'删除成功',
                            type:'success'
                        })
                    }else{
                        this.$message({
                            message:'删除失败',
                            type:'error'
                        })
                    }
                })
            },
            cancelEditNode(){
                this.editNodeFormVisible = false;
            },
            submitEditNode(){
                updateNodeAPI(this.editNodeParams).then(res => {
                    if(res){
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        })
                    }else{
                        this.$message({
                            message: '修改失败',
                            type:'error'
                        })
                    }
                });
            },
            editLink(){

            },
            deleteLink(){

            }
        }

    }
</script>

<style scoped>

</style>
