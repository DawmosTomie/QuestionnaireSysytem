# QuestionnaireSysytem
Practical training project in sophomore year.


# 目录
[1.实践目的 Aim of Project	1](#_toc138964034)

[2.预习内容 Preview Content	1](#_toc138964035)

[3.实践内容和实践过程 Project Content and Process	2](#_toc138964036)

[3.1	概述Overview	2](#_toc138964037)

[3.1.1项目概述：	2](#_toc138964038)

[3.1.2工作概述：	2](#_toc138964039)

[3.2	相关技术Relevant Technologies	3](#_toc138964040)

[3.2.1理论基础	3](#_toc138964041)

[3.2.2方法	4](#_toc138964042)

[3.2.3相关工具与环境	4](#_toc138964043)

[3.3	系统设计System Design	5](#_toc138964044)

[3.3.1功能分析	5](#_toc138964045)

[3.3.2架构设计	6](#_toc138964046)

[3.3.3模块设计	9](#_toc138964047)

[3.3.4数据库设计	10](#_toc138964048)

[3.3.5接口设计	13](#_toc138964049)

[3.3.6交互设计	15](#_toc138964050)

[3.3.7详细设计说明书等文档编写	16](#_toc138964051)

[3.4编码Coding	16](#_toc138964052)

[3.4.1后端：	16](#_toc138964053)

[3.4.2前端：	17](#_toc138964054)

[3.5	测试Testing	21](#_toc138964055)

[3.5.1 Junit测试	21](#_toc138964056)

[3.5.2 系统功能测试及整体界面展示	22](#_toc138964057)

[4.实践总结 Conclusion	34](#_toc138964058)

[5.参考资料 References	35](#_toc138964059)



2

# **1.实践目的 Aim of Project**
本次实践基于是互联网的调查问卷系统。传统的纸质问卷存在很多问题，比如成本高、流程复杂、统计困难等等。为了提高调查的效率、准确性和可控性，迫切需要一个方便设计、发布、统计和整理问卷内容的系统。目标采用B/S架构，通过Web界面，实现用户可以在个人电脑上使用常见的浏览器访问，完成各种增删改查操作以及前端展示的方法。具体来说，实现问卷的创建、查看和发布功能，进行数据分析，统计各题目的情况。并设计问卷，满足个性化的组卷需求，批量导入题库。

在此过程中，锻炼开发过程中的各项能力，如编写规范说明书，搭建Spring Boot工程环境，并利用Junit和log4j来进行MyBatis的测试。同时，学习使用SonarQube评估代码质量，使用Jacoco来分析测试用例的覆盖率。根据其他人的评价以及自己对其他人成果的评价总结，不断优化系统功能。完成迭代的代码编写与系统构建及改进。
# **2.预习内容 Preview Content**
1. 需求分析：用例图、活动图、状态图等建模工具描述系统功能和行为；
1. 设计模式：类图、序列图、组件图等建模工具描述系统结构和交互；使用MVC、单例、观察者、工厂等设计模式提高系统的可维护性和可扩展性。
1. 软件测试：白盒测试、黑盒测试、单元测试等测试方法保证软件质量。
1. 软件配置管理：版本控制工具（Git）、构建工具（Maven）。
1. Java：熟悉Java语言的基本语法、数据类型、控制结构、异常处理等编写程序；利用Java语言的面向对象特性（如封装、继承、多态）、泛型特性、集合框架等编写高效的程序。
1. Vue：学习Vue框架实现前端页面的渲染，将数据与视图进行绑定。包括不限于，组件化开发，将页面分解为可复用的组件；路由管理，实现页面之间的跳转。
1. MySQL：数据库存储和管理数据。 
1. HTTP协议：实现客户端和服务器之间的通信，传输请求和响应数据； HTTP协议的方法（GET、POST）、状态码（200、404）、报文格式。
1. JavaScript：学习使用变量、函数、对象、数组，内置对象（Math、Date、String等）、内置函数（alert、console.log、setTimeout）、内置事件（onmouseover、onmouseout、onchange）等编写程序逻辑。实现网页中的交互和动态效果。
# **3.1	概述Overview**
### **3.1.1项目概述：**
高校满意度调查问卷系统。在高校教育阶段，为提高政府教育管理的针对性与有效性，需要从学生和教师获知其对学校、专业、教学情况、教学资源配套、学校管理水平等多方面评价的信息收集。互联网是效率最高、短时间内涉及人员广的渠道之一，所以依托互联网的调查问卷需求应运而生，通过信息的反馈，进行信息的初步筛选与分析，能够及时反应当前高校情况，为管理手段和政策方针提供依据。

设计问卷系统，问卷发起人登录后可以对项目、问卷进行管理，

查询项目，并针对查询得到的结果项目进行删除、和修改的操作，创建项目填写项目的名称等信息。还可以修改项目的信息、关联的项目问卷等，如果项目中的问卷正在进行中，不能修改。

###**3.1.2工作概述：**
1. 实现问卷系统的基本功能，包括创建、查看、发布问卷，以及数据的增删改查和前端展示；
1. 实现问卷系统的扩展功能，包括数据分析、个性化组卷和题库管理；
1. 使用Vue编写前端页面，并与后端进行数据交互和展示；
1. 学习使用SonarQube和Jacoco进行代码质量度量和测试用例覆盖率分析；
1. 使用Junit和log4j完成mybatis的单元测试和日志记录；
1. 学习使用jmeter进行性能测试和压力测试；
1. 根据互评结果，优化系统功能和界面设计；
1. 搭建Springboot工程环境，配置相关依赖和插件；
1. 编写开发规范说明书，规范代码风格、注释、命名等。

## **3.2	相关技术Relevant Technologies**
###**3.2.1理论基础**
1. 代码编写要遵循一致的命名规范、使用恰当的注释、避免冗余和复杂度、使用合适的设计模式和架构，做到清晰和可读。
1. 编写文档时，简洁而清晰的文档编写，与代码保持一致并及时更新。
1. 数据库设计原则：
- 规范：将数据分解为多个表，并消除数据之间的冗余和依赖关系，以提高数据的完整性和一致性。
- 避免冗余和异常：确保每个表只包含一个主题，并且每个属性只依赖于主键，以避免数据在插入、删除、修改时出现不一致或错误的情况。
- 使用合适的数据类型：根据数据的特征和用途，选择合适的数据类型，如整数、浮点数、字符串、日期、布尔等，以提高数据的有效性和准确性，减少数据的存储空间和处理时间。
- 建立索引和约束：为经常查询或排序的字段建立索引，以提高数据检索的速度和效率。为关键字段建立约束，如主键、外键、唯一性、非空性、范围等。在后续数据生成时实现一致性。
1. 前端开发的原则：
- 响应式设计：使用灵活和自适应的布局，使用户界面能够根据不同的设备和屏幕尺寸进行调整，并保持良好的视觉效果和交互体验。
- 可用：使用直观和友好的设计，使用户界面能够方便地完成用户任务，并提供必要的提示和反馈信息。
- 美观：提供色彩、布局等有设计感的界面，给用户以视觉享受。
1. 后端开发的原则：
- 模块化：将业务逻辑和数据交互分解为多个独立且可复用的模块，并通过接口进行通信和协作。
- 封装：将模块内部的实现细节隐藏起来，并通过公开的方法提供功能和服务，以提高模块的安全性和可维护性。
- 重用：利用已有的代码或组件来实现新的功能，重视代码复用，减少编码工作量，提高代码质量，注重模块化和标准化，与前面的模块化和封装紧密联系。
- 测试：及时进行代码审查与测试，对模块的功能和性能进行系统地检验和验证，以发现并修复错误，验证软件是否符合需求和预期，提升系统的可靠性和稳定性。
### **3.2.2方法**
1. 需求驱动开发方法：以需求为核心开发，先明确和分析需求，然后根据需求来设计、实现、测试和交付。
1. 增量开发方法：将项目分解为多个小型的子项目，每个子项目都会增加软件的功能，并且可以单独地进行测试和部署。增量开发可以根据需求的优先级来安排和执行子项目，每次完成一个子项目都会向客户交付一个更完善的软件产品，并且通过客户的反馈来确定下一个子项目的需求。
1. 单一职责：每个类或方法应该只有一个明确的责任，避免功能过于复杂和耦合度高的代码。
1. 高内聚低耦合：模块之间保持高内聚，模块内部的元素相互关联紧密，而模块之间的依赖关系应尽量降低。
1. 设计模式：如工厂模式、单例模式、观察者模式等，以提高代码的可维护性、可扩展性和可复用性。
1. 模块化和组件化：将大型任务拆分为小模块或组件，每个模块或组件负责一个特定的功能，以提高代码的可管理性和可测试性。
1. 异常处理：合理处理异常，以确保程序的稳定性和可靠性。
1. 前后端分离：让前端和后端各自专注于自己的领域，提高开发效率和质量，以及方便协作和维护。前端负责创建用户界面，处理用户交互，展示数据，调用后端接口等。后端负责处理业务逻辑，存储数据，提供接口等。接口负责连接前端和后端，传递数据，定义规则。
### **3.2.3相关工具与环境**

<table><tr><th valign="top"><b>工具、环境、技术</b></th><th valign="top"><b>名称</b></th><th valign="top"><b>版本</b></th><th valign="top"><b>简介</b></th></tr>
<tr><td valign="top"><b>硬件部署环境</b></td><td valign="top">联想拯救者</td><td valign="top">R7000</td><td valign="top">部署系统和应用</td></tr>
<tr><td valign="top"><b>软件部署环境</b></td><td valign="top">Windows</td><td valign="top">10\.0</td><td valign="top">操作系统</td></tr>
<tr><td rowspan="2" valign="top"><b>开发环境</b></td><td valign="top">JDK</td><td valign="top">1\.8</td><td valign="top">Java开发工具包</td></tr>
<tr><td valign="top">Node.js</td><td valign="top">14\.17.0</td><td valign="top">JS运行环境</td></tr>
<tr><td valign="top"><b>浏览器</b></td><td valign="top">谷歌</td><td valign="top">114\.0.5735.198</td><td valign="top">网页浏览</td></tr>
<tr><td valign="top"><b>开发工具</b></td><td valign="top">Idea</td><td valign="top">2021\.3.1</td><td valign="top">编写和调试代码</td></tr>
<tr><td rowspan="2" valign="top"><b>编程语言</b></td><td valign="top">Java</td><td valign="top">8</td><td valign="top">编程语言</td></tr>
<tr><td valign="top">JavaScript</td><td valign="top">2022</td><td valign="top">脚本语言</td></tr>
<tr><td valign="top"><b>测试工具</b></td><td valign="top">Junit</td><td valign="top">4</td><td valign="top">单元测试框架</td></tr>
<tr><td rowspan="2" valign="top"><b>版本管理工具</b></td><td valign="top">Git</td><td valign="top">2\.40.1</td><td valign="top">管理和追踪代码的版本</td></tr>
<tr><td valign="top">Maven</td><td valign="top">3\.3.9</td><td valign="top">项目依赖和打包</td></tr>
<tr><td valign="top"><b>数据库</b></td><td valign="top">MySQL</td><td valign="top">8\.0.3</td><td valign="top">开源关系型数据库管理系统</td></tr>
<tr><td valign="top"><b>数据库可视化工具</b></td><td valign="top">Navicat</td><td valign="top">16</td><td valign="top">数据库管理和开发工具，提供图形化界面和操作功能</td></tr>
<tr><td valign="top"><b>应用服务器</b></td><td valign="top">Tomcat</td><td valign="top">9\.0.38</td><td valign="top">Java Web应用服务器</td></tr>
<tr><td valign="top"><b>框架</b></td><td valign="top">SpringBoot</td><td valign="top">2\.3.1</td><td valign="top">基于Spring框架的快速开发框架</td></tr>
<tr><td valign="top"><b>日志记录工具</b></td><td valign="top">log4j</td><td valign="top">2\.14.1</td><td valign="top">记录应用程序运行时的日志信息</td></tr>
<tr><td valign="top"><b>数据访问层框架</b></td><td valign="top">mybatis</td><td valign="top">3\.5.13</td><td valign="top">简化数据库访问和操作</td></tr>
<tr><td valign="top"><b>代码质量度量工具</b></td><td valign="top">SonarQube</td><td valign="top">8\.9.10.61524</td><td valign="top">用于静态代码分析和质量度量</td></tr>
<tr><td valign="top"><b>测试覆盖率工具</b></td><td valign="top">Jacoco</td><td valign="top">0\.8.9</td><td valign="top">评估测试用例的覆盖率</td></tr>
<tr><td valign="top"><b>前端框架</b></td><td valign="top">vue</td><td valign="top">5\.0.8</td><td valign="top">前端JavaScript框架，构建交互式的Web界面</td></tr>
<tr><td valign="top"><b>性能测试工具</b></td><td valign="top">jmeter</td><td valign="top">5\.5</td><td valign="top">模拟负载和评估系统的性能</td></tr>
</table>

表格 1
## **3.3	系统设计System Design**
本人在系统设计过程中与小组成员一起进行功能分析，负责架构设计，接口设计模块设计，数据库设计，交互设计。成果如下。交互设计成果在报告后续测试部分给出。
### **3.3.1功能分析**
1\. 数据分析-题目统计功能：

`   `- 在问卷列表页面，点击统计链接，可以统计当前问卷的答题情况。

`   `- 单题统计：统计单题的答题情况，显示每个选项的选择数量以及占总答题人数的比例。

`   `- 答题总人数统计。

2\. 查看问卷功能：

`   `- 通过项目列表的查看按钮，进入问卷查看列表页面。

`   `- 在问卷查看列表页面，点击统计按钮，进入答卷查看页面。

`   `- 输入答卷人相关信息，可以查到答卷人当前项目中所有问卷的答卷明细。

`   `- 答卷明细页面显示当前答卷人、当前问卷内容以及答卷人的答题结果。当前问卷不可编辑。

3\. 发布问卷功能：

`   `- 通过项目列表的查看按钮，进入问卷列表查看界面。

`   `- 问卷列表页面包含问卷名称、问卷发布时间、预览、发布、删除、统计等操作链接。

`   `- 可预览问卷，模拟答题过程。

`   `- 可发布问卷并生成问卷访问链接。

`   `- 删除问卷功能，正在进行中的问卷不能修改，删除的问卷为逻辑删除，系统保留数据。

4\. 创建问卷功能：

`   `- 通过项目列表中的创建问卷按钮或空白模板创建按钮，打开创建问卷页面。

`   `- 创建问卷页面包含所属项目下拉框和调查类型下拉框，默认值根据项目名称和学生类型。

`   `- 可选择空白模板创建，填写问卷名称和调查说明。点击立即创建按钮，提示必填项不可为空；否则进入创建题目页面。

`   `- 创建成功后直接进入编辑试题页面，可选择不同类型的题目，支持修改和删除题目。

5\. 问卷设计-个性化组卷功能：

`   `- 在创建问卷页面中，增加设置答卷的开始时间、截止时间和答卷次数等功能。

`   `- 限时问卷设置：设定答卷时间范围，只有在限定时间内答卷链接才有效，超出范围不能答卷或有相应提示。

`   `- 定次问卷设置：设定答卷次数，每个账户或主机可答卷的次数。

`   `- 发放范围设置：面向公众和指定群组。选择指定群组时，显示系统内置的群组，调查问卷发送给指定的群组。

`   `- 问卷风格设置：内置不同问卷风格，根据选择设定问卷的风格。

6\. 题库管理-批量导入功能：

`   `- 在创建问卷页面中，可以批量导入题目。

`   `- 规划建立题库功能，可通过数据库直接配置题目。

`   `- 可通过题库筛选调查问卷题目，并批量导入题目信息。

`   `- 导入的题目可作为答卷的题目，可以进行再编辑，不影响原题库中的题目内容。

### **3.3.2架构设计**
1. 用户界面层（User Interface Layer）：

用户界面层负责与用户进行交互，展示问卷列表、统计结果、答卷明细等信息。

包括问卷列表页面、问卷统计页面、答卷查看页面、创建问卷页面等。

图 1

1. 应用逻辑层（Application Logic Layer）：

应用逻辑层处理用户请求，调用适当的服务和算法来完成相应的功能。

包括问卷管理、统计分析、答卷管理、题库管理等模块。

图 2

1. 服务层（Service Layer）：

服务层提供各种服务接口，供应用逻辑层调用，完成具体的业务逻辑处理。

包括问卷服务、统计服务、答卷服务、题库服务等。

图 3

1. 数据访问层（Data Access Layer）：

数据访问层负责与数据库进行交互，提供数据的读取、写入和查询等功能。

包括问卷数据访问、统计数据访问、答卷数据访问、题库数据访问等。

图 4

1. 数据库层（Database Layer）：

数据库层存储问卷、统计、答卷和题库等数据。

使用适当的数据库管理系统（MySQL）来管理数据。

图 5

###**3.3.3模块设计**
1\. 问卷管理模块：

`   `- 创建问卷：包括创建空白问卷和从题库导入题目。

`   `- 编辑问卷：对已创建的问卷进行编辑和修改。

`   `- 预览问卷：在问卷列表中查看问卷内容，进行模拟答题。

`   `- 发布问卷：将问卷发布，并生成问卷访问链接。

`   `- 删除问卷：逻辑删除已发布或未发布的问卷。

2\. 统计分析模块：

`   `- 单题统计：统计单个题目的答题情况。

`   `- 问卷统计：显示当前问卷所有题目的统计状况，包括选项选择数量和占总答题人数的比例。

`   `- 单题分类统计：对相同内容的问题进行分类统计，并以表格、柱状图等形式展示结果。

3\. 答卷管理模块：

`   `- 答卷查看：根据答卷人的相关信息，查看其在当前项目中所有问卷的答卷明细。

`   `- 明细查看：查看当前问卷的答卷情况，显示答卷人和答题结果。

`   `- 答卷限制：根据设置的限时和定次规则，限制答卷的时间范围和次数。

4\. 题库管理模块：

`   `- 题目导入：从题库中选择题目并批量导入到问卷中。

`   `- 题目编辑：对导入的题目进行编辑和修改。

`   `- 题目分类：对题库中的题目进行分类管理和统计。

5\. 模块内交互模型：



### **3.3.4数据库设计**
数据库设计如下：

cust\_group：记录不同的人群分组。

project\_info：项目信息。

question\_anwsers：用户提交的问卷的答案。

question\_bank：可以导入问卷的问卷题库。

question\_commit：用户提交的问卷信息存储。

question\_info：问卷中的题目信息。

question\_options：问卷题目的选项信息。

questionnaire\_info：问卷的信息。

questionnaire\_share：问卷的分享链接。

Respondent：答卷人信息。

user\_info：用户信息。


user\_info：用户信息。包含主键id，用户名和密码（用以登陆），补充信息，诸如性别、创建时间、创建人、姓名等等，通过role\_id划分角色，可以对应答卷人，问卷系统使用者，问卷系统管理者等权限。

图 9

project\_info：项目信息。用以实现关于项目功能的增删改查。Id是项目id，作为主键。Userid外键对应user\_info的主键，说明这个问卷由谁创建。Projectname是项目名称，project\_content是项目描述。补充信息包含创建人、创建时间等等。

图 10

图 11

questionnaire\_info：问卷的信息。Questionnaire\_id作为主键，唯一的标识问卷。Title为问卷题目。User\_id也是对应user\_info中id的外键，标识问卷由哪个用户创建。Status标识问卷的发布状态。Desc记载问卷描述。Project\_id与项目id进行外键绑定，每个问卷存在于项目之下。Is\_template标识是否为模板问卷。其他属性作为补充信息，比如发起时间，截止时间，所属人群等等。

图 12

图 13

question\_info：问卷中的题目信息。Question\_id作为主键唯一的标识问题。每个问题都有questionnaire\_id与问卷进行外键绑定。Type标识是单选还是多选等。Is\_must标识是否为必选题。

图 14

图 15

question\_options：问卷题目的选项信息。Option\_id唯一的标识每个选项，与问题id进行外键绑定。Title存储选项描述。

图 16

question\_bank：可以导入问卷的问卷题库。存储作为可选导入题库的问题。

图 17

question\_commit：用户提交的问卷信息存储。Commit\_user\_id与提交人id进行外键绑定。每次提交都是唯一的。并且与问卷id也进行外键绑定。并存储提交时间，作答时长，作答id等补充信息。


图 18

图 19

question\_anwsers：用户提交的问卷的答案。通过commit\_id与提交信息进行绑定，question\_id与问题绑定，记录选择的内容。

图 20

图 21

questionnaire\_share：问卷的分享链接。包含问卷id，问卷链接，链接分享人，链接发布时间等。

图 22
### **3.3.5接口设计**

<table><tr><th colspan="4" valign="top"><b>后端</b></th></tr>
<tr><td rowspan="5" valign="top"><b>ProjectController</b></td><td rowspan="5" valign="top">@RequestMapping("/project")</td><td valign="top">queryProjectList</td><td valign="top">查全部项目</td></tr>
<tr><td valign="top">deleteProjectById</td><td valign="top">删除</td></tr>
<tr><td valign="top">addProjectInfo</td><td valign="top">添加</td></tr>
<tr><td valign="top">modifyProjectInfo</td><td valign="top">修改</td></tr>
<tr><td valign="top">queryProjectName</td><td valign="top">查询特定</td></tr>
<tr><td colspan="4" valign="top"><b>后略</b></td></tr>
<tr><td colspan="4" valign="top"><b>前端</b></td></tr>
<tr><td colspan="2" rowspan="3" valign="top"><b>问卷设计者操作</b></td><td valign="top">designOpera</td><td valign="top">/api/design</td></tr>
<tr><td valign="top">loginPost</td><td valign="top">/api/admin/userLogin</td></tr>
<tr><td valign="top">queryProjectList</td><td valign="top">/api/project/queryProjectList</td></tr>
<tr><td colspan="2" rowspan="6" valign="top"><b>问卷操作</b></td><td valign="top">addQuestionnaire</td><td valign="top">/api/questionnaire/add</td></tr>
<tr><td valign="top">updateQuestionnaire</td><td valign="top">/api/questionnaire/update</td></tr>
<tr><td valign="top">queryQuestions</td><td valign="top">/api/questionnaire/queryQuestions</td></tr>
<tr><td valign="top">publishQuestionnaire</td><td valign="top">/api/questionnaire/publish</td></tr>
<tr><td valign="top">analysis</td><td valign="top">/api/questionnaire/analysis</td></tr>
<tr><td valign="top">answerView</td><td valign="top">/api/questionnaire/answerView</td></tr>
<tr><td colspan="2" valign="top"><b>题目操作</b></td><td valign="top">sageQuestions</td><td valign="top">/api/question/save</td></tr>
<tr><td colspan="2" rowspan="2" valign="top"><b>问卷回答者操作</b></td><td valign="top">answerOpera</td><td valign="top">/api/questionnaire/answerView</td></tr>
<tr><td valign="top">answerQuestionnaire</td><td valign="top">/api/questionnaire/anwser</td></tr>
<tr><td colspan="2" rowspan="6" valign="top"><b>用户操作</b></td><td valign="top">queryUserList</td><td valign="top">/api/admin/queryUserList</td></tr>
<tr><td valign="top">modifyUserStatus</td><td valign="top">/api/admin/modifyUserStatus</td></tr>
<tr><td valign="top">deleteUser</td><td valign="top">/api/admin/deleteUserInfoById</td></tr>
<tr><td valign="top">resetPwd</td><td valign="top">/api/admin/resetPwd</td></tr>
<tr><td valign="top">modifyUserInfo</td><td valign="top">/api/admin/modifyUserInfo</td></tr>
<tr><td valign="top">addUserInfo</td><td valign="top">/api/admin/addUserInfo</td></tr>
</table>

表格 2

如：

图 23

图 24

图 25

###**3.3.6交互设计**
1. 用户需求和目标：
- 快速设计和发布问卷，并能够准确地收集和统计调查结果。
- 易用性，能够满足不同类型的问卷设计需求。
1. 功能和结构：
- 提供友好的问卷设计界面，包括创建、编辑和组织问题、选项和页面的功能。
- 支持多种题型，如单选题、多选题、文本题等，以满足不同类型的调查需求。
- 具备问卷模板库，供用户选择现有的模板或自定义设计问卷。
- 提供问卷预览功能，让用户在发布之前查看问卷的外观和逻辑。
- 支持问卷发布和分享，包括生成唯一的问卷链接、嵌入到网站或通过邮件发送给受访者。
- 提供实时统计和分析功能，以便能够及时了解和分析收集到的数据。
- 支持数据分析。
1. 界面和视觉：
- 设计简洁、直观的用户界面，使用户能够轻松导航和操作系统的各项功能。
- 使用清晰的图标、按钮和颜色，以增强用户对功能和操作的识别和理解。
- 保持界面的一致性，使用户在整个问卷设计和统计过程中能够保持一致的视觉感知。
1. 行为和反馈：
- 设计用户友好的交互行为，如点击、拖拽、滑动等，以便用户能够自然地与问卷系统进行交互。
- 提供实时反馈，如状态指示器、进度条和提示信息，让用户了解其操作的结果和进展。
- 在问卷设计过程中，及时验证用户的输入和逻辑设置，提供错误提示和修正机制，以确保问卷的正确性和完整性。
1. 原型图如：

图 26
### **3.3.7详细设计说明书等文档编写**
按照任务资料中的文档资料，编写详细设计说明书。

## **3.4编码Coding**
编码部分本人负责实现后端创建问卷、查看问卷、发布问卷、数据分析、个性化问卷、题库导入功能并采用前后端分离思想，利用node.js、js、vue编写前端界面并实现展示，确保系统成功运行。
###**3.4.1后端：**
问卷向下划分为问卷信息，问卷题目，题目选项，创建问卷、查看问卷、发布问卷、数据分析、个性化问卷、题库导入功能通过controller调用service，在数据库中通过xml中编写的sql语句调用即可。

具体代码放在源代码中，报告部分只做部分展示。

如问卷questionnaire信息的获取：

Mapper.xml：

1. <select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap">
1. `        `*<!--@mbg.generated-->*
1. `        `select
1. `        `<include refid="Base\_Column\_List"/>
1. `        `from questionnaire\_info
1. `        `where questionnaire\_id = #{questionnaireId,jdbcType=VARCHAR}
1. `    `</select>

Service：

1. public QuestionnaireInfo answerView(String questionnaireId, String commitId) {
1. `        `QuestionnaireInfo questionnaireInfo = questionnaireInfoMapper.selectByPrimaryKey(questionnaireId);
1. `        `if (questionnaireInfo != null) {
1. `            `if(StringUtils.isNotBlank(commitId)){
1. `                `questionnaireInfo.setQuestions(queryQuestionsWithAnwser(questionnaireId,commitId));
1. `            `}else{
1. `                `questionnaireInfo.setQuestions(queryQuestions(questionnaireId));
1. `            `}
1. `        `}
1. `        `return questionnaireInfo;
1. `    `}

Controller：

1. @RequestMapping(value = "/get", method = RequestMethod.POST, headers = "Accept=application/json")
1. `    `public HttpResponseEntity get(@RequestBody QuestionnaireInfo questionnaireInfo) {
1. `        `HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
1. `        `httpResponseEntity.setCode("666");
1. `        `QuestionnaireInfo questionnaireInfo1 = questionnaireInfoService.getQuestionnaireInfoById(questionnaireInfo.getQuestionnaireId());
1. `        `httpResponseEntity.setData(questionnaireInfo1);
1. `        `return httpResponseEntity;
1. `    `}

### **3.4.2前端：**
配置路由：

1. Vue.use(Router)

1. export default new Router({
1. `  `mode: 'history',
1. `  `routes: [
1. `    `{
1. `      `path: '/',
1. `      `name: 'Index',
1. `      `meta: { hidden: true },
1. `      `component: Login
1. `    `},
1. `    `{
1. `      `path: '/login',
1. `      `name: 'Login',
1. `      `meta: { hidden: true },
1. `      `component: Login
1. `    `}
1. …

调用api：

1. export const post = function(url,data,files){
1. `  `return new Promise((resolve,reject) => {
1. `    `service({
1. `      `url:url,
1. `      `method:'post',
1. `      `data:data
1. `    `}).then(res => resolve(res)).catch(err =>reject(err))

1. `  `})
1. }

设计每个页面：

图 27

如项目管理界面进行问卷管理（创建、分析、预览）：

图 28

利用elementui进行组件导入与美化：

1. <el-row :gutter="30" >
1. `      `<el-col :span="8" style="margin-left: 18px">
1. `        `*<!--<el-card class="sidebar-card" v-for="item in detail" :key="item.id">-->*
1. `        `<el-card>
1. `          `<el-row style="margin-top: 5px;margin-bottom: 5px;" :gutter="20">
1. `            `<el-col :span="30">
1. `              `<el-button size="small" type="primary" style="margin-left: 10px;height: 40px;"
1. `                `@click="projectDialog = true, editProject = {}">添加项目</el-button>
1. `            `</el-col>
1. `            `<el-col :span="30">
1. `              `<el-input placeholder="输入项目名称进行过滤" v-model="filterText"></el-input>
1. `            `</el-col>
1. `          `</el-row>

脚本编写通过js调用api实现获取数据显示到界面上：（如问卷添加）

1. addProject() {
1. `      `if (!this.editProject.id) {
1. `        `post('/api/project/addProjectInfo', this.editProject)
1.           .then((res) => {
1. `            `this.$message({
1. `              `type: 'success',
1. `              `message: '新建项目成功',
1. `              `showClose: true,
1. `            `})
1. `            `this.projectDialog = false
1. `            `this.getWjList()
1. `          `})
1.           .catch((err) => {
1. `            `this.$message({
1. `              `type: 'error',
1. `              `message: err,
1. `            `})
1. `          `})
1. `      `} else {
1. `        `post('/api/project/modifyProjectInfo', this.editProject)
1.           .then((res) => {
1. `            `this.$message({
1. `              `type: 'success',
1. `              `message: '修改项目成功',
1. `              `showClose: true,
1. `            `})
1. `            `this.projectDialog = false
1. `          `})
1.           .catch((err) => {
1. `            `this.$message({
1. `              `type: 'error',
1. `              `message: err,
1. `            `})
1. `          `})
1. `      `}
1. `    `},

又如，当查看问卷时，会调用导入的自定义组件并传入参数，丰富界面并实现数据传输。

1. ` `*//查看问卷详情*
1. `    `lookDetail() {
1. `      `*//console.log(this.curQuestionnaire.questionnaireId,"--------",this.curQuestionnaire.title,"-----------", this.curQuestionnaire.desc);*
1. `          `this.$refs.design.init(this.curQuestionnaire.questionnaireId,this.curQuestionnaire.title, this.curQuestionnaire.desc)
1. `          `this.$refs.dataShow.dataAnalysis(this.curQuestionnaire.questionnaireId)
1. `    `},

通过style部分实现布局：

1. <style scoped>
1. #projectHome {
1. `  `font-size: 16px;
1. }

1. .home {
1. `  `width: 100%;
1. `  `height: calc(100vh - 100px);
1. `  `text-align: center;
1. }
1. ...

通过vue基本指令如v-if、V-on，结合全局组件、私有组件，调用data和methods，实现组件切换与父子组件传值，ref获取元素和组件等方法实现前端设计。

除了要求给出的必做和选做题目之外，我还增添了通过题目导入xls、xlsx文件添加题目到题库的功能，使个性化更进一步。

## **3.5	测试Testing**
测试部分我主要负责用户管理和项目管理，以及问卷创建和查看和发布部分的junit测试以及系统成功运行的功能整体测试。
### **3.5.1 Junit测试**
编写junit代码进行测试：

如：

图 29

测试效果：


图 30

### **3.5.2 系统功能测试及整体界面展示**
1. 登录

图 31

主界面：（可以通过左上角项目管理切换至项目主视角）

图 32

1. 项目管理

项目管理主界面（增删改查点击按钮）：

图 33

在项目界面可以看见下属问卷的情况：

图 34

添加项目：

图 35

查看/修改项目：

图 36

1. 创建问卷
- 项目管理入口的创建问卷：

图 37

添加题目：

图 38

- 问卷列表主界面创建（编辑）问卷：（点击删除即删除）

图 39

图 40

1. 查看问卷

预览：

图 41

用户填写情况：

图 42

图 43

1. 发布问卷

（未发布不能分享）

图 44

图 45

分享：

图 46

打开分享网址：

图 47

停止发布：

图 48

图 49

1. 数据统计

整体：

图 50

柱状图：

图 51

圆环图：

图 52

饼图：

图 53

条形图：

图 54

文字类回答详情：

图 55

词云：

图 56

1. 个性化组卷

创建问卷时可以实现人群，模板，有效期的添加：

图 57

问题设计时还可以实现次数限制：

图 58

1. 题库导入

题库管理：

图 59

问卷中题库导入：

图 60

1. 用户填写

图 61

1. 用户管理

图 62

1. 模板管理

模板库：

图 63

创建问卷时选择模板导入：

图 64

1. 群组管理

图 65

# **4.参考资料 References**
[1] 《Java编程思想》- Bruce Eckel著 [M]. 机械工业出版社. 2004-01-01.

[2] 《Effective Java中文版》- Joshua Bloch著 [M]. 机械工业出版社.2009-06-01.

[3] 《Vue.js权威指南》- 尤雨溪等著 [M]. 人民邮电出版社. 2018-06-01.

[4] 《Vue.js实战》- 梁灏著 [M]. 人民邮电出版社. 2018-08-01.

44

