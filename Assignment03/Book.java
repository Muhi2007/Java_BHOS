package Assignment03;

public class Book {
    private int bookId;
    private String title;
    private Author[] authors;
    private double price;
    private boolean isBorrowed;
    private int year;
    private String publisher;
    private int authorCount;

    public Book(int bookId, String title, double price) {
        this.bookId = bookId;
        this.title = title;
        this.authors = new Author[5];
        this.authorCount = 0;
        setPrice(price);
    }

    public Book(int bookId, String title, double price, int maxAuthors) {
        this(bookId, title, price);
        this.authors = new Author[maxAuthors];
        setPrice(price);
    }

    public void setPrice(double price) {
        if (price < 0) {
            this.price = 0.0;
        } else {
            this.price = price;
        }
    }

    public void addAuthor(Author author) {
        if (this.authorCount < this.authors.length) {
            this.authors[this.authorCount] = author;
            this.authorCount++;
            author.addBook(this);
        }
    }

    public void displayBookInfo() {
        System.out.println("ID: " + this.bookId);
        System.out.println("Title: " + this.title);
        System.out.println("Price: " + this.price);
        for (int i = 0; i < this.authorCount; i++) {
            System.out.println(this.authors[i].getName());
        }
    }

    public void getReferenceAPA() {
        if (this.authorCount > 0) {
            System.out.println(this.authors[0].getName() + ". (" + this.year + "). " + this.title + ". " + this.publisher + ".");
        }
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author[] getAuthors() {
        return authors;
    }

    public void setAuthors(Author[] authors) {
        this.authors = authors;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", title=" + title + ", price=" + price + "]";
    }
}