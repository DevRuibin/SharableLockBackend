package org.example.lock;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PatchRequest {
    private EventType eventType;
    private String name;
    private String picture;
    private Float power;
    private Boolean online;
    private Boolean locked;
    private Float latitude;
    private Float longitude;
    private Integer reportBattery;
    private Integer ReportLocation;
    private Long managerId;
    private Long userId;
}
