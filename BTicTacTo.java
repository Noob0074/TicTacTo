import java.util.Scanner;

class BTicTacTo {
    static int p1Wins = 0;
    static int p2Wins = 0;
    static int draws = 0;

    public static String toss(String p1, String p2) {
        int rand = (int) (Math.random() * 2) + 1;
        return rand == 1 ? p1 : p2;
    }

    public static int AI(char box[][], char aiSign, char p1Sign) {
        //  win
        for (int i = 0; i < 9; i++) {
            if (box[i / 3][i % 3] != aiSign && box[i / 3][i % 3] != p1Sign) {
                box[i / 3][i % 3] = aiSign;
                if (checkWin(box, aiSign)) {
                    box[i / 3][i % 3] = (char) ('0' + i);
                    return i;
                }
                box[i / 3][i % 3] = (char) ('0' + i);
            }
        }
        // block opponent
        for (int i = 0; i < 9; i++) {
            if (box[i / 3][i % 3] != aiSign && box[i / 3][i % 3] != p1Sign) {
                box[i / 3][i % 3] = p1Sign;
                if (checkWin(box, p1Sign)) {
                    box[i / 3][i % 3] = (char) ('0' + i);
                    return i;
                }
                box[i / 3][i % 3] = (char) ('0' + i);
            }
        }
        // choose center 
        if (box[1][1] != aiSign && box[1][1] != p1Sign) {
            return 4;
        }
        // choose a random move
        int rand;
        do {
            rand = (int) (Math.random() * 9);
        } while (box[rand / 3][rand % 3] == aiSign || box[rand / 3][rand % 3] == p1Sign);
        return rand;
    }

    public static void printBox(char box[][]) {
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[0].length; j++) {
                if (j > 0) System.out.print("|");
                System.out.print(" " + box[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean checkWin(char box[][], char sign) {
        for (int i = 0; i < 3; i++) {
            if ((box[i][0] == sign && box[i][1] == sign && box[i][2] == sign) ||
                (box[0][i] == sign && box[1][i] == sign && box[2][i] == sign)) {
                return true;
            }
        }
        if ((box[0][0] == sign && box[1][1] == sign && box[2][2] == sign) ||
            (box[0][2] == sign && box[1][1] == sign && box[2][0] == sign)) {
            return true;
        }
        return false;
    }

    public static int gameCheck(char box[][], char p1s, char p2s, String p1, String p2, int count) {
        if (checkWin(box, p1s)) {
            System.out.println(p1 + " WON!! 🏆");
            p1Wins++;
            return 1;
        }
        if (checkWin(box, p2s)) {
            System.out.println(p2 + " WON!! 🏆");
            p2Wins++;
            return 1;
        }
        if (count == 9) {
            System.out.println("DRAW!!");
            draws++;
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("ENTER PLAYER 1 NAME: ");
        String p1 = sc.next();
        char p1s;
        do {
            System.out.print("CHOOSE SIGN O/X: ");
            p1s = sc.next().charAt(0);
        } while (p1s != 'O' && p1s != 'X');

        System.out.print("IS PLAYER 2 HUMAN? (y/n): ");
        char isHuman = sc.next().charAt(0);
        String p2 = "AI";
        char p2s = p1s == 'O' ? 'X' : 'O';

        if (isHuman == 'y') {
            System.out.print("ENTER PLAYER 2 NAME: ");
            p2 = sc.next();
            do {
                System.out.print("CHOOSE SIGN O/X: ");
                p2s = sc.next().charAt(0);
            } while (p2s == p1s || (p2s != 'O' && p2s != 'X'));
        } else {
            System.out.println("PLAYER 2 IS AI");
        }

        String currentPlayer = toss(p1, p2);
        System.out.println(currentPlayer + " WON THE TOSS!!");
        System.out.println(currentPlayer + " HAVE THE FIRST MOVE");

        char[][] box = { {'0', '1', '2'}, {'3', '4', '5'}, {'6', '7', '8'} };
        printBox(box);

        int count = 0;
        while (true) {
            int num;
            if (currentPlayer.equals(p1)) {
                System.out.print(currentPlayer + " CHOOSE A NUMBER: ");
                num = sc.nextInt();
            } else {
                if (isHuman == 'y') {
                    System.out.print(currentPlayer + " CHOOSE A NUMBER: ");
                    num = sc.nextInt();
                } else {
                    num = AI(box, p2s, p1s);
                    System.out.println("AI MOVE: " + num);
                }
            }

            if (num >= 0 && num < 9 && box[num / 3][num % 3] != p1s && box[num / 3][num % 3] != p2s) {
                box[num / 3][num % 3] = currentPlayer.equals(p1) ? p1s : p2s;
                currentPlayer = currentPlayer.equals(p1) ? p2 : p1;
                count++;
            } else {
                System.out.println("INVALID MOVE TRY AGAIN: ");
                continue;
            }

            printBox(box);
            if (gameCheck(box, p1s, p2s, p1, p2, count) > 0) {
                System.out.println("SCORE: " + p1 + " - " + p1Wins + " | " + p2 + " - " + p2Wins + " | Draws - " + draws);
                System.out.print("DO YOU WANT TO PLAY AGAIN? (y/n): ");
                char playAgain = sc.next().charAt(0);
                if (playAgain == 'y') {
                    box = new char[][] { {'0', '1', '2'}, {'3', '4', '5'}, {'6', '7', '8'} };
                    count = 0;
                    currentPlayer = toss(p1, p2);
                    System.out.println(currentPlayer + " WON THE TOSS!!");
                    System.out.println(currentPlayer + " HAVE THE FIRST MOVE");
                    printBox(box);
                } else {
                    break;
                }
            }
        }
        sc.close();
    }
}
