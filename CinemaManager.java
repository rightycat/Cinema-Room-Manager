package cinema;

import java.util.Scanner;

public class CinemaManager {
    private final Scanner sc = new Scanner(System.in);
    private final Cinema cinema;

    public CinemaManager() {
        cinema = new Cinema(sc);
    }

    public void start() {
        boolean exit = false;
        do {
            printOptions();
            int option = sc.nextInt();
            switch (option) {
                case 0 -> exit = true;
                case 1 -> cinema.printCinemaRoom();
                case 2 -> sellTicket();
                case 3 -> cinema.showStatistics();
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (!exit);
    }

    private void printOptions() {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private void sellTicket() {
        int desiredRow;
        int desiredSeat;
        do {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter a row number: ");
            desiredRow = input.nextInt();
            System.out.println("Enter a seat number in that row: ");
            desiredSeat = input.nextInt();
        } while (cinema.isSeatInvalid(desiredRow, desiredSeat));

        int price = cinema.sellTicket(desiredRow, desiredSeat);
        System.out.printf("Ticket price: $%d\n", price);
    }

    public void close() {
        sc.close();
    }
}
