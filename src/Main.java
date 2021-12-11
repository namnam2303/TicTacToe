import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<List> conditions = new ArrayList<List>();
    private static List<Integer> playerPositions = new ArrayList<>();
    private static List<Integer> cpuPositions = new ArrayList<>();
    public static void setConditions(List<List> conditions) {
        conditions.add(Arrays.asList(1, 2, 3));
        conditions.add(Arrays.asList(4, 5, 6));
        conditions.add(Arrays.asList(7, 8, 9));
        conditions.add(Arrays.asList(1, 4, 7));
        conditions.add(Arrays.asList(2, 5, 8));
        conditions.add(Arrays.asList(3, 6, 9));
        conditions.add(Arrays.asList(1, 5, 9));
        conditions.add(Arrays.asList(3, 5, 7));
    }

    private static Scanner _sc = new Scanner(System.in);
    public static void main(String[] args) {
        String gameBoard[][] = {{"   ", " | ", "   ", " | ", "   "},
                {"---", "-+-", "---", "-+-", "---"},
                {"   ", " | ", "   ", " | ", "   "},
                {"---", "-+-", "---", "-+-", "---"},
                {"   ", " | ", "   ", " | ", "   "}};
        setConditions(conditions);
        int result = status(gameBoard);
        while (result == -1) {
            updateGameBoard(gameBoard, getPlayerPos(), "player");
            result = status(gameBoard);
            if(result != -1) {
                printGameBoard(gameBoard);
                break;
            }
            updateGameBoard(gameBoard, randomCpuPos(), "cpu");
            printGameBoard(gameBoard);
            result = status(gameBoard);
        }
        System.out.println(result == 1 ? "Congratulation! You win" : result == 0 ? "Failed! Cpu wins!" : "Drawn!");
    }
    private static int randomCpuPos() {
        Random random = new Random();
        int cpuPos = 0;
        if(cpuPositions.size() >=2 ) {
            for (List condition : conditions) {
                int count = 0;
                for (int i = 0; i < condition.size(); i++) {
                    if(cpuPositions.contains(condition.get(i))) {
                        count++;
                    }
                }
                if (count == 2) {
                    for (int i = 0; i < condition.size(); i++) {
                        if(!cpuPositions.contains(condition.get(i))) {
                            cpuPos = checkPos((int) condition.get(i)) ? 0 : (int) condition.get(i);
                        }
                    }
                }
            }
            if (cpuPos == 0) {
                while (checkPos(cpuPos) || cpuPos == 0) {
                    cpuPos = random.nextInt(9) + 1;
                }
            }
        } else {
            while (checkPos(cpuPos) || cpuPos == 0) {
                cpuPos = random.nextInt(9) + 1;
            }
        }
        cpuPositions.add(cpuPos);
        return cpuPos;
    }
    private static boolean checkPos(int pos) {
        return playerPositions.contains(pos) || cpuPositions.contains(pos);
    }
    private static int status(String[][] gameBoard) {
        for (List c: conditions) {
            if(playerPositions.containsAll(c)) {
                return 1;
            } else if(cpuPositions.containsAll(c)) {
                return 0;
            } else if(playerPositions.size() + cpuPositions.size() == 9) {
                return 2;
            }
        }
        return -1;
    }
    private static void printGameBoard(String gameBoard[][]) {
        for (String [] row: gameBoard) {
            for (String str : row) {
                System.out.print(str);
            }
            System.out.println();
        }
    }
    private static void updateGameBoard(String gameBoard[][], int pos, String user) {
        String symbol = user.equals("player") ? " X " : " 0 ";
        switch (pos) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }
    }
    private static int getPlayerPos() {
        int pos = 0;
        while ((pos < 1 || pos > 9) || checkPos(pos)) {
            System.out.print("Enter your placement [1-9]:");
            pos = _sc.nextInt();
        }
        playerPositions.add(pos);
        return pos;
    }
}
