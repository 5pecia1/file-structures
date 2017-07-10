import java.io.*;
import java.util.Scanner;

public class Student implements Cloneable {
	private final static char DELETE_MARKER = '*';
	
	public      long        file_pointer;
	public      int         file_size;
	public 		String 		number;
	public		String		name;
	public 		char		gender;
	public		String		phone_no;
	public 		String 		address;
	
	public void getOneStudent (Scanner in) {
		number = in.next();
		name = in.next();
		gender = in.next().charAt(0);
		phone_no = in.next();
		address = in.next();
		
		in.nextLine();
	}
	
	public boolean isDeleted() {
		return number.charAt(0) == DELETE_MARKER;
	}
	
	public void addDeleteMarker() {
		number = "*" + number.substring(1);
	}
	
	public void printOneStudent() {
		System.out.println("["+number+"] "+name+"("+((gender == 'm') ? "남자":"여자")+") 전화번호: "+phone_no+", 주소: "+address);
	}
	
	public void updateOneStudentAtPoint(RandomAccessFile din) {
		try {
			byte[] bytes = toString().getBytes("utf-8");
			din.seek(file_pointer);
			din.writeInt(file_size);
			din.write(bytes);
		} catch (UnsupportedEncodingException e1) {
			System.out.println("Unsupported charset");
		} catch (IOException e) {
			System.out.println("file error ...");
		}
	}
	
	public	void storeOneStudent(RandomAccessFile dout) {
		try {
			file_size = toString().getBytes("utf-8").length;
			System.out.print("["+file_pointer+","+ file_size + "] ");
			
			printOneStudent();
			
			file_pointer = dout.getFilePointer();
			updateOneStudentAtPoint(dout);
		} catch (IOException e) {
			System.out.println("file error ...");
		}
	}
	
	public int	readOneStudent(RandomAccessFile din) {
		int size = -1;
		try {
			file_pointer = din.getFilePointer();
			if(din.length() == file_pointer)// if EOF
				return size;
			
			file_size = size = din.readInt();
			
			byte[] bytes = new byte[size];
			din.read(bytes);
			String str = new String(bytes,"utf-8");
			Scanner buffer = new Scanner(str);
			buffer.useDelimiter("\\||\n");
			
			getOneStudent(buffer);
			//System.out.print(bytes.length+"] ");
			//printOneStudent();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported Char-set");
		} catch (IOException err) {
			System.out.println("file I/O error..");
		}
		return size;
	}
	
	public String toString() {
		
		return number+"|"+name+"|"+gender+"|"+phone_no+"|"+address+"|";
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}	