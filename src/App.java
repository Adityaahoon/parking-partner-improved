import java.sql.*;
import java.util.Scanner;

public class App {

    // Database connection method
    public static Connection connect() {
        try {
            // Update with your DB URL, username, and password
            String url = "jdbc:mysql://localhost:3306/parking_partner";
            String user = "root";  // Your MySQL username
            String password = "mysql#@57";  // Your MySQL password

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    // User Registration
    public static boolean registerUser(Connection conn, String UNameL, String EmailL, String PWordL, String CNoL) {
        String query = "INSERT INTO LoginData (User_Name, Email, Password, Contact_No) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, UNameL);
            pstmt.setString(2, EmailL);
            pstmt.setString(3, PWordL);
            pstmt.setString(4, CNoL);
            pstmt.executeUpdate();
            System.out.println("Registration Successful!");
            return true;
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    // User Login
    public static boolean loginUser(Connection conn, String[] loggedInUser, String UName, String PWord) {
        String query = "SELECT * FROM LoginData WHERE User_Name = ? AND Password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, UName);
            pstmt.setString(2, PWord);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login Successful!");
                loggedInUser[0] = UName;
                return true;
            } else {
                System.out.println("Incorrect Username or Password");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    // View Profile
    public static ResultSet viewProfile(Connection conn, String UName) {
        String query = "SELECT * FROM LoginData WHERE User_Name = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, UName);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve profile: " + e.getMessage());
            return null;
        }
    }

    // Book a Spot
    public static boolean bookSpot(Connection conn, String UName, String location, String vehicleType, String date, String entryTime, String exitTime, int hours) {
        String query = "INSERT INTO UserDetails (User_Name, Location, Vehicle_Type, Date, Entry_Time, Exit_Time, Hours) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, UName);
            pstmt.setString(2, location);
            pstmt.setString(3, vehicleType);
            pstmt.setString(4, date);
            pstmt.setString(5, entryTime);
            pstmt.setString(6, exitTime);
            pstmt.setInt(7, hours);
            pstmt.executeUpdate();
            System.out.println("Booking successful. INR 350 for " + hours + " hours.");
            return true;
        } catch (SQLException e) {
            System.out.println("Booking failed: " + e.getMessage());
            return false;
        }
    }

    // Main Program
    public static void main(String[] args) {
        Connection conn = connect();
        if (conn == null) {
            return;
        }

        Scanner sc = new Scanner(System.in);
        String[] loggedInUser = new String[1];  // Store logged-in user's name

        System.out.println("Welcome to PARKING PARTNER!");

        System.out.print("1) Register \n2) Login \nSelect an option: ");
        int option = sc.nextInt();
        sc.nextLine();  // Consume the newline

        if (option == 1) {
            // User Registration
            System.out.print("Enter User Name (minimum 5 characters): ");
            String UNameL = sc.nextLine();

            System.out.print("Enter Email: ");
            String EmailL = sc.nextLine();

            System.out.print("Create Password (minimum 8 characters): ");
            String PWordL = sc.nextLine();

            System.out.print("Enter Contact Number (+91): ");
            String CNoL = sc.nextLine();

            registerUser(conn, UNameL, EmailL, PWordL, CNoL);
        } else if (option == 2) {
            // User Login
            System.out.print("Enter User Name: ");
            String UName = sc.nextLine();

            System.out.print("Enter Password: ");
            String PWord = sc.nextLine();

            boolean loggedIn = loginUser(conn, loggedInUser, UName, PWord);
            if (loggedIn) {
                boolean continueMenu = true;
                while (continueMenu) {
                    System.out.println("\nMain Menu");
                    System.out.println("1) View Profile");
                    System.out.println("2) Book a Spot");
                    System.out.println("3) Logout");
                    System.out.print("Enter Choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();  // Consume the newline character

                    switch (choice) {
                        case 1:
                            ResultSet profileData = viewProfile(conn, loggedInUser[0]);
                            try {
                                if (profileData != null && profileData.next()) {
                                    System.out.println("User Name: " + profileData.getString("User_Name"));
                                    System.out.println("Email: " + profileData.getString("Email"));
                                    System.out.println("Contact Number: " + profileData.getString("Contact_No"));
                                } else {
                                    System.out.println("Profile not found.");
                                }
                            } catch (SQLException e) {
                                System.out.println("Error displaying profile: " + e.getMessage());
                            }
                            break;
                        case 2:
                            // Book a Spot
                            System.out.print("Enter Location: ");
                            String location = sc.nextLine();

                            System.out.print("Enter Vehicle Type: ");
                            String vehicleType = sc.nextLine();

                            System.out.print("Enter Date (YYYY-MM-DD): ");
                            String date = sc.nextLine();

                            System.out.print("Enter Entry Time (HH:MM): ");
                            String entryTime = sc.nextLine();

                            System.out.print("Enter Exit Time (HH:MM): ");
                            String exitTime = sc.nextLine();

                            System.out.print("Enter Number of Hours: ");
                            int hours = sc.nextInt();
                            sc.nextLine();  // Consume the newline character

                            bookSpot(conn, loggedInUser[0], location, vehicleType, date, entryTime, exitTime, hours);
                            break;
                        case 3:
                            continueMenu = false;
                            System.out.println("Logged Out");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                }
            }
        } else {
            System.out.println("Invalid option. Please restart the program.");
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }

        sc.close();  // Close Scanner at the end of the program
    }
}
