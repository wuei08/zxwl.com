$(document).ready(function () {

    var inited = false;
    var module_list = [];

    var initModuleTree = function () {
        Request.get("module?paging=false&sorts[0].name=sortIndex", function (e) {
            module_list = e;
            var tree = moduleTree.init();
            var rootNodes = tree.getRootNodes(e);

            $('#module_tree').treeview({
                data: rootNodes,
                onNodeSelected: function (event, data) {
                    setModuleInfo(module_list[data.cid]);
                }
            });

            $('#module_tree').treeview('selectNode', [0]);

        });
    };

    initModuleTree();

    var moduleTree = {
        init: function () {
            if (inited) return this;
            if (jQuery === undefined){
                console.error("Required jQuery support is not available");
            } else {
                inited = true;
                var that = this;
                $(function() {

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
                        icon: item.icon,
                        parentId: item.parentId,
                        text: item.name,
                        nodes: []
                    };
                    obj.nodes = that.getChildNodes(data, item);
                    result.push(obj);
                }
            });
            console.log("-----------");
            console.log(result);
            console.log("-----------");
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
                        icon: item.icon,
                        parentId: parentNode.id,
                        text: item.name,
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

    var defaultGridData = [
        { id: "M", text: "菜单可见",checked:true}
        ,{ id: "R", text: "查询",checked:true}
        ,{ id: "C", text: "新增",checked:true}
        ,{ id: "U", text: "修改",checked:true}
        ,{ id: "D", text: "删除",checked:false}
    ];

    var setModuleInfo = function (module) {

        $('#module_id').val(module.id).attr("disabled", true);
        $('#module_name').val(module.name);
        $('#module_title').html(module.name);
        $('#module_uri').val(module.uri);
        $('#module_icon').val(module.icon);
        $('#module_parentId').val(module.parentId);

        $('#module_remark').val(module.remark);
        $('#module_sortIndex').val(module.sortIndex);
        $('#is_add').val(0);

        // 把数据转成对象 并展示在表格里
        moduleTreeGrid(JSON.parse(module.optional));

    };
    // 这里两个方法懒得合并优化了 跟催命似的在催进度
    var setModuleInfoEmpty = function () {

        $('#module_id').val("").removeAttr("disabled");
        $('#module_name').val("");
        $('#module_title').html("新增权限");
        $('#module_uri').val("");
        $('#module_icon').val("");
        var module = $('#module_tree').treeview('getSelected')[0];
        if (module != null && module != undefined) {
            $('#module_parentId').val(module.id);
        } else {
            $('#module_parentId').val("-1");
        }
        $('#module_remark').val("");
        $('#module_sortIndex').val("");
        $('#is_add').val(1);

        // 把数据转成对象 并展示在表格里
        moduleTreeGrid(defaultGridData);
    };


    var switchButtonInit = function () {
        // size : null, 'mini', 'small', 'normal', 'large'
        $("input[type='checkbox']").bootstrapSwitch({
            size: 'mini'
        });
    };

    // --------------
    // 列表权限构建
    var moduleTreeGrid = function (optional) {

        $('#m-treegrid').empty().treegridData({
            id: 'id',
            parentColumn: 'parentId',
            data: optional,
            expandColumn: null,//在哪一列上面显示展开按钮
            striped: true,   //是否各行渐变色
            bordered: true,  //是否显示边框
            //expandAll: false,  //是否全部展开
            columns: [
                {
                    title: '排序',
                    field: 'order'
                },
                {
                    title: 'ID',
                    field: 'id'
                },
                {
                    title: '备注',
                    field: 'text'
                },
                {
                    title: '默认勾选',
                    field: 'checked'
                },
                {
                    title: '操作',
                    field: 'control'
                }
            ]
        });

        switchButtonInit();
    };


    $("#m-treegrid").off('click', '.btn-module-remove').on('click', '.btn-module-remove', function () {
        var that = $(this);
        confirm('警告', '真的要删除这个选项吗?', function () {
            var tr = that.parent().parent();
            var id = tr.data("id");
            // ajax 请求后端删除 id
            tr.remove();
        });
    });
    $(".btn-module-add").off().on('click', function () {
        var tbody = $("#m-treegrid tbody");
        var num = tbody.find("tr").length;

        var html = '<tr class="treegrid-' + num + '">';
        html += '<td><span class="treegrid-expander"></span>'+num+'</td>';
        html += '<td><input type="text" class="form-control input-sm" value=""/></td>';
        html += '<td><input type="text" class="form-control input-sm" value=""/></td>';
        html += '<td><input type="checkbox" name="checkbox'+num+'" />';
        html += '<td><button type="button" class="btn btn-xs btn-danger btn-module-remove"><i class="fa fa-remove"></i></button></td>'
        html += '</tr>';
        tbody.append(html);

        switchButtonInit();

    });
    $("#panel-box").off('click', '.btn-del').on('click', '.btn-del', function () {

        var module_id = $("#module_id").val();
        var module_name = $("#module_name").val();

        confirm('警告', '真的要删除： [' + module_name + '] 吗，如果为主节点，将会导致子节点都被删除?', function () {
            // 请求 module_id 删除
            var id = module_id;
            Request.delete("module/" + id , {}, function (e) {
                if (e.success) {
                    toastr.success("删除成功");
                    initModuleTree();
                } else {
                    toastr.error(e.message);
                }
            });
        });
    });
    $(".btn-add-new").off('click').on('click', function() {
        setModuleInfoEmpty();
    });

    $("form#module_form").validate({
        rules: {
            id: {required: true},
            name: {required: true}
        },
        messages: {
            id: {required: "权限ID 必须填写，且只能英文."},
            name: {required: "权限名称必须填写"}
        },
        submitHandler: function (form) {
            var btn = $('button[type="submit"]');
            btn.attr('disabled',"true");
            btn.html("保存中..请稍后");
            var new_m_option = [];
            $("#m-treegrid tbody tr").each(function(i, item) {
                var tds = $(item).find("td");
                var id = $(tds).eq(1).find("input").val();
                var text = $(tds).eq(2).find("input").val();
                var checked = $(tds).eq(3).find("input").prop("checked")

                new_m_option.push({id: id, text: text, checked: checked});
            });

            var params = {
                icon: $(form).find("#module_icon").val(),
                id: $(form).find("#module_id").val(),
                name: $(form).find("#module_name").val(),
                uri: $(form).find("#module_uri").val(),
                parentId: $(form).find("#module_parentId").val() == '' ? '-1' : $(form).find("#module_parentId").val(),
                remark: $(form).find("#module_remark").val(),
                sortIndex: $(form).find("#module_sortIndex").val(),
                optional: new_m_option
            };

            var type = $(form).find("#is_add").val() == '1';

            var req = type ? Request.post : Request.put;

            req("module/" + (type ? "" : params.id), JSON.stringify(params), function (e) {

                if (e.success) {
                    toastr.info("保存完毕", opts);
                    initModuleTree();
                } else {
                    toastr.error("保存失败", opts)
                }

                btn.html("保存");
                btn.removeAttr('disabled');
            });
        }
    });


});

