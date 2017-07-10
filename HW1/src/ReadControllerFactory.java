import java.io.RandomAccessFile;

public class ReadControllerFactory {
	public static ReadController getReadController(String inputFile, String mode, String argument, RandomAccessFile din) throws IllegalArgumentException{
		if (mode == null || argument == null) {
			throw new IllegalArgumentException("command or arugment is null");
		}
		
		ReadController controller = null;
		switch (mode) {
		case "p" :
			try {
			String fileName = inputFile.substring(0, inputFile.lastIndexOf("."));
			String maxCount = fileName.substring(fileName.lastIndexOf("_") + 1);
			Integer maxStudentNumber = new Integer(maxCount);
			Integer lineNumber = new Integer(argument);
			controller = new StudentPrinter(maxStudentNumber, lineNumber, din);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("illegal number format");
			}
			break;
		case "s" :
			controller = new StudentNumberSearcher(argument, din);
			break;
		case "n" :
			controller = new StudentNameSearcher(argument, din);
			break;
		case "a" :
			controller = new StudentKeySearcher(argument, din);
			break;
		default : 
			throw new IllegalArgumentException("illegal command");
		}
		
		return controller;
	}
}
