<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<title>在线json格式化工具</title>
<meta name="Keywords" content=""/>
<meta name="description" content="" />
<link rel="stylesheet" href="/js/google-code-prettify/prettify.css">
<script src="/js/google-code-prettify/prettify.js"></script>
<style type="text/css">
</style>
</head>
<body onload="prettyPrint()">
    <form action="json" method="post">
        <#if outValue ??>
        结果:<br/>
        <div style="height:400px;overflow-y:auto">
            <pre class="prettyprint linenums lang-javascript">${outValue}</pre>
        </div>
        </#if>
        原始数据:<br/>
        <textarea rows="10" cols="50" style="width:100%;" name="value"><#if value ??>${value}</#if></textarea><br/>
        <input type="submit" value="提交" style="width:80px;height:40px;" /> <br/>        
    </form>
</body>
</html>
