package cinema.CinemaRoomManager;

import java.util.Scanner;

public class Cinema {
    private final Scanner sc;
    private int rows;
    private int middleRow;
    private int seatsInRow;
    private int totalSeats;
    private int frontRowsSeats;
    private int backRowsSeats;
    private int ticketsSold;
    private int currentIncome;
    private int maxIncome;
    private double percentageSold;
    private final int DISCOUNTED_TICKET_PRICE = 8;
    private final int STANDARD_TICKET_PRICE = 10;
    private String[][] seatsStatus;

    public static void main(String[] args) {
        CinemaManager cinemaManager = new CinemaManager();
        cinemaManager.start();
        cinemaManager.close();
    }

    // Constructor that takes a Scanner for input
    public Cinema(Scanner scanner) {
        sc = scanner;
        initializeCinemaDimensions();
        initializeSeatsStatus();
        setTotalIncome();
    }

    public Scanner getScanner() {
        return sc;
    }

    // Calculate the maximum income based on seat prices

    // Initialize cinema dimensions based on user input
    private void initializeCinemaDimensions() {
        Scanner input = getScanner();
        System.out.println("Enter the number of rows: ");
        rows = input.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        seatsInRow = input.nextInt();
        totalSeats = rows * seatsInRow;
        middleRow = rows/2;
        frontRowsSeats = middleRow * seatsInRow;
        backRowsSeats = (rows % 2 != 0 ? middleRow + 1 : 0) * seatsInRow;
    }

    // Initialize the status of cinema seats
    private void initializeSeatsStatus() {
        seatsStatus = new String[rows][seatsInRow];
        ticketsSold = 0;
        currentIncome = 0;
        for (int i = 0; i < seatsStatus.length; i++) {
            for (int k = 0; k < seatsStatus[0].length; k++) {
                seatsStatus[i][k] = "S";
            }
        }
    }

    // Calculate the maximum income based on seat prices
    private void setTotalIncome() {
        if (totalSeats <= 60 || rows % 2 == 0) {
            maxIncome = totalSeats * STANDARD_TICKET_PRICE;
        } else {
            maxIncome = frontRowsSeats * STANDARD_TICKET_PRICE + (backRowsSeats) * DISCOUNTED_TICKET_PRICE;
        }
    }

    // Print the cinema room layout
    void printCinemaRoom() {
        StringBuilder cinemaRoom = new StringBuilder("\nCinema: \n");
        printColumnHeaders(cinemaRoom);
        printSeats(cinemaRoom);
        System.out.println(cinemaRoom);
    }

    // Formats a string of the form "  1 2 3 4 5 6 7", leading space included
    private void printColumnHeaders(StringBuilder cinemaRoom) {
        cinemaRoom.append(" ");
        for (int i = 1; i <= seatsInRow; i++) {
            cinemaRoom.append(" ").append(i);
        }
        cinemaRoom.append("\n");
    }

    // Print rows starting with their number followed by the status of each seat in it
    private void printSeats(StringBuilder cinemaRoom) {
        for (int i = 0; i < rows; i++) {
            cinemaRoom.append(i + 1);
            for (int k = 0; k < seatsInRow; k++) {
                cinemaRoom.append(" ").append(seatsStatus[i][k]);
            }
            cinemaRoom.append("\n");
        }
    }

    // Sell a ticket and update statistics
    public int sellTicket(int desiredRow, int desiredSeat) {
        // Change seat status to booked
        seatsStatus[desiredRow - 1][desiredSeat - 1] = "B";

        // If room has more than 60 seats, and it's a back-row seat, use a discounted price
        // Otherwise, use the standard price
        int price = (totalSeats <= 60 || desiredRow <= middleRow) ? STANDARD_TICKET_PRICE : DISCOUNTED_TICKET_PRICE;
        currentIncome += price;
        ticketsSold++;
        percentageSold = (double) ticketsSold / totalSeats * 100;
        return price;
    }

    // Check if a seat is invalid (out of bounds or already booked)
    boolean isSeatInvalid(int desiredRow, int desiredSeat) {
        if (!isSeatPositionValid(desiredRow, desiredSeat)) {
            System.out.println("\nWrong input!\n");
            return true;
        } else if (!isSeatAvailable(desiredRow, desiredSeat)) {
            System.out.println("\nThat ticket has already been purchased!\n");
            return true;
        } else return false;
    }

    private boolean isSeatPositionValid(int desiredRow, int desiredSeat) {
        if (desiredRow == 0 || desiredSeat == 0) {
            return false;
        } else return desiredRow <= rows && desiredSeat <= seatsInRow;
    }

    private boolean isSeatAvailable(int desiredRow, int desiredSeat) {
        return !seatsStatus[desiredRow - 1][desiredSeat - 1].equals("B");
    }

    void showStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", ticketsSold);
        System.out.printf("Percentage: %.2f%%\n", percentageSold);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", maxIncome);
    }
}