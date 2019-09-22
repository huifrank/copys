package git.huifrank.copys;

import git.huifrank.annotation.ToString;
import git.huifrank.copys.handler.MobileHandler;
import git.huifrank.copys.handler.NameHandler;


@ToString(
        handler = {
                @ToString.ToStringMapper(mapper = MobileHandler.class,propertyName = "mobile"),
                @ToString.ToStringMapper(mapper = NameHandler.class,propertyName = "name")
        }
)
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
