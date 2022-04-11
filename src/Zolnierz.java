import java.io.Serializable;

public class Zolnierz implements Serializable {
    protected int rank;
    protected int exp;

    Zolnierz(int rank) {
        this.rank = rank;
        this.exp = 1;
    }

    Zolnierz(int rank, int exp) {
        this.rank = rank;
        this.exp = exp;
    }

    public int getRank() {
        return rank;
    }

    public int getEXP() {
        return exp;
    }

    public int getPower() {
        return rank*exp;
    }

    public void increaseExp() {
        if (!(rank == 4 && exp == 20)) {
            exp++;
            if (exp == 5 * rank) {
                rank++;
                exp = 1;
            }
        }
    }

    public void decreaseExp() {
        exp--;
    }
}