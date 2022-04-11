import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Game implements Serializable {

    private static final long serialVersionUID = 10L;
    private Army subArmy = new Army();
    private General[] generals;
    private int currentGeneral;
    private Integer priceOfSoldiers1;
    private Integer priceOfSoldiers2;
    private Integer priceOfSoldiers3;
    private Integer priceOfSoldiers4;
    private Integer priceOfArmy;
    private boolean isOK = false;
    private Integer exp;
    private Integer numberOfSoldiers;
    private Integer rank;
    private String playerInput;
    public static final String filePath = "savefile.txt";
    private boolean isCorrectInput = true;
    private String[] commands1 = {"a", "b"};
    private String[] commands2 = {"a", "b", "c"};
    private String[] unitTypes = {"1", "2", "3", "4"};
    private String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
            "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"};
    private String[] soldierExperience = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};

    public Game() {
        Integer money = 300;
        this.generals = new General[]{new General(money), new General(money)};
        this.currentGeneral = 0;
    }

    public void buyUnits(Scanner scan) {
        System.out.println("Opcje: 'a': kupno jednostek  'b':koniec. Podaj opcję: ");
        playerInput = scan.nextLine();

        if (!Arrays.asList(commands1).contains(playerInput)) {
            isCorrectInput = false;
            while (!isCorrectInput) {
                System.out.println("Podano niepoprawną opcję. Podaj poprawną opcję: ");
                playerInput = scan.nextLine();
                if (playerInput.equals("a") || playerInput.equals("b")) {
                    isCorrectInput = true;
                }
            }
        }
        if (playerInput.equals("a")) {
            isOK = false;
            while (!isOK) {
                System.out.println("Podaj rangę jednostek. Dostępne rangi jednostek to: '1'-szeregowy, '2'-kapral, '3'-kapitan, '4'-major: ");
                playerInput = scan.nextLine();

                if (!Arrays.asList(unitTypes).contains(playerInput)) {
                    isCorrectInput = false;
                    while (!isCorrectInput) {
                        System.out.println("Taka ranga nie istnieje. Podaj poprawną rangę: ");
                        playerInput = scan.nextLine();
                        if (Arrays.asList(unitTypes).contains(playerInput)) {
                            isCorrectInput = true;
                        }
                    }
                }

                rank = Integer.parseInt(playerInput);
                System.out.println("Ile jednostek chcesz kupić? ");
                playerInput = scan.nextLine();

                if (!Arrays.asList(numbers).contains(playerInput)) {
                    isCorrectInput = false;
                    while (!isCorrectInput) {
                        System.out.println("Możesz kupić maksymalnie 40 jednostek, podaj poprawną liczbę: ");
                        playerInput = scan.nextLine();
                        if (Arrays.asList(numbers).contains(playerInput)) {
                            isCorrectInput = true;
                        }
                    }
                }

                numberOfSoldiers = Integer.parseInt(playerInput);

                if (generals[currentGeneral].buySoldiers(rank, numberOfSoldiers)) {
                    System.out.println("Kupiono jednostki.");
                    isOK = true;
                    if (currentGeneral > 0) {
                        currentGeneral--;
                    } else {
                        currentGeneral++;
                    }
                } else {
                    System.out.println("Nie stać cię na kupno tylu jednostek.");
                }
            }
        } else if (playerInput.equals("b")) {
            System.out.println("Wrócono do menu");
            isOK = true;
        }
    }

    public void Manuevers(Scanner scan) {
        if (!subArmy.getArmy().isEmpty()) {
            subArmy.getArmy().clear();
        }
        System.out.println("Wyślij jednostki na manewry. Opcje: 'a'-wyślij konkretne jednostki, 'b'-wyślij całą armię, 'c'-koniec: ");
        playerInput = scan.nextLine();

        if (!Arrays.asList(commands2).contains(playerInput)) {
            isCorrectInput = false;
            while (!isCorrectInput) {
                System.out.println("Nie ma takiej komendy. Wprowadź poprawną komendę: ");
                playerInput = scan.nextLine();
                if (playerInput.equals("a") || playerInput.equals("b") || playerInput.equals("c")) {
                    isCorrectInput = true;
                }
            }
        }
        if (playerInput.equals("a")) {
            isOK = false;
            while (!isOK) {
                System.out.println("Podaj rangę jednostek: ");
                playerInput = scan.nextLine();

                if (!Arrays.asList(unitTypes).contains(playerInput)) {
                    isCorrectInput = false;
                    while (!isCorrectInput) {
                        System.out.println("Nie ma takiej rangi. Wprowadź poprawną rangę: ");
                        playerInput = scan.nextLine();
                        if (Arrays.asList(unitTypes).contains(playerInput)) {
                            isCorrectInput = true;
                        }
                    }
                }

                rank = Integer.parseInt(playerInput);
                System.out.println("Podaj doświadczenie jednostek, maksymalna liczba, jaką możesz wprowadzić to 19: ");
                playerInput = scan.nextLine();

                if (!Arrays.asList(soldierExperience).contains(playerInput)) {
                    System.out.println("Podano złą liczbę, wprowadź poprawną liczbę: ");
                    playerInput = scan.nextLine();
                    if (Arrays.asList(soldierExperience).contains(playerInput)) {
                        isCorrectInput = true;
                    }
                }

                exp = Integer.parseInt(playerInput);

                System.out.println("Podaj liczbę jednostek: ");
                playerInput = scan.nextLine();

                if (!Arrays.asList(numbers).contains(playerInput)) {
                    System.out.println("Podano niepoprawną liczbę, wprowadź poprawną liczbę: ");
                    playerInput = scan.nextLine();
                    if (Arrays.asList(numbers).contains(playerInput)) {
                        isCorrectInput = true;
                    }
                }


                numberOfSoldiers = Integer.parseInt(playerInput);

                if (!generals[currentGeneral].army.checkIfSoldiers(rank, exp, numberOfSoldiers)) {
                    System.out.println("Nie masz tylu jednostek w armii");
                }

                if (generals[currentGeneral].army.checkIfSoldiers(rank, exp, numberOfSoldiers)) {
                    if (rank * numberOfSoldiers > generals[currentGeneral].money) {
                        System.out.println("Nie stać cię na tą akcję.");
                    } else {
                        generals[currentGeneral].money -= rank * numberOfSoldiers;
                        subArmy.addSoldiers(rank, exp, numberOfSoldiers);
                        isOK = true;
                        if (currentGeneral > 0) {
                            currentGeneral--;
                        } else {
                            currentGeneral++;
                        }
                    }
                }
            }
        } else if (playerInput.equals("b")) {
            isOK = false;
            while (!isOK) {
                AtomicReference<Integer> cost = new AtomicReference<>(0);

                Map<Army.Soldier, Integer> army = generals[currentGeneral].army.getArmy();
                army.forEach((soldier, count) -> {
                    cost.updateAndGet(v -> v + soldier.rank * 10);
                    subArmy.addSoldiers(soldier.rank, soldier.exp, numberOfSoldiers);
                });

                String amount = cost.toString();

                Integer priceOfArmy = Integer.parseInt(amount);

                if (priceOfArmy > generals[currentGeneral].money) {
                    System.out.println("Nie stać cię na manewry całej armii.");
                } else {
                    generals[currentGeneral].money -= priceOfArmy;
                    System.out.println("Dodano jednostki do armii treningowej");
                    isOK = true;
                }
            }
        } else if (playerInput.equals("c")) {
            System.out.println("Powrócono do menu.");
        }
        if (!subArmy.getArmy().isEmpty()) {
            generals[currentGeneral].army.increaseExp(subArmy);
            System.out.println("Wyszkolono jednostki.");
            if (currentGeneral > 0) {
                currentGeneral--;
            } else {
                currentGeneral++;
            }
        }
    }

    public void Fight() {
        AtomicReference<Integer> power1 = new AtomicReference<>(0);
        AtomicReference<Integer> power2 = new AtomicReference<>(0);

        Map<Army.Soldier, Integer> army = generals[0].army.getArmy();
        army.forEach((soldier, count) -> {
            power1.updateAndGet(v -> v + soldier.getPower() * count);
        });

        army = generals[1].army.getArmy();
        army.forEach((soldier, count) -> {
            power2.updateAndGet(v -> v + soldier.getPower() * count);
        });

        String gP1 = power1.toString();
        String gP2 = power2.toString();

        Integer general1Power = Integer.parseInt(gP1);
        Integer general2Power = Integer.parseInt(gP2);

        if (currentGeneral > 0) {
            currentGeneral--;
        } else {
            currentGeneral++;
        }
        if (general1Power > general2Power) {
            generals[1].loseBattle();
            generals[0].winBattle(generals[1].loseBattle());
            System.out.println("Wygrał Gracz1. Gracz1 zyskał, a Gracz2 stracił " + generals[1].loseBattle());
        } else if (general2Power > general1Power){
            generals[0].loseBattle();
            generals[1].winBattle(generals[0].loseBattle());
            System.out.println("Wygrał Gracz2. Gracz2 zyskał, a Gracz1 stracił " + generals[0].loseBattle());
        } else {
            if (generals[0].army.getArmy().isEmpty() || generals[1].army.getArmy().isEmpty()) {
                System.out.println("Nie można walczyć z pustą armią");
            } else {
                generals[0].army.removeRandom();
                generals[1].army.removeRandom();
                System.out.println("Remis! Zabito po jednej losowej jednostce z armii graczy.");
            }
        }
    }

    public void saveGame() throws IOException {
        try {
            FileOutputStream file1 = new FileOutputStream(filePath);
            ObjectOutputStream out1 = new ObjectOutputStream(file1);
            out1.writeObject(this);
            out1.close();
            file1.close();
            System.out.println("Zapisano dane.");
        } catch (FileNotFoundException e) {
            System.out.println("Błąd, nie znaleziono pliku");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game loadGame () {
        Game g1 = null;
        try {
            FileInputStream file1 = new FileInputStream(filePath);
            ObjectInputStream out1 = new ObjectInputStream(file1);
            g1 = (Game) out1.readObject();
            out1.close();
            file1.close();
            System.out.println("Pomyslnie wczytano stan gry");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nie udalo sie wczytac stanu gry, gdyz plik najprawdopodobniej nie istnieje.");
        }
        return g1;
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Game game;
        File file = new File(filePath);
        if (file.exists()) {
            game = loadGame();
            file.delete();
        } else {
            game = new Game();
        }
        boolean isWin = false;


        while (!isWin) {
            String playersInput;
            Map<Army.Soldier, Integer> army = game.generals[game.currentGeneral].army.getArmy();
            System.out.println("Ruch generała" + (game.currentGeneral+1));
            System.out.println("Armia: ");
            army.forEach((soldier, count) -> {
                System.out.println("Ranga: " + soldier.rank + " Ilość: " + army.get(soldier) + " Exp: " + soldier.exp);
            });
            System.out.println(" Pieniądze: " + game.generals[game.currentGeneral].money);
            System.out.println("Co chcesz zrobić? Opcje: 'a'-kupno jednostek, 'b'-manewry, 'c'-walka, 'd'-zapisz i wyjdź, 'e'-wczytaj grę( jeżeli istnieje )");
            playersInput = scan.nextLine();

            if (game.generals[game.currentGeneral].money < 10 && game.generals[game.currentGeneral].army.getArmy().isEmpty()) {
                isWin = true;
                System.out.println("Koniec gry.");
            } else if (playersInput.equals("a")) {
                game.buyUnits(scan);
            } else if (playersInput.equals("b")) {
                game.Manuevers(scan);
            } else if (playersInput.equals("c")) {
                game.Fight();
            } else if (playersInput.equals("d")) {
                game.saveGame();
                isWin = true;
            }else {
                System.out.println("Nie ma takiej komendy, wprowadź komendę jeszcze raz.");
            }
        }
    }
}
