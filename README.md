# HttpMaster
一次性实现：http请求方法体+gson的model的工具  

### 前言  
Android的网络操作是很常见的，而且网络请求的方法体都是类似的，我希望能创造一个工具，通过一个回车键就写好网络请求的方法体，生成用于gson解析的model，顺便再写好测试用例什么的。希望能通过这个工具减少模板式的代码。于是HttpMaster就诞生了！   

### 示例   

### 如何使用  
这个应用程序就是android的程序，推荐配合ARC（在chrome中运行apk的插件）使用。  
ARC的下载说明：http://www.cnblogs.com/tianzhijiexian/p/4702327.html  
运行起ARC后导入这个应用的apk文件即可。  

### 转换为符合自己项目的工具  
因为本工具仅仅是实现了比较通用的模板，如果你想转换为符合自己项目的方法体，请复写：kale.http.framework.presenter.class这个类中的protected方法。关于如何复写，请参考源码的实现和注释。

### 准备做的事情  
支持RxJava  
支持自动生成测试模板  
支持URL和代码独立
