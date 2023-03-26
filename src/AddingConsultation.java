import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddingConsultation extends JFrame implements ActionListener {
    JButton addConsultation;
    JLabel availability;
    JButton checkAvailability;
    DatePicker datePicker;
    JComboBox<String> hoursBox;
    JLabel availabilityField;
    JLabel instruction;
    JPanel rowOne;
    JPanel rowTwo;
    JPanel rowThree;
    JPanel rowFour;
    JPanel rowFive;
    JPanel rowSix;
    Font font1 = new Font("Ariel", Font.BOLD, 16);
    int firstSelectedDoctor = ViewTable.selectedDoctor;
    Doctor selectedDoctor = WestminsterSkinConsultationManager.doctors.get(firstSelectedDoctor);
    public static LocalDate lookingDate;
    public  static LocalTime time;

    public AddingConsultation(){

        Container container = getContentPane();
        container.setLayout(new GridLayout(6,1,10,10));
//        container.setLayout(new FlowLayout());

        rowOne = new JPanel();
        rowOne.setLayout(new FlowLayout());

        instruction = new JLabel("Select the date and time and check the availability of the doctor");
        instruction.setFont(font1);
        rowOne.add(instruction);
        add(rowOne);

        rowTwo = new JPanel();
        rowTwo.setLayout(new FlowLayout());

        JLabel dateLabel = new JLabel("Date  ");
        rowTwo.add(dateLabel);

        datePicker = new DatePicker();
        rowTwo.add(datePicker);

        JLabel timeLabel = new JLabel("           Time  ");
        rowTwo.add(timeLabel);

        Integer[] hours = new Integer[15];
        String[] hours2 = new String[15];
        for (int x=8; x<=22; x++){
            hours[x-8] = x;
            hours2[x-8] = ""+x+".00";
        }
        hoursBox = new JComboBox<>(hours2);
        rowTwo.add(hoursBox);

        add(rowTwo);

        rowThree = new JPanel();
        rowThree.setLayout(new FlowLayout());

        checkAvailability = new JButton("Check Availability of Dr. "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getName()+" "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getSurname()+"");
        checkAvailability.setActionCommand("Check Availability");
        rowThree.add(checkAvailability);

        add(rowThree);

        rowFour = new JPanel();
        rowFour.setLayout(new FlowLayout());

        availability = new JLabel();
        availability.setFont(font1);
        rowFour.add(availability);
        add(rowFour);

        rowFive = new JPanel();
        rowFive.setLayout(new FlowLayout());

        availabilityField = new JLabel();
        availabilityField.setFont(font1);
        availabilityField.setVisible(false);
        rowFive.add(availabilityField);
        add(rowFive);

        rowSix = new JPanel();
        rowSix.setLayout(new FlowLayout());

        addConsultation = new JButton("Add Consultation");
        addConsultation.setActionCommand("Add Consultation");
        addConsultation.setVisible(false);
        rowSix.add(addConsultation);
        add(rowSix);

        checkAvailability.addActionListener(this);
        addConsultation.addActionListener(this);


    }

    public static void main(String[] args) {
        AddingConsultation addingConsultation = new AddingConsultation();
        addingConsultation.setSize(650,350);
        addingConsultation.setLocationRelativeTo(null);
        addingConsultation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addingConsultation.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonLabel = e.getActionCommand();

        switch (buttonLabel){

            case "Check Availability" -> {
                lookingDate = datePicker.getDate();
                if (lookingDate == null){
                    JOptionPane.showMessageDialog(null, "Please select a date !", "Date field is empty", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

                int hour = hoursBox.getSelectedIndex()+8;
                time = LocalTime.of(hour, 0);

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime localDateTime = LocalDateTime.now();
                String t = localDateTime.format(format);
                LocalDateTime dateTime = LocalDateTime.parse(t, format);
                String t2 = lookingDate+" "+time;
                LocalDateTime lookingDateTime = LocalDateTime.parse(t2, format);



                if (lookingDateTime.isBefore(dateTime)) {
                    availability.setVisible(false);
                    addConsultation.setVisible(false);
                    availabilityField.setVisible(false);
                    JOptionPane.showMessageDialog(null, "The date or time you have selected has already passed.\nPlease select a valid date and time !", "Invalid date or time", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }



                if (WestminsterSkinConsultationManager.consultations.get(firstSelectedDoctor).size() == 0){
                    availability.setText("Dr. "+selectedDoctor.getName()+" "+selectedDoctor.getSurname()+" is available at that time.");
                    addConsultation.setText("Add consultation for Dr. "+selectedDoctor.getName()+" "+selectedDoctor.getSurname()+"");
                    availability.setForeground(Color.BLACK);
                    availability.setVisible(true);
                    addConsultation.setVisible(true);}

                else {
                    boolean doctorIsAvailable = true;
                    int startHour;
                    int endHour;
                    for (Consultation consultation : WestminsterSkinConsultationManager.consultations.get(firstSelectedDoctor)){
                        startHour = consultation.getTime().getHour();
                        endHour = consultation.getTime().getHour()+consultation.getDuration();

                        if (!consultation.getDate().equals(lookingDate) || (!consultation.getTime().equals(time) && !(hour>startHour && hour<endHour)))  {
                            continue;
                        }
                        doctorIsAvailable = false;
                    }

                    if (doctorIsAvailable) {
                        availability.setText("Dr. "+selectedDoctor.getName()+" "+selectedDoctor.getSurname()+" is available at that time.");
                        availability.setForeground(Color.BLACK);
                        addConsultation.setText("Add consultation for Dr. "+selectedDoctor.getName()+" "+selectedDoctor.getSurname()+"");
                        addConsultation.setVisible(true);
                        availabilityField.setVisible(false);
                        availability.setVisible(true);
                    }

                    else {
                        availability.setText("Dr. "+selectedDoctor.getName()+" "+selectedDoctor.getSurname()+" is not available at that time.\nTry a different date or time.");
                        availability.setForeground(Color.RED);
                        addConsultation.setVisible(false);
                        availabilityField.setVisible(false);
                        availability.setVisible(true);
                        ArrayList<Integer> availableDoctorIndexes = new ArrayList<>();
                        int doctorIndex = 0;
                        for (Doctor doctor : WestminsterSkinConsultationManager.doctors) {
                            if (!Objects.equals(doctor.getMedicalLicenceNumber(), selectedDoctor.getMedicalLicenceNumber()) && doctor.getSpecialisation().equals(selectedDoctor.getSpecialisation())){
                                boolean newDoctorIsAvailable = true;
                                int newStartHour;
                                int newEndHour;
                                for (Consultation consultation : WestminsterSkinConsultationManager.consultations.get(doctorIndex)){
                                    newStartHour = consultation.getTime().getHour();
                                    newEndHour = consultation.getTime().getHour()+consultation.getDuration();
                                    if (!consultation.getDate().equals(lookingDate) || (!consultation.getTime().equals(time) && !(hour>newStartHour && hour<newEndHour))) {
                                        continue;
                                    }
                                    newDoctorIsAvailable = false;
                                }
                                if(newDoctorIsAvailable){
                                    availableDoctorIndexes.add(doctorIndex);
//                                    System.out.println(selectedDoctor.getMedicalLicenceNumber());
                                }

                            }
                            doctorIndex++;
                        }
//                        System.out.println(availableDoctorIndexes);
                        int randomDoctor = (int) (Math.random()*((availableDoctorIndexes.size() - 1) +1)+0);
                        if (availableDoctorIndexes.size() != 0){
                            ViewTable.selectedDoctor = availableDoctorIndexes.get(randomDoctor);
                            addConsultation.setText("Add consultation for Dr. "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getName()+" "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getSurname()+" ");
                            addConsultation.setVisible(true);
                            availabilityField.setText("You can add a consultation for Dr. "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getName()+" "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getSurname()+" who is available at that time.");
                            availabilityField.setVisible(true);
                        }

                    }
                }
            }
            case "Add Consultation" -> {
                addConsultation.setVisible(false);
                PatientDetails.main(null);
            }
        }

    }

    public static void saveFile(String fileName) throws IOException {
        File file = new File(fileName); // Creating new file.
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        for (Doctor newDoctor : WestminsterSkinConsultationManager.doctors) { // Writing all objects in doctors arraylist to the text file.
            objOut.writeObject(newDoctor);
        }
        System.out.println("\nData saved successfully\n");
    }

    public static void saveConsultations(String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        for (ArrayList<Consultation> doctorConsultations : WestminsterSkinConsultationManager.consultations){
            objOut.writeObject(doctorConsultations);
        }
    }

}
