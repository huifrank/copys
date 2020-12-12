package git.huifrank.copys.service.impl;

import git.huifrank.annotation.AroundSlf4j;
import git.huifrank.copys.BankCardDTO;
import git.huifrank.copys.service.BankCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BankCardServiceImpl implements BankCardService {

    private Logger logger = LoggerFactory.getLogger(BankCardServiceImpl.class);

    @Override
    @AroundSlf4j
    public int saveBankCard(BankCardDTO bankCardDTO){
        if(bankCardDTO == null){
            return 0;
        }


        System.out.println(bankCardDTO);
        return 1;

    }



}
