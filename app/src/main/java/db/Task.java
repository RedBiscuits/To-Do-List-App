package db;

public class Task {
    private String description;
    private int status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Task(String description, int status) {
        this.description = description;
        this.status = status;
    }
}
