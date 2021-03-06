jQuery(document).ready(function () {

    $("form#add_tree_form").validate({
        rules: {
            class_code: {required: true},
            class_name: {required: true}
        },
        messages: {
            class_code: {required: "类别编码不可为空."},
            class_name: {required: "类别名称不可为空"}
        },
        submitHandler: function (form) {
            var btn = $('button[type="submit"]');
            btn.attr('disabled',"true");
            btn.html("保存中..请稍后");

            var params = {
                parentCode: $(form).find("#parent_code").val(),
                classCode: $(form).find("#class_code").val(),
                className: $(form).find("#class_name").val()
            };
            var id = $(form).find('#id').val();

            var type = id == '';

            var req = type ? Request.post : Request.put;
            req("goodsclass/" + (type ? "" : id), JSON.stringify(params), function (e) {

                if (e.success) {
                    toastr.info("保存完毕", opts);
                } else {
                    toastr.error("保存失败", opts)
                }

                btn.html("保存");
                btn.removeAttr('disabled');
            });

        }
    });

    // 树结构操作绑定
    $('.btn-tree-add').off('click').on('click', function () {
        $("#modal-tree").modal('show');
        var selected = $('#base_tree').treeview('getSelected');
        if (selected == null || selected.length == 0) {
            $("#parent_code_name").val("根节点");
        } else {
            $("#id").val(selected[0].id);
            $("#parent_code").val(selected[0].classCode);
            $("#parent_code_name").val(selected[0].text);
        }
    });

    $('.btn-tree-edit').off('click').on('click', function () {
        var selected = $('#base_tree').treeview('getSelected');
        if (selected == null || selected.length == 0) {
            toastr.warning('请选择类型后在编辑', opts);
            return;
        }

        $("#modal-tree").modal('show');
        $("#id").val(selected[0].id);
        $("#parent_code").val(selected[0].classCode);
        $("#parent_code_name").val(selected[0].text);
    });

    $('.btn-tree-del').off('click').on('click', function () {
        var selected = $('#base_tree').treeview('getSelected');
        if (selected == null || selected.length == 0) {
            toastr.warning('请选择一个类别后才能进行删除', opts);
        }

        confirm('提示', '确定要删除类别： ' + selected[0].text + ' 吗？', function () {
            // 删除操作
            toastr.success('删除成功!', opts);
        });
    });

    var inited = false, classTree = {
        baseTree: null,
        init: function () {
            if (inited) return this;

            inited = true;
            this.reload();
            return this;
        },
        reload: function () {
            var that = this;
            Request.get('goodsinfo/goodsClassTree', {}, function (e) {
                if (e.success) {
                    that.baseTree = $('#base_tree').treeview({
                        data: e.data
                    });
                    that.baseTree.treeview('selectNode', [0]);
                }
            });
        }
    };
    window.classTree = classTree.init();

    // 页面整体事件

    $('#btn_add_new').off('click').on('click', function () {
        var addType = $('.tab-content .tab-pane.active').attr('id');
        console.log('选择分页卡', addType);

        // 这里做判断
        switch (addType) {
            case 'base_list':
                // 基本信息分页新增

                $("#modal-basebox").modal('show');

                break;
            case 'class_list':
                // 规格列表分页新增
                break;
            case 'comment_list':
                // 评价列表分页新增
                break;
        }

    });

    // 基本信息列表
    var baseTable = $("#base_data_table").DataTable({
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
                url: BASE_PATH + "goodsinfo",
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
            {"data": "id"},
            {"data": "classCode"},
            {"data": "title"},
            {"data": "price"},
            {"data": "describe"}
        ],
        "aoColumnDefs": [
            {
                "sClass":"center",
                "aTargets":[5],
                "mData":"id",
                "mRender":function(a,b,c,d) {//a表示statCleanRevampId对应的值，c表示当前记录行对象
                    // 修改 删除 权限判断
                    var buttons = '<div class="btn-group">';
                    buttons += '<div class="btn-group">';
                    buttons += '<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">操作';
                    buttons += '<span class="caret"></span></button>';
                    buttons += '<ul class="dropdown-menu">';
                    if (accessUpdate) {
                        buttons += '<li><a href="javascript:;" class="btn-edit" data-id="'+a+'">编辑</a></li>';
                    }
                    if (accessDelete) {
                        buttons += '<li><a href="javascript:;" class="btn-del" data-id="'+a+'">删除</a></li>';
                    }
                    buttons += '</ul></div></div>';

                    return buttons;

                }
            }
        ]
    });
    // 基本信息列表单行点击
    $('#base_data_table tbody').on('click', 'td', function () {

        var tr = $(this).closest('tr');
        var row = baseTable.row( tr );
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }

    });

    function format ( d ) {
        // `d` is the original data object for the row
        return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
            '<tr>'+
            '<td>Full name:</td>'+
            '<td></td>'+
            '</tr>'+
            '<tr>'+
            '<td>Extension number:</td>'+
            '<td></td>'+
            '</tr>'+
            '<tr>'+
            '<td>Extra info:</td>'+
            '<td>And any further details here (images etc)...</td>'+
            '</tr>'+
            '</table>';
    }

    // 规格列表信息加载


    // 评价列表加载

});

$(document).ready(function () {

    $("#kv-explorer").fileinput({
        'theme': 'explorer',
        'uploadUrl': Request.BASH_PATH + 'file/singleUpload',
        overwriteInitial: false,
        initialPreviewAsData: false/*,
        initialPreview: [
            "http://lorempixel.com/1920/1080/nature/1",
            "http://lorempixel.com/1920/1080/nature/2",
            "http://lorempixel.com/1920/1080/nature/3"
        ],
        initialPreviewConfig: [
            {caption: "nature-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
            {caption: "nature-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
            {caption: "nature-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3}
        ]*/
    });
});