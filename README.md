# autoScreen
## install

### 下载eclipse并且安装插件
* [eclipse](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon3) Neno download

* [eclipse neno swing 插件安装](http://download.eclipse.org/windowbuilder/WB/release/4.6/)

### import autoScreen 下面的所有Project (Maven Project)
* File->Import->Maven->Existing Maven Projects
* 选择xxx\autoScreen,import 所有Project
![image](https://github.com/yueheng-li/autoScreen/blob/master/images/import.PNG)

### Error 
``` java
Description	Resource	Path	Location	Type
Project configuration is not up-to-date with pom.xml. Select: Maven->Update Project... from the project context menu or use Quick Fix.	sikulixapi		line 1	Maven Configuration Problem
``` 
#### 上面error发生的时候，下面手顺解决。
* 右键工程->Maven->Update Project...
![image](https://github.com/yueheng-li/autoScreen/blob/master/images/maven_update_project.PNG)

### eclipse debug 启动
IntelligentOperation1\sikuli_ide.java Run Java Application

### sikuli start
* mvn package assembly:single
* 生成之后的jar包的名称，需要修改成sikulix.jar，否则会因为设置的路径问题，不能正常启动。
* java -jar sikulix.jar命令启动，sikuli画面。



