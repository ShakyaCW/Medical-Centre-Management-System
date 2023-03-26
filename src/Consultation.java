import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Consultation implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;

    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private LocalTime time;
    private int cost;
    private int duration;

    private String consultationId;

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDuration() {return duration;}

    public void setDuration(int duration) {this.duration = duration;}

    public Consultation(Doctor doctor, Patient patient, LocalDate date, LocalTime time, int duration, String consultationId, int cost) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.consultationId = consultationId;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "doctor=" + doctor +
                ", patient=" + patient +
                ", date=" + date +
                ", time=" + time +
                ", duration=" + duration +
                ", Consultation Id=" + consultationId +
                ", Cost=" + cost +
                '}';
    }
}
