package Assignment03;

import java.util.Objects;

public class Author {
    private int authorId;
    private String name;
    private Book[] booksWritten;
    private int bookCount;

    public Author(int authorId, String name) {
        this.authorId = authorId;
        this.name = name;
        this.booksWritten = new Book[10];
        this.bookCount = 0;
    }

    public Author(int authorId, String name, int maxBooks) {
        this(authorId, name);
        this.booksWritten = new Book[maxBooks];
    }

    public void addBook(Book book) {
        if (this.bookCount < this.booksWritten.length) {
            this.booksWritten[this.bookCount] = book;
            this.bookCount++;
        }
    }

    public void displayAuthorInfo() {
        System.out.println("ID: " + this.authorId);
        System.out.println("Name: " + this.name);
        for (int i = 0; i < this.bookCount; i++) {
            System.out.println(this.booksWritten[i].getTitle());
        }
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book[] getBooksWritten() {
        return booksWritten;
    }

    public void setBooksWritten(Book[] booksWritten) {
        this.booksWritten = booksWritten;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    @Override
    public String toString() {
        return "Author [authorId=" + authorId + ", name=" + name + ", bookCount=" + bookCount + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Author other = (Author) obj;
        return this.authorId == other.authorId;
    }
}