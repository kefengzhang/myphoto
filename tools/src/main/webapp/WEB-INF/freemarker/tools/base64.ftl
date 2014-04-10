<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<title>在线BASE64工具</title>
<meta name="Keywords" content=""/>
<meta name="description" content="" />
</head>
<body >
    <form action="base64" enctype="multipart/form-data" method="post">
        <#if outValue ??>
        结果:<br/>
        <textarea rows="10" cols="50" style="width:100%;" name="outValue">${outValue}</textarea><br/>
        </#if>
        原始数据:<br/>
        <textarea rows="10" cols="50" style="width:100%;" name="value"><#if value ??>${value}</#if></textarea><br/>
        选择上传文件:<input type="file"  name="value_file"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="提交" style="width:80px;height:40px;" /> <br/>
        <fieldset>
            <legend>请选择行为</legend>
            <input type="radio" name="do_thing" value="1" <#if do_thing ?? && do_thing==0><#else> checked="checked" </#if>/>编码,
            每行字符数:<input type="text" name="max_line_chars" value="<#if max_line_chars ??>${max_line_chars}<#else>76</#if>"/><br/>
            <input type="radio" name="do_thing" value="0" <#if do_thing ?? && do_thing==0>checked="checked" </#if> />解码
        </fieldset>
        <fieldset>
            <legend>输出类型</legend>
            <input type="radio" name="output_type" value="1" <#if output_type ?? && output_type==0><#else> checked="checked" </#if>/>用字符串输出<br/>
            <input type="radio" name="output_type" value="0"  <#if output_type ?? && output_type==0>checked="checked" </#if>/>
            下载文件,文件名:<input type="text" name="out_file_name" value="<#if out_file_name ??>${out_file_name}<#else>base64.bin</#if>"/>
        </fieldset>
        编码:
        <select name="character">
            <option value="UTF-8"  <#if character ?? && character=='UTF-8'> selected="selected"</#if>>UTF-8</option>
            <option value="GBK" <#if character ?? && character=='GBK'> selected="selected"</#if>>GBK</option>
            <option value="GB2312" <#if character ?? && character=='GB2312'> selected="selected"</#if>>GB2312</option>
            <option value="BIG5" <#if character ?? && character=='BIG5'> selected="selected"</#if>>BIG5</option>
            <option value="ISO-8859-1" <#if character ?? && character=='ISO-8859-1'> selected="selected"</#if>>ISO-8859-1</option>
            <option value="GB18030" <#if character ?? && character=='GB18030'> selected="selected"</#if>>GB18030</option>
            <option value="US-ASCII" <#if character ?? && character=='US-ASCII'> selected="selected"</#if>>US-ASCII</option>
            <option value="WINDOWS-1250" <#if character ?? && character=='WINDOWS-1250'> selected="selected"</#if>>WINDOWS-1250</option>
            <option value="SHIFT_JIS" <#if character ?? && character=='SHIFT_JIS'> selected="selected"</#if>>SHIFT_JIS</option>
        </select>,
        别的编码:<input type="text" name="other_character" value="<#if other_character ??>${other_character}</#if>"/><br/>
    </form>
</body>
</html>
