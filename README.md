### 2021-04-12

1. 重构 d3 渲染节点逻辑，初步实现节点和关系的建立和显示
2. 关于坐标还是有 NaN 的报错，接下来主要解决这个问题
3. font_size 目前在前端是写死的，也就是说暂不支持修改字体大小，这个留到迭代三完成
4. 所有类型的节点右键显示操作菜单功能完成，关系待完成
5. 鼠标移到节点上显示灰色边框功能完成
6. 半径目前是留给用户输入，不友好，后期优化

罗列一下今天未解决的 bug：

1. 目前创建新的节点和关系在不刷新的情况下会出现重复多个
2. 删除失败，没有给 id



### 2021-04-13

已完成：

1. 节点和关系重复显示的 bug 修复
2. 删除修复
3. 关系右键显示菜单
4. 节点拖拽
5. 缩放



待完成：

1. 关系操作菜单功能显示 bug
2. 禁止系统默认右键





### 2021-04-14

已完成：

1. 节点禁止系统默认右键
2. 节点和关系右键菜单及各项操作功能



待完成：

1. 节点关系操作自动刷新