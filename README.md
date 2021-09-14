# NewsDaily
2021夏季学期大作业
## 代码结构
整体代码使用了如下几个第三方库
* ```gson```
    > 提供json解析

* ```swiperefreshlayout```
    > 下拉刷新功能

* ```SugarOrm```
    > 提供简便的SQL数据库支持

* ```andyoom/draggrid```
    > 提供频道管理功能

整体上app由两种activity构成 :
1. MainActivity
    - 主activity
    - 包含包括主页、历史记录、收藏夹三个碎片
    1. 主页
        - 包括了搜索栏，频道选择和新闻封面展示，封面根据返回json判断是否有图片，并且区分有图片和无图片的封面
        - 频道选择的功能交由draggrim完成，对其源码进行一部分修改
        - 新闻封面展示由嵌套在swiperefreshlayout里的listview实现
    2. 历史记录和收藏夹通过数据库实现，使用比较轻便的Sugar库
2. DetailActivity
    - 详情页面
    - 通过webview实现，离线缓存功能也是基于webview的缓存机制，启用Dom和数据库接口，实现网页本地保存

代码中有如下自定义控件
* searchbar
    > 继承自EditorText，添加左右icon，重写ontouch方法检测点击，同时添加了点击右侧```X```清除搜索栏的功能

* news_box
    > 新闻封面的控件

* bottom_nav_menu
    > 底部导航栏，实现主页、历史记录和收藏夹的三者转换

## 具体实现
下面着重说一下几个比较重要的实现
* 下拉刷新&&上拉加载
    - 由于谷歌的swiperefreshlayout没有支持，也没有接口。本来决定再嵌套一个提供上拉加载的布局来实现，但是找到的布局都和下拉刷新的布局不太搭，移植起来也比较费事。所以我的上拉加载的功能最终是自己通过重写onScrollListenner里的OnScrollStateChanged函数，在加载列表底部足后一个封面时自动往列表中添加新数据实现的
    - 下拉刷新用了谷歌提供的swiperefreshLayout，它提供OnRefresh的接口，在下拉的时候开辟新线程，获取网络数据，再成功获取网络数据后通过handler调用主线程方法刷新UI，同时清除布局加载中的状态

* 离线缓存
    - webview功能十分全面，提供Rom和数据库支持，同时可以加载js，比起自己设计一个丑陋、信息与原文不对应且功能残缺的详情页，我更偏向于用webview，网页数据通过加载detailAvtivity时下载到本地，可以在重启app的时候仍然能进入缓存过的页面

* 本地历史记录以及读后变灰
    - 历史记录通过数据库存到本地，在主页再次点击后会被更新到最后一行
    - 变灰则是进入网页时设置字体颜色，由于刷新数据后，得到的数据列表虽然与原列表相同，但是并非同一个对象引用，所以识别是否浏览过的最终解决办法是比对详情页面的URL，若某一数据存在于数据库中详情页url的那一列数据中，则认定用户已访问过该页面

* 收藏功能
    - 涉及到不同activity之间的数据互通，并不是每次点击右上角的🌟都会向数据库中存入收藏的数据，而是重写了finish方法，在调用父类finish之前通过setResult方法把当前网页的url和被收藏的bool值传递到接收这个result的方法（在主activity），然后再处理数据库，修改收藏状态

* 频道管理
    - 主要实现是交由第三方的```draggrid```来实现的，但是这个库有一些bug，且年久失修无法跟上谷歌更新进度，所以我把源码下载下来导入项目中，修复了那些bug，同时完善了一下功能与界面，整体上更贴合我的app设计

> 整体开发过程中的难点就是IDE的使用，无论是as还是idea，都提供了很多的功能和插件，强大但也十分新手不友好。
> 为了导入第三方库，琢磨了不少资料看了不少文档，特别是源码导入添加依赖关系时，各个文档都有一定的区别，直到我捋清楚整个项目的搭建逻辑以后我才大致清除这么做的目的

## 总结与心得
### 本次学习收获：
1. 一定要多看源码，按cmd+单击可以翻阅无论是自己写的代码还是库中的代码的定义部分，不查永远不知道Date类型变量的年份会+-1900，还反复修改格式化器，伤透了脑筋。
2. 在入手一门新的语言之前，一定要弄明白整体的一个架构，再到语法和句法的部分
3. 在用第三方库之前，最好仔细阅读readme和主页，防止出现遗漏而引发不必要的问题


### 对这个project的意见：
```
 接口的不确定性实在让人难以接受。。。
 ```
