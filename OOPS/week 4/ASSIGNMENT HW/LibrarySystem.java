class Book {
    String title;
    String author;
    String isbn;
    boolean isAvailable;

    Book() {
        this.title = "";
        this.author = "";
        this.isbn = "";
        this.isAvailable = true;
    }

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isbn = "N/A";
        this.isAvailable = true;
    }

    Book(String title, String author, String isbn, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
    }

    void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Book borrowed: " + title);
        } else {
            System.out.println("Book already borrowed: " + title);
        }
    }

    void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("Book returned: " + title);
        } else {
            System.out.println("Book was not borrowed: " + title);
        }
    }

    void displayBookInfo() {
        System.out.println("Book Info");
        System.out.println("Title      : " + title);
        System.out.println("Author     : " + author);
        System.out.println("ISBN       : " + isbn);
        System.out.println("Available  : " + (isAvailable ? "Yes" : "No"));
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Book b1 = new Book();
        Book b2 = new Book("1984", "George Orwell");
        Book b3 = new Book("To Kill a Mockingbird", "Harper Lee", "978-0061120084", true);

        b1.displayBookInfo();
        b2.displayBookInfo();
        b3.displayBookInfo();

        b2.borrowBook();
        b2.displayBookInfo();

        b2.returnBook();
        b2.displayBookInfo();

        b3.borrowBook();
        b3.borrowBook();
    }
}
