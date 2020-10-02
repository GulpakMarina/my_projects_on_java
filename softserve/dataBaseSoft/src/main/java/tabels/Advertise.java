package tabels;

import java.time.LocalDate;

public class Advertise {
    private int id;
    private String title;
    private String description;
    private LocalDate dateCreation;
    private LocalDate dateUpdate;
    //private User user;
    private int userId;

    public Advertise(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public LocalDate getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDate dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Advertise(String title, String description, LocalDate dateCreation, int userId) {
        this.title = title;
        this.description = description;
        this.dateCreation = dateCreation;
        this.dateUpdate = dateCreation;
        this.userId = userId;
    }

    public Advertise(int id, String title, String description, LocalDate dateCreation, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreation = dateCreation;
        this.dateUpdate = dateCreation;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Advertise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateUpdate=" + dateUpdate +
                ", userId=" + userId +
                '}';
    }
}
