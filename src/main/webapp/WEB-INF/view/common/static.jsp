<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/6/26
  Time: 0:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.dropdown.hack.css" rel="stylesheet" />

<script src="<%=request.getContextPath()%>/bootstrap/js/jquery-3.3.1.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>

<!-- 引入boobox弹框插件的css文件和js文件 -->
<script src="<%=request.getContextPath()%>/bootstrap/js/bootbox.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootbox.locales.min.js"></script>

<!-- 引入datatables表格插件的css文件和js文件 -->
<link href="<%=request.getContextPath()%>/js/DataTables/css/dataTables.bootstrap.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/DataTables/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/js/DataTables/js/dataTables.bootstrap.min.js"></script>

<!-- 引入datetimepicker日期插件的css文件和js文件 -->
<link href="<%=request.getContextPath()%>/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<!-- 引入zTree的css文件和js文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/zTree/js/jquery.ztree.all.min.js"></script>
<link href="<%=request.getContextPath()%>/js/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />

<!-- 引入fileinput文件上传插件的css文件和js文件 -->
<link href="<%=request.getContextPath()%>/js/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-fileinput/js/locales/zh.js"></script>
<script src="<%=request.getContextPath()%>/js/date-format.js"></script>
<script>
    var chinese = {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项结果",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };
</script>
