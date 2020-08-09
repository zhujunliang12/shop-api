<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/6/29
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- 引入zTree的css文件和js文件 -->

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
</head>
<body>

<div class="content_wrap" >
    <input type="button" value="增加类型" onclick="addArea()" style="color: #0e90d2;margin-right: 60px">
    <input type="button" value="删除类型" onclick="deleteAreaIds()" style="color: #0e90d2;margin-right: 60px">
    <input type="button" value="修改类型" onclick="updateArea()" style="color: #0e90d2;margin-right: 60px">
    <div class="zTreeDemoBackground left" style="margin-left: 100px">
        <ul id="treeDemo" class="ztree"></ul>

    </div>
</div>

<div id="addCatygoryDiv" style="display:none">

    <form id="addAreaForm" class="form-horizontal" style="">

        <div class="form-group">
            <label  class="col-sm-2 control-label">上级名称</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="parentName" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">增加的类型</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="name" />
            </div>
        </div>

    </form>
</div>



<script src="<%=request.getContextPath()%>/bootstrap/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/zTree/js/jquery.ztree.all.min.js"></script>
</body>
</html>

<script type="text/javascript">


    var setting = {
        view: {
            //禁止多选
            selectedMulti: false
        },
        //是否显示选中的复选框
        check: {
            enable: true
        },
        data: {
            key: {
                name: "name",
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: 0
            }
        }
    };

    $(function(){
        initZtree();
        catreHtml=$("#addCatygoryDiv").html();
    })

    function initZtree() {
        $.ajax({
            url:"<%=request.getContextPath()%>/category/queryCategory",
            type:"post",
            data:{},
            dataType:"json",
            error:function(){
                alert("失败!");
            },
            success:function(result){
                if(result.code == 200){
                    $.fn.zTree.init($("#treeDemo"), setting, result.data);
                }

            }

        });
    }
    ;
    function addArea() {

        //获取被选中的节点
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var id="";
        var selectedNodes = treeObj.getSelectedNodes();

        for(var i=0;i<selectedNodes.length;i++){
            $("#parentName").val(selectedNodes[i].name);
            id+=selectedNodes[i].id;
        }
        if(id == "" || id==null){
            alert("请选择增加的类型目录");
            return false;
        }
        bootbox.confirm({
            title:"新增类型",
            size:"large",
            message:$("#addCatygoryDiv #addAreaForm"),
            buttons:{
                confirm:{
                    label:"确认",
                    className:"btn btn-success"
                },
                cancel:{
                    label:"取消",
                    className:"btn btn-danger"
                }
            },
            callback:function(result){
                if(result){
                    var name=$("#name").val();
                    $.ajax({
                        url:"<%=request.getContextPath()%>/category/addCategory",
                        type:"post",
                        data:{"id":id,"name":name},
                        dataType:"json",
                        error:function(){
                            alert("失败!");
                        },
                        success:function(result){
                            if(result.code==200){
                                alert("增加成功");
                               window.location.reload();
                            }
                        }
                    });
                }
                $("#addCatygoryDiv").html(catreHtml);
            }
        });
    }


    function deleteAreaIds() {
        var ztreeObj = $.fn.zTree.getZTreeObj("treeDemo");
        //获取权限树被选中的节点数组
        var selectedNodes = ztreeObj.getSelectedNodes();
        if(selectedNodes.length > 0) {
            //获取被选中的节点
            var selectedNode = selectedNodes[0];
            //判断被选中的节点是不是根节点，如果是根节点则不允许修改

            if (selectedNode.pid == 0) {
                alert("不允许删除根节点!");
            } else {
                bootbox.confirm({
                    title: "删除提示",
                    message: "您确认要删除吗？",
                    buttons: {
                        //設置確定按鈕的文字和樣式
                        confirm: {
                            label: "確認",
                            className: "btn btn-success"
                        },
                        //設置取消按鈕的文字和樣式
                        cancel: {
                            label: "取消",
                            className: "btn btn-danger"
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            //获取被选中的节点及其所有后代节点的数组
                            var nodeArr = ztreeObj.transformToArray(selectedNode);
                            var ids = [];
                            for (var i = 0; i < nodeArr.length; i++) {
                                ids.push(nodeArr[i].id);
                            }
                            //发起一个删除权限的ajax请求
                            $.ajax({
                                url: "<%=request.getContextPath()%>/category/deleteCateIds",
                                type: "post",
                                data: {"ids": ids},
                                dataType: "json",
                                success: function (result) {
                                    if (result.code == 200) {
                                        //调用ztree树的删除节点方法
                                        alert("删除成功")
                                        ztreeObj.removeNode(selectedNode);
                                    } else {
                                        alert("删除权限失败!");
                                    }
                                },
                                error: function () {
                                    alert("删除权限失败!");
                                }
                            });
                        }
                    }
                });
            }
        }
    }
</script>

