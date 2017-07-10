import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentPrinter extends ReadController{
	private final int lineNumber;
	private final int maxStudentNumber;

	public StudentPrinter(int maxStudentNumber, int lineNumber, RandomAccessFile din) {
		super(din);
		this.lineNumber = lineNumber;
		this.maxStudentNumber = maxStudentNumber;
	}

	@Override
	public void print() throws IOException {
		int lineCount = 1;
		boolean isPrintFirstLine = false;
		
		if (lineNumber >= 0 || maxStudentNumber >= lineNumber) {
			for (Student s : students){
				if (s == null) {
					return;
				}
				
				if (lineNumber == 0 || lineNumber == lineCount) {
					if (!isPrintFirstLine) {
						isPrintFirstLine = !isPrintFirstLine;
					}
					s.printOneStudent();
					if (lineNumber == lineCount) {
						break;
					}
				}
				
				lineCount++;
			}
		}
		
		if (!isPrintFirstLine){
			System.out.println("illegal size number");
		}
	}


}
