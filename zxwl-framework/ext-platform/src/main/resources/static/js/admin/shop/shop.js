$(document).ready(function () {
    var inited = false;
    var area_list = [];
    var is_add = true;
    var shop_id = null;
    var logoUrl = null;
    var businessUrl = null;
    var imgUrl = [];

    var initAreaTree = function () {
        Request.get("area?paging=false&sorts[0].name=sortIndex", function (e) {
            area_list = e;
            console.log(e);
            var tree = areaTree.init();

            var rootNodes = tree.getRootNodes(e);
            $('#area_tree').treeview({
                data: rootNodes
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
        "ordering": true,
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
            {"data": null},
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
        "aoColumnDefs": [{
            "searchable": false,
            "orderable": false,
            "targets": 1
        }],
        "order": [[2, 'asc']],
        "aoColumnDefs": [
            {
                "sClass": "center",
                "aTargets": [11],
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
    shop_list.column(10).visible(false);
    shop_list.column(9).visible(false);
    shop_list.column(8).visible(true);


    $('#shop_list').on('draw.dt', function () {
        var nodes = shop_list.column(1).nodes();
        for (var i = 0; i < nodes.length; i++) {
            nodes[i].innerHTML = i + 1;
        }
    });
    shop_list.columns().draw();

    //初始化文件提交框
    function initFileInput() {
        var control = $('#business_url');
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl:'file/singleUpload',
            showUpload: true, //是否显示上传按钮
            showRemove: true,
            dropZoneEnabled: false,
            showCaption: true,//是否显示标题
            allowedPreviewTypes: ['image'],
            allowedFileTypes: ['image'],
            allowedFileExtensions: ['jpg', 'png', 'jpeg'],
            maxFileSize: 2000,
            maxFileCount: 1,
            uploadAsync: true //同步上传
        }).on("fileuploaded", function(event, data, previewId, index) {
            var response = data.response;
            if(data.response.code !='200'){
                toastr("上传营业执照图片失败，请重试！", opts);
            }
            else{
                businessUrl = data.response.data.id;
            }

        });

        control = $('#logo');
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl:'file/singleUpload',
            showUpload: true, //是否显示上传按钮
            showRemove: true,
            dropZoneEnabled: false,
            showCaption: true,//是否显示标题
            allowedPreviewTypes: ['image'],
            allowedFileTypes: ['image'],
            allowedFileExtensions: ['jpg', 'png', 'jpeg'],
            maxFileSize: 2000,
            maxFileCount: 1,
            uploadAsync: true //同步上传
        }).on("fileuploaded", function(event, data,previewId, index) {
            if(data.response.code !='200'){
                toastr("上传Logo图片失败，请重试！", opts);
            }
            else{
                logoUrl = data.response.data.id;
            }

        });

        control = $('#img1' );
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl:'file/singleUpload',
            showUpload: true, //是否显示上传按钮
            showRemove: true,
            dropZoneEnabled: false,
            showCaption: true,//是否显示标题
            allowedPreviewTypes: ['image'],
            allowedFileTypes: ['image'],
            allowedFileExtensions: ['jpg', 'png', 'jpeg'],
            maxFileSize: 2000,
            maxFileCount: 3,
            uploadAsync: true //同步上传
        }).on("fileuploaded", function(event, data,previewId, index) {
            var response = data.response;
            if(data.response.code !='200'){
                toastr("上传店铺图片失败，请重试！", opts);
            }
            else{
                imgUrl.push(data.response.data.id);
            }
        });

    };

    initFileInput();

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
                if(businessUrl == null){
                    toastr.info("请选择一张营业执照！",opts);
                }else {
                    if (imgUrl.length == 0) {
                        toastr.info("请选择一张店铺照片！", opts);
                    }
                    else {
                        if(logoUrl == null){
                            toastr.info("请选择一张店铺Logo照片！", opts);
                        }
                        else{
                            var params = {
                                shopName: $(form).find("#shop_name").val(),
                                logo: logoUrl,
                                principal: $(form).find("#principal").val(),
                                principalTel: $(form).find("#principal_tel").val(),
                                legalName: $(form).find("#legal_name").val(),
                                businessUrl: businessUrl,
                                address: $(form).find("#address").val(),
                                img1: imgUrl[0],
                                img2: null,
                                img3: null,
                                areaId: selected[0].id,
                                content: $(form).find("#content").val()
                            };
                        }
                    }
                }
            }
            else {           //修改
                var params = {
                    id: shop_id,
                    shopName: $(form).find("#shop_name").val(),
                    logo: logoUrl,
                    principal: $(form).find("#principal").val(),
                    principalTel: $(form).find("#principal_tel").val(),
                    legalName: $(form).find("#legal_name").val(),
                    businessUrl: businessUrl,
                    address: $(form).find("#address").val(),
                    img1: imgUrl[0],
                    img1: null,
                    img1: null,
                    content: $(form).find("#content").val()
                };
            }
            if(imgUrl.length >= 2){
                params.img2 = imgUrl[1]
            }
            if(imgUrl.length == 3){
                params.img3 = imgUrl[2]
            }

            var req = is_add ? Request.post : Request.put;

            req("shop/" + (is_add ? "" : params.id), JSON.stringify(params), function (e) {
                if (e.success) {
                    toastr.info("保存完毕", opts);
                    $("#modal-add").modal('hide');
                    shop_list.draw();
                    logoUrl = null;
                    businessUrl = null;
                    imgUrl = [];
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
            toastr.info("请选择新增店铺所处区域", opts);
        }
        else {

            $(".modal-title").html("新增店铺");
            $("#modal-add").modal('show');
            //
            // $("input#shop_name").val("");
            // $("input#principal").val("");
            // $("input#principal_tel").val("");
            // $("input#legal_name").val("");
            // $("input#business_url").val("");
            // $("input#address").val("");
            // $("input#img1").val("");
            // $("input#content").val("");
            // $("input#logo").val("");

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

