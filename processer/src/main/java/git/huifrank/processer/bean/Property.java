package git.huifrank.processer.bean;

import com.google.common.base.MoreObjects;
import com.sun.tools.javac.code.Type;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 变量标识
 * 变量名+变量类型
 */
public class Property {

    private String name;
    private Type type;


    public Property(String name, Type type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(name, property.name) &&
                Objects.equals(type, property.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Property.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("type=" + type)
                .toString();
    }
}
