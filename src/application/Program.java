package application;

import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> list = new ArrayList<>();

        System.out.print("Enter file path: ");
        String sourceFileStr = sc.nextLine();

        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent();

        boolean success = new File(sourceFolderStr + "\\out").mkdir();

        String targetFile = sourceFolderStr + "\\out\\summary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {

            String line = br.readLine();

            while (line != null) {

                String[] fields = line.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                list.add(new Product(name, price, quantity));

                line = br.readLine();

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))) {
                    for (Product product : list) {
                        bw.write(product.getName() + "," + String.format("%.2f", product.totalPrice()));
                        bw.newLine();
                    }

                    System.out.println(targetFile + "CREATED!");
                } catch (IOException e) {
                    System.out.println("Error message: " + e.getMessage());
                }

            }
        } catch (IOException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        sc.close();
    }
}
