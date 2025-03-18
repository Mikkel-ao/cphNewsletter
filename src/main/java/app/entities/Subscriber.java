package app.entities;

public class Subscriber {
    private String email;
    private String created;

    public Subscriber(String email, String created) {
        this.email = email;
        this.created = created;
    }

    public Subscriber(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "email='" + email + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
