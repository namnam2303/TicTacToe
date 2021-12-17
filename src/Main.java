import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<List> conditions = new ArrayList<List>();
    private static List<Integer> playerPositions = new ArrayList<>();
    private static List<Integer> cpuPositions = new ArrayList<>();
    // Điều kiện thắng ( lưu vị trí của các trường hợp thắng)
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
        int result = status();
        while (result == -1) { // Chạy đến khi nào game kết thúc
            updateGameBoard(gameBoard, getPlayerPos(), "player");
            result = status();
            if (result != -1) {
                printGameBoard(gameBoard);
                break;
            }
            updateGameBoard(gameBoard, randomCpuPos(), "cpu");
            printGameBoard(gameBoard);
            result = status();
        }
        // 1 là người thắng, 0 là máy thắng và 2 là hòa
        System.out.println(result == 1 ? "Congratulations! You win" : result == 0 ? "Failed! Cpu wins!" : "Drawn!");
    }
    // Method random vị trí của máy
    private static int randomCpuPos() {
        Random random = new Random();
        int cpuPos = 0;
        if (cpuPositions.size() >= 2) { // Nếu máy đã chọn từ 2 lần trở lên sẽ ưu tiên chọn vị trí để chiến thắng
            for (List condition : conditions) {
                int count = 0;
                for (int i = 0; i < condition.size(); i++) {
                    if (cpuPositions.contains(condition.get(i))) {
                        count++;
                    }
                }
                if (count == 2) {
                    for (int i = 0; i < condition.size(); i++) {
                        if (!cpuPositions.contains(condition.get(i))) {
                            cpuPos = checkPos((int) condition.get(i)) ? 0 : (int) condition.get(i);
                        }
                    }
                }
            }
            if (cpuPos == 0) { // Nếu ko tìm được vị trí để thắng luôn thì ngăn chặn hoặc random
                cpuPos = preventPlayerWin();
                if (cpuPos == 0) {
                    while (checkPos(cpuPos) || cpuPos == 0) {
                        cpuPos = random.nextInt(9) + 1;
                    }
                }
            }
        } else {
            cpuPos = preventPlayerWin();    // Ngăn chặn người chơi thắng
            if (cpuPos == 0) {  // Nếu người chơi chưa thể thắng thì random như bình thường
                while (checkPos(cpuPos) || cpuPos == 0) {
                    cpuPos = random.nextInt(9) + 1;
                }
            }
        }
        cpuPositions.add(cpuPos);
        return cpuPos;
    }
    // Phương thức kiểm tra vị trí mới với các vị trí đã được đánh dấu xem có trùng lặp không
    private static boolean checkPos(int pos) {
        return playerPositions.contains(pos) || cpuPositions.contains(pos);
    }
    // Phương thức kiểm tra trạng thái của game sau mỗi lần cập nhật vị trí của người chơi và máy
    private static int status() {
        if (playerPositions.size() + cpuPositions.size() == 9) {
            for (List c : conditions) {
                if (playerPositions.containsAll(c)) {
                    return 1;
                } else if (cpuPositions.containsAll(c)) {
                    return 0;
                }
            }
            return 2;
        } else {
            for (List c : conditions) {
                if (playerPositions.containsAll(c)) {
                    return 1;
                } else if (cpuPositions.containsAll(c)) {
                    return 0;
                }
            }
            return -1;
        }
    }
    // Phương thức ngăn chặn người chơi chiến thắng nếu người chơi có 2/3 vị trí để thắng
    private static int preventPlayerWin() {
        int cpuPos = 0;
        for (List condition : conditions) {
            int countPlayerPos = 0;
            for (int i = 0; i < condition.size(); i++) {
                if (playerPositions.contains(condition.get(i))) {
                    countPlayerPos++;
                }
            }
            if (countPlayerPos == 2) {
                for (int i = 0; i < condition.size(); i++) {
                    if (!playerPositions.contains(condition.get(i))) {
                        cpuPos = checkPos((int) condition.get(i)) ? 0 : (int) condition.get(i);
                    }
                }
            }
        }
        return cpuPos;
    }
    // Phương thức in game ra màn hình
    private static void printGameBoard(String gameBoard[][]) {
        for (String[] row : gameBoard) {
            for (String str : row) {
                System.out.print(str);
            }
            System.out.println();
        }
    }
    // Phương thức cập nhập bảng game sau khi đã có vị trí của người hoặc máy
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
    // Phương thức lấy vị trí do người chơi chọn
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
