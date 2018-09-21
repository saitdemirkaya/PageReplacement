import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Assignment2 {

	public static void main(String[] args) throws Exception {
		Process process = new Process();/*for all operations*/
		File file = new File(args[0]);/*input.txt*/
		PrintWriter writer = null;
		writer = new PrintWriter(new FileOutputStream("output.txt"));
        BufferedReader reader = new BufferedReader(new FileReader(file));
      	process.file_read(reader,file,writer);     	
	}
}