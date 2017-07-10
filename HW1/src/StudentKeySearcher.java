import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentKeySearcher extends ReadController{
	private final String key;

	public StudentKeySearcher(String key, RandomAccessFile din) {
		super(din);
		this.key = key;
	}

	@Override
	public void print() throws IOException {
		boolean isPrintFirstLine = false;
		
		for (Student s : students){
			if (s == null) {
				return;
			}
			
			if (s.address.contains(key)) {
				isPrintFirstLine = true;
				s.printOneStudent();
			}
		}
		
		if (!isPrintFirstLine){
			System.out.println("not exist address");
		}
	}

}
