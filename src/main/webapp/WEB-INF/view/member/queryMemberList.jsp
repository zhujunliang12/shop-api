<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/6/26
  Time: 0:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/static.jsp"/>
    <script>
        var memberTable;

        function search(){
            memberTable.ajax.reload();
        }
        <!--初始化-->
        $(function(){
            initmemberTable();
            // 	addPhoneDigalHtml=$("#addUserDiv").html(); //获取div元素赋值给全局变量
            /*	updatePhoneDigalHtml=$("#updateUserDiv").html();  */
          /*  initDatetimpicker("#minBirthday", 1)
          initDatetimpicker("#maxBirthday", 1)*/
         /* initDatetimpicker("#minCreateDate", 2)
          initDatetimpicker("#maxCreateDate", 2)*/
        });
        function initDatetimpicker(id,type){
            var format = type == 2 ? "yyyy-mm-dd":"yyyy-mm-dd hh:mm:ss";
            $(id).datetimepicker({
                format: 'yyyy-mm-dd',
                //选择语言
                language:"zh-CN",
                //自动关闭
                autoclose:true
            });
        }

        <!--分页和精确查询-->
        function initmemberTable() {
            memberTable = $("#memberTable").DataTable({
                serverSide:true,//开启服务端模式，想要从后台获取数据，必须要把这个属性的值设为true
                processing:true,//是否显示正在处理中状态
                language:chinese,
                searching:false,//是否显示搜索框
                ordering:false,//是否开启排序
                lengthMenu:[5,10,15],//设置每页显示条数下拉框中值
                ajax:{
                    url:"<%=request.getContextPath()%>/member/queryMemberPage",
                    type:"post",
                    data:function(param){
                        param.memberName=$("#queryForm #memberName").val();
                        param.realName=$("#queryForm #realName").val();
                        param.provinceId=$($("select[name=areaSelect]")[0]).val();
                        param.cityId=$($("select[name=areaSelect]")[1]).val();
                        param.countyId=$($("select[name=areaSelect]")[2]).val();
                       //   param.minCreateDate=$("#queryForm #minCreateDate").val();
                      //    alert(param.minCreateDate)
                      //   param.maxCreateDate=$("#queryForm #maxCreateDate").val();
                    }
                },
                columns:[
                    {

                        data:"id",

                        render:function(data){  //data相当于封装类的属性return 希望返回什么
                            return "<input type='checkbox' name='id' value='"+data+"' />";
                        }
                    },
                    {data:"memberName"},

                    {data:"realName"},
                    {data:"mail"},
                    {data:"phone"},
                    {
                        data:"birthday",
                        render:function(data){
                            return data;
                        }

                    },
                    {data:"areaName"},
                    {
                        data:"id",
                        //data= roleId,row 一行数据相当于一个role对象
                        render:function(data,type,member){
                            var buttonHTML ="";
                            buttonHTML += '<div class="btn-group btn-group-xs">';
                            buttonHTML += '<button type="button" onclick="showUpdateRoleDiv(' + data + ')" class="btn btn-primary">';
                            buttonHTML += '<span class="glyphicon glyphicon-pencil"></span>&nbsp;修改';
                            buttonHTML += '</button>';

                            buttonHTML += '<button type="button" onclick="deleteRole(' + data + ')" class="btn btn-danger">';
                            buttonHTML += '<span class="glyphicon glyphicon-trash"></span>&nbsp;删除';
                            buttonHTML += '</button>';
                            return buttonHTML;
                        }
                    }
                ]
            });
        }

    </script>
</head>
<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        条件查询
    </div>
    <div class="panel-body">
        <form id="queryForm" class="form-horizontal">
            <div class="container">
                    <!-- 一行 -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">会员名:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="memberName" name="memberName" placeholder="请输入会员名">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">真实名:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="realName" name="realName" placeholder="请输入真实名">
                                </div>
                            </div>
                        </div>
                    </div>
                <!-- 一行 -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">出生日期:</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minCreateDate" >
                                    <span class="input-group-addon">--</span>
                                    <input type="text" class="form-control" id="maxCreateDate">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12" >
                        <label class="col-sm-2 control-label">地区：</label>
                        <div class="form-group" id="areaDiv">
                            <div class="col-md-3" style="width: 200px" >

                            </div>

                        </div>
                    </div>
                </div>


                <div class="row">
                    <div style="padding-left:120px">
                        <button type="button" onclick="search()" class="btn btn-primary">
                            <span class="glyphicon glyphicon-search"></span>查询
                        </button>
                        &nbsp;
                        <button type="reset" class="btn btn-danger">
                            <span class="glyphicon glyphicon-refresh"></span>重置
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>


<div class="panel panel-primary">
    <div class="panel-heading">会员展示列表</div>
    <div class="panel-body">
        <div style="margin-bottom:10px">
            <button type="button" onclick="showAddUserDiv()" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span>新增
            </button>
            <button type="button" onclick="batchDelete()" class="btn btn-danger">
                <span class="glyphicon glyphicon-minus"></span>批量删除
            </button>

            <button type="button" onclick="importPdf()" class="btn btn-danger">
                <span class="glyphicon glyphicon-plus"></span>导出pdf
            </button>
            <button type="button" onclick="importWork()" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span>导出work
            </button>
            <button type="button" onclick="importExcel()" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span>导出excel
            </button>

        </div>
        <table id="memberTable" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>
                    <input onclick="checkAll()"  id="checkAll" type="checkbox" />
                </th>
                <th>会员名</th>
                <th>真实姓名</th>
                <th>邮箱</th>
                <th>手机号</th>
                <th>出生日期</th>
                <th>地区</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>


</body>
<script>
    $(function () {
        initArea(1);
    });

    function initArea(id,leave) {
        if(leave){
            $(leave).parent().nextAll().remove();
        }
        $.ajax({
            url:"/area",
            type:"get",
            data:{"id":id},
            dataType:"json",
            error:function(){
                alert("失败!");
            },
            success:function(result){
                if(result.code ==200){
                    var areaArr=result.data;
                    if(areaArr.length>0){
                        var areaHtml='<div class="col-md-3" style="width: 200px"><select class="form-control" name="areaSelect" onchange="initArea(this.value,this)"><option value="-1">请选择</option>';
                        for(let area of areaArr) {
                            areaHtml += '<option value="' + area.id + '" data-area-name="'+area.areaname+'">' + area.areaname + '</option>'
                        }
                        areaHtml+='</div></select>'
                        $("#areaDiv").append(areaHtml);
                    }
                }else{
                    alert(result.msg)
                }
            }
        });
    }
    
    
    function importPdf() {
        window.location.href = "<%=request.getContextPath()%>/member/exportPdf";
    }
    
    function importExcel() {
        var form=document.getElementById("queryForm");
        form.action="/member/exportExcel";
        form.method="post";
        form.submit();
    }


</script>
</html>
