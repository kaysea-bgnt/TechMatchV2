package appdev.com.techmatch.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Event {
    
    @Id
    private String eventID;

    private String eventName;
    private String description;
    private String location;
    private String eventType;
    private String eventTopics; // Store topics as a comma-separated string
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String organization;
    private boolean isFree;
    private int capacity;

    @Lob
    private byte[] eventImage; // Store image binary data

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user; // Foreign key to User table

    // Getters and Setters
    public String getEventID() { return eventID; }
    public void setEventID(String eventID) { this.eventID = eventID; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getEventTopics() { return eventTopics; }
    public void setEventTopics(String eventTopics) { this.eventTopics = eventTopics; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }
    public boolean isFree() { return isFree; }
    public void setFree(boolean free) { isFree = free; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public byte[] getEventImage() { return eventImage; }
    public void setEventImage(byte[] eventImage) { this.eventImage = eventImage; }
    public User getUser() { return user; } 
    public void setUser(User user) { this.user = user; }

}
