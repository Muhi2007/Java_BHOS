public class StudentService {
    
    public Student createStudent(String name, int age, int[] grades) {
        Student newStu = new Student(name, age, grades);
        return newStu;
    }

    public void printAllStudents(Student[] students) {
        for (Student i: students) {
            System.out.println(i.toString() + '\n');
        }
    }

    public Student getTopStudent(Student[] students) {
        Student top = students[0];

        for (Student i : students) {
            if (top.calculateAverage() < i.calculateAverage()) {
                top = i;
            }
        }

        return top;
    }

    public double getClassAverage(Student[] students) {
        int total = 0;

        for (Student i : students) {
            total += i.calculateAverage();
        }

        return ((double)total/students.length);
    }

    public double calculateAverage(int[] grades) {// Overloading
        int total = 0;

        for (int grade: grades) {
            total += grade;
        }

        return((double)total/grades.length);
    }

    public double calculateAverage(Student student) {// Overloading
        return student.calculateAverage();
    }
}
