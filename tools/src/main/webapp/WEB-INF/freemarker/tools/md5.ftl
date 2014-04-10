<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<title>在线MD5码工具</title>
<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/2.0/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery.md5.js"></script> 
<meta name="Keywords" content=""/>
<meta name="description" content="" />
<style type="text/css">
.tools_content{
    padding-top:30px;
    margin:0 auto;
    width:1000px;
    max-width:1000ox;
}
</style>
</head>
<body >
<div class="tools_content">
    <div style="text-align:left; margin-top:30px; margin-bottom:15px;">
            MD5: <input type="text" name="value" placeholder="请输入您要加密的字符串" autocomplete="off" value="" style="width:300px; height:20px;" />
        </div>
        <div id="results">
            <p>16位小写：<span class="_16"></span></p>
            <p>16位大写：<span class="_16up"></span></p>
            <p>32位小写：<span class="_32"></span></p>
            <p>32位大写：<span class="_32up"></span></p>
        </div>
    </div>
</div>

<script type="text/javascript">
(function(){
    var encode = function(){
        var str = $("input[name=value]").val();
        var res = '';
        if(str != ''){
            res = $.md5(str);
            $("._32").html(res);
            $("._32up").html(res.toUpperCase());
            $("._16").html( res.substr(8,16) );
            $("._16up").html( res.substr(8,16).toUpperCase() );
        }
    };
    $("input[name=value]").keyup(function(){
        encode();
    });
    
})();
</script>
    
</body>
</html>
