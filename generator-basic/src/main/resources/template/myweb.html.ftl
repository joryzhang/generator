<!DOCTYPE html>

<html>
<head>
    <title>Jory Zhang</title>
</head>
<body>
<h1>welcome to Jory Club</h1>
<ul>
    <#-- 循环渲染导航条 FTL指令 #list代表循环输出 menuItems可能是一个数组 循环输出每一个item-->
    <#list menuItems as item>
        <li><a href="${item.url}">${item.label}</a> </li>
    </#list>
</ul>

<#--底部版权信息-->
<footer>
    ${currentYear} Jory Club.ALL rights reserved.
</footer>
</body>

</html>