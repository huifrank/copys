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
        }else if(bankCardDTO == new Object()){
            return 1;
        }

        System.out.println(2);
        return 2;
    }
    @Override
    @AroundSlf4j
    public BankCardDTO queryById(long id){
        BankCardDTO bankCardDTO = new BankCardDTO();
        for(int i =0 ; i< 10 ;i++){
            if(i > 10){
                return bankCardDTO;
            }

            if(i< 11){
                return bankCardDTO;
            }
        }


        return bankCardDTO;

    }



}
