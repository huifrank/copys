package git.huifrank.copys;

import git.huifrank.processer.BeanCopys;

public class Runner {

    public static void main(String[] args) {
        BankCardDO cardDO = new BankCardDO();
        cardDO.setName("aa");
        cardDO.setId("1");
        cardDO.setUserId(2L);


        BankCardDTO cardDTO  = new BankCardDTO();
        BeanCopys.copyProperties(cardDO,cardDTO);

        System.out.println(cardDTO);



    }
}
