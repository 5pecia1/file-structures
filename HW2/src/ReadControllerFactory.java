import java.io.RandomAccessFile;

public class ReadControllerFactory {
	public static ReadController getReadController(
			String mode, String[] arguments, RandomAccessFile din) 
					throws IllegalArgumentException {
		ReadController controller = null;
		switch (mode) {
		case "p" :
			checkOneArguments(arguments);
			try {
				Integer lineNumber = new Integer(arguments[0]);
				controller = new StudentPrinter(lineNumber, din);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("illegal number format");
			}
			break;
		case "s" :
			checkOneArguments(arguments);
			controller = new StudentNumberSearcher(arguments[0], din);
			break;
		case "n" :
			checkOneArguments(arguments);
			controller = new StudentNameSearcher(arguments[0], din);
			break;
		case "d" :
			checkOneArguments(arguments);
			controller = new StudentDeleter(arguments[0], din);
			
			break;
		case "u" :
			if (arguments.length != 5) {
				throw new IllegalArgumentException("illegal command");
			}
			
			controller = new StudentUpdater(din, arguments);
			break;
		default : 
			throw new IllegalArgumentException("illegal command");
		}
		
		return controller;
	}
	
	private static void checkOneArguments(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("illegal command");
		}
	}
}
