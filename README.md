# HttpMaster
一次性实现：http请求方法体+gson的model的工具  

### 前言  
Android的网络操作是很常见的，而且网络请求的方法体都是类似的，我希望能创造一个工具，通过一个回车键就写好网络请求的方法体，生成用于gson解析的model，顺便再写好测试用例什么的。希望能通过这个工具减少模板式的代码。于是HttpMaster就诞生了！   
本工具中产生gson的model的算法是copy：https://jsontojava.appspot.com/ 中算法实现的，感谢这个优秀的项目。

### 示例   
![](./demo/httpmaster.gif)

### 如何使用  
这个应用程序就是android的程序，推荐配合ARC（在chrome中运行apk的插件）使用。  
ARC的下载说明：http://www.cnblogs.com/tianzhijiexian/p/4702327.html    
运行起ARC后导入这个应用的apk文件即可。  
![](./demo/arc.png)

### 转换为符合自己项目的工具  
因为本工具仅仅是实现了比较通用的模板，不能够满足所以的项目需要，目前有如下两种方式来转换为符合自己项目的方法体。  
**1. 简单**
将您的项目的http管理类继承`HttpRequest`来做网络请求，网络请求的返回值用`HttpResponse`做。这种方式虽然很简单，但需要引入本开源项目中的如下三个类：  
**2.复杂**  
请复写：`kale.http.framework.presenter.class`这个类中的protected方法。这样就可以为自己的项目定制一套方法体了。  
如果对json数据的解析有不同的处理，可以去复写`kale.http.framework.presenter.class`中的`getJsonStr`方法，最终只需要返回一个json格式的string即可。     
 
*PS:关于如何复写，请参考源码的实现和注释。*      

### 准备做的事情  
- 支持RxJava  
- 支持自动生成测试模板  
- 支持URL和代码独立  

### 开发者
![](https://avatars3.githubusercontent.com/u/9552155?v=3&s=460)

Jack Tony: <developer_kale@.com>   


### License

    Copyright 2015 Jack Tony

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
