import java.io.Serializable;
import java.util.*;

public class General implements Serializable {
    protected int money;
    protected Army army = new Army();
    private static final long serialVersionUID = 10L;

    private Army subArmy;

    General(int money) {
        this.money = money;
    }

    public boolean buySoldiers(int rank, int numberOfSoldiers) {
        int price = 10*rank*numberOfSoldiers;
        if (price > money) {
            return false;
        }
        money -= price;
        army.addSoldiers(rank, numberOfSoldiers);
        return true;
    }

    public int loseBattle() {
        army.decreaseExp();
        int lostMoney = (int) Math.floor(money*0.1);
        money -= lostMoney;
        return lostMoney;
    }

    public void winBattle(int amount) {
        army.increaseExp();
        money += amount;
    }

}