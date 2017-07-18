package com.zxwl.web.bean.po.module;

import org.hibernate.validator.constraints.NotBlank;
import org.hsweb.commons.MD5;
import com.zxwl.web.bean.po.GenericPo;

/**
 * 模块配置信息
 * Created by zhouhao on 16-5-10.
 */
public class ModuleMeta extends GenericPo<String> {

    @NotBlank(message = "key不能为空")
    private String key;

    private String remark;

    @NotBlank(message = "模块不能为空")
    private String moduleId;

    private String roleId;

    private String meta;

    private int status;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMd5() {
        StringBuilder builder = new StringBuilder();
        builder.append(key);
        builder.append(moduleId);
        builder.append(roleId);
        builder.append(meta);
        builder.append(status);
        return MD5.defaultEncode(builder.toString());
    }



public interface Property extends GenericPo.Property{
	/**
	 *
	 * @see ModuleMeta#key
	 */
	String key="key";
	/**
	 *
	 * @see ModuleMeta#remark
	 */
	String remark="remark";
	/**
	 *
	 * @see ModuleMeta#moduleId
	 */
	String moduleId="moduleId";
	/**
	 *
	 * @see ModuleMeta#roleId
	 */
	String roleId="roleId";
	/**
	 *
	 * @see ModuleMeta#meta
	 */
	String meta="meta";
	/**
	 *
	 * @see ModuleMeta#status
	 */
	String status="status";
	}
}