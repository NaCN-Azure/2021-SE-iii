backend-parent：后端系统的根目录（父工程），暂时管理两个子模块

+ common：公共模块
    + common-util：工具类模块，所有模块都可以依赖于它
    + service-base：service 服务的 base 包，包含 service 服务的公共配置类，所有 service 模块依赖于它
    + spring-security：认证与授权模块，需要认证授权的 service 服务依赖于它
+ service：api 接口服务
    + sevice_coin：知识图谱相关
    + sevice_oss：阿里云 oss api 接口服务
    + sevice_users：用户 api 接口服务
    + sevice_acl：用户权限管理 api 接口服务（用户管理、角色管理和权限管理等）

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

2021-04-09
- 1.sql文件修改（梅开二度），nodeType表加入了domain_id成员，建议服务器重新弄一下
- 2.所有有关牵扯类型的方法均加入了domainId属性
- 3.修正了删除逻辑
- 4.修改EnitityService的findByName方法，返回值变为List，同时加入了boolean strict变量，表示是否进行严格查找
- 5.增设类型/节点过滤接口（没写完），增设节点、关系单个模糊查询（没写完）

2021-04-10
- 1.两个模糊查询方法搞定，均返回List，目前只支持局部匹配
- 2.按照节点进行筛选的方法getGraphScreen完成，输入一串节点列表，返回只包含这些节点及其关系/子节点的图
- 3.理论上2可以通过cyper查出来，但我没找到该咋用，只好人工java手动筛了

2021-04-11
- 1.颜色逻辑改动，前端不再传输任何颜色，第一次新增类型会选取预设颜色，第二次之后查数据库可得
- 2.取消修改颜色方法，新增修改节点类型方法

2021-04-12
- 1.新增Entity的r成员和fontsize成员
- 2.updateNode方法加入r更新
- 3.createNode方法加入输入参数r成员

2021-04-13
- 1.废除updateType方法，和updateNode合并，前端逻辑无需修改
- 2.增设默认颜色的检查功能，发现在关闭后端再重开会导致新增类型的颜色和之前上一次关掉前的已保存的节点颜色是一样的，现已修复

2021-04-14
- 1.createLink优化，现在如果对“两节点已存在关系”的两节点新增关系，则改为对关系名字加长处理
- 2.修复update的null bug

2021-04-15 - 2021-04-17
- 1.updateNode加入修改xy策略,颜色bug
- 2.加入fontsize的更新
- 3.加入排版模式的xy成员，暂时不做改动
- 4.更新DefaultUtils的颜色库
- 5.domaindelete方法
- 6.待解决事宜，颜色库必须移植到数据库且和domainId绑定

2021-05-07

- 新建多个模块
- 数据库更新

2021-05-20
- domain表新增String的user_id,对应类新增了userId，服务器数据库记得改
- getAlldomain方法加入新参数userId，返回该用户的所有domain
- 创建domain记得传userId！
