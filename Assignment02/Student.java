import java.util.Arrays;
import java.util.Objects;

public class Student { // Encapsualtion
    private String name;
    private int age;
    private int[] grades;

    // Constructors
    Student() {
        this.age = 0;
        this.name = "Unknown";
        this.grades = new int[3];
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.grades = new int[3];
    }

    Student(String name, int age, int[] grades) {
        this.name = name;
        this.age = age;
        this.grades = grades;
    }


    // Setters & Getters
    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }
    
    public int[] getGrades(){
        return this.grades;
    }

    public void setGrades(int grade1, int grade2, int grade3){ //Just implemeted if the user wants to add grades one-by-one onstead of one array. Can be a example of Overloading
        this.grades[0] = grade1;
        this.grades[1] = grade2;
        this.grades[2] = grade3;
    }

    public double calculateAverage() { // Helper method for StudentService. Just to make things little easier
        double total = 0;
        for (int i : this.grades) {
            total += i;
        }

        return(total/3);
    }
    
    // Overriding of equals(), toString(), and hashCode()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass() || obj == null) return false; //This case can only be passed if the object is a memeber of Student class

        Student input = (Student) obj;

        return (
            (input.age == this.age) &&
            (input.name.equals(this.name)) &&
            (input.grades.equals(this.grades))
        );
    }

    @Override
    public int hashCode(){
        int hash = Objects.hash(name, age);
        int result = 31*hash + Arrays.hashCode(grades); //Manual implementation for grades array as the Objects.hash() method only looks at the address of array not all its elements individually
        return result;
    }
    
    @Override
    public String toString() {
        return("{Name: "+this.name
        +"\nAge: "+this.age
        +"\nGrades: "+Arrays.toString(this.grades)+"}");
    }
}
