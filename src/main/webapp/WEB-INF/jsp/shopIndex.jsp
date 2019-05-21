<%@ page import="java.util.List" %>
<%@ page import="edu.nju.yummy.model.Dish" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
<%--<%@ taglib prefix="mytag" uri="http://mycompany.com" %>--%>
<%--<mytag:checkShopSession/>--%>

<html>
<head>
    <meta charset="utf-8">
    <!-- Bootstrap-->
    <%--<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
    <%--<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>--%>
    <%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>

    <base href='<%=basePath %>' />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <%--日期选择--%>
    <link rel="stylesheet" href="css/bootstrap-datepicker.min.css"/>
    <script src="js/bootstrap-datepicker.min.js"></script>
    <script src="js/bootstrap-datepicker.zh-CN.min.js"></script>

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/shopIndex.css">

    <tit--le>Yummy!-商户</tit--le>

    <%--<link rel="stylesheet" href="css/login.css">--%>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Yummy!</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar">
            <ul class="nav navbar-nav">
                <li><a href="shop/index">主页</a></li>
                <li><a href="shop/info">商户信息</a></li>
                <li><a href="shop/statistics">统计信息</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a><% out.print(session.getAttribute("id")); %></a></li>
                <li><a href="shop/logout">登出</a></li>

            </ul>
        </div>
    </div>
</nav>

<div class="container" style="padding-top: 60px;">
    <%--<label><% out.print(session.getAttribute("id")); %></label>--%>
    <%--<a class="btn btn-primary" href="shop/logout">登出</a>--%>
    <%--<a class="btn btn-default" href="shop/info">商户信息</a>--%>
    <%--<a class="btn btn-default" href="shop/statistics">统计信息</a>--%>

    <jsp:useBean id="dishListDto" type="edu.nju.yummy.dto.DishListDto" scope="request"/>
    <%
        List<String> typeList = dishListDto.getTypeList();
    %>

    <div class="">
        <row>
            <h3>店内商品</h3>
            <jsp:useBean id="modifyInfoState" type="java.lang.Integer" scope="request"/>
            <%
                if(modifyInfoState == 1){
            %>
            <button class="btn btn-default" data-toggle="modal" data-target="#addDishModal">新增商品</button>
            <button class="btn btn-default" data-toggle="modal" data-target="#addTypeModal">新增分类</button>
            <button class="btn btn-default" data-toggle="modal" data-target="#addPackageModal">新增套餐</button>
            <%
                } else {
            %>
            <button class="btn btn-default" disabled>新增商品</button>
            <button class="btn btn-default" disabled>新增分类</button>
            <small>* 修改信息的申请尚未通过</small>
            <%
                }
            %>
        </row>
        <ul class="nav nav-tabs">
            <li role="presentation" <% if(dishListDto.getTypeToShow() == null) out.print("class=\"active\""); %>><a href="shop/index">全部</a></li>
            <%
                for(String type : typeList){
            %>
            <li role="presentation" <% if(type.equals(dishListDto.getTypeToShow()))  out.print("class=\"active\""); %>><a href="<% out.print("shop/index?type="+type); %>"><% out.print(type); %></a></li>
            <%
                }
            %>
        </ul>
        <%--新增商品Modal--%>
        <div class="modal fade" id="addDishModal" tabindex="-1" role="dialog" aria-labelledby="addDishModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="addDishModalLabel">新增商品</h4>
                    </div>
                    <form class="form" method="post" action="shop/addDish">
                        <div class="modal-body">
                            <div class="form-group">
                                <label class="control-label" for="name_addDish">名称</label>
                                <input class="form-control" id="name_addDish" name="name" required>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="type_addDish">类型</label>
                                <select class="form-control" id="type_addDish" name="type" required>
                                    <%
                                        for (String type : typeList){
                                            if(type.equals("套餐")){
                                                continue;
                                            }
                                            out.println("<option>"+type+"</option>");
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="price_addDish">单价</label>
                                <input class="form-control" type="number" step="0.1" id="price_addDish" name="price" required>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="brief_addDish">简介</label>
                                <textarea id="brief_addDish" name="brief" class="form-control" rows="2" placeholder="brief"></textarea>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="num_addDish">数量</label>
                                <input class="form-control" type="number" id="num_addDish" name="num" required>
                            </div>
                            <div class="form-group checkbox">
                                <label>
                                    <input type="checkbox" class="limited">限时商品?
                                </label>
                                <input type="number" class="isLimitedChecked" name="isLimited" value="0" hidden>
                            </div>
                            <div class="form-group date_container" style="display:none;">
                                <div class="input-daterange input-group datepicker">
                                    <input type="text" class="input-sm form-control" name="startTime" placeholder="开始时间"/>
                                    <span class="input-group-addon">至</span>
                                    <input type="text" class="input-sm form-control" name="endTime" placeholder="结束时间"/>
                                </div>
                            </div>
                            <div class="form-group checkbox">
                                <label>
                                    <input type="checkbox" class="discount">折扣商品?
                                </label>
                                <input type="number" class="isDiscountChecked" name="isDiscount" value="0" hidden>
                            </div>
                            <div class="form-group discount_container" style="display:none;">
                                <label class="control-label" for="discount_addDish">折扣价</label>
                                <input class="form-control" type="number" step="0.1" id="discount_addDish" name="discount">
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确定</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%--新增分类Modal--%>
        <div class="modal fade" id="addTypeModal" tabindex="-1" role="dialog" aria-labelledby="addTypeModalLabel">
            <div class="modal-dialog  modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="addTypeModalLabel">新增分类</h4>
                    </div>
                    <form class="form" method="post" action="shop/addType">
                        <div class="modal-body">
                            <input name="shopid" value="<% out.print((String)session.getAttribute("id")); %>" hidden>
                            <div class="form-group">
                                <label class="control-label" for="name_addType">分类名称</label>
                                <input class="form-control" id="name_addType" name="typeName" required>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确定</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%--新增套餐Modal--%>
        <div class="modal fade" id="addPackageModal" tabindex="-1" role="dialog" aria-labelledby="addPackageModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="addPackageModalLabel">新增套餐</h4>
                    </div>
                    <form class="form" method="post" action="shop/addDish">
                        <div class="modal-body">
                            <div class="form-group">
                                <label class="control-label" for="name_addPackage">名称</label>
                                <input class="form-control" id="name_addPackage" name="name" required>
                            </div>
                            <div class="form-group">
                                <%--<label class="control-label" for="type_addPackage">类型</label>--%>
                                <%--<input class="form-control" id="type_addPackage" name="type" value="套餐" hidden required>--%>
                                <input name="type" value="套餐" hidden>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="price_addPackage">单价</label>
                                <input class="form-control" type="number" step="0.1" id="price_addPackage" name="price" required>
                            </div>
                            <%--<div class="form-group">--%>
                                <%--<label class="control-label" for="brief_addPackage">简介</label>--%>
                                <%--<textarea id="brief_addPackage" name="brief" class="form-control" rows="2" placeholder="brief"></textarea>--%>
                            <%--</div>--%>
                            <input name="brief" id="packageBrief" hidden>
                            <div class="form-group">
                                <label class="control-label" for="num_addPackage">数量</label>
                                <input class="form-control" type="number" id="num_addPackage" name="num" required>
                            </div>
                            <div class="form-group">
                                <label class="control-label">添加套餐商品</label>
                                <select id="selectPackageDish">
                                    <option value="">请选择商品</option>
                                    <%--<option value="米饭">1</option>--%>
                                    <%--<option value="青菜">2</option>--%>
                                    <%
                                        List<String> dishList_str = (List<String>) request.getAttribute("dishList_str");
                                        for(String dish_str : dishList_str){
                                    %>
                                    <option value="<% out.print(dish_str); %>"><% out.print(dish_str); %></option>
                                    <%
                                        }
                                    %>
                                </select>
                                <input id="packageDishNum" type="number" value="1">
                                <button type="button" onclick="addPackageDish()">+</button>
                                <div id="packageContent"></div>
                            </div>
                            <div class="form-group checkbox">
                                <label>
                                    <input type="checkbox" class="limited">限时商品?
                                </label>
                                <input type="number" class="isLimitedChecked" name="isLimited" value="0" hidden>
                            </div>
                            <div class="form-group date_container" style="display:none;">
                                <div class="input-daterange input-group datepicker">
                                    <input type="text" class="input-sm form-control" name="startTime" placeholder="开始时间"/>
                                    <span class="input-group-addon">至</span>
                                    <input type="text" class="input-sm form-control" name="endTime" placeholder="结束时间"/>
                                </div>
                            </div>
                            <div class="form-group checkbox">
                                <label>
                                    <input type="checkbox" class="discount">折扣商品?
                                </label>
                                <input type="number" class="isDiscountChecked" name="isDiscount" value="0" hidden>
                            </div>
                            <div class="form-group discount_container" style="display:none;">
                                <label class="control-label" for="discount_addPackage">折扣价</label>
                                <input class="form-control" type="number" step="0.1" id="discount_addPackage" name="discount">
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary" id="btn_addPackage" disabled>确定</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%--商品列表--%>
        <div class="dish_container">
            <%
                List<Dish> dishList = dishListDto.getDishList();
                for (int i = 0; dishList != null && i < dishList.size(); i++){
                    Dish dish = dishList.get(i);
            %>
            <div class="tab_dish">
                <div class="img_container pull-left">
                    <a class="thumbnail pull-left">
                        <img src="image/yummy.jpg" alt="">
                    </a>
                </div>
                <div class="caption col-md-3">
                    <label><% out.print(dish.getName()); %></label>
                    <span class="pull-right"><small><% out.print(dish.getType()); %></small></span>
                    <p class="brief"><% out.print(dish.getBrief()); %></p>
                    <p class="time" style="color: red;">
                        <%
                            if (dish.getIslimited() == 1){
                                out.print(dish.getStarttime().toString()+" 至 "+dish.getEndtime());
                            }
                        %>
                    </p>
                    <label>数量: </label><span><% out.print(dish.getNum()); %></span>
                    <%
                        if(dish.getActual() != -1){
                    %>
                    <small><del>￥<% out.print(dish.getPrice()); %></del></small><label> ￥<% out.print(dish.getActual()); %></label>
                    <%
                    } else {
                    %>
                    <label>￥<% out.print(dish.getPrice()); %></label>
                    <%
                        }
                    %>
                    <button class="btn btn-sm btn-default pull-right" data-toggle="modal" data-target="#modifyDishModal_<% out.print(dish.getDishid()); %>">修改</button>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="modifyDishModal_<% out.print(dish.getDishid()); %>" tabindex="-1" role="dialog" aria-labelledby="modifyDishModalLabel_<% out.print(dish.getDishid()); %>">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modifyDishModalLabel_<% out.print(dish.getDishid()); %>">修改商品信息</h4>
                        </div>
                        <form class="form" method="post" action="shop/modifyDish">
                            <div class="modal-body">
                                <%--商品id--%>
                                <input name="dishid" value="<% out.print(dish.getDishid()); %>" hidden>
                                <div class="form-group">
                                    <label class="control-label" for="name_<% out.print(dish.getDishid()); %>">名称</label>
                                    <input class="form-control" id="name_<% out.print(dish.getDishid()); %>" name="name" value="<% out.print(dish.getName()); %>" required>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="type_<% out.print(dish.getDishid()); %>">类型</label>
                                    <select class="form-control" id="type_<% out.print(dish.getDishid()); %>" name="type" required>
                                        <%
                                            for (String type : typeList){
                                                if(type.equals(dish.getType())){
                                                    out.println("<option selected>"+type+"</option>");
                                                }
                                                else{
                                                    out.println("<option>"+type+"</option>");
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                        <label class="control-label" for="price_<% out.print(dish.getDishid()); %>">单价</label>
                                        <input class="form-control" type="number" step="0.1" id="price_<% out.print(dish.getDishid()); %>" name="price" value="<% out.print(dish.getPrice()); %>" required>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="brief_<% out.print(dish.getDishid()); %>">简介</label>
                                    <textarea id="brief_<% out.print(dish.getDishid()); %>" name="brief" class="form-control" rows="2" placeholder="brief"><% out.print(dish.getBrief()); %></textarea>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="num_<% out.print(dish.getDishid()); %>">数量</label>
                                    <input class="form-control" type="number" id="num_<% out.print(dish.getDishid()); %>" name="num" value="<% out.print(dish.getNum()); %>" required>
                                </div>
                                <div class="form-group checkbox">
                                    <label>
                                        <input type="checkbox" class="limited" <% if(dish.getIslimited() == 1) out.print("checked"); %>>限时商品?
                                    </label>
                                    <input type="number" class="isLimitedChecked" name="isLimited" value="<% out.print(dish.getIslimited()); %>" hidden>
                                </div>
                                <div class="form-group date_container" <% if(dish.getIslimited() == 0) out.print("style=\" display:none; \""); %>>
                                    <div class="input-daterange input-group datepicker">
                                        <input type="text" class="input-sm form-control" name="startTime" placeholder="开始时间" value="<% if(dish.getIslimited() == 1) out.print(dish.getStarttime()); %>"/>
                                        <span class="input-group-addon">至</span>
                                        <input type="text" class="input-sm form-control" name="endTime" placeholder="结束时间" value="<% if(dish.getIslimited() == 1) out.print(dish.getEndtime()); %>"/>
                                    </div>
                                </div>
                                <div class="form-group checkbox">
                                    <label>
                                        <input type="checkbox" class="discount" <% if(dish.getActual() != -1) out.print("checked"); %>>折扣商品?
                                    </label>
                                    <input type="number" class="isDiscountChecked" name="isDiscount" value="<% out.print(dish.getActual() == -1 ? 0 : 1); %>" hidden>
                                </div>
                                <div class="form-group discount_container" <% if(dish.getActual() == -1) out.print("style=\" display:none; \""); %>>
                                    <label class="control-label" for="discount_<% out.print(dish.getDishid()); %>">折扣价</label>
                                    <input class="form-control" type="number" step="0.1" id="discount_<% out.print(dish.getDishid()); %>" name="discount" <% if(dish.getActual() != -1) out.print("value=\""+dish.getActual()+"\""); %>>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <button type="submit" class="btn btn-primary">确定</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>

</div>

<script src="js/shopIndex.js"></script>

</body>
</html>
