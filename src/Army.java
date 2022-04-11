import java.util.Random;
import java.io.Serializable;
import java.util.HashMap;

public class Army implements Serializable {
    public static class Soldier implements Serializable {

        protected final int rank;
        protected final int exp;
        public static Soldier soldier420 = new Soldier(4, 20);
        public static Soldier soldier419 = new Soldier(4, 19);

        public Soldier(int rank, int exp) {
            this.rank = rank;
            this.exp = exp;
        }

        public int getPower() {
            return rank*exp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Soldier)) return false;
            Soldier key = (Soldier) o;
            return rank == key.rank && exp == key.exp;
        }

        @Override
        public int hashCode() {
            int result = rank;
            result = 31 * result + exp;
            return result;
        }

    }


    private HashMap<Soldier, Integer> army;
    private HashMap<Soldier, Integer> army2;
    Army() {
        army = new HashMap<>();
    }

    public void addSoldiers(int rank, int exp, int numberOfSoldiers) {
        Soldier soldier = new Soldier(rank, exp);
        Integer count = army.get(soldier);
        if (count == null) {
            army.put(soldier, numberOfSoldiers);
        } else {
            army.put(soldier, numberOfSoldiers+count);
        }
    }

    public void addSoldiers(int rank, int numberOfSoldiers) {
        addSoldiers(rank, 1, numberOfSoldiers);
    }

    public void removeRandom() {
        Random rand = new Random();
        int position = rand.nextInt(army.size()); //random pomiędzy 0 a size

        Soldier element = (Soldier) army.keySet().toArray()[position];
        Integer count = army.get(element);
        if (count == 1) {
            army.remove(element);
        } else {
            army.put(element, count-1);
        }
    }

    public void increaseExp() {
        army2 = new HashMap<>();
        army.forEach(((soldier, count) -> {
            int count420=0;
            if (soldier == Soldier.soldier420 || soldier == Soldier.soldier419) {
                count420 += count;
            } else {
                army2.put(new Soldier(soldier.rank, soldier.exp+1), count);
            }
            if (count420 > 0) {
                army2.put(Soldier.soldier420, count420);
            }
        }));
        army = army2;
    }

    public void decreaseExp() {
        army2 = new HashMap<>();
        army.forEach(((soldier, count) -> {
            if (soldier.exp > 1) {
                army2.put(new Soldier(soldier.rank, soldier.exp-1), count);
            }
        }));
        army = army2;
    }

    public boolean checkIfSoldiers(int rank, int exp, int numberOfSoldiers) {
        Integer count = army.get(new Soldier(rank, exp));
        return count != null && count >= numberOfSoldiers;
    }

    protected HashMap<Soldier, Integer> getArmy() {
        return army;
    }

    //zakładam, że w subArmy nie ma przypdaku new Soldier(4, 20)
    public void increaseExp(Army subArmy) {

        subArmy.getArmy().forEach(((soldier, count) -> {
            Soldier upgradedSoldier = new Soldier(soldier.rank, soldier.exp+1);
            Integer countDest = army.get(upgradedSoldier);
            Integer countSrc = army.get(soldier);

            if (countSrc.equals(count) ) {
                army.remove(soldier);
            } else {
                army.put(soldier, countSrc-count);
            }

            if (countDest == null) {
                army.put(upgradedSoldier, count);
            } else {
                army.put(upgradedSoldier, countDest+count);
            }
        }));
    }
}
