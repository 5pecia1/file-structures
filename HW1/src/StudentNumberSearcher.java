import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentNumberSearcher extends ReadController{
	private final String studentNumber;

	public StudentNumberSearcher(String studentNumber, RandomAccessFile din) throws IllegalArgumentException {
		super(din);
		this.studentNumber = studentNumber;
	}

	@Override
	public void print() throws IOException {
		boolean isPrintFirstLine = false;
	
		for (Student s : students) {
			if (s == null) {
				return;
			}
			
			if (s.number.equals(studentNumber)) {
				isPrintFirstLine = !isPrintFirstLine;
				s.printOneStudent();
				break;
			}
		}
		
		if (!isPrintFirstLine){
			System.out.println("not exist number");
		}	
	}

}
