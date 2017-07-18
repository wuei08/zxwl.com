$(document).ready(function () {
    var inited = false;
    var area_list = [];
    var is_add = true;
    var shop_id = null;

    var initAreaTree = function () {
        Request.get("area?paging=false&sorts[0].name=sortIndex", function (e) {
            area_list = e;
            console.log(e);
            var tree = areaTree.init();

            var rootNodes = tree.getRootNodes(e);
            $('#area_tree').treeview({
                data: rootNodes
                // onNodeSelected: function (event, data) {
                //     alert(data.text + "->  parentId :" + data.parentId);
                // }
            });
            $('#area_tree').treeview('selectNode', [0]);
        });
    };

    initAreaTree();

    var areaTree = {
        init: function () {
            if (inited) return this;
            if (jQuery === undefined) {
                console.error("Required jQuery support is not available");
            } else {
                inited = true;
                var that = this;
                $(function () {

                });
            }
            return this;
        },
        load: function () {

        },
        reload: function () {

        },
        getRootNodes: function (data) {
            var that = this;
            var result = [];
            $.each(data, function (index, item) {
                if (item['parentId'] == '-1') {
                    var obj = {
                        id: item.id,
                        cid: index,
                        // level: 0,
                        parentId: item.parentId,
                        text: item.areaName,
                        nodes: []
                    };
                    // console.log(item.areaName);
                    // console.log(obj);
                    obj.nodes = that.getChildNodes(data, item);
                    result.push(obj);
                }
            });
            return result;
        },
        getChildNodes: function (data, parentNode) {
            var that = this;
            var result = [];
            $.each(data, function (i, item) {
                if (item['parentId'] == parentNode['id']) {
                    var obj = {
                        id: item.id,
                        cid: i,
                        // level: parentNode[level]+1,
                        parentId: item.parentId,
                        text: item.areaName,
                        nodes: null
                    };
                    result.push(obj);
                    var childNodes = that.getChildNodes(data, item);
                    if (childNodes != null && childNodes.length > 0) {
                        obj.nodes = childNodes;
                    }
                }
            });
            return result;
        }
    };

    var shop_list = $('#shop_list').DataTable({
        "language": lang,
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "bStateSave": true,
        "bFilter": true, //搜索栏
        "serverSide": true,
        "sPaginationType": "full_numbers",
        "ajax": function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;
            param.pageIndex = data.start;
            param.page = (data.start / data.length) + 1;
            $.ajax({
                url: BASE_PATH + "shop",
                type: "GET",
                cache: false,
                data: param,
                dataType: "json",
                success: function (result) {

                    console.log(result);

                    var resultData = {};
                    resultData.draw = data.draw;
                    resultData.recordsTotal = result.total;
                    resultData.recordsFiltered = result.total;
                    resultData.data = result.data;
                    callback(resultData);
                },
                error: function (jqXhr) {
                    toastr.warning("请求列表数据失败, 请重试");
                }
            });
        },
        columns: [
            // {"data": "num"},
            {"data": "id"},
            {"data": "shopName"},
            {"data": "principal"},
            {"data": "principalTel"},
            {"data": "legalName"},
            {"data": "businessUrl"},
            {"data": "address"},
            {"data": "img1"},
            {"data": "logo"},
            {"data": "content"},
            {"data": null}
        ],

        "aoColumnDefs": [
            {
                "sClass": "center",
                "aTargets": [10],
                "mData": "id",
                "mRender": function (a, b, c, d) {//a表示statCleanRevampId对应的值，c表示当前记录行对象
                    // 修改 删除 权限判断
                    var buttons = '';
                    buttons += '<button type="button" data-id="' + c.id + '" class="btn btn-default btn-sm btn-info">详情</button>\n';
                    buttons += '<button type="button" data-id="' + c.id + '" data-name="' + c.shopName + '" class="btn btn-default btn-sm btn-edit">编辑</button>';
                    buttons += '<button type="button" data-id="' + c.id + '" data-name="' + c.shopName + '" class="btn btn-default btn-sm btn-delete">删除</button>';

                    return buttons;
                }
            }
        ]

    });
    shop_list.column(0).visible(false);
    shop_list.column(8).visible(false);
    shop_list.column(9).visible(false);
    shop_list.columns().draw();

    var switchButtonInit = function () {
        // size : null, 'mini', 'small', 'normal', 'large'
        $("input[type='checkbox']").bootstrapSwitch({
            size: 'mini'
        });
    };


    $("form#shop_form").validate({
        rules: {
            shop_name: {required: true},
            logo: {required: true},
            principal: {required: true},
            principal_tel: {required: true},
            legal_name: {required: true},
            bussiness_url: {required: true},
            address: {required: true},
            img1: {required: true},
            remark: {required: true}
        },
        messages: {
            shop_name: {required: "店铺名不能为空."},
            logo: {required: "Logo不能为空."},
            principal: {required: "负责人不能为空."},
            principal_tel: {required: "负责人电话不能为空."},
            legal_name: {required: "法人不能为空."},
            bussiness_url: {required: "营业执照不能为空."},
            address: {required: "详细地址不能为空."},
            img1: {required: "店铺图片不能为空."},
            remark: {required: "图文信息不能为空."}
        },
        submitHandler: function (form) {
            var btn = $('button[type="submit"]');
            btn.attr('disabled', "true");
            btn.html("保存中..请稍后");
            var selected = $('#area_tree').treeview('getSelected');
            //在选中节点的子节点添加节点

            if (is_add) {     //添加
                var params = {
                    shopName: $(form).find("#shop_name").val(),
                    logo: $(form).find("#logo").val(),
                    principal: $(form).find("#principal").val(),
                    principalTel: $(form).find("#principal_tel").val(),
                    legalName: $(form).find("#legal_name").val(),
                    businessUrl: $(form).find("#business_url").val(),
                    address: $(form).find("#address").val(),
                    img1: $(form).find("#img1").val(),
                    areaId: selected[0].id,
                    content: $(form).find("#content").val()
                };
            }
            else {           //修改
                var params = {
                    id: shop_id,
                    shopName: $(form).find("#shop_name").val(),
                    logo: $(form).find("#logo").val(),
                    principal: $(form).find("#principal").val(),
                    principalTel: $(form).find("#principal_tel").val(),
                    legalName: $(form).find("#legal_name").val(),
                    businessUrl: $(form).find("#business_url").val(),
                    address: $(form).find("#address").val(),
                    img1: $(form).find("#img1").val(),
                    content: $(form).find("#content").val()
                };
            }
            var req = is_add ? Request.post : Request.put;

            req("shop/" + (is_add ? "" : params.id), JSON.stringify(params), function (e) {
                if (e.success) {
                    toastr.info("保存完毕", opts);
                    $("#modal-add").modal('hide');
                    shop_list.draw();
                } else {
                    toastr.error("保存失败", opts)
                }

                btn.html("保存");
                btn.removeAttr('disabled');
            });
        }
    });

    //添加店铺按钮事件
    $(".box-tools").off('click', '.btn-add').on('click', '.btn-add', function () {
        var selected = $('#area_tree').treeview('getSelected');
        if (selected.length == 0) {
            alert("in botton-add");
            toastr.info("请选择新增店铺所处区域", opts);
        }
        else {

            $(".modal-title").html("新增店铺");
            $("#modal-add").modal('show');

            $("input#shop_name").val("");
            $("input#principal").val("");
            $("input#principal_tel").val("");
            $("input#legal_name").val("");
            $("input#business_url").val("");
            $("input#address").val("");
            $("input#img1").val("");
            $("input#content").val("");
            $("input#logo").val("");

            is_add = true;
        }
    });

    //删除店铺操作
    $("#shop_list").off('click', '.btn-delete').on('click', '.btn-delete', function () {
        var that = $(this);
        var shop_id = that.data('id');
        var shop_name = that.data('name');
        if (shop_id == null) {
            toastr.error("请选择需要删除的店铺");
        }
        else {
            confirm('警告', '真的要删除： [id: ' + shop_id + "  name: " + shop_name + '] 吗?', function () {
                // 请求 module_id 删除
                Request.delete("shop/" + shop_id, {}, function (e) {
                    if (e.success) {
                        toastr.success("删除成功");
                        shop_list.draw();
                    } else {
                        toastr.error(e.message);
                    }
                });
            });

        }
    });

    //编辑店铺
    $("#shop_list").off('click', '.btn-edit').on('click', '.btn-edit', function () {
        var that = $(this);
        shop_id = that.data('id');
        var shop_name = that.data('name');
        var rows = shop_list.rows().data();
        for (var i = 0; i < rows.length; i++) {
            if (rows[i]["id"] == shop_id) {
                alert('[获取数据]' + rows[i].shopName);

                $(".modal-title").html("编辑店铺");
                $("#modal-add").modal('show');

                $("input#shop_name").val(rows[i]["shopName"]);
                $("input#principal").val(rows[i]["principal"]);
                $("input#principal_tel").val(rows[i]["principalTel"]);
                $("input#legal_name").val(rows[i]["legalName"]);
                $("input#business_url").val(rows[i]["businessUrl"]);
                $("input#address").val(rows[i]["address"]);
                $("input#img1").val(rows[i]["img1"]);
                $("input#content").val(rows[i]["content"]);
                $("input#logo").val(rows[i]["logo"]);
                is_add = false;
            }
        }
    });
});

