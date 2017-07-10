import java.io.*;
import java.util.Scanner;

public class WriteStudents {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("USAGE: java WriteStudents INPUT_TEXT_FILE");
			System.exit(0);
		}
		String	input_file = args[0];
		
		try {
			File in_f = new File(input_file);
			if(!in_f.exists()) {
				System.out.println(input_file+" does not exist");
				System.exit(0);
			}
			
			FileReader fr = new FileReader(in_f);
			BufferedReader br = new BufferedReader(fr);
			Scanner in = new Scanner(br);

			RandomAccessFile dout = new RandomAccessFile(input_file+"_dat", "rw");
			Student s = new Student();
			
			while(in.hasNext()) {
				s.getOneStudent(in);
				s.storeOneStudent(dout);
			}
			System.out.println("File Size : "+dout.length());
			br.close();
			dout.close();
		} catch (IOException e) {
			System.out.println("file error ...");
		} 
	}
}