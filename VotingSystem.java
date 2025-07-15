import java.util.*;

class Voter {
    String aadhar;
    String name;
    String birthDate;

    Voter(String aadhar, String name, String birthDate) {
        this.aadhar = aadhar;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Voter) {
            Voter v = (Voter) o;
            return aadhar.equals(v.aadhar) && name.equals(v.name) && birthDate.equals(v.birthDate);
        }
        return false;
    }
}

public class VotingSystem {

    static Scanner scanner = new Scanner(System.in);
    static int R = 3;
    static int vote1 = 0, vote2 = 0, vote3 = 0, vote4 = 0, vote5 = 0;
    static List<Voter> voterList = new ArrayList<>();
    static Set<Voter> votedUsers = new HashSet<>();

    public static void main(String[] args) {
        seedVoters();

        while (true) {
            System.out.println("\n\n\t****** WELCOME TO THE ONLINE VOTING PORTAL ******\n");
            System.out.println("\t***************************************************");
            System.out.println("\n\tEnter 1 to proceed to the main logs: ");
            int input = scanner.nextInt();
            scanner.nextLine(); // clear newline
            if (input == 1) {
                mainLogs();
            }
        }
    }

    static void mainLogs() {
        System.out.println("\n1. FOR VOTE ENTRY");
        System.out.println("2. FOR ADMIN PANEL");
        System.out.println("3. FOR WINNER\n");
        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                voterInsert();
                break;
            case 2:
                admin();
                break;
            case 3:
                winner();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    static void voterInsert() {
        while (R > 0) {
            System.out.print("Enter AADHAR ID: ");
            String aadhar = scanner.nextLine().trim();

            System.out.print("Enter NAME: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter BIRTH DATE (dd-mm-yyyy): ");
            String birthDate = scanner.nextLine().trim();

            Voter voter = new Voter(aadhar, name, birthDate);

            if (voterList.contains(voter)) {
                if (votedUsers.contains(voter)) {
                    notAgain();
                    return;
                } else {
                    votedUsers.add(voter);
                    voting();
                    return;
                }
            } else {
                R--;
                System.out.println("Invalid credentials. Attempts left: " + R);
                if (R == 0) {
                    stop();
                }
            }
        }
    }

    static void voting() {
        System.out.println("\n\n\t* * * * * LIST OF CANDIDATES * * * * *");
        System.out.println("1. Mamata Banerjee         (Symbol: Fish)");
        System.out.println("2. Deepa Dasmunsi          (Symbol: Boat)");
        System.out.println("3. Protima Rajak           (Symbol: Motorcycle)");
        System.out.println("4. Joydeb Halder           (Symbol: Broomstick)");
        System.out.println("5. Kartik Chandra Ghosh    (Symbol: Wheel)");

        System.out.print("\nEnter your choice (1-5): ");
        int vote = scanner.nextInt();
        scanner.nextLine();

        switch (vote) {
            case 1:
                vote1++;
                System.out.println("Voted for Mamata Banerjee");
                break;
            case 2:
                vote2++;
                System.out.println("Voted for Deepa Dasmunsi");
                break;
            case 3:
                vote3++;
                System.out.println("Voted for Protima Rajak");
                break;
            case 4:
                vote4++;
                System.out.println("Voted for Joydeb Halder");
                break;
            case 5:
                vote5++;
                System.out.println("Voted for Kartik Chandra Ghosh");
                break;
            default:
                System.out.println("Invalid vote. Try again.");
                return;
        }

        System.out.println("\nTHANK YOU FOR VOTING!");
    }

    static void admin() {
        System.out.print("Enter Admin Password: ");
        int password = scanner.nextInt();
        scanner.nextLine();

        if (password == 3692) {
            show();
        } else {
            System.out.println("Incorrect Password.");
        }
    }

    static void show() {
        System.out.println("\nVOTE COUNT:");
        System.out.println("Mamata Banerjee: " + vote1);
        System.out.println("Deepa Dasmunsi: " + vote2);
        System.out.println("Protima Rajak: " + vote3);
        System.out.println("Joydeb Halder: " + vote4);
        System.out.println("Kartik Chandra Ghosh: " + vote5);

        System.out.println("\nEnter 1234 to close portal or any other key to return to main logs:");
        String input = scanner.nextLine();

        if (input.equals("1234")) {
            exit();
        } else {
            mainLogs();
        }
    }

    static void winner() {
        int maxVote = Math.max(vote1, Math.max(vote2, Math.max(vote3, Math.max(vote4, vote5))));
        System.out.println("\nWinner(s):");

        if (vote1 == maxVote) System.out.println("Mamata Banerjee with " + vote1 + " votes");
        if (vote2 == maxVote) System.out.println("Deepa Dasmunsi with " + vote2 + " votes");
        if (vote3 == maxVote) System.out.println("Protima Rajak with " + vote3 + " votes");
        if (vote4 == maxVote) System.out.println("Joydeb Halder with " + vote4 + " votes");
        if (vote5 == maxVote) System.out.println("Kartik Chandra Ghosh with " + vote5 + " votes");
    }

    static void notAgain() {
        System.out.println("\nYou have already voted. You cannot vote more than once.");
        System.out.println("Enter 1 to view winner, or any other key to return:");
        String input = scanner.nextLine();
        if (input.equals("1")) {
            winner();
        } else {
            mainLogs();
        }
    }

    static void stop() {
        System.out.println("Too many invalid attempts. Please try again later.");
        System.exit(0);
    }

    static void exit() {
        System.out.println("\nExiting the portal... Thank you!");
        System.exit(0);
    }

    static void seedVoters() {
        voterList.add(new Voter("10001", "Rajat Kumar", "31-03-1999"));
        voterList.add(new Voter("10002", "Goutam Kumar Bhadani", "01-01-1999"));
        voterList.add(new Voter("10003", "Gautam Kumar", "12-10-1999"));
        voterList.add(new Voter("10004", "Kundan Kumar", "08-12-1999"));
        voterList.add(new Voter("10005", "Aniket Arora", "28-08-1999"));
        voterList.add(new Voter("10006", "Dipika Singh", "20-10-2000"));
        voterList.add(new Voter("10007", "Avinash Kumar", "02-03-1999"));
        voterList.add(new Voter("10008", "Ravi Raj", "26-12-1999"));
        voterList.add(new Voter("10009", "Shubham Kumar", "02-01-1999"));
        voterList.add(new Voter("10010", "Subham Kumar", "03-01-1999"));
    }
}
