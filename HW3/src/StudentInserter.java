import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class StudentInserter extends ReadController{
	private final RandomAccessFile din;
	private final File textFile;

	public StudentInserter(String textFileName, RandomAccessFile din) throws IllegalArgumentException {
		super(din);
		this.din = din;
		this.textFile = new File(textFileName);
	}

	@Override
	public void print() throws IOException {
		if (!textFile.exists()) {
			System.out.println(textFile+" does not exist");
			System.exit(0);
		}
	
		LinkedHashMap<String, Student> inputStudentList = getInputStudents();
	
		LinkedList<Student> deletedList = 
				updateStudentsAndGetDeletedList(inputStudentList);
		if (deletedList == null) {
			return;
		}
		
		insertAtDeletedList(inputStudentList, deletedList);
		
		insertAtFileEnd(inputStudentList);
	}
	
	public static void printUpdateStudent(Student student) {
		System.out.print("update : ");
		student.printOneStudent();	
	}
	
	private LinkedHashMap<String, Student> getInputStudents() throws IOException {
		LinkedHashMap<String, Student> inputStudentList = new LinkedHashMap<>();
		
		try(FileReader fileReader = new FileReader(textFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader);) {
			Scanner in = new Scanner(bufferedReader);
			
			while(in.hasNext()) {
				Student student = new Student();
				student.getOneStudent(in);
				student.file_size = student.toString().getBytes("utf-8").length;
				inputStudentList.put(student.number, student);
			}
		} catch (IOException ioe) {
			throw ioe;
		}
		
		return inputStudentList;
	}
	
	private LinkedList<Student> updateStudentsAndGetDeletedList(
			LinkedHashMap<String, Student> inputStudentList) throws IOException {
		LinkedList<Student> deletedList = new LinkedList<>();
		
		for (Student s : students) {
			if (s == null) {
				return null;
			}
			if (s.isDeleted()){
				deletedList.add(s);
			} else if(inputStudentList.containsKey(s.number)){
				Student inputStudent = inputStudentList.get(s.number);
				
				long currentPointer = din.getFilePointer();
				
				if (inputStudent.file_size <= s.file_size) {
					inputStudent.file_size = s.file_size;
					inputStudent.file_pointer = s.file_pointer;
				} else {
					inputStudent.file_pointer = din.length();
					s.addDeleteMarker();
					s.updateOneStudentAtPoint(din);
				}
				
				inputStudentList.remove(inputStudent.number);
				inputStudent.updateOneStudentAtPoint(din);
		
				din.seek(currentPointer);
				printUpdateStudent(inputStudent);
			}
		}
		
		return deletedList;
	}
	
	private void insertAtDeletedList(LinkedHashMap<String, Student> inputStudentList,
			LinkedList<Student> deletedList) {
		deletedList
		.forEach(deletedStudent -> {
			if(inputStudentList.isEmpty()) {
				return;
			}
			
			Set<String> studentKeySet = inputStudentList.keySet();
			
			Student inputStudent = null;
			for(String studentKey : studentKeySet) {
				Student student = inputStudentList.get(studentKey);
				
				if (deletedStudent.file_size >= student.file_size) {
					inputStudent = student;
					break;
				}
			}
			
			if (inputStudent != null) {
				inputStudent.file_size = deletedStudent.file_size;
				inputStudent.file_pointer = deletedStudent.file_pointer;
				inputStudent.updateOneStudentAtPoint(din);
				inputStudentList.remove(inputStudent.number);
				printUpdateStudent(inputStudent);
			}
		});
	}
	
	private void insertAtFileEnd(LinkedHashMap<String, Student> inputStudentList) throws IOException {
		if(!inputStudentList.isEmpty()) {
			for(Student s : inputStudentList.values()) {
				s.file_pointer = din.length();
				s.updateOneStudentAtPoint(din);
				printUpdateStudent(s);
			}
		}	
	}
}
