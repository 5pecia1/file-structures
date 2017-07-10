import java.io.*;
import java.util.Arrays;

public class ManageStudent {
	public static void main(String[] args) {
		if(args.length < 3 ) {
			System.out.println("USAGE: java ReadStudents INPUT_DAT_FILE MODE [ARGUMENT...]");
			System.exit(0);
		}
		
		
		String input_file = args[0];
		String mode = args[1];
		String[] arguments = Arrays.copyOfRange(args, 2, args.length);
		
		String format = input_file.substring(input_file.lastIndexOf("_") + 1);
		if (!"dat".equals(format)){
			System.out.println("illegal file format");
			System.exit(0);
		}
		
		
		File in_f = new File(input_file);
		if(!in_f.exists()) {
			System.out.println(input_file+" does not exist");
			System.exit(0);
		}
		
		try (RandomAccessFile din = new RandomAccessFile(in_f, "rw")){
			ReadController readController = null;
			try {
				readController = ReadControllerFactory.getReadController( mode, arguments, din);
			} catch (IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
				System.out.println("USAGE: java ReadStudents INPUT_DAT_FILE MODE [ARGUMENT...]");
				System.exit(0);
			}
			
			try {
				readController.print();
			} catch (IOException err) {
				System.out.println("file I/O error..");
			}
		} catch (IOException e) {
			System.out.println("file error ...");
		} 	
	}
}