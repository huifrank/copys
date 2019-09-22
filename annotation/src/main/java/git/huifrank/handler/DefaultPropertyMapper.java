package git.huifrank.handler;

import java.util.Objects;

public class DefaultPropertyMapper implements PropertiesMapper{
    @Override
    public String apply(Object obj) {
        return Objects.toString(obj);
    }
}
