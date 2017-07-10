import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

public abstract class ReadController {
	protected final Iterable<Student> students;
	
	public ReadController(RandomAccessFile din) throws IllegalArgumentException{
		students = new Iterable<Student>() {
			
			@Override
			public Iterator<Student> iterator() {
				return new Iterator<Student>() {

					@Override
					public boolean hasNext(){
						try {
							return din.length() != din.getFilePointer();
						} catch (IOException e) {
							System.out.println("file I/O error..");
							return false;
						}
					}

					@Override
					public Student next() {
						Student s = new Student();
						s.readOneStudent(din);
						return s;
					}
				};
			}
		};
	}
	
	public abstract void print() throws IOException;
}
