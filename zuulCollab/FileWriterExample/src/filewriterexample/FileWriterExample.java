/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filewriterexample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Chris
 */
public class FileWriterExample
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        File f = new File ("Tekst.txt");
        if (! f.exists())
        {
            JOptionPane.showMessageDialog(null, "File does not exist", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(2);
        }
        PrintWriter pw = new PrintWriter(new FileWriter("Tekst.txt", true));

        boolean done = false;

        while (!done)
        {
            
            System.out.println("Enter some text: ");
            String s = sc.nextLine();;
            if (s.equals(""))
            {
                done = true;
            }
            else
            {
                System.out.println("Enter a number:");
                int number = sc.nextInt();
                sc.skip("\n");
                
                pw.printf("%-20s %3d\n", s, number);
            }
        }
        pw.close();
    }
}
