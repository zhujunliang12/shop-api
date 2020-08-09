<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/7/14
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/static.jsp"/>
</head>
<body>
<div id="addProductDiv">

    <form id="addProductForm" class="form-horizontal" style="">

        <div class="form-group">
            <label  class="col-sm-2 control-label">商品名</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="productName" />
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">标题</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="title" />
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">库存</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="stock" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">价格</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="price" />
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
                <input type="text" id="avatarPath"/>
                <!-- 用于回显图片 -->
                <img src="" id="avatarImg" width="100" height="50"/>
                <input type="file" class="form-control" name="avatar" id="avatar" />
            </div>
        </div>

        <!-- select选择框相关 -->
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌：</label>
            <div class="col-md-6">
                <select class="form-control" id="selectId">
                    <option value="-1">请选择</option>

                </select>
            </div>
        </div>


        <div style="margin-bottom:10px;text-align: center">
            <button type="button" onclick="addProduct()" class="btn btn-primary">
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
        $("#addProductForm #avatar").fileinput({
            language:"zh",
            allowedFileTypes:["image"],
            maxFileCount:1,
            //设置上传文件的地址
            // uploadUrl:"<%=request.getContextPath()%>/upload/fileUploadOss"
            uploadUrl:"<%=request.getContextPath()%>/upload/ossUplaodFilter"
        });

        //用于文件上传结果处理的回调函数，每上传一个文件就会调用一下这个函数
        $("#addProductForm #avatar").on("fileuploaded",function(event,data,preView,index){
            //获取服务器返回的数据
            var result = data.response;
            if(result.code == 200){
                alert(result.data);
                $("#addProductForm #avatarPath").val(result.data);
                $("#addProductForm #avatarImg").attr("src",result.data);

            }else{
                alert("文件上传过程中出现异常，请联系管理员！");
            }
        });

    });

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

    function addProduct() {
        var param={};              //通过id选择器获取 获取文本的值  赋值给参数
        param.productName = $("#addProductForm #productName").val();
        param.title = $("#addProductForm #title").val();
        param.stock = $("#addProductForm #stock").val();
        param.price = $("#addProductForm #price").val();
        param.status = $("#addProductForm [name=status]:checked").val();
        param.isHot = $("#addProductForm [name=isHot]:checked").val();
        param.productImg = $("#addProductForm #avatarPath").val();
        param.brandId = $("#addProductForm #selectId").val();
        $.ajax({
            url:"<%=request.getContextPath()%>/product/addProduct",
            type:"post",
            data:param,    //发送请求的参数
            dataType:"json",
            success:function(result){
                if(result.code==200){
                    //刷新dataTables数据
                    alert("增加成功");
                    window.location.href="initProduct";
                }
            },
            error:function(){
                alert("失败!");
            }
        });
    }


</script>
