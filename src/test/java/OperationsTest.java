import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sber.controller.Operations;
import ru.sber.model.Card;
import ru.sber.util.DbUtil;

import java.io.IOException;
import java.util.List;

public class OperationsTest {

    @BeforeClass
    public static void start() throws IOException {
        DbUtil.createDB();
    }

    @Test
    public void addBalance() {
        Operations.depositMoney(1234234534564567590L, 123456.0);
        Double result = Operations.checkBalanceByCard(1234234534564567590L);
        Double expected = 124466.0;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void checkCardBalance() {
        Double result = Operations.checkBalanceByCard(1234234534564567590L);
        Double expected = 1010.0;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addNewCard() {
        List<Card> start = Operations.readCardsByAccount(1000200030004000504L);
        Operations.addNewCardByAccount(1000200030004000504L);
        List<Card> finish = Operations.readCardsByAccount(1000200030004000504L);
        Assert.assertEquals(start.size() + 1, finish.size());

    }

    @Test
    public void showCardsByClient() {
        List<Card> result = Operations.readCardsByClient(100000005L);
        String expected = "[Card{id=1234234534564567594, balance=10000.0, idAccount=Account{idAccount=1000200030004000505," +
                " client=Client{idClient=100000005, fio='Esenina Alexandra'}, balance=10000.0}, status='opened'}," +
                " Card{id=1234234534564567595, balance=10000.0, idAccount=Account{idAccount=1000200030004000505, client=Client{idClient=100000005, fio='Esenina Alexandra'}, balance=10000.0}, status='opened'}, Card{id=1234234534564567596, balance=10000.0, idAccount=Account{idAccount=1000200030004000505, client=Client{idClient=100000005, fio='Esenina Alexandra'}, balance=10000.0}, status='opened'}]";
        Assert.assertEquals(expected, result.toString());
    }

    @Test
    public void showCardsByAccount() {
        List<Card> result = Operations.readCardsByAccount(1000200030004000505L);
        String expected ="[Card{id=1234234534564567594, balance=10000.0, idAccount=Account{idAccount=1000200030004000505, " +
                "client=Client{idClient=100000005, fio='Esenina Alexandra'}, balance=10000.0}, status='opened'}," +
                " Card{id=1234234534564567595, balance=10000.0, idAccount=Account{idAccount=1000200030004000505, " +
                "client=Client{idClient=100000005, fio='Esenina Alexandra'}, balance=10000.0}, status='opened'}, Card{id=1234234534564567596, balance=10000.0," +
                " idAccount=Account{idAccount=1000200030004000505, " +
                "client=Client{idClient=100000005, fio='Esenina Alexandra'}, balance=10000.0}, status='opened'}]";
        Assert.assertEquals(expected, result.toString());
    }
}
