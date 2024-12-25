package se_parmida_jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static se_parmida_jdbc.JDBCUtil.closeConnection;

public class Main {
    public static void main(String[] args) {
        WorkRoleDAO workRoleDAO = new WorkRoleDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nVälj ett alternativ:");
            System.out.println("1. Skapa ny arbetsroll");
            System.out.println("2. Visa alla arbetsroller");
            System.out.println("3. Logga in som anställd");
            System.out.println("4. Avsluta");

            int val = scanner.nextInt();
            scanner.nextLine();

            switch (val) {
                // användaren får skriva in uppgifterna, skapar ett nytt worlrole,
                //sedan infogas de i databasen.
                case 1 -> {
                    System.out.print("Ange titel: ");
                    String title = scanner.nextLine();
                    System.out.print("Ange beskrivning: ");
                    String description = scanner.nextLine();
                    System.out.print("Ange lön: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();

                    WorkRole role = new WorkRole();
                    role.setTitle(title);
                    role.setDescription(description);
                    role.setSalary(salary);
                    role.setCreationDate(new java.util.Date());
                    workRoleDAO.insertWorkRole(role);

                    System.out.println("Ny arbetsroll skapad.");
                }
                //Visar alla roller, visar tid och titel.
                case 2 -> {
                    System.out.println("Arbetsroller:");
                    workRoleDAO.getAllWorkRoles().forEach(role ->
                            System.out.println(role.getRoleId() + ": " + role.getTitle()));
                }
                // Användaren får skriva in sina uppgifter, kollar ifall uppgifterna stämmer.
                case 3 -> {
                    System.out.print("Ange email: ");
                    String email = scanner.nextLine();
                    System.out.print("Ange lösenord: ");
                    String password = scanner.nextLine();

                    Employee loggedIn = employeeDAO.login(email, password);
                    if (loggedIn != null) {
                        System.out.println("Inloggning lyckades!");
                    } else {
                        System.out.println("Fel email eller lösenord.");
                    }
                }
                case 4 -> running = false;
                default -> System.out.println("Ogiltigt val, försök igen.");
            }
        }
        scanner.close();

    }
}
