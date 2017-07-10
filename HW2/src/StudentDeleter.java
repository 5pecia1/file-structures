import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentDeleter extends ReadController{
	private final static String PRINT_ALL_DELETED_RECORD = "0";
	private final String number;
	private final RandomAccessFile din;
	
	public StudentDeleter(String number, RandomAccessFile din) throws IllegalArgumentException {
		super(din);
		this.number = number;
		this.din  = din;
	}

	@Override
	public void print() throws IOException {
		int lineCount = 1;
		boolean isPrintFirstLine = false;
		
		for(Student s : students) {
			if(s == null) {
				return;
			}
				
			if (s.number.equals(number)) {
				isPrintFirstLine = true;
				s.addDeleteMarker();
				s.updateOneStudentAtPoint(din);
				System.out.println("success");
			} else if (PRINT_ALL_DELETED_RECORD.equals(number)) {
				if (s.isDeleted()) {
					isPrintFirstLine = true;
					System.out.format("[%d] 삭제된 공간 %d 바이트\n", 
							lineCount, s.file_size);
				}
			}
			lineCount++;
		}
		
		if (!isPrintFirstLine){
			System.out.println("illegal student number");
		}	
	}

}
