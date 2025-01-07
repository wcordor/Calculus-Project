import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;


public class CalculatorTool {

    private List<Function<Double, Double>> functions; // List of functions
    private Map<Integer, List<String>> functionHistory; // Tracks calculations by function
    private int selectedFunctionIndex = -1;
    
    // Constructor
    public CalculatorTool() {
        this.functions = new ArrayList<>();
        this.functionHistory = new HashMap<>();
        initializeFunctions();
    }

    private void initializeFunctions() {
        functions.add(x -> Math.pow(x, 3) + 3 * Math.pow(x, 2) - x);  // Power Rule: x^3 + 3x^2 - x
        functions.add(x -> (Math.pow(x, 3) + 3 * x) * (6 * x + 4));  // Product Rule: (x^3 + 3x)(6x + 4)
        functions.add(x -> Math.pow((4 * Math.pow(x, 2) + 9 * x), 3)); // Chain Rule: (4x^2 + 9x)^3
        functions.add(x -> (Math.pow(x, 2) + 1) / (4 * x + 3)); // Quotient Rule: (x^2 + 1) / (4x + 3)
    }

    public List<String> getFunctions() {
        List<String> functionDescriptions = new ArrayList<>();
        functionDescriptions.add("x^3 + 3x^2 - x"); // Power Rule
        functionDescriptions.add("(x^3 + 3x)(6x + 4)"); // Product Rule
        functionDescriptions.add("(4x^2 + 9x)^3"); // Chain Rule
        functionDescriptions.add("(x^2 + 1) / (4x + 3)"); // Quotient Rule
        return functionDescriptions;
    }

    public void selectFunction(int index) {
        if (index >= 0 && index < functions.size()) {
            this.selectedFunctionIndex = index;
            functionHistory.putIfAbsent(index, new ArrayList<>());
        } else {
            throw new IllegalArgumentException("Invalid function index selected.");
        }
    }
    
  
    public int getSelectedFunctionIndex() {
        return selectedFunctionIndex;
    }


    
    public Map<Integer, List<String>> getFunctionHistory() {
        return functionHistory;
    }


    public void addCalculationToHistory(String calculation) {
        if (selectedFunctionIndex == -1) {
            throw new IllegalStateException("No function selected.");
        }
        List<String> calculations = functionHistory.get(selectedFunctionIndex);

        if (calculations != null) {
            calculations.add(calculation);
        } else {
            throw new IllegalStateException("Function history not initialized for the selected function.");
        }
    }

   
    public String calculateSymbolicFirstDerivative() {
        String symbolicResult;

        switch (selectedFunctionIndex) {
             case 0: symbolicResult = "This function can be solved using the power rule. To apply the power rule when solving for a derivative follow the formula:\n n* x^(n-1) . \n So we evaluate x^3 as 3*x^(3-1) and 3x^2 as 3*2x^(2-1) and x as 1*x^(1-1) \n Result: 3x^2+6x-1 \n"; break;
             case 1: symbolicResult = "This function can be solved using the product rule. To apply the product rule when solving for a derivative follow the formula:\n f'(x)*g(x)+g'(x)*f(x).\n So we evaluate by first differentiating (x^3+3x) by using the power rule and multiplying by g(x). So we have (3x^2+3)*(6x+4). Now differentiate (6x+4) using the power rule and multipy by f(x). So we have (6)*(x^3+3x). \n Result: (3x^2 + 3)(6x + 4) + (x^3 + 3x)(6) \n"; break;
             case 2: symbolicResult = "This function can be solved using the chain rule. To apply the chain rule when solving for a derivative follow the formula:\n n*(f(x))^(n-1)* f'(x).\n So we first evaluate by using the power rule so, 3*(4x^2 + 9x)^(3-1). We then multiply by the derivative of the function within the parenthesis which is reffered to as 'chaining out'. This derivative can also be solved using the power rule, 2*4x^(2-1) + 1*9x^(1-1). \n Result: 3(4x^2 + 9x)^2 * (8x + 9) \n"; break;
             case 3: symbolicResult = "This function can be solved using the quotient rule. To apply the quotient rule when solving for a derivative follow the formula:\n [f'(x)*g(x)-g,(x)f(x)]/(g(x))^2. \n So we evaluate by taking the derivative of (x^2+1) by using the power rule so, 2*x^(2-1), and then multiply by g(x) or the function in the denominator. We then subtract the derivative of g(x), 4*x^(1-1), multiplied by f(x) from this. Finally, divide by g(x)^2. \n Result: [(2x)(4x + 3) - (x^2 + 1)(4)] / (4x + 3)^2 \n"; break;
             default: symbolicResult = "Function not recognized for symbolic differentiation. \n"; break;
        }
        System.out.println();

        addCalculationToHistory("First Derivative (Symbolic):\n" + symbolicResult);
        return symbolicResult;
        
    }
    
    public String calculateNumericalFirstDerivative(double x) {
        double h = 1e-5; // Small value for h
        Function<Double, Double> selectedFunction = functions.get(selectedFunctionIndex);
        double derivative = (selectedFunction.apply(x + h) - selectedFunction.apply(x)) / h;

        String result = "Calculating the first derivative numerically at x = " + x + ":\n" +
                "By plugging in " +x+ " into the function"+ "\n" +
                "Result: f'(" + x + ") = " + derivative + " \n ";
                System.out.println();
        addCalculationToHistory(result);
        return result;
    }

    // Method to calculate and display the second derivative as a written expression
    public String calculateSymbolicSecondDerivative() {
        String symbolicResult = switch (selectedFunctionIndex) {
            case 0 -> "This function can be solved using the power rule. To apply the power rule when solving for a derivative follow the formula:\n n* x^(n-1). \n Result: 6x+6 \n" ;
            case 1 -> "This function can be solved using the product rule. To apply the product rule when solving for a derivative follow the formula:\n f'(x)*g(x)+g'(x)*f(x). \n Result: 72x^2+24x+36 \n ";
            case 2 -> "This function can be solved using the chain rule. To apply the chain rule when solving for a derivative follow the formula:\n n*(f(x))^(n-1)* f'(x).\n Result: 6(4x^2+9x)(8x+9)^2 + 24(4x^2+9x)^2 \n ";
            case 3 -> "This function can be solved using the quotient rule. To apply the quotient rule when solving for a derivative follow the formula: \n [f'(x)*g(x)-g,(x)f(x)]/(g(x))^2. \n Result: [(8x+6)(4x+3)^2 - (32x+24)(4x^2+6x-4)] / (4x+3)^4 \n ";
            default -> "Function not recognized for symbolic differentiation. \n ";
        };
        System.out.println();
        addCalculationToHistory("Second Derivative (Symbolic):\n" + symbolicResult);
        return symbolicResult;
    }

    // Method to calculate the second derivative numerically at a specific point
    public String calculateNumericalSecondDerivative(double x) {
        double h = 1e-5; // Small value for h
        Function<Double, Double> selectedFunction = functions.get(selectedFunctionIndex);
        double secondDerivative = (selectedFunction.apply(x + h) - 2 * selectedFunction.apply(x) +
                selectedFunction.apply(x - h)) / (h * h);

        String result = "Calculating the second derivative numerically at x = " + x + ":\n" +
                "By plugging in " +x+ " into the function" + "\n" +
                "Result: f''(" + x + ") = " + secondDerivative;
                System.out.println();
        addCalculationToHistory(result);
        return result;
    }
    
    public String calculateLimit(double x) {
        // Ensure selectedFunctionIndex is valid
        if (selectedFunctionIndex == -1) {
            throw new IllegalStateException("No function selected.");
        }
    
        // Get the selected function
        Function<Double, Double> selectedFunction = functions.get(selectedFunctionIndex);
    
        // Try direct substitution
        double directValue = selectedFunction.apply(x);
    
        if (!Double.isNaN(directValue) && !Double.isInfinite(directValue)) {
            String result = "For this function we can solve for the limit using direct substitution, meaning plugging the desired x value directly into the function and solving. \n Limit at x = " + x + " exists and is approximately: " + directValue;
            addCalculationToHistory(result);
            return result;
        }
    
        // Check for indeterminate form 0/0
        if (isIndeterminate(selectedFunction, x)) {
            return solveUsingLHopital(selectedFunction, x);
        }
    
        // Fallback to numerical approximation
        return calculateLimitNumerically(selectedFunction, x);
    }
    
    // Helper method to detect indeterminate form (0/0)
    private boolean isIndeterminate(Function<Double, Double> function, double x) {
        double numerator = function.apply(x);
        return Math.abs(numerator) < 1e-5; // Treat close to zero as 0
    }
    
    // Solve using L'Hopital's Rule
    private String solveUsingLHopital(Function<Double, Double> function, double x) {
        String derivativeResult = calculateNumericalFirstDerivative(x);

        // Extract the numerical result from the method
        double derivativeValue;
        try {
            String[] parts = derivativeResult.split("Result: f'\\(");
            String valuePart = parts[1].split("\\)")[1].trim();
            derivativeValue = Double.parseDouble(valuePart);
        } 
        catch (Exception e) {
            return "Using L'Hopital's Rule, the limit at x = " + x + " cannot be determined.";
        }

        // Return the result
        if (!Double.isNaN(derivativeValue) && !Double.isInfinite(derivativeValue)) {
         String result = "For this function we can solve for the limit by using L'Hopital's Rule beacuse direct substitution results in an indeterminate form (0/0 or infinity/infinity). To apply this rule just derive the numerator and denominator, treating them as separate functions. Continue to derive teh functions untill direct substituation can be used and there is no longer an indeterminate result. \n The limit at x = " + x + " is approximately: " + derivativeValue;
         addCalculationToHistory(result);
            return result;
        }  
        else {
         return "Using L'Hopital's Rule, the limit at x = " + x + " does not exist.";
        }
    }
    
    // Fallback numerical approximation that uses recursion to solve for limit
    private String calculateLimitNumerically(Function<Double, Double> function, double x, double h, int depth, double tolerance, int maxDepth) {
        // Base case: Exceeded max recursion depth
        if (depth >= maxDepth) {
            return "Numerical approximation: Limit at x = " + x + " does not exist.";
        }
    
        double leftLimit = function.apply(x - h);
        double rightLimit = function.apply(x + h);
    
        // Check if the limits are approximately equal
        if (Math.abs(leftLimit - rightLimit) < tolerance) {
            String result = "Numerical approximation: Limit at x = " + x + " exists and is approximately: " + leftLimit;
            addCalculationToHistory(result);
            return result;
        }
    
        // Recursive case: Reduce `h` and increase depth
        return calculateLimitNumerically(function, x, h / 2, depth + 1, tolerance, maxDepth);
    }

    public String calculateLimitNumerically(Function<Double, Double> function, double x) {
        double initialH = 1e-2;
        double tolerance = 1e-5;
        int maxDepth = 10;
        return calculateLimitNumerically(function, x, initialH, 0, tolerance, maxDepth);
    }

    public String calculateDefiniteIntegral(double a, double b) {
        Function<Double, Double> selectedFunction = functions.get(selectedFunctionIndex);
        int n = 1000; // Number of trapezoids
        double h = (b - a) / n; // Width of each trapezoid
        double sum = 0.0;
    
        // Apply the trapezoidal rule
        for (int i = 0; i < n; i++) {
            double x1 = a + i * h;
            double x2 = a + (i + 1) * h;
            sum += 0.5 * (selectedFunction.apply(x1) + selectedFunction.apply(x2)) * h;
        }
    
        String result = "Definite Integral from " + a + " to " + b + ":\n" +
                "We can solve for a definite integral by using the trapezoidal rule with " + n + " intervals.\n" + "The trapezoidal rule works by approximating the region under the graph of the function f(x) as a trapezoid and calculating its area, following the equation (b-a)*1/2(f(a)+f(b)). \n" +
                "Result: " + sum;
        addCalculationToHistory(result);
        return result;
    }

    public String findInflectionPoints(double start, double end, double step) {
        Function<Double, Double> selectedFunction = functions.get(selectedFunctionIndex);
        double h = 1e-5; // Small value for numerical approximation
    
        List<Double> inflectionPoints = new ArrayList<>();
    
        for (double x = start; x <= end; x += step) {
            // Second derivative approximation using central difference
            double secondDerivative = (selectedFunction.apply(x + h) - 2 * selectedFunction.apply(x) + selectedFunction.apply(x - h)) / (h * h);
    
            // Check for near-zero second derivative
            if (Math.abs(secondDerivative) < 1e-3) {
                // Check for sign change in the concavity
                double prevConcavity = (selectedFunction.apply(x - step) - 2 * selectedFunction.apply(x - 2 * step) + selectedFunction.apply(x - 3 * step)) / (step * step);
                double nextConcavity = (selectedFunction.apply(x + step) - 2 * selectedFunction.apply(x) + selectedFunction.apply(x - step)) / (step * step);
    
                if (Math.signum(prevConcavity) != Math.signum(nextConcavity)) {
                    inflectionPoints.add(x);
                }
            }
        }
    
        // Format the result for output
        StringBuilder result = new StringBuilder("Inflection Points in range [" + start + ", " + end + "] with step " + step + ":\n" + "An inflection point tells you where the function's concavity changes direction. It can be solved for by setting the second derivative of a function equal to 0 and solving for the x values. \n");
        if (inflectionPoints.isEmpty()) {
            result.append("No inflection points found.");
        } else {
            result.append("Inflection points found at: ").append(inflectionPoints);
        }
    
        addCalculationToHistory(result.toString());
        return result.toString();
    }   
     

    public void plotFunction(double start, double end, double step) {
        if (selectedFunctionIndex == -1) {
            throw new IllegalStateException("No function selected.");
        }
    
        // Get the selected function
        Function<Double, Double> selectedFunction = functions.get(selectedFunctionIndex);
    
        // Create a dataset for the function
        XYSeries functionSeries = new XYSeries("Function");
        XYSeries inflectionPointsSeries = new XYSeries("Inflection Points");
    
        for (double x = start; x <= end; x += step) {
            double y = selectedFunction.apply(x);
            functionSeries.add(x, y);
    
            // Check for inflection points
            double h = 1e-5;
            double secondDerivative = (selectedFunction.apply(x + h) - 2 * y + selectedFunction.apply(x - h)) / (h * h);
            if (Math.abs(secondDerivative) < 1e-3) {
                inflectionPointsSeries.add(x, y);
            }
        }
    
        // Create the dataset and chart
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(functionSeries);
        dataset.addSeries(inflectionPointsSeries);
    
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Graph of Function with Inflection Points",
                "X-Axis",
                "Y-Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    
        // Annotate the chart with inflection points
        XYPlot plot = chart.getXYPlot();
        for (int i = 0; i < inflectionPointsSeries.getItemCount(); i++) {
            double x = inflectionPointsSeries.getX(i).doubleValue();
            double y = inflectionPointsSeries.getY(i).doubleValue();
            XYTextAnnotation annotation = new XYTextAnnotation("Inflection Point (" + x + ", " + y + ")", x, y);
            plot.addAnnotation(annotation);
        }
    
        // Display the chart in a Swing window
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Function Graph");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new ChartPanel(chart));
            frame.pack();
            frame.setVisible(true);
        });
    }


    public void saveResultsToFile(String firstName, String lastName) {
        String fileName = firstName + "_" + lastName + "_CalculusResults.txt";
        String path = "C:\\CalcResults";
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path, fileName)))) {
            writer.write("Calculus Results for " + firstName + " " + lastName + ":\n");
            writer.write("=========================================\n");
    
            // Retrieve function descriptions
            List<String> functionDescriptions = getFunctions();
    
            for (Map.Entry<Integer, List<String>> entry : functionHistory.entrySet()) {
                int functionIndex = entry.getKey();
                List<String> calculations = entry.getValue();
    
                // Use the human-readable function description
                String functionDescription = functionDescriptions.get(functionIndex);
    
                writer.write("Selected Function: " + functionDescription + "\n");
                for (String calculation : calculations) {
                    writer.write(calculation + "\n");
                }
                writer.write("\n");
            }
    
            writer.write("End of Results\n");
            System.out.println("Results successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving results to file: " + e.getMessage());
        }

        
        //String path = "C:\\CalcResults";

        /*File file = new File(fileName);
        
        

        File[] files = dir.listFiles();
        File dir = new File();Z*/

        
    }
}
