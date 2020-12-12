package git.huifrank.copys.service;

import git.huifrank.annotation.AroundSlf4j;
import git.huifrank.copys.BankCardDTO;

public interface BankCardService {


    int saveBankCard(BankCardDTO bankCardDTO);
    @AroundSlf4j
    BankCardDTO queryById(long id);
}
