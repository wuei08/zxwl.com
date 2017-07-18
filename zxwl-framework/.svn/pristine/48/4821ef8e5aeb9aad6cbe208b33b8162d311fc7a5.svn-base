$(document).ready(function () {
    var id;
    // 事件绑定
    $("#role_list").off('click', '.btn-edit').on('click', '.btn-edit', function () {
        var that = $(this);
        id = that.data('id');
        // 加载要修改的数据到 modal
        loadData(id);
        $("#modal-edit").modal('show');
    });
    // 加载修改信息数据
    var loadData = function (id) {
        Request.get("radiofrequency/" + id, {}, function (e) {
            if (e.success) {
                setTimeout(function () {
                    setCheckedActions(e.data.modules)
                }, 10);
                $("form#edit_form").find("#e_devNum").val(e.data.devNum);
                $("form#edit_form").find("#e_devCode").val(e.data.devCode);
            }
        });
    }

    $("#role_list").off('click', '.btn-del').on('click', '.btn-del', function () {
        var that = $(this);
        var id = that.data('id');

        confirm('删除提示', '真的要删除 ID:' + id + " 吗？", function () {
            Request.delete('radiofrequency/' + id, function (e) {
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

    // 事件绑定结束

    // 新增
    $("form#add_form").validate({
        // rules: {
        //     role_name: {required: true},
        //     role_id: {required: true}
        // },
        // messages: {
        //     role_name: {required: "请输入设备编码."},
        //     role_id: {required: "请输入设备序号"}
        // },
        submitHandler: function (form) {
            var btn = $('button[type="submit"]');
            btn.attr('disabled',"true");
            var params = {
                devNum: $(form).find("#devNum").val(),
                modules: [],
                devCode: $(form).find("#devCode").val(),
                type: ""
            };
            params.modules = getCheckedActions('add_form');
            // 发送新增请求
            Request.post("radiofrequency/", JSON.stringify(params), function (e) {
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
        // rules: {
        //     e_role_name: {required: true}
        // },
        // messages: {
        //     e_role_name: {required: "请输入角色名."},
        // },
        submitHandler: function (form) {
            var btn = $('button[type="submit"]');
            btn.attr('disabled',"true");
            var params = {
                modules: [],
                devNum: $(form).find("#e_devNum").val(),
                devCode: $(form).find("#e_devCode").val(),
                type: ""
            };
            params.modules = getCheckedActions('edit_form');
            // 发送修改请求
            Request.put("radiofrequency/" + id, JSON.stringify(params), function (e) {
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
                url: BASE_PATH + "radiofrequency",
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
            {"data": "devNum"},
            {"data": "devCode"}
            // {"data": "remark"}
        ],
        "aoColumnDefs": [
            {
                "sClass":"center",
                "aTargets":[3],
                "mData":"id",
                "mRender":function(a,b,c,d) {//a表示statCleanRevampId对应的值，c表示当前记录行对象
                    // 修改 删除 权限判断
                    var buttons = '<div class="btn-group">';
                    buttons += '<div class="btn-group">';
                    buttons += '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">操作';
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
    //批量上传
    function F_Open_dialog()
    {
        document.getElementById("btn_file").click();
        var ss =  $(form).find("#btn_file").val();
        alert(ss);
    }

});