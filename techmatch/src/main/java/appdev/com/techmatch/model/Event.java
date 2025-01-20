package appdev.com.techmatch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDate endDate;

    private LocalTime endTime;

    private String location;

    @Lob
    private String description;

    private String ticketType;

    private boolean requiresApproval;

    private int capacity;

    private String type;

    @ElementCollection
    private List<String> topics;

    @Lob
    private byte[] image; // Store image as a binary blob

    @Transient
    private String base64Image; // Store image as a base64 string

    // Getters and setters
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}

