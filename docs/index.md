XLG serves as a primary place to host the component i design and a place where i can reference/utilize in my future job.

Spring Framework has been my favourite framework to start off any project/application i'm working on. It is open source and there are many goodies residing in the code to learn from. All the component depict here is tie to Spring Framework and will work out of the box. However, with some tweak it should be able to be incorporate into your project. Source code are provided so feel free to hack it.

# Pagination component
In almost all web sites we visit or web based application that we used today, we definitely came across a use case to project information in a piece-wise fashion, this is commonly refer to as Pagination. To properly implement this feature we need a collaboration between front end as well as server side skill. There are many js framework out in the open source community to address the UI portion. A quick search on the internet you can find support for angular or react easily. As for the server side, there are techniques using specific api from hibernate or entity framework to piece-wise fetch the data from the DB, i.e. only project the neccessary page. But these techniques tie you to a specific ORM framework and i tend to move away from ORM, less is more, plain JDBC or simple mapper is my choice. In fact this component does work with hibernate without the api involvement, although i have not tested it. The Pagination component depict here is tested against spring framework and with some tweak it should be able to work in other environment.

## Problem Domain
To properly address the Pagination use case we need to understand the pain the developer need to overcome to achieve it.
1. Extract the page to fetch from the request. For a web based application, the page to fetch can be establish from the query parameter.
2. Propagating the page and no of records to the DAO layer, possibly introducing new api method or extends a class to pass in the page and no of records.
3. Write the sql to retrieve the records required only.
Extracting the page to fetch might not be difficult, a simple filter will do, however the propagating of the info across various application layer down to the DAO layer without the need to introduce api changes to accommodate the additional parameter is the key. Here we make use of the [ThreadLocal](http://tutorials.jenkov.com/java-concurrency/threadlocal.html) to propagate the information across various application layer. Next, it is the task of writing the sql which is optimise for each DB it is working on.

## Design
The idea we came about to address this feature comprise 3 component, i.e.
1. The use of __ThreadLocal__ to propagate our information across various application layer.
2. The use of __AOP__. This is a classic case of cross cutting concern. With the help of __AOP__ we can selectively indicate whether a specific __DAO__ api should have a __Pagination__ behavior.
3. The use of __Proxy Pattern__. A proxied data source with a real delegate data source. The proxied data source will construct a connection object that has the capacity to override the sql with pagination qualifiers via `prepareStatement` api.
