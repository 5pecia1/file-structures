import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Scanner;

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
						int size;
						
						try{
							size = din.readInt();
							byte[] bytes = new byte[size];
							din.read(bytes);
							String str = new String(bytes, "utf-8");
							Scanner buffer = new Scanner(str);
							buffer.useDelimiter("\\||\n");
							
							s.getOneStudent(buffer);
						} catch (UnsupportedEncodingException e) {
							System.out.println("Unsupported Char-set");
						} catch (IOException ioe) {
							System.out.println("file I/O error..");
							return null;
						}
					
						return s;
					}
				};
			}
		};
	}
	
	public abstract void print() throws IOException;
}
