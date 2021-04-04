package cinema;

import java.util.Locale;
import java.util.Scanner;

public class Cinema {
    private static final Scanner in = new Scanner(System.in);
    private static int r = 8;
    private static int c = 9;
    private static int price = 10;
    private static char[][] cinema;
    private static int currentIncome = 0;

    private static int getPrice(int row) {
        if (r * c > 60) {
            if (r / 2 < row) {
                return price - 2;
            } else {
                return price;
            }
        } else {
            return price;
        }
    }

    private static int getIncome() {
        if (r * c > 60) {
            int firstHalf = r / 2 * c * getPrice(0);
            int secondHalfRow = (int) Math.ceil(r / 2.0);
            int secondHalf = secondHalfRow * c * getPrice(secondHalfRow);
            return firstHalf + secondHalf;
        } else {
            return r * c * price;
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        r = in.nextInt();
        System.out.println("Enter the number of seats in each row:");
        c = in.nextInt();
        cinema = new char[r][c];
        initPlaces(r, c);

        int selector = -1;
        while (selector != 0) {
            printMenu();
            selector = in.nextInt();
            switch (selector) {
                case 1:
                    printPlaces();
                    break;
                case 2:
                    bookPlace();
                    break;
                case 3:
                    printStatistics();
                    break;
                default:
                    break;
            }
        }
//        printIncome();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }


    private static void bookPlace() {
        System.out.println();
        System.out.println("Enter a row number:");
        int bookR = in.nextInt();
        System.out.println("Enter a seat number in that row:");
        int bookC = in.nextInt();

        while (!checkInput(bookR, bookC) || !book(bookR, bookC)){
            System.out.println();
            System.out.println("Enter a row number:");
            bookR = in.nextInt();
            System.out.println("Enter a seat number in that row:");
            bookC = in.nextInt();
        }

        System.out.println("Ticket price: $" + getPrice(bookR));
        currentIncome += getPrice(bookR);
    }

    private static boolean checkInput(int bookR, int bookC) {
        if (bookR - 1 >= r || bookC - 1 >= c) {
            System.out.println();
            System.out.println("Wrong input!");
            return false;
        } else {
            return true;
        }
    }

    private static boolean book(int row, int column) {
        if (cinema[row - 1][column - 1] != 'B') {
            cinema[row - 1][column - 1] = 'B';
            return true;
        } else {
            System.out.println();
            System.out.println("That ticket has already been purchased!");
            return false;
        }
    }

    private static void printIncome() {
        System.out.println("Total income:");
        System.out.println("$" + getIncome());
    }

    private static void initPlaces(int r, int c) {
        char l = 'S';
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                cinema[i][j] = l;
            }
        }
    }

    private static void printPlaces() {
        System.out.println();
        System.out.println("Cinema:");
        for (int i = 0; i <= c; i++) {
            if (i == 0) {
                System.out.print(" ");
            } else {
                System.out.print(i);
            }
            if (i != c) {
                System.out.print(" ");
            }
        }
        System.out.println();
        for (int i = 1; i <= r; i++) {
            for (int j = 0; j <= c; j++) {
                if (j == 0) {
                    System.out.print(i);
                } else {
                    System.out.print(cinema[i - 1][j - 1]);
                }
                if (j != c) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static void printStatistics() {
        int purchasedTickets = getCountPurchasedTickets();
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        double percentage = (double) purchasedTickets / (r * c) * 100;
        System.out.println(String.format(
                Locale.ROOT, "Percentage: %.2f", percentage
                ) + "%"
        );
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + getIncome());
    }

    private static int getCountPurchasedTickets() {
        int count = 0;
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                if (cinema[i][j] == 'B') {
                    count++;
                }
            }
        }
        return count;
    }
}