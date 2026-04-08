package Assignment03;

public class Library {
    private String libraryName;
    private Book[] inventory;
    private int inventoryCount;

    public Library(String libraryName, int capacity) {
        this.libraryName = libraryName;
        this.inventory = new Book[capacity];
        this.inventoryCount = 0;
    }

    public void addBook(Book book) {
        if (this.inventoryCount < this.inventory.length) {
            this.inventory[this.inventoryCount] = book;
            this.inventoryCount++;
        }
    }

    public void removeBook(int bookId) {
        int targetIndex = -1;
        
        for (int i = 0; i < this.inventoryCount; i++) {
            if (this.inventory[i].getBookId() == bookId) {
                targetIndex = i;
                break;
            }
        }
        
        if (targetIndex != -1) {
            for (int i = targetIndex; i < this.inventoryCount - 1; i++) {
                this.inventory[i] = this.inventory[i + 1];
            }
            this.inventory[this.inventoryCount - 1] = null;
            this.inventoryCount--;
        }
    }

    public void searchBookByAuthor(String authorName) {
        for (int i = 0; i < this.inventoryCount; i++) {
            Author[] authors = this.inventory[i].getAuthors();
            for (int j = 0; j < authors.length; j++) {
                if (authors[j] != null && authors[j].getName().equals(authorName)) {
                    this.inventory[i].displayBookInfo();
                    break;
                }
            }
        }
    }

    public double getAveragePrice() {
        if (this.inventoryCount == 0) {
            return 0.0;
        }
        
        double total = 0.0;
        for (int i = 0; i < this.inventoryCount; i++) {
            total += this.inventory[i].getPrice();
        }
        
        return total / this.inventoryCount;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public Book[] getInventory() {
        return inventory;
    }

    public void setInventory(Book[] inventory) {
        this.inventory = inventory;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @Override
    public String toString() {
        return "Library [libraryName=" + libraryName + ", inventoryCount=" + inventoryCount + "]";
    }
}