package Assignment03;

public class Main {
    public static void main(String[] args) { // Just for testing purposes
        Author author1 = new Author(1, "Elxan Elatli.");
        Book book1 = new Book(1001, "Elekeçmez Yaguar", 45.50);
        
        book1.setYear(2020);
        book1.setPublisher("TeasPress");
        book1.addAuthor(author1);
        
        Library library = new Library("Central Library", 50);
        library.addBook(book1);
        
        System.out.println("--- Author Info ---");
        author1.displayAuthorInfo();
        
        System.out.println("\n--- APA Reference ---");
        book1.getReferenceAPA();
        
        System.out.println("\n--- Search Results ---");
        library.searchBookByAuthor("Elxan Elatli.");
        
        System.out.println("\nAverage Book Price: $" + library.getAveragePrice());
        
        library.removeBook(1001);
    }
}