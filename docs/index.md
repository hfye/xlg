XLG serves as a primary place to host the component i design and a place where i can reference/utilize in my future job.

Spring Framework has been my favourite framework to start off any project/application i'm working on. It is open source and there are many goodies residing in the code to learn from. All the component depict here is tie to Spring Framework and will work out of the box. However, with some tweak it should be able to be incorporate into your project. Source code are provided so feel free to hack it.

# Pagination component - A Server Side Solution
In almost all web sites we visit or web based application that we used today, we definitely came across a use case to project information in a piece-wise fashion, this is commonly refer to as Pagination. To properly implement this feature we need a collaboration between front end as well as server side skill. There are many js framework out in the open source community to address the UI portion. A quick search on the internet you can find support for angular or react easily. As for the server side, there are techniques using specific api from hibernate or entity framework to piece-wise fetch the data from the DB, i.e. only project the neccessary page. But these techniques tie you to a specific ORM framework and i tend to move away from ORM, less is more, plain JDBC or simple mapper is my choice. In fact this component does work with hibernate without the api involvement, although i have not tested it. The Pagination component depict here is tested against spring framework and with some tweak it should be able to work in other environment.

## Problem Domain
To properly address the Pagination use case we need to understand the pain the developer have to overcome to achieve it.
1. Extract the page to fetch from the request. For a web based application, the page to fetch can be establish from the query parameter.
2. Propagating the page and no of records to the DAO layer, possibly introducing new api method or extends a class to pass in the page and no of records.
3. Write the sql to retrieve the records required only.

Extracting the page to fetch might not be difficult, a simple filter will do, however the propagating of the info across various application layer down to the DAO layer without the need to introduce api changes to accommodate the additional parameter is the key. Here we make use of the [ThreadLocal](http://tutorials.jenkov.com/java-concurrency/threadlocal.html) to propagate the information across various application layer. Next, it is the task of writing the sql which is optimise for each DB it is working on.

## Design
The idea we came about to address this feature comprises 3 component, i.e.
1. The use of __ThreadLocal__ to propagate our information across various application layer.
2. The use of __AOP__. This is a classic case of cross cutting concern. With the help of __AOP__ we can selectively indicate whether a specific __DAO__ api should have a __Pagination__ behavior.
3. The use of __Proxy Pattern__. A proxied data source with a real delegate data source serve as a factory to construct a connection object that has the capacity to override the sql with pagination qualifiers pertaining to the underlying DB used. All method call to the following api  `Connection.prepareStatement(String sql)`, `Connection.prepareStatement(String sql, int resultSetType, int resultSetConcurrency)`, `Connection.prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)`, will override the sql given. 

E.g. with given sql statement of `select test_date, test_id from test_table order by test_date, test_id`, the resulting sql on postgresql will be `select test_date, test_id from test_table order by test_date, test_id LIMIT 10 OFFSET 0`, given page size 10.

# User Guide
## Introduction
The XLG server side __Pagination__ component is design to be non intrusive although it is design for use in Spring Framework java. The core concept is to intercept and override SQL to inject pagination qualifiers at the jdbc level. With the help of __ThreadLocal__ we can propagate pagination required info across application layer without modification of existing api.

## Quick Start
The XLG server side __Pagination__ component is tested using Spring Framework java and the integration test cases is build on Spring Framework java as well. To start using in spring web application you generally need to,
1. Define a filter in web.xml with reference to `com.xlg.pagination.PaginationFilter`. The filter will look for a query parameter named, `page`, and if it exist it will setup a `com.xlg.pagination.PagingConfig` in the __ThreadLocal__ to be access later in `com.xlg.pagination.ProxyConnection` and `com.xlg.pagination.PaginationAdvice`. 
```XML
    <filter>
        <filter-name>paginationFilterChain</filter-name>
        <filter-class>com.xlg.pagination.PaginationFilter</filter-class>
    </filter>
```
2. Configure a data source which will act as a delegate to provide the DB connection. Pls refrain assigning `dataSource` as a bean name for the delegate data source. Generally, we will named it `delegateDataSource`. E.g. with a DBCP configuration,

```XML
    <bean 
        id="delegateDataSource" 
        class="org.apache.commons.dbcp.BasicDataSource" 
        destroy-method="close">
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="defaultAutoCommit" value="${jdbc.defaultAutoCommit}"/>
        <property name="initialSize" value="${jdbc.pool.initialSize}"/>
        <property name="maxActive" value="${jdbc.pool.maxActive}"/>
        <property name="maxIdle" value="${jdbc.pool.maxIdle}"/>
        <property name="minIdle" value="${jdbc.pool.minIdle}"/>
        <property name="maxWait" value="${jdbc.pool.maxWait}"/>
        <property name="validationQuery" value="${jdbc.conn.validationQuery}"/>
        <property name="testOnBorrow" value="${jdbc.conn.testOnBorrow}"/>
        <property name="testOnReturn" value="${jdbc.conn.testOnReturn}"/>
        <property name="testWhileIdle" value="${jdbc.conn.testWhileIdle}"/>
        <property name="timeBetweenEvictionRunsMillis" value="${jdbc.conn.timeBetweenEvictionRunsMillis}"/>
        <property name="numTestsPerEvictionRun" value="${jdbc.conn.numTestsPerEvictionRun}"/>
        <property name="minEvictableIdleTimeMillis" value="${jdbc.conn.minEvictableIdleTimeMillis}"/>
        <property name="accessToUnderlyingConnectionAllowed" value="${jdbc.conn.accessToUnderlyingConnectionAllowed}"/>
        <property name="removeAbandoned" value="${jdbc.conn.removeAbandoned}"/>
        <property name="removeAbandonedTimeout" value="${jdbc.conn.removeAbandonedTimeout}"/>
        <property name="logAbandoned" value="${jdbc.conn.logAbandoned}"/>
        <property name="poolPreparedStatements" value="${jdbc.prep.stmt.poolPreparedStatements}"/>
        <property name="maxOpenPreparedStatements" value="${jdbc.prep.stmt.maxOpenPreparedStatements}"/>
    </bean>
```
3. Define a properties `delegate.datasource=delegateDataSource` to wired the delegate data source to the proxy data source.
4. Define a properties `db.type=postgresql` or `db.type=sqlserver` to wired either `com.xlg.pagination.PostgreSqlOverrideSql` or `com.xlg.pagination.SqlServerOverrideSql`.
5. Define a AOP either on the service layer or controller of the use case that required pagination to inject pagination on the DAO api needed. In this component, advice is provide and it is developer responbility to defined the pointcut and reference the advice. Also, the advice contains the page size info configuration, default is 0, which means no paging. So a non 0 is needed to be configured to enable paging. E.g.

```XML
    <bean id="publicPaginationAdvice" class="com.xlg.pagination.PaginationAdvice">
        <property name="pageSize" value="10" />
    </bean>

    <aop:config>
        <aop:aspect id="paginationAspect1" ref="publicPaginationAdvice">
	    <aop:pointcut expression="execution(* com.xlg.pagination.dao.TestTableDao.getAllTestTable(..))" id="selectAllTestTable"/>
	    <aop:around method="paginate" pointcut-ref="selectAllTestTable"/>
        </aop:aspect>
    </aop:config>
```
