# 监控系统

## 效果展示
![Alt 登录](https://raw.githubusercontent.com/NichenLg/state/master/frontend/static/img/monitor-login.png)
![Alt 基础信息](https://raw.githubusercontent.com/NichenLg/state/master/frontend/static/img/monitor-bases.png)
![Alt 基础信息详情](https://raw.githubusercontent.com/NichenLg/state/master/frontend/static/img/monitor-base-info.png)
![Alt 复制信息](https://raw.githubusercontent.com/NichenLg/state/master/frontend/static/img/monitor-replications.png)
![Alt 复制信息详情](https://raw.githubusercontent.com/NichenLg/state/master/frontend/static/img/monitor-replication-info.png)
### 菜单
> 菜单是从接口中动态获取, 目前考虑的是有MySQL

#### MySQL --> 基本状态
> 1. 圆圈波浪显示一个数据库实例, 波浪流动表示正常
> 2. 点击弹出modal框, 列表显示`show status`的内容, 有搜索筛选功能

#### MySQL --> 复制状态
展示MySQL的复制状态
> 1. 一个小圆圈表示一个数据库实例, 蓝色流动表示存活, 其他颜色表示出现问题
> 2. 箭头表示数据复制流动的方向(从主库复制到从库)
> 3. 复制出错时, 从库节点颜色变为红色, 复制的数据流动箭头消失
> 4. 点击一个数据库实例, 弹出该数据库实例关于复制的详细信息

## 后端设计
后端基于[play-1.4.1(https://playframework.com/documentation/1.4.x/home)](https://playframework.com/documentation/1.4.x/home)编写, 前端程序已打包到public目录, 可直接运行server端程序
> 1. 在配置文件中定义访问的用户名和密码, 默认是admin/admin
> 2. 在配置文件中配置MySQL的所有实例, 可灵活配置每个数据库实例
> 3. 定时(5s)轮询获取所有MySQL实例的相关状态数据, 并放在缓存中, 数据最长存储时间1分钟
> 4. 心跳数据从缓存中获取并返回
> 5. 详细信息数据即时获取
> 6. 出现问题, 邮件通知, 并可配置通知频率
