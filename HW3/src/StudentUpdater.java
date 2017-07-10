import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentUpdater extends ReadController{
	private final static String NOT_CHANGED = "-";
	
	private final RandomAccessFile din;
	private final String number;
	private final String name;
	private final char gender;
	private final String phoneNumber;
	private final String address;
	

	public StudentUpdater(RandomAccessFile din, String[] arguments)throws IllegalArgumentException {
		super(din);
		this.number = arguments[0];
		this.name = arguments[1];
		this.gender= arguments[2].charAt(0);
		this.phoneNumber = arguments[3];
		this.address = arguments[4];
		this.din = din;
	}

	@Override
	public void print() throws IOException {
		boolean isPrintFirstLine = false;
		
		for (Student s : students) {
			if (s == null) {
				return;
			}
			
			if (!s.isDeleted() && s.number.equals(number)) {
				isPrintFirstLine = !isPrintFirstLine;
				
				try {
					Student student = (Student) s.clone();
					if (!NOT_CHANGED.equals(name)) {
						student.name = name;
					}
					if (!NOT_CHANGED.equals(gender)) {
						student.gender = gender;
					}
					if (!NOT_CHANGED.equals(phoneNumber)) {
						student.phone_no = phoneNumber;
					}
					if (!NOT_CHANGED.equals(address)) {
						student.address = address;
					}
				
					int length = student.toString().getBytes("utf-8").length;

					if (length <= s.file_size) {
						student.updateOneStudentAtPoint(din);
					} else { //long
						student.file_size = length;
						student.file_pointer = din.length();
						
						s.addDeleteMarker();
						s.updateOneStudentAtPoint(din);
						student.updateOneStudentAtPoint(din);
					}
				
					System.out.print("updated : ");
					student.printOneStudent();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		if (!isPrintFirstLine){
			System.out.println("not exist number");
		}	
	}

}
