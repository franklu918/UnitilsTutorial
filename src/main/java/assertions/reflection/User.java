package assertions.reflection;

/**
 * @author Sergey Ponomarev
 */
public class User {

    long id;
    String first;
    String last;
    Address address;

    public User(long id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public User(long id, String first, String last, Address address) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.address = address;
    }
}
