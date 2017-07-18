## 后台管理基础框架

### 1.项目配置文件
配置文件采用 `yml` 格式文件，请自行查阅 `.yml` 配置文件风格  
配置文件路径: `/ext-platform/src/main/resources/application*.yml`  

### 2. 项目初始化自动构建表结构 SQL 文件
数据库文件路径：`ext-platform/src/main/resources/system.install.sql/[h2|mysql|oracle]/init-data.sql`

### 3. 项目基础页面模板
> 1. 项目采用 `thymeleaf` HTML5 模板引擎进行渲染  
> 2. 项目模板已经将公共部分 `html` 代码分离成单独的文件，请根据需要使用  
> `<import th:include="admin/include/head" th:remove="tag"/>` 进行引入  
> 3. 项目模板使用和规范例子：  
> `/base-ui-bootstrap/src/main/resources/templates/admin/demo/[list.html|form.html]`    
> `/base-ui-bootstrap/src/main/resources/static/js/admin/demo/demo.js`  
> **4. 用户确认提示框**  
> 请直接在 `js` 中调用方法 `confirm(title, content, *callback)`  

### 4. 代码/环境规范
1. 项目采用 `maven` 构建，请确保你的电脑可以 ~~翻墙~~ 
2. 当类写完后/正在写时，请即时使用 IDE 的代码格式化功能对你的代码进行标准风格格式化，  
Eclipse 为 `Ctrl + Shift + F` 或代码窗口中 `右键-source-format`  
IDEA 为 `Ctrl + Alt + L` 或菜单栏 `Code-Reformat Code`  
3. 项目必须使用 `Java JDK 8` 以上版本，`Tomcat8` 以上版本。(`Tomcat8.5` 已经经过长时间测试并且取得稳定线上环境运行的结果)  
4. 项目中有引入 `Guava` 工具包，详情可以看这里 -> [URL:Guava 是什么,怎么用?](http://ifeve.com/google-guava/)  
5. **任何可能需要注意的地方一定要写备注**

### 文档
1. [安装使用](doc/1.安装使用.md)
2. [API](doc/2.API.md)