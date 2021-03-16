<template>
    <div class="graph-container">
        <svg width="960" height="600"></svg>
        <!-- 节点操作 -->
        <div class="nodeOperationMenu">

        </div>
    </div>
</template>

<script>
    import * as d3 from 'd3';
    import {mapGetters} from "vuex";

    var svg;
    var simulation;
    var linkGroup;
    var nodeGroup;
    var linkTextGroup;
    var nodeTextGroup;

    // 为关系添加箭头
    var addMarker = function () {
        var arrowMarker = svg.append("marker")
            .attr("id","arrow")
            .attr("markerUnits","strokeWidth")
            .attr("markerWidth","18")//
            .attr("markerHeight","18")
            .attr("viewBox","0 0 12 12")
            .attr("refX","30")// 13
            .attr("refY","6")
            .attr("orient","auto");
        var arrow_path = "M2,2 L10,6 L2,10 L6,6 L2,2";// 定义箭头形状
        arrowMarker.append("path").attr("d",arrow_path).attr("fill","#fce6d4"); // 应该是箭头颜色，后面再改
    }

    // 刷新页面函数
    var ticked = function () {

    }

    //
    export default {
        name: "showGraph",
        // props: {
        //     width: String,
        //     height: String,
        // },
        data(){
            return{
                nodes:[
                    {id:'1',name:'a'},
                    {id:'2',name:'b'},
                    {id:'3',name:'c'},
                ],
                links:[
                    {fromId:'1',toId:'2',name:'ab'},
                    {fromId: '2',toId: '3',name: 'bc'},
                ]
            }
        },
        mounted() {
            // this.init();
            // var s = d3.select('.graph-container');
            // var width = s._groups[0][0].offsetWidth; //?
            // var height = window.screen.height - 100; // 100先随便设的
            // svg = s.append('svg');
            // svg.attr('width', width);
            // svg.attr('height', height);
            //     simulation = d3.forceSimulation()
            //         .force("link",d3.forceLink(this.links).distance(220).id(function (d) {return d.id}))
            //         .force("charge",d3.forceManyBody().strength(-400))
            //         .force("collide",d3.forceCollide().strength(-30))
            //         .force("center",d3.forceCenter(width/2,(height-200)/2))
            //         .nodes(this.nodes)
            //         .on("tick",ticked)
            //         .start();
            //     linkGroup = svg.append("g").attr("class","line");
            //     linkTextGroup = svg.append("g").attr("class","line-text");
            //     nodeGroup = svg.append("g").attr("class","node");
            //     nodeTextGroup = svg.append("g").attr("class","node-text");
            //     this.addMarker();
            //     var node = nodeGroup.selectAll('.node').data(this.nodes).enter().append('g');
            //     node.append('circle')
            //         .attr("class",function(d,i){
            //             if(d.id ===1){
            //                 return 'node centerNode';
            //             }else{
            //                 return 'node'
            //             }
            //         })
            //         .attr('r',function (d,i) {
            //             if(d.id === 1){
            //                 return 50;
            //             }else{
            //                 return 25;
            //             }
            //         })
            //         .style('fill',function (d) {
            //             return 'red';
            //         })
            // },
            // created(){
            //     // this.getGraph();  // 应该是一个获取所有domain的方法，还没看明白
            // },
            // computed: {
            //     ...mapGetters([
            //         'nodeList', // d3渲染用的nodes数组
            //         'linkList' // d3渲染用的links数组
            //     ])
            // },
            // methods: {
            //     // 从前端返回的links数组中获取nodes
            //     getNodesFromLinks (links) {
            //         var nodes = {};
            //         links.forEach(function (link) {
            //             if(link.type == 1){
            //                 // 正常关系
            //                 nodes.push(link.startEntity);
            //                 nodes.push(link.endEntity);
            //             }else{
            //                 // 孤立节点
            //                 nodes.push(link.startEntity);
            //             }
            //         });
            //         return nodes;
            //     },
            //
            //
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
                        "bgColor": "yellow",
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

            var nodesData = this.getNodesFromRelationships(relationships);
            var linksData = this.getLinksFromRelationships(relationships);

            console.log(nodesData);
            console.log(linksData);

            var simulation = d3.forceSimulation()
                .nodes(nodesData)
                .force('charge_force', d3.forceManyBody())
                .force('center_force', d3.forceCenter(width / 2, height / 2))

            var node = g.append('g')
                .attr('class', 'nodes')
                .selectAll('circle')
                .data(nodesData)
                .enter()
                .append('circle')
                .attr('r', 20)
                .attr('fill', (d) => {return d.bgColor})

            var link = g.append('g')
                .attr('class', 'links')
                .selectAll('line')
                .data(linksData)
                .enter()
                .append('line')
                .attr('stroke-width', 2)
                .style('stroke', this.linkColor)

            var nodeText = g.append('g')
                .selectAll("text")
                .data(nodesData)
                .enter()
                .append("text")
                .style("fill","black")
                .attr("dx",5)
                .attr("dy",20) // 在节点右下角显示文字
                .text((d) => { return d.name });

            var linkText = g.append('g')
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

            simulation.force('links', linkForce)

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
                // if (d.type === 'A') {
                //     return 'green'
                // } else {
                //     return 'red'
                // }
                return 'black' // 目前连线颜色都设为黑色
            }
        }

    }
</script>

<style scoped>

</style>