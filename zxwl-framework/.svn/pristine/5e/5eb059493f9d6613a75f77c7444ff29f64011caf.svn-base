$(document).ready(function () {
    // 事件绑定
    $("#role_list").off('click', '.btn-edit').on('click', '.btn-edit', function () {
        var that = $(this);
        var id = that.data('id');
        // 加载要修改的数据到 modal
        loadData(id);
        $("#modal-edit").modal('show');
    });
    $("#role_list").off('click', '.btn-del').on('click', '.btn-del', function () {
        var that = $(this);
        var id = that.data('id');

        confirm('删除提示', '真的要删除 ID:' + id + " 吗？", function () {
            Request.delete('role/' + id, function (e) {
                if (e.success) {
                    // 删除，删除成功后调用列表重载
                    toastr.info("删除成功", opts);
                    roleList.ajax.reload();
                } else {
                    toastr.error("删除失败", opts);
                }
            });
        });
    });
    $(".box-tools").on('click', '.btn-add', function () {
        $('#modules_tb').empty().treegridData({
            id: 'id',
            parentColumn: 'parentId',
            type: "GET", //请求数据的ajax类型
            url: BASE_PATH + 'module',   //请求数据的ajax的url
            ajaxParams: {}, //请求数据的ajax的data属性
            expandColumn: null,//在哪一列上面显示展开按钮
            striped: true,   //是否各行渐变色
            bordered: true,  //是否显示边框
            //expandAll: false,  //是否全部展开
            columns: [
                {
                    title: 'ID',
                    field: 'id'
                },
                {
                    title: '模块名称',
                    field: 'name'
                },
                {
                    title: '权限',
                    field: 'optional'
                }
            ]
        });
    });
    // 事件绑定结束

    // 新增
    $("form#add_form").validate({
        rules: {
            role_name: {required: true},
            role_id: {required: true}
        },
        messages: {
            role_name: {required: "请输入角色名."},
            role_id: {required: "角色标识必须填写, 且只能英文"}
        },
        submitHandler: function (form) {
            var btn = $('button[type="submit"]');
            btn.attr('disabled',"true");

            var params = {
                id: $(form).find("#role_id").val(),
                modules: [],
                name: $(form).find("#role_name").val(),
                remark: $(form).find("#role_info").val(),
                type: ""
            };
/*
            $("form#add_form #modules_tb input[type='checkbox']:checked").each(function(index, ele) {
                console.log($(ele).data("mid"));
                console.log($(ele).val());
            });*/

            params.modules = getCheckedActions('add_form');

            // 发送新增请求
            Request.post("role/", JSON.stringify(params), function (e) {
                if (e.success) {
                    // 弹出 新增成功 提示，关闭 modal 框
                    toastr.info("新增成功", opts);
                    $("#modal-add").modal('hide');
                    // 移除保存按钮的禁用状态
                    btn.removeAttr('disabled');
                    // 列表重载
                    roleList.ajax.reload();
                } else {
                    toastr.error("新增失败", opts);
                }
            });

        }
    });

    // 修改
    $("form#edit_form").validate({
        rules: {
            e_role_name: {required: true}
        },
        messages: {
            e_role_name: {required: "请输入角色名."},
        },
        submitHandler: function (form) {
            var btn = $('button[type="submit"]');
            btn.attr('disabled',"true");

            var params = {
                id: $(form).find("#e_role_id").val(),
                modules: [],
                name: $(form).find("#e_role_name").val(),
                remark: $(form).find("#e_role_info").val(),
                type: ""
            };
            /*
             $("form#add_form #modules_tb input[type='checkbox']:checked").each(function(index, ele) {
             console.log($(ele).data("mid"));
             console.log($(ele).val());
             });*/

            params.modules = getCheckedActions('edit_form');

            // 发送修改请求
            Request.put("role/" + params.id, JSON.stringify(params), function (e) {
                if (e.success) {
                    // 弹出 新增成功 提示，关闭 modal 框
                    toastr.info("修改成功", opts);
                    $("#modal-edit").modal('hide');
                    // 移除保存按钮的禁用状态
                    btn.removeAttr('disabled');
                    // 列表重载
                    roleList.ajax.reload();
                } else {
                    toastr.error("修改失败", opts);
                }
            });

        }
    });

    function getCheckedActions(form_id) {
        var actions = {};
        $("form#"+form_id+" input[type='checkbox']").each(function (i, e) {
            var moduleId = $(e).data("mid");
            var action = $(e).val();
            if ($(e).prop("checked")) {
                var action_mapping = actions[moduleId];
                if (!action_mapping) {
                    action_mapping = [];
                    actions[moduleId] = action_mapping;
                }
                action_mapping.push(action);
            }
        });
        var newData = [];
        for (var f in actions) {
            newData.push({moduleId: f, actions: actions[f]});
        }
        return newData;
    }

    function setCheckedActions(actions) {

        $("form#edit_form input[type='checkbox']").prop("checked", false);
        var actionsMap = {};
        $(actions).each(function (i, e) {
            actionsMap[e.moduleId] = e.actions;
        });
        $("form#edit_form input[type='checkbox']").each(function (i, e) {
            var moduleId = $(e).data("mid")
            var action = $(e).val();
            if (!actionsMap[moduleId])return;
            if (actionsMap[moduleId].indexOf(action) != -1) {
                $(e).prop("checked", "checked");
            }

        });
    }

    // 加载列表数据
    var roleList = $('#role_list').DataTable({
        "language": lang,
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "bStateSave": true,
        "serverSide": true,
        "sPaginationType": "full_numbers",
        "ajax": function (data, callback, settings) {
            var param = {};
            param.pageSize = data.length;
            param.pageIndex = data.start;
            param.page = (data.start / data.length) + 1;

            $.ajax({
                url: BASE_PATH + "role",
                type: "GET",
                cache: false,
                data: param,
                dataType: "json",
                success: function (result) {


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
            {"data": "name"},
            {"data": "type"},
            {"data": "remark"}
        ],
        "aoColumnDefs": [
            {
                "sClass":"center",
                "aTargets":[4],
                "mData":"id",
                "mRender":function(a,b,c,d) {//a表示statCleanRevampId对应的值，c表示当前记录行对象
                    // 修改 删除 权限判断
                    var buttons = '';
                    if (accessUpdate) {
                        buttons += '<button type="button" data-id="'+a+'" class="btn btn-default btn-sm btn-edit">修改</button>\n';
                    }
                    if (accessDelete) {
                        buttons += '<button type="button" data-id="'+a+'" class="btn btn-danger btn-sm btn-del">删除</button>';
                    }
                    return buttons;

                }
            }
        ]
    });

    // 加载修改信息数据
    var loadData = function (id) {

        $('#e_modules_tb').empty().treegridData({
            id: 'id',
            parentColumn: 'parentId',
            type: "GET", //请求数据的ajax类型
            url: BASE_PATH + 'module',   //请求数据的ajax的url
            ajaxParams: {"paging": "0"}, //请求数据的ajax的data属性
            expandColumn: null,//在哪一列上面显示展开按钮
            striped: true,   //是否各行渐变色
            bordered: true,  //是否显示边框
            //expandAll: false,  //是否全部展开
            columns: [
                {
                    title: 'ID',
                    field: 'id'
                },
                {
                    title: '模块名称',
                    field: 'name'
                },
                {
                    title: '权限',
                    field: 'optional'
                }
            ]
        });

        Request.get("role/" + id, {}, function (e) {
            if (e.success) {

                setTimeout(function () {
                    setCheckedActions(e.data.modules)
                }, 10);

                $("form#edit_form").find("#e_role_id").val(e.data.id);
                $("form#edit_form").find("#e_role_name").val(e.data.name);
                $("form#edit_form").find("#e_role_info").val(e.data.remark);
                $("form#edit_form").find("#e_role_type").val(e.data.type);
            }
        });

        $("#e_modules_tb").off('click', '.select_all').on('click', '.select_all', function () {
            var that = $(this);

            var td = that.parent().parent();

            var checkboxList = td.find('input[type="checkbox"]');

            var checked = that.prop('checked') ? 'checked' : '';

            if (checked !== '') {
                that.parent().find('span').text('反选');
            } else {
                that.parent().find('span').text('全选');
            }

            $.each(checkboxList, function (i, item) {
                $(item).prop('checked', checked);
            });
        });
    }
});