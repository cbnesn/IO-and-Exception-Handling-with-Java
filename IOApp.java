import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class IOApp {

    static String fileName="transactions.txt";
    static int limitRecord= 100;
    static Record[] records;
    static int lastIndex;

    static class Record {
        String name;
        Integer price, number;
    }

    public static void main(String[] args) throws Exception{
        initialProcess();
        menu();
    }


    private static void menu() throws IOException{

        System.out.println(
                "\n-----------------MENU------------------" +
                        "\n------------1-ADD RECORD---------------" +
                        "\n------------2-REMOVE RECORD------------" +
                        "\n------------3-SEARCH RECORD------------" +
                        "\n------------4-LIST ALL-----------------" +
                        "\n------------5-DELETE ALL---------------" +
                        "\n------------6-CLOSE--------------------" +
                        "\nPlease make your choice: ");


        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();


        if (choice == 1) {

            System.out.println("Please enter name: ");
            String name = input.next();

            System.out.println("Please enter price: ");
            int price = input.nextInt();

            System.out.println("Please enter number: ");
            int number = input.nextInt();

            addRecord(name, price, number);
            update();
            System.out.println("Record is added. ");
            menu();

        } else if (choice == 2) {

            System.out.println("Please enter name: ");
            String name = input.next();

            if (removeRecord(name)) {

                lastIndex--;
                update();
                System.out.println("Record is removed. ");

            } else {
                System.out.println("Record is not available. ");

            }
            menu();

        } else if (choice == 3) {
            System.out.println("Please enter name: ");
            String name = input.next();
            searchRecord(name);
            menu();

        } else if (choice == 4) {
            System.out.println("Records:");

            listRecord();
            menu();

        } else if (choice == 5) {

            System.out.println("All records are going to delete. Are you sure ? (y/n)");
            String answer = input.next();

            if (answer.equalsIgnoreCase("y")) {
                deleteAll();
                update();

            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("Transferring to the menu. ->");
            } else {
                System.out.println("Try again. ");

            }
            menu();
        } else if (choice == 6) {

            System.out.println("System is exited.");
            System.exit(0);
        } else {
            System.out.println("\nThere's no process like " + choice + ". Please try again.");
            menu();
        }
    }



    private static void listRecord(){

        for (int i=0; i<lastIndex; i++) {
            System.out.println(records[i].name + " " +  records[i].price + " " + records[i].number);

        }
    }



    private static void addRecord(String name, Integer price, Integer number){

        Record recentRecord = new Record();
        recentRecord.name=name;
        recentRecord.price=price;
        recentRecord.number=number;

        records[lastIndex]=recentRecord;
        lastIndex++;


    }



    private static void update() throws IOException {

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));

        for (int i = 0; i < lastIndex; i++) {
            bufferedOutputStream.write((records[i].name + "\t\t" + records[i].price + "\t" + records[i].number + "\n").getBytes());
        }
        bufferedOutputStream.close();
    }


    private static boolean removeRecord(String name) {

        boolean duration = false;
        int i;
        for (i = 0; i < lastIndex; i++) {
            if (records[i].name.equalsIgnoreCase(name)) {
                duration = true;
                break;
            }

        }

        records[i]=new Record();
        int j;

        for (j = i ; j<lastIndex-i ; j++){
            records[j]=records[j+1];
        }
        records[j]=new Record();

        return duration;
    }



    private static void searchRecord(String name){

        boolean duration = false;
        int i;
        for (i = 0 ;i<lastIndex ; i++){
            if (records[i].name.equalsIgnoreCase(name)){
                duration = true;
                break;
            }
        }

        if (duration) {
            System.out.println(records[i].name + "\t\t" + records[i].price + "\t\t" + records[i].number);
        } else {
            System.out.println("Record is not available!");
        }


    }

    private static void deleteAll() {

        records=new Record[limitRecord];

        for (int i=0; i<limitRecord; i++)
        {
            records[i]=new Record();
        }

        System.out.println("All records are deleted.");

        lastIndex=0;

    }



    private static void initialProcess(){

        records=new Record[limitRecord];
        for(int i=0 ; i<limitRecord; i++){

            records[i]= new Record();
        }

        try{
            Reader reader = new InputStreamReader(new FileInputStream(fileName), "Windows-1254");
            BufferedReader br = new BufferedReader(reader);
            String strLine;
            int i=0;
            while((strLine=br.readLine())!= null){

                StringTokenizer tokens = new StringTokenizer(strLine, "\t");
                String [] t = new String[3];
                int j=0;

                while(tokens.hasMoreTokens()){
                    t[j]=tokens.nextToken();
                    j++;
                }
                records[i].name=t[0];
                records[i].price=Integer.valueOf(t[1]);
                records[i].number=Integer.valueOf(t[2]);
                i++;
            }

            lastIndex=i;
            reader.close();

        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}