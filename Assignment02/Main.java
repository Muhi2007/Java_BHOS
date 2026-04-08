public class Main {
    public static void main(String[] args) {
        StudentService service = new StudentService();
        Student[] students = new Student[3];

        students[0] = service.createStudent("Peter Parker", 18, new int[] {88,87,90});
        students[1] = service.createStudent("Tony Stark", 38, new int[] {99,100,97});
        students[2] = service.createStudent("Bruce Banner", 40, new int[] {92,94,96});

        System.out.println("-----------All Students-----------");
        for (Student s : students) { // Implementation of for-each loop
            System.out.println(s + "\nAverage: "+ s.calculateAverage() + '\n');
        }

        System.out.printf("-----------Class Average-----------\n%.2f\n", service.getClassAverage(students));
        

        System.out.println("--------------Top Student-------------\n" + service.getTopStudent(students));
    }
}
