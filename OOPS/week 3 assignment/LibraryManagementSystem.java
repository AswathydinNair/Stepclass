import java.util.*;

class Book {
    String bookId;
    String title;
    String author;
    String isbn;
    String category;
    boolean isIssued;
    String issueDate;
    String dueDate;
    int issueCount;

    public Book(String bookId, String title, String author, String isbn, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isIssued = false;
        this.issueDate = null;
        this.dueDate = null;
        this.issueCount = 0;
    }

    @Override
    public String toString() {
        return bookId + " - " + title + " by " + author + " (Category: " + category + ", ISBN: " + isbn + ")";
    }
}

class Member {
    String memberId;
    String memberName;
    String memberType;
    List<Book> booksIssued;
    double totalFines;
    String membershipDate;

    static int totalBooks = 0;
    static int totalMembers = 0;
    static String libraryName = "Mystery Library";
    static double finePerDay = 2.0;
    static int maxBooksAllowed = 3;

    public Member(String memberId, String memberName, String memberType, String membershipDate) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberType = memberType;
        this.booksIssued = new ArrayList<>();
        this.totalFines = 0.0;
        this.membershipDate = membershipDate;
        totalMembers++;
    }

    public boolean issueBook(Book book, String issueDate, String dueDate) {
        int allowed = getBorrowLimit();
        if (booksIssued.size() >= allowed) {
            System.out.println(memberName + " cannot borrow more than " + allowed + " books.");
            return false;
        }
        if (book.isIssued) {
            System.out.println("Book already issued.");
            return false;
        }
        book.isIssued = true;
        book.issueDate = issueDate;
        book.dueDate = dueDate;
        book.issueCount++;
        booksIssued.add(book);
        return true;
    }

    public boolean returnBook(String bookId, String returnDate) {
        for (Book book : booksIssued) {
            if (book.bookId.equals(bookId)) {
                double fine = calculateFine(book.dueDate, returnDate);
                totalFines += fine;
                book.isIssued = false;
                book.issueDate = null;
                book.dueDate = null;
                booksIssued.remove(book);
                System.out.println("Book returned. Fine: " + fine);
                return true;
            }
        }
        System.out.println("Book not found in issued list.");
        return false;
    }

    public boolean renewBook(String bookId, String newDueDate) {
        for (Book book : booksIssued) {
            if (book.bookId.equals(bookId)) {
                book.dueDate = newDueDate;
                System.out.println("Book renewed. New due date: " + newDueDate);
                return true;
            }
        }
        return false;
    }

    public void reserveBook(Book book) {
        if (book.isIssued) {
            System.out.println(memberName + " reserved book: " + book.title);
        } else {
            System.out.println("Book is available. No need to reserve.");
        }
    }

    private double calculateFine(String dueDate, String returnDate) {
        int due = Integer.parseInt(dueDate.replace("-", ""));
        int ret = Integer.parseInt(returnDate.replace("-", ""));
        if (ret > due) {
            int overdueDays = ret - due;
            return overdueDays * finePerDay;
        }
        return 0;
    }

    private int getBorrowLimit() {
        switch (memberType.toLowerCase()) {
            case "student": return 3;
            case "faculty": return 5;
            case "general": return 2;
            default: return maxBooksAllowed;
        }
    }

    public static void generateLibraryReport(List<Book> books, List<Member> members) {
        System.out.println("\n=== " + libraryName + " Report ===");
        System.out.println("Total Books: " + books.size());
        System.out.println("Total Members: " + members.size());
        System.out.println("Issued Books: " + books.stream().filter(b -> b.isIssued).count());
        System.out.println("Total Fines Collected: " + members.stream().mapToDouble(m -> m.totalFines).sum());
    }

    public static List<Book> getOverdueBooks(List<Book> books, String todayDate) {
        List<Book> overdue = new ArrayList<>();
        int today = Integer.parseInt(todayDate.replace("-", ""));
        for (Book b : books) {
            if (b.isIssued && Integer.parseInt(b.dueDate.replace("-", "")) < today) {
                overdue.add(b);
            }
        }
        return overdue;
    }

    public static List<Book> getMostPopularBooks(List<Book> books, int count) {
        books.sort((a, b) -> Integer.compare(b.issueCount, a.issueCount));
        return books.subList(0, Math.min(count, books.size()));
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("01", "Java Programming", "James Gosling", "167283", "Programming"));
        books.add(new Book("02", "Python Basics", "Guido van Rossum", "67890", "Programming"));
        books.add(new Book("03", "Data Structures", "Robert Lafore", "103876", "CS"));
        books.add(new Book("04", "Database Systems", "Elmasri", "632737", "Database"));

        List<Member> members = new ArrayList<>();
        Member m1 = new Member("01", "Aswathy", "Student", "2022-01-10");
        Member m2 = new Member("02", "ashvini", "Faculty", "2022-02-15");
        members.add(m1);
        members.add(m2);

        m1.issueBook(books.get(0), "20250101", "20250105");
        m2.issueBook(books.get(1), "20250102", "20250106");

        m1.returnBook("01", "20250110"); // late return → fine

        m1.renewBook("02", "20250115"); // renew attempt (fails if not issued)

        m1.reserveBook(books.get(1));

        Member.generateLibraryReport(books, members);

        List<Book> overdue = Member.getOverdueBooks(books, "20250108");
        System.out.println("\nOverdue Books:");
        for (Book b : overdue) System.out.println(b);

        List<Book> popular = Member.getMostPopularBooks(books, 2);
        System.out.println("\nMost Popular Books:");
        for (Book b : popular) System.out.println(b);
    }
}
