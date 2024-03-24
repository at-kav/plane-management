import java.util.*;
import java.util.regex.*;

public class w2053759_PlaneManagement {
    // initialization of array to store information of plane seats
    public static int[][] plane_seats = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    // initializing array to store ticket info
    public static Ticket[] array_of_tickets = new Ticket[52];

    // getting scanner object as s1
    public static Scanner s1 = new Scanner(System.in);

    public static void main(String[] args) {

//        welcome massage
        System.out.println("Welcome to the Plane Management application");

//        Display menu
        display_menu();
        boolean run = true; // variable to keep the program run
        while (run) {
            try {
                // getting user input
                System.out.print("\nPlease select an option:");
                int option = s1.nextInt();

                switch (option) {
                    case 1:
                        buy_seat();
                        display_menu();
                        break;
                    case 2:
                        cancel_seat();
                        display_menu();
                        break;
                    case 3:
                        find_first_available();
                        display_menu();
                        break;
                    case 4:
                        show_seating_plan();
                        display_menu();
                        break;
                    case 5:
                        print_tickets_info();
                        display_menu();
                        break;
                    case 6:
                        search_ticket();
                        display_menu();
                        break;
                    case 0:
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid command.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid command. Enter a menu option");
                s1.next();
            }
        }
    }

    private static void display_menu() {
        // Display menu options
        System.out.println("\n*****************************************************");
        System.out.println("*\t\t\t\t\tMENU OPTIONS\t\t\t\t\t*");
        System.out.println("*****************************************************");
        System.out.println("\t1) Buy a seat");
        System.out.println("\t2) Cancel a seat");
        System.out.println("\t3) Find first available seat");
        System.out.println("\t4) Show seating plan");
        System.out.println("\t5) Print tickets information and total sales");
        System.out.println("\t6) Search ticket");
        System.out.println("\t0) Quit");
        System.out.println("*****************************************************");
    }

//    To validate row letter
    private static int validate_row_letter() {
        while (true) {
            System.out.print("\nEnter row letter(A-D): ");
            String row = s1.next().toUpperCase();
            int row_number;
            switch (row) {
                case ("A"):
                    row_number = 0;
                    return row_number;
                case ("B"):
                    row_number = 1;
                    return row_number;
                case ("C"):
                    row_number = 2;
                    return row_number;
                case ("D"):
                    row_number = 3;
                    return row_number;
                default:
                    System.out.println("Incorrect row letter");
                    continue;
            }
        }
    }


//    To validate seat number
    private static int validate_seat(int row_number) {
        int seat = -1;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter seat number: ");
                seat = s1.nextInt();
                    if((row_number == 0 || row_number == 3) && seat <=14){
                        valid=true;
                        return seat;
                    } else if ((row_number == 1 || row_number == 2) && seat <= 12) {
                        valid=true;
                        return seat;
                    } else {
                        System.out.println("Incorrect seat number\n");
                        valid = false;
                    }
            } catch (InputMismatchException e) {
                System.out.println("Incorrect seat number\n");
                s1.next();
            }
        }
        return seat;
    }

//    To get the row letter from the number assigned
    private static String get_row_letter(int row_number) {
        String row;
        switch (row_number) {
            case 0:
                row = "A";
                return row;
            case 1:
                row = "B";
                return row;
            case 2:
                row = "C";
                return row;
            case 3:
                row = "D";
                return row;
            default:
                return null;
        }
    }

//    To buy a seat
    private static void buy_seat() {
        int row_number = validate_row_letter();
        int seat = validate_seat(row_number);
        if (plane_seats[row_number][seat - 1] == 1) {
            System.out.println("\nSeat is already booked");
        } else {
            plane_seats[row_number][seat - 1] = 1;
            System.out.print("Enter name: ");
            String name = s1.next();
            System.out.print("Enter Surname: ");
            String surname = s1.next();
            String email = validate_email();
            add_ticket_to_array(name, surname, email, row_number, seat);
            System.out.println("\nSeat is successfully booked");
        }
    }

//    to cancel a seat
    private static void cancel_seat() {
        int row_number = validate_row_letter();
        int seat = validate_seat(row_number);
        if (plane_seats[row_number][seat - 1] == 0) {
            System.out.println("\nSeat is not booked");
        } else {
            plane_seats[row_number][seat - 1] = 0;
            remove_ticket_from_array(row_number, seat);
            System.out.println("\nSeat is successfully canceled");
        }
    }

//    to find the First find available seat
    private static void find_first_available() {
        boolean seat_found = false;
        for (int row = 0; row < plane_seats.length; row++) {
            for (int column = 0; column < plane_seats[row].length; column++) {
                if (plane_seats[row][column] == 0 && !seat_found) {
                    System.out.println("\nFirst available seat is " + get_row_letter(row) + (column + 1));
                    seat_found = true;
                    break;
                }
            }
        }
        if (!seat_found) {
            System.out.println("\nNo seats available");
        }
    }

//    Show seating plan
    private static void show_seating_plan() {
        System.out.println("");
        for (int row = 0; row < plane_seats.length; row++) {
            System.out.print(get_row_letter(row) + " : ");
            for (int column = 0; column < plane_seats[row].length; column++) {
                if (plane_seats[row][column] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println("");
        }
    }

//    To validate email address
    private static String validate_email(){
        String email_regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(email_regex);
        while (true){
            System.out.print("Enter email: ");
            String email = s1.next();
            Matcher matcher = pattern.matcher(email);
            if(matcher.matches()){
                return email;
            }else {
                System.out.println("Invalid email.");
                continue;
            }
        }
    }

//    To add ticket to the array
    private static void add_ticket_to_array(String name, String surname, String email, int row_number, int seat) {
        double price;
        if (seat < 5) {
            price = 200.00;
        } else if (seat < 9) {
            price = 150.00;
        } else {
            price = 180.00;
        }
        String row = get_row_letter(row_number);

        // new person
        Person person = new Person(name, surname, email);

        // new ticket
        Ticket ticket = new Ticket(row, seat, price, person);

        // save ticket information to file
        ticket.save();

        // add ticket to array
        for (int index = 0; index < array_of_tickets.length; index++) {
            if (array_of_tickets[index] == null) {
                array_of_tickets[index] = ticket;
                break;
            }

        }
    }

//    Remove a ticket from array
    private static void remove_ticket_from_array(int row_number, int seat) {
        String row = get_row_letter(row_number);
        for (int index = 0; index < array_of_tickets.length; index++) {
            if (array_of_tickets[index] != null && array_of_tickets[index].getSeat() == seat
                    && array_of_tickets[index].getRow().equals(row)) {
                array_of_tickets[index] = null;
                break;
            }
        }
    }

//    Print ticket information
    private static void print_tickets_info() {
        double total_price = 0;
        System.out.println("");
        for (Ticket ticket : array_of_tickets) {
            if (ticket != null) {
                ticket.print_ticket_info();
                total_price += ticket.getPrice();
                System.out.println("");
            }
        }
        System.out.println("Total price: £" + total_price);
    }

//    Search ticket availability
    private static void search_ticket() {
        int row_number = validate_row_letter();
        int seat = validate_seat(row_number);
        String row = get_row_letter(row_number);
        boolean found = false;
        System.out.println("\nSearch Results :");
        for (Ticket ticket : array_of_tickets) {
                if (ticket != null && ticket.getSeat() == seat
                    && ticket.getRow().equals(row)) {
                found = true;
                ticket.print_ticket_info();
                break;
            }
        }
        if (!found) {
            System.out.println("This seat is available");
        }
    }
}
