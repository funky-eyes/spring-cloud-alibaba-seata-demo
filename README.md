# Seata 1.2 进阶入门

本篇作者:陈健斌(funkye),github:https://github.com/a364176773 gitee:https://gitee.com/xrzi2016/spring-cloud-seata-demo  

初级入门地址:https://mp.weixin.qq.com/s/BOntA2mcdF-e0t2e4CBjPQ

本篇视频教程:https://www.bilibili.com/video/BV1tz411z7BX

## 介绍

本篇将介绍,如何通过官网的新人文档,参数说明,博客区等,进行Seata 1.2 的整合,以及使用nacos作为我们的配置中心跟注册中心,mysql作为server高可用db模式的数据库.

以及常见的入门5大难题解析:

1.xid未传递 

2.数据源未代理

3.事务分组配置出错导致无法找到tc

![image-20200430222042837](C:\Users\陈健斌\AppData\Roaming\Typora\typora-user-images\image-20200430222042837.png)

4.Global lock acquire failed

5.Could not found global transaction xid

## 正文

**第一步:**

首先访问:https://seata.io/zh-cn/blog/download.html

下载我们需要使用的seata1.2服务

**第二步:**

1.在你的参与全局事务的数据库中加入undo_log这张表

```mysql

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'increment id',
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME     NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME     NOT NULL COMMENT 'modify datetime',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';
```

2.在你的mysql数据库中创建名为seata的库,并使用以下下sql

```mysql
-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(96),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_branch_id` (`branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

```

**第三步:**

在你的项目中引入seata依赖

如果你的微服务是dubbo:

```java
            <dependency>
                <groupId>io.seata</groupId>
                <artifactId>seata-spring-boot-starter</artifactId>
                <version>1.2.0</version>
            </dependency>
```

如果你是springcloud:

```java
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-seata</artifactId>
                <version>2.2.0.RELEASE</version>
                <exclusions>
                    <exclusion>
                        <groupId>io.seata</groupId>
                        <artifactId>seata-spring-boot-starter</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>io.seata</groupId>
                <artifactId>seata-spring-boot-starter</artifactId>
                <version>1.2.0</version>
            </dependency>
```

**第四步:**

从官方github仓库拿到参考配置做修改:https://github.com/seata/seata/tree/develop/script/client

加到你项目的application.yml中.

```yaml
seata:
  enabled: true
  application-id: applicationName
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true
  config:
    type: nacos
    nacos:
      namespace:
      serverAddr: 127.0.0.1:8848
      group: SEATA_GROUP
      userName: "nacos"
      password: "nacos"
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: 127.0.0.1:8848
      namespace:
      userName: "nacos"
      password: "nacos"
```

**第五步:**

运行你下载的nacos,并参考https://github.com/seata/seata/tree/develop/script/config-center 的config.txt并修改

```
service.vgroupMapping.my_test_tx_group=default
store.mode=db
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true
store.db.user=username
store.db.password=password
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
```

运行仓库中提供的nacos脚本,将以上信息提交到nacos控制台,如果有需要更改,可直接通过控制台更改

**第六步**:

更改server中的registry.conf

```java
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"
  nacos {
    application = "seata-server"
    serverAddr = "localhost"
    namespace = ""
    cluster = "default"
    username = "nacos"
    password = "nacos"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "nacos"

  nacos {
    serverAddr = "localhost"
    namespace = ""
    group = "SEATA_GROUP"
    username = "nacos"
    password = "nacos"
  }
}
```

**第七步:**

运行seata-server,成功后,运行自己的服务提供者,服务参与者.

在全局事务调用者(发起全局事务的服务)的接口上加入@GlobalTransactional



自此,整合seata1.2及nacos的配置与注册中心全部整合完毕.

## 常见问题解析

**1.xid未传递** 

一般常见于springcloud的用户,请注意是否只引入了seata-all或者seata-spring-boot-starter.如果是,请换为本文介绍给springcloud的依赖即可.

如果是自己实现了WebMvcConfigurer,那么请参考com.alibaba.cloud.seata.web.SeataHandlerInterceptorConfiguration#addInterceptors把xid传递拦截器加入到你的拦截链路中

**2.数据源未代理**

一般分为2种情况

数据源自己创建后,代理数据源的beanname为DataSourceProxy而不是dataSource,导致sqlsessionfactory注入的并不是被代理后的.

如果是已经开启了自动代理的用户,请确认是否手写了sqlsessionfactory此时注入的DataSource并未显示是DataSourceproxy代理的情况,请进行调整,保证注入是代理的数据源.

**3.事务分组配置出错导致无法找到tc**

一般由于对事务分组的理解出现偏差导致的,请仔细阅读官网的参数配置中的介绍.

TM端: seata.tx-service-group=自定分组名 seata.service.vgroup-mapping(配置中心中是叫:service.vgroupMapping).自定分组名=服务端注册中心配置的cluster/application的值

拿nacos举例子

比如我server中nacos的cluster

```
  nacos {
    application = "seata-server"
    serverAddr = "localhost"
    namespace = ""
    cluster = "testCluster"
    username = "nacos"
    password = "nacos"
  }
```

我的事务分组为

```
seata:
  tx-service-group: test
```

那么nacos中需要配置test事务分组

```
service.vgroupMapping.test=testCluster
```

**4.Global lock acquire failed**

一般这种情况也是分为两种

常见的就是并没有对select语句进行forupdate,如果你不开启forupdate,seata默认是遇到并发性竞争锁并不会去尝试重试,导致拿不到lock当前事务进行回滚.不影响一致性,如果你想没forupdate也开启竞争锁client.rm.lock.retryPolicyBranchRollbackOnConflict设置为false(有死锁风险)

还有一种就是由于大并发下对同一个数据并发操作时,前一个事务还未提交,后续的事务一直在等待,而seata默认的超时周期为300ms,此时超时后就会抛出Global lock acquire failed,当前事务进行回滚,如果你想保住竞争锁的周期足够长,请更改client.rm.lock.retryTimes和client.rm.lock.retryInterval来保证周期的合理性.

**5.Could not found global transaction xid**

一般出现这个提示与服务重试及超时有关,比如A->B此时A调B超时,A默认是重试的,B等于被调了2遍,第二次被调用的B进行了响应,A发起者接收到结果后进行了提交/回滚,这次超时的B因为网络问题现在才被调用,他也收到了一样的全局事务id,进行业务处理,直到注册分支,此时全局事务已经被提交/回滚,导致当前超时的分支事务B无法注册上.

这种问题一般保证你的业务不会去超时重试,如果你需要,请确认全局事务状态,做好幂等,反正已经做过的处理重复操作.

## **题外话**

本人目前处于求职中,个人邮箱为364176773@qq.com  微信与qq都是这个,如果杭州地区(希望是西湖或余杭)有能帮本人内推者,不胜感激.简历请联系上述方式获取.有关于seata相关的问题也可以联系.