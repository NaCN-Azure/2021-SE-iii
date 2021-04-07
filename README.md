backend-parent：后端系统的根目录（父工程），暂时管理两个子模块

+ common：公共模块
    + common-util：工具类模块，所有模块都可以依赖于它
    + service-base：service 服务的 base 包，包含 service 服务的公共配置类，所有 service 模块依赖于它
    + spring-security：认证与授权模块，需要认证授权的 service 服务依赖于它
+ service：api 接口服务，暂时只有 sevice_coin 一个子模块，后续添加很方便

还有一个 schema 文件夹，用于存放数据库文件

近期更新日志
2021-04-06 纳思彧
- 1.修改了mysql数据库的内容，现存在domain和nodeType表
- 2.修改updateEntity方法，现在该方法不再修改颜色，会修改description
- 3.引入NodeType的说明，该type存储于mysql数据库和节点中，和颜色具有唯一映射，颜色仅在其第一次创建才可自选
- 4.新增优化：新增节点方法可自动从找出type的同类型颜色，增设“新增类型”方法，当数据库存在同类数据会报错
- 5.引入type的相关服务类型
- 6.节点neo4j加入x和y位置，原先的neo4j数据需要被清除
- 7.更新颜色被写成了新的方法，会一并更新同类型的节点颜色
- 8.删除节点逻辑优化，当一个类型最后的一个节点被删除时，该类型也被删除
- 9.暂时请不要用csv导入！！！
- 10.数据库被更新了，记得配置
- 11.对了可能还得改，因为Type少了个domainId属性，这就意味着所有的domain共享一个类型表

2021-04-07
- 1.更新xy位置方法改为全体更新
- 2.新增筛选节点类型方法
- 3.待完成搜索方法（模糊匹配）
