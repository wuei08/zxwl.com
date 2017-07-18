//店铺信息
database.createOrAlter("t_shop")
 .addColumn().name("u_id").alias("id").comment("ID").jdbcType(java.sql.JDBCType.VARCHAR).length(32).primaryKey().commit()
.addColumn().name("shop_name").alias("shopName").comment("店铺名称").jdbcType(java.sql.JDBCType.VARCHAR).length(64).commit()
.addColumn().name("principal").alias("principal").comment("负责人名称").jdbcType(java.sql.JDBCType.VARCHAR).length(20).commit()
.addColumn().name("principal_tel").alias("principalTel").comment("负责人电话").jdbcType(java.sql.JDBCType.VARCHAR).length(36).commit()
.addColumn().name("legal_name").alias("legalName").comment("法人").jdbcType(java.sql.JDBCType.VARCHAR).length(20).commit()
.addColumn().name("business_url").alias("businessUrl").comment("营业执照 url ,与 t_metadata_rel 关系表对应").jdbcType(java.sql.JDBCType.VARCHAR).length(32).commit()
.addColumn().name("address").alias("address").comment("店铺详细地址").jdbcType(java.sql.JDBCType.VARCHAR).length(100).commit()
.addColumn().name("area_id").alias("areaId").comment("区域编码").jdbcType(java.sql.JDBCType.VARCHAR).length(32).commit()
.addColumn().name("brand_id").alias("brandId").comment("品牌编码").jdbcType(java.sql.JDBCType.VARCHAR).length(32).commit()
.addColumn().name("logo").alias("logo").comment("店铺 logo，与 t_metadata_rel 关系表 u_id 关联取得 logo 图片").jdbcType(java.sql.JDBCType.VARCHAR).length(32).commit()
.addColumn().name("remark").alias("remark").comment("备注").jdbcType(java.sql.JDBCType.VARCHAR).length(500).commit()
.addColumn().name("creator_id").alias("creatorId").comment("所属用户 id，创建者 id（该店铺归属于哪个用户，与 s_user 的 u_id 关联）").jdbcType(java.sql.JDBCType.VARCHAR).length(32).commit()
.addColumn().name("gmt_create").alias("gmtCreate").comment("创建时间").jdbcType(java.sql.JDBCType.TIMESTAMP).commit()
.addColumn().name("gmt_modify").alias("gmtModify").comment("修改时间").jdbcType(java.sql.JDBCType.TIMESTAMP).commit()
.addColumn().name("last_change_user").alias("lastChangeUser").comment("最后一次更变的用户id").jdbcType(java.sql.JDBCType.VARCHAR).length(32).commit()
 .comment("店铺信息").commit();

def Shop_module= [u_id: 'Shop', name: '店铺信息', uri: 'admin/Shop/list.html', icon: '', parent_id: '-1', remark: '', status: 1, optional: '[{"id":"M","text":"菜单可见","checked":true},{"id":"import","text":"导入excel","checked":true},{"id":"export","text":"导出excel","checked":true},{"id":"R","text":"查询","checked":true},{"id":"C","text":"新增","checked":true},{"id":"U","text":"修改","checked":true},{"id":"D","text":"删除","checked":false}]', sort_index: 1];
database.getTable("s_modules").createInsert().value(Shop_module).exec();
