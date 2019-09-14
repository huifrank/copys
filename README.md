
## 一个利用编译时注解实现的小工具

需要使用jdk中的tools.jar 编译时请确保其在classpath中

 目前的功能:  
    @PropertiesConvert注解 
    根据注解参数配置的source,target类型 自动生成复制属性代码  （前提是双方类型一致，名称一致）
    
    
    
    
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
            bankCardDTO.setId(bankCardDTO.getId());
            bankCardDTO.setName(bankCardDTO.getName());
        }
    }
    
    

后续会支持更多功能 


    
    