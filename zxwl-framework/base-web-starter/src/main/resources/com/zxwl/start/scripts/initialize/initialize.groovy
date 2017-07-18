package com.zxwl.start.scripts.initialize

import org.hsweb.commons.MD5


database.getTable("s_user")
        .createInsert()
        .value(["u_id": "admin", "username": "admin", "password": MD5.encode("admin"), "name": "超级管理员", "status": 1, "create_date": new Date()])
        .exec();

def s_modules = database.getTable("s_modules");

def modules = [
        [u_id: 'sys', name: '系统配置', uri: '', icon: 'fa fa-cog', parent_id: '-1', remark: '系统权限', status: 1, optional: '[{"id":"M","checked":"true"}]', sort_index: 3]
        , [u_id: 'module', name: '权限管理', uri: 'admin/module/list.html', icon: 'fa fa-list-alt', parent_id: 'sys', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"C","text":"新增","checked":false},{"id":"R","text":"查询","checked":false},{"id":"U","text":"修改","checked":false},{"id":"D","text":"删除","checked":false}]', sort_index: 301]
        , [u_id: 'role', name: '角色管理', uri: 'admin/role/list.html', icon: 'fa fa-users', parent_id: 'sys', remark: '初始数据', status: 1, optional: '[{"id":"M", "text":"菜单可见", "uri":""},{"id":"C", "text":"新增", "uri":""},{"id":"R", "text":"查询", "uri":""},{"id":"U", "text":"修改", "uri":""},{"id":"D", "text":"删除", "uri":""}]', sort_index: 302]
        , [u_id: 'user', name: '用户管理', uri: 'admin/user/list.html', icon: 'fa fa-user', parent_id: 'sys', remark: '初始数据', status: 1, optional: '[{"id":"M","text":"菜单可见"},{"id":"C","text":"新增"},{"id":"R","text":"查询"},{"id":"U","text":"修改"},{"id":"D","text":"删除"},{"id":"enable","text":"启用"},{"id":"disable","text":"禁用"}]', sort_index: 303]
        , [u_id: 'sys-parent', name: '系统开发', uri: '', icon: 'icon-application', parent_id: '-1', remark: '', status: 1, optional: '[{"id":"M"}]', sort_index: 4]
        , [u_id: 'form', name: '表单管理', uri: 'admin/form/list.html', icon: 'fa fa-wpforms', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"C","text":"新增","checked":false},{"id":"R","text":"查询","checked":false},{"id":"U","text":"修改","checked":false},{"id":"D","text":"删除","checked":false},{"id":"deploy","text":"发布","checked":false}]', sort_index: 401]
        , [u_id: 'module-meta', name: '模块设置', uri: 'admin/system-dev/list.html', icon: 'icon-application', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 402]
        , [u_id: 'config', name: '配置管理', uri: 'admin/config/list.html', icon: '', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 403]
        , [u_id: 'classified', name: '分类管理', uri: '', icon: '', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 404]
        , [u_id: 'script', name: '脚本管理', uri: 'admin/script/list.html', icon: 'icon-page-white', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"exec","text":"运行"},{"id":"compile","text":"编译"},{"id":"M","text":"菜单可见","checked":"false"},{"id":"R","text":"查询","checked":"false"},{"id":"C","text":"新增","checked":"false"},{"id":"U","text":"修改","checked":"false"},{"id":"D","text":"删除","checked":false}]', sort_index: 405]
        , [u_id: 'database', name: '数据库维护', uri: 'admin/database/list.html', icon: '', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"drop"},{"id":"comment"},{"id":"create"},{"id":"alter"},{"id":"R"},{"id":"M","text":"菜单可见","checked":true},{"id":"select","text":"查询","checked":true},{"id":"insert","text":"新增","checked":true},{"id":"update","text":"修改","checked":true},{"id":"delete","text":"删除","checked":false}]', sort_index: 406]
        , [u_id: 'generator', name: '代码生成器', uri: 'admin/system-dev/generator/code-generator.html', icon: 'icon-application-cascade', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true}]', sort_index: 407]
        , [u_id: 'datasource', name: '数据源', uri: 'admin/datasource/list.html', icon: '', parent_id: 'sys-parent', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"import","text":"导入excel","checked":true},{"id":"export","text":"导出excel","checked":true},{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 409]
        , [u_id: 'sys-monitor', name: '系统维护', uri: '', icon: 'icon-application-xp-terminal', parent_id: '-1', remark: '', status: 1, optional: '[{"id":"M"}]', sort_index: 5]
        , [u_id: 'system-monitor', name: '系统监控', uri: 'admin/system-monitor/cpu-mem.html', icon: '', parent_id: 'sys-monitor', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 501]
        , [u_id: 'ide', name: 'IDE', uri: '', icon: '', parent_id: 'sys-parent', remark: '默认权限', status: 1, optional: '[{"id":"meta","text":"查询","checked":true},{"id":"exec","text":"执行脚本","checked":false}]', sort_index: 420]
        , [u_id: 'others', name: '其他权限', uri: '', icon: 'icon-page-white-magnify', parent_id: '-1', remark: '', status: 1, optional: '[]', sort_index: 6]
        , [u_id: 'resources', name: '文件管理', uri: '', icon: '', parent_id: 'others', remark: '', status: 1, optional: '[{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 601]
        , [u_id: 'query-plan', name: '查询方案', uri: '', icon: 'icon-table-multiple', parent_id: 'others', remark: '', status: 1, optional: '[{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 602]
        , [u_id: 'monitor-cache', name: '缓存监控', uri: 'admin/system-monitor/cache.html', icon: 'icon-monitor', parent_id: 'sys-monitor', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 502]
        , [u_id: 'quartz', name: '定时任务', uri: 'admin/quartz/list.html', icon: '', parent_id: 'sys-monitor', remark: '', status: 1, optional: '[{"id":"enable"},{"id":"disable"},{"id":"history","text":"历史记录"},{"id":"M","text":"菜单可见","checked":true},{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 503]
];
for (module in modules) {
    s_modules.createInsert().value(module).exec();
}