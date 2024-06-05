import java.util.Scanner;


public class Interface {

    boolean loop = false;

    public void main(String[] args) {
        while (!loop) {
            System.out.println("Welcome to Shape Identifier.");
            System.out.println("First, please import your file.");
            //call the readFile method
            Scanner s1 = new Scanner(System.in);
            String fileType = s1.nextLine();
            System.out.println("Are you using a psd or an image file? If psd, type 'PSD'. If image, type 'IMAGE'.");
            //method to store file type...different obj? or just text.
            System.out.println("-------->");

            System.out.println("-------->");
            System.out.println("Would you like to process another image? If yes type 1, if no type 0.");
            if (s1.nextInt() == 1) {
                loop = true;
            } else if (s1.nextInt() != 0) {
                System.out.println("Invalid input. Please try again");
            } else {
                loop = false;
            }
        }
    }
}
        /*
    public static void readFile(String[] args) {
        try {
            File myObj = new File("filename.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Unable to read file. Please try again.");
            e.printStackTrace();
        }
    }


}
*/


