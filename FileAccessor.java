import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.awt.Desktop;
//import java.awt.List;
import java.io.IOException;

public class FileAccessor {
    private Scanner scnr;

    public FileAccessor() {
        new File("C:\\CalcResults").mkdirs();
        this.scnr = new Scanner(System.in);
    }

    public void searchName(File[] directory, String fileName) {
        String dir = "C:\\CalcResults";
        String fullPath;
        boolean notFound = true;
        for (int i = 0; i < directory.length; i++) {
            if (directory[i].getName().equals(fileName)) {
                System.out.println(fileName + ": Index " + i);
                notFound = false;
                break;
            }
        }
        if (notFound) {
            System.out.println(fileName + " was not found.");
        }
        else {
            fullPath = dir + File.separator + fileName;
            try {
                File file = new File(fullPath); 
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openFile(File[] list) {
        String dir = "C:\\CalcResults" + File.separator;
        
        System.out.print("Enter index number: ");
        int index = scnr.nextInt();

        @SuppressWarnings("rawtypes")
        List fList = new ArrayList<>(Arrays.asList(list));
        System.out.println("\nOpenning " + fList.get(index));

            try {
                File file = new File(dir + ((File) fList.get(index)).getName()); 
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
