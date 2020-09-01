package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args){

        //uaktualnienie tablicy z danymi pobranymi z pliku
        dataInsideFile = writeFromFileMethod(s);     // przypisujemy wynik do zmiennej dataInsideFile

        // wyświetlenie menu
        menuMethod();

    }
    // kolory czcionki
    public static final String blue = "\u001b[34m";
    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[31m";

    public static void menuMethod(){
        String choice;
        do {
            //System.out.println();
            System.out.println("\n" + blue + "Please select an option:" + reset);
            System.out.println("add");
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            Scanner scan = new Scanner(System.in);
            choice = scan.nextLine();

            switch (choice) {
                case "add":
                    addMethod( filePath);
                    break;
                case "remove":
                    removeMethod(dataInsideFile);
                    break;
                case "list":
                    listMethod(dataInsideFile);
                    break;
                case "exit":
                    System.out.println(red + "bye bye" + reset);
                    break;
                default:
                    System.out.println("Wybierz poprawną opcję");
            }
        }while (!choice.equals("exit"));
    }

    // ścieżka do pliku
    static final String filePath = "/home/majk/Repositories/TaskManager/src/pl/coderslab/tasks.csv";
    static String[][] dataInsideFile;
    static final String s = "/home/majk/Repositories/TaskManager/src/pl/coderslab/tasks.csv";

    // zapisanie do talicy dwuwymiarowej danych z pliku
    public static String[][] writeFromFileMethod(String filePath){
        Path path = Paths.get(s);                           // ścieżka do pliku
        String str = "";                                    // pusta zmienna str do zapisania nowej linii
        List<String[]> line = new ArrayList<String[]>();    // tworzymy listę argumentów tablicy string
        String[][] arr = null;                              // tworzymy pustą tablicę dwuwymairową typu string
        try {
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path))); // czytanie tekstu z pliku
            while((str = br.readLine()) !=null){            // pobieramy kolejne linie jeśli są do zmiennej str
                line.add(str.split("'"));             //zapisujemy kolejne wczytane linie do zmiennej line
            }
            arr = new String[line.size()][0];               // określamy wielkość tablicy
            line.toArray(arr);                              // kopiujemy tablicę line do tablicy arr
            //for(int i=0;i<arr.length;i++){
            //for(int j=0;j<arr[i].length;j++) {
            //System.out.println(i+1 +": " + arr[i][j]);
            //}
            //}

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return arr;
    }

    // wyświetlenie wszystkich zadań znajdujących się w pliku
    public static void listMethod(String[][] arr){
        for(int i=0;i< arr.length;i++){
            System.out.print(i + ": ");
            for(int j=0;j<arr[i].length;j++){
                System.out.println(arr[i][j]);
            }
        }
    }

    // dodawanie nowego zadania
    public static void addMethod(String path){

        // wpisanie danych nowego zadania
        Scanner scan = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        System.out.println("Please add task description: ");
        String first = scan.nextLine();
        System.out.println("Please add task due date: ");
        String second = scan.nextLine();
        System.out.println("Is your task is important: true/false");
        String third = scan.nextLine();

        // złącznie ich w jedną linię i dodanie do nowej tablicy powiększonej o 1
        sb.append(first).append(", ").append(second).append(", ").append(third);
        String newLine = sb.toString();
        dataInsideFile = Arrays.copyOf(dataInsideFile, dataInsideFile.length+1);
        dataInsideFile[dataInsideFile.length-1] = new String[1];
        dataInsideFile[dataInsideFile.length-1][0] =newLine;

        // zapisanie nowej tablicy do pliku
        try (FileWriter fileWriter = new FileWriter(path, true)){
            fileWriter.append("\n");
            fileWriter.append(newLine);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    // usuwanie zadania z pliku
    public static void removeMethod(String[][]arr){
        Path path = Paths.get(filePath);
        Scanner scan = new Scanner(System.in);
        System.out.println("Select number to remove: ");
        int input = scan.nextInt();
        if (input >= 0 && input < arr.length) {

            // wykorzystanie klasy ArrayUtils do usunięcia zadania
            try {
                dataInsideFile = ArrayUtils.remove(arr, input);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Nie ma takiego elementu");
            }
        }

        // zapisanie nowej tablicy w pliku, (zapisanie do jednej metody i wywoływanie przy remove i add)
        String[] lines = new String[dataInsideFile.length];
        for(int i=0;i< dataInsideFile.length;i++){
            lines[i] = String.join(",",arr[i]);
        }
        try{
            Files.write(path,Arrays.asList(lines));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

///home/majk/Repositories/KRA_JEE_W_06_JEE_Podstawy_Java/src/main/java/pl/coderslab/files/tasks.csv