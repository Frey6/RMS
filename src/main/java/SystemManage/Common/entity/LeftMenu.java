package SystemManage.Common.entity;

import java.io.Serializable;
import java.util.List;

public class LeftMenu implements Serializable {
    /**
     * "title" : "二级菜单演示", "icon" : "&#xe61c;", "href" : "", "spread" : false,
     * "children" : [
     */
    private Long id;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private List<LeftMenu> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public List<LeftMenu> getChildren() {
        return children;
    }

    public void setChildren(List<LeftMenu> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LeftMenu [title=" + title + ", icon=" + icon + ", href=" + href + ", spread=" + spread + ", children="
                + children + "]";
    }

}
