# pyDeal
发现杯的deal部分

**11-18-14：33 更新 by @Galaxyzeta **

1. 重写框架结构，把web改webapp，引入servlet包，由此解决几个奇怪问题，如浏览器定时崩溃（Port引起的），静态资源更新了但Tomcat还在用老资源，引入依赖直接无法启动等。
2. HelloWeb项目中包含了前端发送JSON，后台如何接收的Demo。
3. 上传一份学习总结，希望少走弯路。

**11-29-20：00 更新 by @Galaxyzeta**

1. 原先的框架删除，更新为SpringBoot框架，并附带了Demo
2. 学习总结进一步拓展，基本涵盖了SSM的全部入门内容

**2020 2-11 03:24 更新 By @Hades300**

部署

- 修改pom.xml的java-version部分
- 增加.gitignore文件，隐藏 `.idea\target` 仅保留源码+必要的配置文件+尽量减小仓库大小
- 在idea中create from exists，再拉取依赖应该就行。
- 完成了user和deal类的实体类、dao、service，数据库部分使用了mybatis，基于xml文件来绑定语句的方式（dbq 我后知后觉 但是不想改了）
- user的认证使用了token模式，密码后端哈希值入库。
- 路由如下
  - /users/list
  - /users/login
  - /users/add
  - /deals/list
  - 鉴权
    - /deas/update
    - /deals/add
    - /deals/delete
- 数据库部署在我的学生机中，具体信息私戳