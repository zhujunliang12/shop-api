<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/7/14
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/static.jsp"/>
</head>
<body>
<div id="updateProductDiv">
    <input type="text" id="id" value="${porduct.productId}">
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

        <div style="margin-bottom:10px;text-align: center">
            <button type="button" onclick="checkProduct()" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span>确认
            </button>
        </div>

    </form>
</div>
</body>
</html>
<script>
    $(function () {
        initQueryBrand();
        getById();
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
    })
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
                        $("#selectId1").append("<option value='"+brand.brandId+"'>"+brand.name+"</option>")

                    }
                }
            },
            error:function(){
                alert("失败!");
            }
        });
    }

    function getById() {
        var id=$("#id").val();
        $.ajax({
            url:"<%=request.getContextPath()%>/product/getProductById",
            type:"get",
            data:{"productId":id},
            dataType:"json",
            error:function(){
                alert("失败!");
            },
            success:function(result) {
                //result = user        获取回显返回的对象  result代表返回的对象里面有属性
                if (result.code == 200) {
                    var result = result.data;
                    $("#updateProductForm #productId").val(result.productId);
                    $("#updateProductForm #productName1").val(result.productName);
                    $("#updateProductForm #stock1").val(result.stock);
                    $("#updateProductForm [name=status][value=" + result.status + "]").prop("checked", true);
                    $("#updateProductForm [name=isHot][value=" + result.isHot + "]").prop("checked", true);
                    var arr = $("#selectId1")[0].options;
                    for (var i = 0; i < arr.length; i++) {
                        if (result.brandId == arr[i].value) {
                            $("#updateProductForm #selectId1").val(arr[i].value);
                        }
                    }
                    $("#updateProductForm #price1").val(result.price);
                    $("#updateProductForm #title1").val(result.title);
                    $("#updateProductForm #avatarPath1").val(result.productImg);
                    $("#updateProductForm #avatarImg1").attr("src", result.productImg);
                }
            }
        });
    }

    function checkProduct() {
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
                    window.location.href="initProduct";
                }else{
                    alert(data.msg)
                }
            }
        });
    }
</script>
