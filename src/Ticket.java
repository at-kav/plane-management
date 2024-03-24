import java.io.*;

public class Ticket {
    private Person person;
    private String row;
    private int seat;
    private double price;

    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public String getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


//    method for printing ticket information
    public void print_ticket_info() {
        person.print_person_info();
        System.out.println("Seat: " + getRow() + "" + getSeat());
        System.out.println("Price: £" + getPrice());
    }

//    Method to save Ticket information to a file
    public void save() {
        try {
            FileWriter myWriter = new FileWriter(getRow() + String.valueOf(getSeat()) + ".txt");
            myWriter.write("Ticket Details:"+"\nName: " + person.getName() + "\nSurname: " + person.getSurname() + "\nEmail: "
                    + person.getEmail() + "\nSeat: " + getRow() + "" + getSeat() + "\nPrice: £" + getPrice() + "\n\nSafe Flight!");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving.");
        }
    }
}
