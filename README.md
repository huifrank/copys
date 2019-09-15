
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
     


经编译后，将会生成如下字节码


    public class BankCardConvert {
        public BankCardConvert() {
        }

        public void convertDO2DTO(BankCardDO cardDO, BankCardDTO bankCardDTO) {
            bankCardDTO.setUserId(bankCardDTO.getUserId());
            bankCardDTO.setSystem(bankCardDTO.getSystem());
            bankCardDTO.setId(bankCardDTO.getId());
            bankCardDTO.setName(bankCardDTO.getName());
        }
    }
    

请注意 其中system属性继承自父类BaseBean

    public abstract class Basebean {

        private String system;    
    }

后续会支持更多功能 

目前的想法：
+ 属性复制支持定义convert 复制不同类型 或者在复制时对属性对自定义处理
+ 生成toString方法，支持对特定字段转换后再拼入toString方法( 敏感信息掩码时会很有用 )
+ 方法入口 出口自动打印日志 


    
    