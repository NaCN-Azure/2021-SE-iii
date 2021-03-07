backend-parent：后端系统的根目录（父工程），暂时管理两个子模块

+ common：公共模块
    + common-util：工具类模块，所有模块都可以依赖于它
    + service-base：service 服务的 base 包，包含 service 服务的公共配置类，所有 service 模块依赖于它
    + spring-security：认证与授权模块，需要认证授权的 service 服务依赖于它
+ service：api 接口服务，暂时只有 sevice_coin 一个子模块，后续添加很方便



还有一个 schema 文件夹，用于存放数据库文件