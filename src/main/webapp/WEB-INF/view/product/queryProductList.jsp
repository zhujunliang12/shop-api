<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/6/30
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/static.jsp"/>
    <script>
        var productTable;

        function search(){
            productTable.ajax.reload();
        }
        <!--初始化-->
        $(function(){

            initproductTable();
            initQueryBrand();
            addPhoneDigalHtml=$("#addProductDiv").html(); //获取div元素赋值给全局变量
            updateStudentDigalHtml=$("#updateProductDiv").html();
            //初始化日期选择插件
            $("#queryForm #minCreateDate").datetimepicker({
                format:"yyyy-mm-dd",
                language:"zh-CN",
                minView:"decade",//设置只显示到月份
                autoclose:true
            });
              $("#queryForm #maxCreateDate").datetimepicker({
                format:"yyyy-mm-dd",
                language:"zh-CN",
                minView:"decade",//设置只显示到月份
                autoclose:true
            });
              $("#queryForm #minDate").datetimepicker({
                format:"yyyy-mm-dd",
                language:"zh-CN",
                minView:"decade",//设置只显示到月份
                autoclose:true
            });
              $("#queryForm #maxDate").datetimepicker({
                format:"yyyy-mm-dd",
                language:"zh-CN",
                minView:"decade",//设置只显示到月份
                autoclose:true
            });
        });



        <!--分页和精确查询-->
        function initproductTable() {
            productTable = $("#productTable").DataTable({
                serverSide:true,//开启服务端模式，想要从后台获取数据，必须要把这个属性的值设为true
                processing:true,//是否显示正在处理中状态
                language:chinese,
                searching:false,//是否显示搜索框
                ordering:false,//是否开启排序
                lengthMenu:[5,10,15],//设置每页显示条数下拉框中值
                ajax:{
                    url:"<%=request.getContextPath()%>/product/queryProductList",
                    type:"post",
                    data:function(param){
                        param.productName=$("#queryForm #productName").val();
                        param.status=$("#queryForm [name=status]:checked").val();
                        param.brandName=$("#queryForm #brandName").val();

                        param.isHot=$("#queryForm [name=isHot]:checked").val();
                        param.minDate=$("#queryForm #minDate").val();
                        param.maxDate=$("#queryForm #maxDate").val();
                        param.minCreateDate=$("#queryForm #minCreateDate").val();
                        param.maxCreateDate=$("#queryForm #maxCreateDate").val();

                    }
                },
                columns:[
                    {

                        data:"productId",

                        render:function(data){  //data相当于封装类的属性return 希望返回什么
                            return "<input type='checkbox' name='productId' value='"+data+"' />";
                        }
                    },
                    {data:"productName"},

                    {data:"title"},
                    {data:"status",
                        render:function(data){
                            return data==0?"下架":"上架";
                        },
                    },
                    {data:"isHot",
                        render:function(data){
                            return data==0?"冷场":"热销";
                        },
                    },
                    {data:"stock"},
                    {data:"price"},
                    {
                        data:"productImg",
                        render:function(data){
                            return '<img width="50" height="20" src=" '+data + ' "/>';
                        },
                    },
                    {data:"name"},
                    {
                        data:"createTime",
                        render:function(data){
                            return data;
                        }

                    },
                    {
                        data:"updateTime",
                        render:function(data){
                            return data;
                        }

                    },
                    {
                        data:"productId",
                        //data= roleId,row 一行数据相当于一个role对象
                        render:function(data,type,product){
                            var buttonHTML ="";
                            buttonHTML += '<div class="btn-group btn-group-xs">';
                            buttonHTML += '<button type="button" onclick="showUpdateRoleDiv(' + data + ')" class="btn btn-primary">';
                            buttonHTML += '<span class="glyphicon glyphicon-pencil"></span>&nbsp;修改';
                            buttonHTML += '</button>';
                            buttonHTML += '<div class="btn-group btn-group-xs">';
                            buttonHTML += '<button type="button" onclick="targerUpdateProduct(' + data + ')" class="btn btn-primary">';
                            buttonHTML += '<span class="glyphicon glyphicon-pencil"></span>&nbsp;跳转修改';
                            buttonHTML += '</button>';

                            buttonHTML += '<button type="button" onclick="deleteRole(' + data + ')" class="btn btn-danger">';
                            buttonHTML += '<span class="glyphicon glyphicon-trash"></span>&nbsp;删除';
                            buttonHTML += '</button>';

                            if(product.status == 1){
                                //启用状态
                                buttonHTML += '<button type="button" onclick="changeStatu(' + data + ','+product.status+')" class="btn btn-success">';
                                buttonHTML += '<span class="glyphicon glyphicon-arrow-down"></span>&nbsp;下架';
                                buttonHTML += '</button>';
                            }else{
                                buttonHTML += '<button type="button" onclick="changeStatu(' + data + ','+product.status+')" class="btn btn-warning">';
                                buttonHTML += '<span class="glyphicon glyphicon-arrow-up"></span>&nbsp;上架';
                                buttonHTML += '</button>';
                            }
                            if(product.isHot == 1){
                                //禁用状态
                                buttonHTML += '<button type="button" onclick="changeHotStatu(' + data + ','+product.isHot+')" class="btn btn-default">';
                                buttonHTML += '<span class="glyphicon glyphicon-ok-circle"></span>&nbsp;非热销';
                                buttonHTML += '</button>';
                            }else{
                                buttonHTML += '<button type="button" onclick="changeHotStatu(' + data + ','+product.isHot+')" class="btn btn-info">';
                                buttonHTML += '<span class="glyphicon glyphicon-ban-circle"></span>&nbsp;热销';
                                buttonHTML += '</button>';

                            }
                            return buttonHTML;
                        }
                    }
                ]
            });
        }
    </script>
    <script>

        function changeStatu(id,leave) {
            if(confirm(leave == 1?"你确定要下架吗？":"你确定要上架吗？")){
                $.ajax({
                    url:"<%=request.getContextPath()%>/product/changeStatu",
                    type:"post",
                    data:{"productId":id},
                    dataType:"json",
                    error:function(){
                        alert("失败!");
                    },
                    success:function(result){
                        if(result.code==200){
                            window.location.reload();
                        }
                    }

                });
            }
        }
        function changeHotStatu(id,leave) {
                $.ajax({
                    url:"<%=request.getContextPath()%>/product/changeHotStatu",
                    type:"post",
                    data:{"productId":id},
                    dataType:"json",
                    error:function(){
                        alert("失败!");
                    },
                    success:function(result){
                        if(result.code==200){
                            window.location.reload();
                        }
                    }

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
                            <label class="col-sm-2 control-label">产品名:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="productName" name="productName" placeholder="请输入产品名">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-8">
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="0"/>下架
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="1"/>上架
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 一行 -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">创建日期:</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minCreateDate" name="minCreateDate">
                                    <span class="input-group-addon">--</span>
                                    <input type="text" class="form-control" id="maxCreateDate" name="maxCreateDate">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">产品日期:</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minDate" name="minDate">
                                    <span class="input-group-addon">--</span>
                                    <input type="text" class="form-control" id="maxDate" name="maxDate">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 一行 -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">品牌名:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="brandName" name="brandName" placeholder="请输入品牌名">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">是否热销</label>
                            <div class="col-sm-8">
                                <label class="radio-inline">
                                    <input type="radio" name="isHot" value="0"/>非热销
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="isHot" value="1"/>热销
                                </label>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row" style="text-align: center">
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
    <div class="panel-heading">商品展示列表</div>
    <div class="panel-body">
        <div style="margin-bottom:10px">
            <button type="button" onclick="showAddProductDiv()" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span>新增
            </button>
            <button type="button" onclick="batchDelete()" class="btn btn-danger">
                <span class="glyphicon glyphicon-minus"></span>批量删除
            </button>
            <button type="button" onclick="clearCatch()" class="btn btn-danger">
                <span class="glyphicon glyphicon-minus"></span>刷新缓存
            </button>
            <button type="button" onclick="exprotPdf()" class="btn btn-danger">
                <span class="glyphicon glyphicon-minus"></span>导出pdf
            </button>
            <button type="button" onclick="exprotWork()" class="btn btn-danger">
                <span class="glyphicon glyphicon-minus"></span>导出work
            </button>
            <button type="button" onclick="downFiler()" class="btn btn-info">
                <span class="glyphicon glyphicon-minus"></span>下载
            </button>
            <button type="button" onclick="uplaodZipFilter()" class="btn btn-info">
                <span class="glyphicon glyphicon-minus"></span>上传zip
            </button>
            <form id="form" action="/upload/uplaodExcelFilter" enctype="multipart/form-data" method="post">
               <input type="file" id="excelFilter" name="excelFilter">
            </form>
            <button type="button" onclick="uplaodExcelFilter()" class="btn btn-info">
                <span class="glyphicon glyphicon-minus"></span>上传excel
            </button>
        </div>
        <table id="productTable" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>
                    <input onclick="checkAll()"  id="checkAll" type="checkbox" />
                </th>
                <th>商品名</th>
                <th>标题</th>
                <th>状态</th>
                <th>是否热销</th>
                <th>库存</th>
                <th>价格</th>
                <th>图像</th>
                <th>品牌名</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>


    <input type="file" id="zipFilter" name="zipFilter">


<div id="updateProductDiv" style="display:none">

    <form id="updateProductForm" class="form-horizontal" style="">
        <input type="text" id="productId">
        <input type="text" id="newAvatarPath1">
        <div class="form-group">
            <label  class="col-sm-2 control-label">商品名</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="productName1" />
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">标题</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="title1" />
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">库存</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="stock1" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">价格</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="price1" />
            </div>
        </div>


        <div class="form-group">
            <label  class="col-sm-2 control-label">是否上架</label>
            <div class="col-sm-8">
                <label class="radio-inline">
                    <input type="radio" name="status" value="0"/>下架
                </label>
                <label class="radio-inline">
                    <input type="radio" name="status" value="1"/>上架
                </label>
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">是否热销</label>
            <div class="col-sm-8">
                <label class="radio-inline">
                    <input type="radio" name="isHot" value="0"/>非热销
                </label>
                <label class="radio-inline">
                    <input type="radio" name="isHot" value="1"/>热销
                </label>
            </div>
        </div>

        <!-- 头像图片相关 -->
        <div class="form-group">
            <label  class="col-sm-2 control-label">头像</label>
            <div class="col-sm-8">
                <!-- 用于存放图片上传成功之后的相对路径的隐藏域 -->
                <input type="text" id="avatarPath1"/>
                <!-- 用于回显图片 -->
                <img src="" id="avatarImg1" width="100" height="50"/>
                <input type="file" class="form-control" name="avatar" id="avatar1" />
            </div>
        </div>

        <!-- select选择框相关 -->
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌：</label>
            <div class="col-md-6">
                <select class="form-control" id="selectId1">
                    <option value="-1">请选择</option>

                </select>
            </div>
        </div>

    </form>
</div>
</body>
</html>
<script>
    
    function exprotPdf() {
            var form=document.getElementById("queryForm");
            form.action="/product/exprotPdf";
            form.method="post";
            form.submit();
    }
    function exprotWork() {
            var form=document.getElementById("queryForm");
            form.action="/product/exprotWork";
            form.method="post";
            form.submit();
    }

    function initQueryBrand() {
        $.ajax({
            url:"<%=request.getContextPath()%>/brand",
            type:"get",
            data:{},    //发送请求的参数
            dataType:"json",
            success:function(result){
                if(result.code==200){
                    var brandArr=result.data;
                    for(let brand of brandArr){
                        $("#selectId").append("<option value='"+brand.brandId+"'>"+brand.name+"</option>")
                    }
                }
            },
            error:function(){
                alert("失败!");
            }
        });
    }

    function showAddProductDiv() {
        window.location.href="/product/initAddProduct";
    }

    function showUpdateRoleDiv(id) {
        $("#updateProductForm #avatar1").fileinput({
            language:"zh",
            allowedFileTypes:["image"],
            maxFileCount:1,
            //设置上传文件的地址
          //  uploadUrl:"<%=request.getContextPath()%>/upload/fileUploadOss"
            uploadUrl:"<%=request.getContextPath()%>/upload/ossUplaodFilter"
        });

        //用于文件上传结果处理的回调函数，每上传一个文件就会调用一下这个函数
        $("#updateProductForm #avatar1").on("fileuploaded",function(event,data,preView,index){
            //获取服务器返回的数据
            var result = data.response;
            if(result.code == 200){
                $("#updateProductForm #newAvatarPath1").val(result.data);
                $("#updateProductForm #avatarImg1").attr("src",result.data);

            }else{
                alert("文件上传过程中出现异常，请联系管理员！");
            }
        });
        $.ajax({
            url:"<%=request.getContextPath()%>/brand",
            type:"get",
            data:{},    //发送请求的参数
            dataType:"json",
            success:function(result){
                if(result.code==200){
                    var brandArr=result.data;
                    for(let brand of brandArr){
                        $("#selectId1").append("<option value='"+brand.brandId+"'>"+brand.name+"</option>")
                    }
                }
            },
            error:function(){
                alert("失败!");
            }
        });
        $.ajax({
            url:"<%=request.getContextPath()%>/product/getProductById",
            type:"get",
            data:{"productId":id},
            dataType:"json",
            error:function(){
                alert("失败!");
            },
            success:function(result){
                //result = user        获取回显返回的对象  result代表返回的对象里面有属性
                if(result.code ==200){
                    var result=result.data;
                    $("#updateProductForm #productId").val(result.productId);
                    $("#updateProductForm #productName1").val(result.productName);
                    $("#updateProductForm #stock1").val(result.stock);
                    $("#updateProductForm [name=status][value="+result.status+"]").prop("checked",true);
                    $("#updateProductForm [name=isHot][value="+result.isHot+"]").prop("checked",true);
                    var arr=$("#selectId1")[0].options;
                    for(var i=0;i<arr.length;i++){
                        if(result.brandId==arr[i].value){
                          //  $("#updateProductForm #selectId1").val(arr[i].value);
                            arr[i].selected=true;
                        }
                    }
                    $("#updateProductForm #price1").val(result.price);
                    $("#updateProductForm #title1").val(result.title);
                    $("#updateProductForm #avatarPath1").val(result.productImg);
                    $("#updateProductForm #avatarImg1").attr("src",result.productImg);
                }
                bootbox.confirm({
                    title:"修改用户信息",
                    size:"large",
                    message:$("#updateProductDiv form"),
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
                            //获取修改后的表单信息
                            var param={};              //通过id选择器获取 获取文本的值  赋值给参数
                            param.productName = $("#updateProductForm #productName1").val();
                            param.productId = $("#updateProductForm #productId").val();
                            param.title = $("#updateProductForm #title1").val();
                            param.stock = $("#updateProductForm #stock1").val();
                            param.price = $("#updateProductForm #price1").val();
                            param.status = $("#updateProductForm [name=status]:checked").val();
                            param.isHot = $("#updateProductForm [name=isHot]:checked").val();
                            param.productImg = $("#updateProductForm #avatarPath1").val();
                            param.brandId = $("#updateProductForm #selectId1").val();
                            param.newProductImg = $("#updateProductForm #newAvatarPath1").val();
                            if(result){
                                //发起ajax修改用户
                                $.ajax({
                                    url:"<%=request.getContextPath()%>/product/updateProduct",
                                    type:"post",
                                    data:param,
                                    dataType:"json",
                                    error:function(){
                                        alert("失败!");
                                    },
                                    success:function(data){
                                        //data = addUser接口的返回值
                                        if(data.code == 200){
                                            alert("修改成功")
                                            //刷新dataTables数据
                                            productTable.ajax.reload()
                                        }else{
                                            alert(data.msg)
                                        }
                                    }

                                });
                            }

                        }
                                $("#updateProductDiv").html(updateStudentDigalHtml);
                    }
                });

            }

        });
    }

    function targerUpdateProduct(id) {
        window.location.href="initUpdateProduct?productId="+id;
    }

    //刷新缓存
    function clearCatch() {
        $.ajax({
            url:'clearCatch',
            type:'get',
            success:function (result) {
                if(result.code==200){
                    alert("刷新成功");
                }
            }
        })
    }

    //下载
    function downFiler() {
        var boxArr=$("input[name=productId]");
        var count=0;
        var id=-1;
       for(var i=0;i<boxArr.length;i++){
           if(boxArr[i].checked){
               count++;
               id=boxArr[i].value;
           }
       }
       if(count==0){
           alert("请选择一个");
           return false;
       }
       if(count>=2){
           alert("只能选择一个");
           return false;
       }
       $.ajax({
           url:'downFiler',
           type:'post',
           data:{"productId":id},
           success:function (result) {
               if(result.code==200){
                   alert("下载成功");
               }
           }
       })
    }

    
    function uplaodZipFilter() {
        var filter=$("#zipFilter").val();
        var zhui = filter.substring(filter.lastIndexOf("."));
        if(zhui != ".zip"){
            alert("请选择正确的格式");
            return false;
        }

        $("#zipFilter").fileinput({
            language:"zh",
            maxFileCount:1,
            //设置上传文件的地址
            uploadUrl:"<%=request.getContextPath()%>/upload/uplaodZipFilter"
        });

        //用于文件上传结果处理的回调函数，每上传一个文件就会调用一下这个函数
        $("#zipFilter").on("fileuploaded",function(event,data,preView,index){
            //获取服务器返回的数据
            var result = data.response;
            if(result.code == 200){
                alert("zip文件上传成功")
              //  $("#updateProductForm #newAvatarPath1").val(result.data);
              //  $("#updateProductForm #avatarImg1").attr("src",result.data);

            }else{
                alert("文件上传过程中出现异常，请联系管理员！");
            }
        });
    }

    function uplaodExcelFilter() {
        var excelFilter=$("#excelFilter").val();
        var zhui = excelFilter.substring(excelFilter.lastIndexOf("."));
        if(zhui != ".xls" && zhui !='.xlsx'){
            alert("请选择正确的格式");
            return false;
        }
        $("#form").submit();

    }
</script>
