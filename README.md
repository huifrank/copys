
## 一个利用编译时注解实现的小工具

需要使用jdk中的tools.jar 编译时请确保其在classpath中

 目前的功能:  
    *@PropertiesConvert* 注解 <br/>
    * 根据注解参数配置的source,target类型 自动生成复制属性代码  （前提是双方类型一致，名称一致）
    
   <br/>
   
   例如
    
    
    public class BankCardConvert {
     
         @PropertiesConvert(source = BankCardDO.class,target = BankCardDTO.class)
         public void convertDO2DTO(BankCardDO cardDO, BankCardDTO bankCardDTO){
     
         }
     }
     


经编译后，将会生成如下代码的字节码


    public class BankCardConvert {
        public BankCardConvert() {
        }

        public void convertDO2DTO(BankCardDO cardDO, BankCardDTO bankCardDTO) {
             bankCardDTO.setName(cardDO.getName());
             bankCardDTO.setUserId(cardDO.getUserId());
             bankCardDTO.setSystem(cardDO.getSystem());
             bankCardDTO.setId(cardDO.getId());
        }
    }
    

如果运行失败 请坚持pom中tools.jar的依赖路径是否正确

      
      <dependency>
            <groupId>com.sun</groupId>
            <artifactId>tools</artifactId>
            <scope>system</scope>
            <systemPath>${java.home}/../lib/tools.jar</systemPath>
            <version>8</version>
        </dependency>
       
由于tools.jar不是jre中的一部分 所以 pom中没有这个依赖的话打包时会报错  目前还没有想到好的办法解决。

如果你有好的想法  请联系我.....

---
Q: 为什么要用原始的set/get方法来复制属性bean，而不是直接用spring提供的BeanUtils.copyProperties

A: BeanUtils.copyProperties很慢  非常慢（java 8改善了很多 但是还是很慢）。 


后续会支持更多功能 

目前的想法：
+ 属性复制支持定义convert 复制不同类型 或者在复制时对属性对自定义处理
+ 生成toString方法，支持对特定字段转换后再拼入toString方法( 敏感信息掩码时会很有用 )



    
    