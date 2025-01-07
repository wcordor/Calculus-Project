import java.io.File;
import java.util.Scanner;

public class CalculusUI {

    private CalculatorTool calculusTool;
    private String firstName;
    private String lastName;
    private Scanner scnr;
    private boolean menu = true;
    private boolean notDone = true;
    private boolean backToCalc = true;
    private boolean invalid = true;
    private boolean noFiles = true;
    private FileAccessor fa;
    File folder = new File("C:\\CalcResults");

    // Constructor
    public CalculusUI() {
        this.calculusTool = new CalculatorTool();
        this.scnr = new Scanner(System.in);
        this.firstName = "";
        this.lastName = "";
        this.fa = new FileAccessor();
    }

    public void mainMenu() {
        
        System.out.println();

        // Get user name
        collectUserInfo();

        boolean exit = false;
        int choose;
        while (!exit) {
            System.out.println();
            printBox("Select a Function");
            selectFunction();
            System.out.println();

            while (invalid) {
                selectFunction();
            }

            System.out.println("Select an Option:");
            System.out.println("-----------------");
            System.out.println("1.) Perform a Calculation\n");
            System.out.println("2.) Graph Function\n");
            System.out.print("Choice: ");
            choose = scnr.nextInt();
            System.out.println();

            while (menu) {
                switch (choose) {
                    case 1:
                        printBox("Perform a Calculation");
                        performCalculation();
                        break;
                    case 2:
                        printBox("Graph " + calculusTool.getFunctions().get(calculusTool.getSelectedFunctionIndex()));
                        plotGraphMenu();
                        System.out.println();
                        System.out.println("Graph Created");
                        break;
                    default: 
                        System.out.println("Invalid choice. Back to Functions Menu.");
                        backToCalc = false;
                        menu = false;
                        break;
                } 
            }

            while (backToCalc) {
                performCalculation();
            }

            if (notDone) {
                menu = true;
            }

            if (!menu && !noFiles) {
                while (!menu && !noFiles) {
                    System.out.println("\nChoose an option");
                    System.out.println("----------------");
                    System.out.println("1.) Choose Another Function\n");
                    System.out.println("2.) Files\n");
                    System.out.println("3.) Exit Program\n");
                    System.out.print("Choice: ");
                    int option = scnr.nextInt();

                    switch (option) {
                        case 1: 
                            menu = true;
                            break;
                        case 2:
                            files();
                            break;
                        case 3:
                            exit = true;
                            menu = true;
                            printBox("Thank you for using the Calculus Calculator! Goodbye!");
                            break;
                        default:
                            System.out.println("\nInvalid Choice. Try Again.");
                    }
                }
                menu = true;
            }

            else if (!menu) {
                System.out.print("\nType 'e' to exit program. Otherwise, type another key to choose another function. ");
                char choice = scnr.next().charAt(0);
                System.out.println();

                if (choice == 'e') {
                    exit = true;
                    printBox("Thank you for using the Calculus Calculator! Goodbye!");
                }
                menu = true;
            }
        }
    }

    private void collectUserInfo() {
        System.out.print("Enter your first name: ");
        this.firstName = scnr.nextLine();
        System.out.print("Enter your last name: ");
        this.lastName = scnr.nextLine();
        System.out.println();
        printBox("Welcome " + firstName + " " + lastName + ", " + "to the Calculus Calculator Teacher!");
    }

    private void selectFunction() {
        System.out.println("\nAvailable Functions:");
        System.out.println("--------------------");
        for (int i = 0; i < calculusTool.getFunctions().size(); i++) {
            System.out.println((i + 1) + ". " + calculusTool.getFunctions().get(i));
            System.out.println();
        }
        System.out.print("Enter the number of the function you want to select: ");
        int functionChoice = scnr.nextInt();
        scnr.nextLine();
        
        try {
            calculusTool.selectFunction(functionChoice - 1);
            System.out.println("Function selected: " + calculusTool.getFunctions().get(functionChoice - 1));
            invalid = false;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid selection. Please try again.");
            invalid = true;
            return;
        }
    }
    
    private void performCalculation() {

        System.out.println("\n       Calculation Menu");
        System.out.println("------------------------------");
        System.out.println("1. First Derivative\n");
        System.out.println("2. Second Derivative\n");
        System.out.println("3. Limit\n");
        System.out.println("4. Definite Integral\n");
        System.out.println("5. Find Inflection Points\n");
        System.out.println("6. Go Back to Functions Menu\n");
        System.out.print("Enter your choice: ");
        int calculationChoice = scnr.nextInt();
    
        String result = "";
        char choice = '.';
    
        switch (calculationChoice) {
            case 1: // First Derivative (Symbolic + Numerical)
                System.out.println("*");
                System.out.println("*");
                System.out.println("*");
                System.out.println("First Derivative (Symbolic):");
                System.out.print("----------------------------");
                String symbolicFirst = calculusTool.calculateSymbolicFirstDerivative();
                System.out.println(symbolicFirst);

                System.out.print("Enter the point (x) for numerical evaluation: ");
                double x1 = scnr.nextDouble();
                String numericalFirst = calculusTool.calculateNumericalFirstDerivative(x1);
                System.out.println(numericalFirst);

                result = "First Derivative Results:\n" + symbolicFirst + "\n" + numericalFirst;

                // Add result to history once
                calculusTool.addCalculationToHistory(result);
                menu = false;
                notDone = false;
                break;
    
            case 2: // Second Derivative (Symbolic + Numerical)
                System.out.println("*");
                System.out.println("*");
                System.out.println("*");
                System.out.println("Second Derivative (Symbolic):");
                System.out.print("-----------------------------");
                String symbolicSecond = calculusTool.calculateSymbolicSecondDerivative();
                System.out.println(symbolicSecond);

                System.out.print("Enter the point (x) for numerical evaluation: ");
                double x2 = scnr.nextDouble();
                String numericalSecond = calculusTool.calculateNumericalSecondDerivative(x2);
                System.out.println(numericalSecond);

                result = "Second Derivative Results:\n" + symbolicSecond + "\n" + numericalSecond;

                // Add result to history once
                calculusTool.addCalculationToHistory(result);
                menu = false;
                notDone = false;
                break;
    
            case 3: // Limit
                System.out.println("*");
                System.out.println("*");
                System.out.println("*");
                System.out.print("Enter the point (x) where you want to calculate the limit: ");
                double x = scnr.nextDouble();
                result = calculusTool.calculateLimit(x);
                menu = false;
                notDone = false;
                break;
    
            case 4: // Definite Integral
                System.out.println("*");
                System.out.println("*");
                System.out.println("*");
                System.out.print("Enter the start point (a) of the integral: ");
                double a = scnr.nextDouble();
                System.out.print("Enter the end point (b) of the integral: ");
                double b = scnr.nextDouble();
                System.out.println();
                result = calculusTool.calculateDefiniteIntegral(a, b);
                menu = false;
                notDone = false;
                break;
    
            case 5: // Critical Points
                System.out.println("*");
                System.out.println("*");
                System.out.println("*");
                System.out.print("Enter the range start: ");
                double start = scnr.nextDouble();
                System.out.print("Enter the range end: ");
                double end = scnr.nextDouble();
                System.out.print("Enter the step size: ");
                double step = scnr.nextDouble();  
                System.out.println(calculusTool.findInflectionPoints(start, end, step));
                menu = false;
                notDone = false;
                break;

            case 6: // Return to Functions Menu
                menu = false;
                notDone = true;
                backToCalc = false;
                return;
    
            default:
                System.out.println("Invalid choice. Try Again.\n");
                return;
        }
    
        System.out.println(result);

         System.out.println("\na.) Save results");
               System.out.println("b.) Choose another Calculation\n");

               System.out.print("Enter your choice: ");
               choice = scnr.next().charAt(0);
               System.out.println();


               switch (choice) {
                   case 'a': // Save results
                       saveResultsToFile();
                       backToCalc = false;
                       break;
                   case 'b': // Go back to Calculation Menu
                       backToCalc = true;
                       return;
                   default:
                       backToCalc = true;
                       System.out.println("Invalid choice. Back to Calculation Menu.");
                       return;
               }
    }

    private void saveResultsToFile() {
        calculusTool.saveResultsToFile(firstName, lastName);
        System.out.println("\nResults saved to file: " + firstName + "_" + lastName + "_CalculusResults.txt");
        noFiles = false;
    }

    private void plotGraphMenu() {
        System.out.println("\nPlot Function with Inflection Points:");
        try {
            // Prompt user for range start, end, and step size
            System.out.print("Enter the range start (x): ");
            double start = scnr.nextDouble();
    
            System.out.print("Enter the range end (x): ");
            double end = scnr.nextDouble();
    
            System.out.print("Enter the step size: ");
            double step = scnr.nextDouble();
            scnr.nextLine(); // Consume the newline character
    
            // Call the graphing method in CalculusTool
            calculusTool.plotFunction(start, end, step);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            scnr.nextLine(); // Clear the scanner buffer in case of invalid input
        }
        notDone = false;
        menu = false;
    }

    private void files() {
        
        File[] fileList = folder.listFiles();
        System.out.println();
        printBox("Files");
        System.out.println("\nSelect an Option");
        System.out.println("----------------");
        System.out.println("1.) Find Index of File\n"); // Linear Search
        System.out.println("2.) Open File\n");
        System.out.print("Choice: ");
        int choose = scnr.nextInt();
        scnr.nextLine();
        System.out.println();

        switch (choose) {
            case 1:
                System.out.print("First Name: ");
                String firstName = scnr.nextLine();
                System.out.print("Last Name: ");
                String lastName = scnr.nextLine();
                System.out.println();
                String file = firstName + "_" + lastName + "_CalculusResults.txt";
                fa.searchName(fileList, file);
                break;
            case 2:
                fa.openFile(fileList);
                break;
            default:
                System.out.println("Incorrect Selection. Going Back.");
                return;
        }


    }

    private static int getMaxLength(String... strings) {
        int len = Integer.MIN_VALUE;
        for (String str : strings) {
            len = Math.max(str.length(), len);
        }
        return len;
    }

    private static String padString(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        return sb.append(fill(' ', len - str.length())).toString();
    }

    private static String fill(char ch, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void printBox(String... strings) {
        int maxBoxWidth = getMaxLength(strings);
        String line = "+" + fill('-', maxBoxWidth + 2) + "+";
        System.out.println(line);
        for (String str : strings) {
            System.out.printf("| %s |%n", padString(str, maxBoxWidth));
        }
        System.out.println(line);
    }

}

    