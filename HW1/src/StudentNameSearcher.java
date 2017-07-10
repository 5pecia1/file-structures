import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentNameSearcher extends ReadController{
	private final String name;

	public StudentNameSearcher(String name, RandomAccessFile din) {
		super(din);
		this.name = name;
	}

	@Override
	public void print() throws IOException {
		boolean isPrintFirstLine = false;
		
		for (Student s : students) {
			if (s == null) {
				return;
			}
			
			if (s.name.equals(name)) {
				isPrintFirstLine = true;
				s.printOneStudent();
			}
		}
		
		if (!isPrintFirstLine){
			System.out.println("not exist name");
		}	
	}

}
