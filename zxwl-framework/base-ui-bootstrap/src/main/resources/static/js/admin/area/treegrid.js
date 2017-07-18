(function ($) {
    "use strict";

    $.fn.treegridData = function (options, param) {
        //如果是调用方法
        if (typeof options == 'string') {
            return $.fn.treegridData.methods[options](this, param);
        }

        //如果是初始化组件
        options = $.extend({}, $.fn.treegridData.defaults, options || {});
        var target = $(this);
        //得到根节点
        target.getRootNodes = function (data) {
            var result = [];
            $.each(data, function (index, item) {
                if (!item[options.parentColumn]) {
                    result.push(item);
                }
            });
            return result;
        };
        var j = 0;
        //递归获取子节点并且设置子节点
        target.getChildNodes = function (data, parentNode, parentIndex, tbody) {
            $.each(data, function (i, item) {
                if (item[options.parentColumn] == parentNode[options.id]) {
                    var tr = $('<tr></tr>');
                    var nowParentIndex = (parentIndex + (j++) + 1);
                    tr.addClass('treegrid-' + nowParentIndex);
                    tr.addClass('treegrid-parent-' + parentIndex);
                    $.each(options.columns, function (index, column) {
                        var td = $('<td></td>');
                        td.text(item[column.field]);
                        tr.append(td);
                    });
                    tbody.append(tr);
                    target.getChildNodes(data, item, nowParentIndex, tbody)

                }
            });
        };
        target.addClass('table');
        if (options.striped) {
            target.addClass('table-striped');
        }
        if (options.bordered) {
            target.addClass('table-bordered');
        }

        //构造表头
        var thr = $('<tr></tr>');
        $.each(options.columns, function (i, item) {
            var th = $('<th style="padding:10px;"></th>');
            th.text(item.title);
            thr.append(th);
        });
        var thead = $('<thead></thead>');
        thead.append(thr);
        target.append(thead);

        //构造表体
        var tbody = $('<tbody></tbody>');
        var rootNode = target.getRootNodes(options.data);
        $.each(rootNode, function (i, item) {
            var tr = $('<tr data-mid="'+i+'"></tr>');
            tr.addClass('treegrid-' + (j + i));
            $.each(options.columns, function (index, column) {
                var td = $('<td></td>');

                td.text(item[column.field]);
                if (column.field === 'order') {
                    td.text(i);
                }
                if (column.field === 'id' || column.field === 'text') {
                    td.html('<input type="text" class="form-control input-sm" name="' + column.field + '-' + i + '" value="' + (item[column.field] === undefined ? '' : item[column.field]) + '"/>');
                }
                if (column.field === 'checked') {
                    td.html('<input type="checkbox" name="checkbox'+i+'" ' + (item[column.field] ? 'checked' : '' ) + '/>');
                }
                if (column.field === 'control') {
                    td.html('<button type="button" class="btn btn-xs btn-danger btn-module-remove"><i class="fa fa-remove"></i></button>');
                }

                tr.append(td);
            });
            tbody.append(tr);

            target.getChildNodes(options.data, item, (j + i), tbody);
        });
        target.append(tbody);
        target.treegrid({
            expanderExpandedClass: options.expanderExpandedClass,
            expanderCollapsedClass: options.expanderCollapsedClass
        });
        if (!options.expandAll) {
            target.treegrid('collapseAll');
        }

        return target;
    };

    $.fn.treegridData.methods = {
        getAllNodes: function (target, data) {
            return target.treegrid('getAllNodes');
        },
        //组件的其他方法也可以进行类似封装........
    };

    $.fn.treegridData.defaults = {
        id: 'id',
        parentColumn: 'parentId',
        data: [],    //构造table的数据集合
        expandColumn: null,//在哪一列上面显示展开按钮
        expandAll: true,  //是否全部展开
        striped: false,   //是否各行渐变色
        bordered: false,  //是否显示边框
        columns: [],
        expanderExpandedClass: 'glyphicon glyphicon-chevron-down',//展开的按钮的图标
        expanderCollapsedClass: 'glyphicon glyphicon-chevron-right'//缩起的按钮的图标

    };
})(jQuery);