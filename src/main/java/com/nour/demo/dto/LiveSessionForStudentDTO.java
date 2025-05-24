package com.nour.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveSessionForStudentDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private Integer durationInMinutes;
    private String meetingLink;
    private Integer availableSeats;
    private String courseTitle;

   public Long getId() {
      return id;
}

 public void setId(Long id) {
   this.id = id;
    }

 public String getTitle() {
   return title;
   }

   public void setTitle(String title) {
       this.title = title;
 }

    public String getDescription() {
    return description;
  }

    public void setDescription(String description) {
     this.description = description;
    }

     public LocalDateTime getStartTime() {
       return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
      this.startTime = startTime;
   }

    public Integer getDurationInMinutes() {
         return durationInMinutes;
     }

     public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
  }

    public String getMeetingLink() {
         return meetingLink;
     }

    public void setMeetingLink(String meetingLink) {
         this.meetingLink = meetingLink;
    }



    public String getCourseTitle() {
     return courseTitle;
     }

     public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
    }

}