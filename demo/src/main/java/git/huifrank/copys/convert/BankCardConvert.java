package git.huifrank.copys.convert;

import git.huifrank.annotation.PropertieConvert;
import git.huifrank.copys.BankCardDO;
import git.huifrank.copys.BankCardDTO;

public class BankCardConvert {

    @PropertieConvert(source = BankCardDO.class,target = BankCardDTO.class)
    public void convertDO2DTO(BankCardDO cardDO, BankCardDTO bankCardDTO){

    }
}
