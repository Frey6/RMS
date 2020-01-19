package SystemManage.ResourceManage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @description：资源
 */
public class Resource implements Serializable {

    private static final long serialVersionUID = -5321613594382537470L;

    private Long id;            // 资源ID

    private String name;        // 资源名称

    private String url;          // 资源路径

    private String permission;   // 权限标识

    private String description;  // 资源描述

    @JsonProperty("iconCls")
    private String icon;    // 图标

    private Long pid;     // 父 ID

    private Integer seq; // 全称(seqencing)  // 起一个排序的作用

    private Integer status;   // 状态

    private Integer resourcetype;  // 资源类型   是菜单类型的 还是按钮类型的

    private Date createdate;   // 创建日期

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(Integer resourcetype) {
        this.resourcetype = resourcetype;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", permission='" + permission + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", pid=" + pid +
                ", seq=" + seq +
                ", status=" + status +
                ", resourcetype=" + resourcetype +
                ", createdate=" + createdate +
                '}';
    }
}