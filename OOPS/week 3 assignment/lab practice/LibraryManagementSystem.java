class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    private static int totalBooks = 0;
    private static int availableBooks = 0;
    private static int bookCounter = 1;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.bookId = generateBookId();
        this.isAvailable = true;
        totalBooks++;
        availableBooks++;
    }

    private static String generateBookId() {
        return String.format("B%03d", bookCounter++);
    }

    public void issueBook() {
        if (isAvailable) {
            isAvailable = false;
            availableBooks--;
            System.out.println(title + " has been issued.");
        } else {
            System.out.println(title + " is not available.");
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            availableBooks++;
            System.out.println(title + " has been returned.");
        } else {
            System.out.println(title + " was not issued.");
        }
    }

    public void displayBookInfo() {
        System.out.println("\n--- Book Info ---");
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Available: " + isAvailable);
    }

    public String getBookId() {
        return bookId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public static int getTotalBooks() {
        return totalBooks;
    }

    public static int getAvailableBooks() {
        return availableBooks;
    }
}

class Member {
    private String memberId;
    private String memberName;
    private String[] booksIssued;
    private int bookCount;

    private static int memberCounter = 1;

    public Member(String memberName, int maxBooks) {
        this.memberName = memberName;
        this.memberId = generateMemberId();
        this.booksIssued = new String[maxBooks];
        this.bookCount = 0;
    }

    private static String generateMemberId() {
        return String.format("M%03d", memberCounter++);
    }

    public void borrowBook(Book book) {
        if (book.isAvailable() && bookCount < booksIssued.length) {
            book.issueBook();
            booksIssued[bookCount++] = book.getBookId();
            System.out.println(memberName + " borrowed " + book.getBookId());
        } else {
            System.out.println("Cannot borrow book. Either not available or limit reached.");
        }
    }

    public void returnBook(String bookId, Book[] books) {
        for (int i = 0; i < bookCount; i++) {
            if (booksIssued[i].equals(bookId)) {
                // Shift remaining books in array
                for (int j = i; j < bookCount - 1; j++) {
                    booksIssued[j] = booksIssued[j + 1];
                }
                booksIssued[--bookCount] = null;

                for (Book b : books) {
                    if (b.getBookId().equals(bookId)) {
                        b.returnBook();
                        System.out.println(memberName + " returned " + bookId);
                        return;
                    }
                }
            }
        }
        System.out.println("Book " + bookId + " not found in " + memberName + "'s issued list.");
    }

    public void displayMemberInfo() {
        System.out.println("\n--- Member Info ---");
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + memberName);
        System.out.print("Books Issued: ");
        if (bookCount == 0) {
            System.out.println("None");
        } else {
            for (int i = 0; i < bookCount; i++) {
                System.out.print(booksIssued[i] + " ");
            }
            System.out.println();
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Book[] books = new Book[5];
        books[0] = new Book("The Hobbit", "J.R.R. Tolkien");
        books[1] = new Book("1984", "George Orwell");
        books[2] = new Book("The Catcher in the Rye", "J.D. Salinger");
        books[3] = new Book("Pride and Prejudice", "Jane Austen");
        books[4] = new Book("Moby Dick", "Herman Melville");

        Member[] members = new Member[2];
        members[0] = new Member("ganesh", 3);
        members[1] = new Member("Bhavana", 2);

        members[0].borrowBook(books[0]);
        members[0].borrowBook(books[1]);
        members[1].borrowBook(books[1]); // already borrowed
        members[1].borrowBook(books[2]);

        members[0].displayMemberInfo();
        members[1].displayMemberInfo();

        System.out.println("\nTotal Books: " + Book.getTotalBooks());
        System.out.println("Available Books: " + Book.getAvailableBooks());

        members[0].returnBook("B001", books);
        members[0].displayMemberInfo();

        books[0].displayBookInfo();
    }
}
