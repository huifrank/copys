package git.huifrank.copys;

import git.huifrank.copys.handler.MobileHandler;
import git.huifrank.handler.ToStringBuilder;

@ToStringBuilder(handler = MobileHandler.class,propertyName = {"mobile","name"})
public class BankCardDO extends Basebean {
    private String id;
    private String name;
    private Long userId;
    private String mobile;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
