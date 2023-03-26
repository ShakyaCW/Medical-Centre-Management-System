import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.Key;
import java.util.Objects;

public class ViewConsultation extends JFrame implements ActionListener {

    JLabel heading, consultId, heading2, heading3, viewFName, viewLName, viewDob, viewMobileNo, viewDoc, viewDate, viewTime, duration, image, infoLabel, noImage, noText, imageLabel, textLabel, viewEmail, viewPatientId;
    JTextField consultIdField;
    JButton viewConsult;
    JPanel firstRow, secondRow;
    Font font1 = new Font("Ariel", Font.BOLD, 20);
//    Font font2 = new Font("Ariel", Font.BOLD, 13);
    Font font3 = new Font("Ariel", Font.BOLD, 15);
    boolean correctDetailsEntered;

    public ViewConsultation() {
        Container container = getContentPane();
        container.setLayout(new GridLayout(2, 1));

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(2, 1));

        firstRow = new JPanel();
        firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.PAGE_AXIS));

        JPanel headingRow = new JPanel();
        heading = new JLabel("Enter your consultation number.");
        heading.setFont(font1);
        headingRow.add(heading);
        firstRow.add(headingRow, BorderLayout.PAGE_START);

        JPanel idRow = new JPanel();
        consultId = new JLabel("Consultation Number : ");
        idRow.add(consultId);
        consultIdField = new JTextField(10);
        idRow.add(consultIdField);
        firstRow.add(idRow, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        viewConsult = new JButton("View Consultation");
        viewConsult.setActionCommand("View");
        btnPanel.add(viewConsult);
        firstRow.add(btnPanel, BorderLayout.CENTER);
        cardPanel.add(firstRow);

        secondRow = new JPanel();
        secondRow.setLayout(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        heading2 = new JLabel("                     Consultation Details");
        heading2.setVisible(false);
        heading2.setFont(font1);
        leftPanel.add(heading2);

        leftPanel.setLayout(new GridLayout(6, 1));
        viewDoc = new JLabel();
        leftPanel.add(viewDoc);
        viewDate = new JLabel();
        leftPanel.add(viewDate);
        viewTime = new JLabel();
        leftPanel.add(viewTime);
        duration = new JLabel();
        leftPanel.add(duration);
        viewLName = new JLabel();
        leftPanel.add(viewLName);
        secondRow.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6, 1));
        heading3 = new JLabel("                   Patient Details");
        heading3.setVisible(false);
        heading3.setFont(font1);
        rightPanel.add(heading3);

        viewFName = new JLabel();
        rightPanel.add(viewFName);
        viewEmail = new JLabel();
        rightPanel.add(viewEmail);
        viewPatientId = new JLabel();
        rightPanel.add(viewPatientId);
        viewDob = new JLabel();
        rightPanel.add(viewDob);
        viewMobileNo = new JLabel();
        rightPanel.add(viewMobileNo);
        secondRow.add(rightPanel);
        cardPanel.add(secondRow);
        add(cardPanel);

        JPanel underPanel = new JPanel();
        underPanel.setLayout(new GridLayout(1, 2));
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(new EmptyBorder(50,10,10,10));
        imagePanel.setLayout(new FlowLayout());
        imageLabel = new JLabel("                Image");
        imageLabel.setVisible(false);
        imageLabel.setFont(font1);
        imagePanel.add(imageLabel);
        noImage = new JLabel();
        noImage.setVisible(false);
        imagePanel.add(noImage);
        image = new JLabel();
        imagePanel.add(image);
        underPanel.add(imagePanel);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        textPanel.setBorder(new EmptyBorder(10,0,220,0));
        GridBagConstraints containerConstrains = new GridBagConstraints();
        containerConstrains.anchor = GridBagConstraints.WEST;
        containerConstrains.insets = new Insets(10, 10, 10, 10);
        containerConstrains.gridy = 0;
        containerConstrains.gridx = 0;
        textLabel = new JLabel("Text Info");
        textLabel.setVisible(false);
        textLabel.setFont(font1);
        textPanel.add(textLabel, containerConstrains);
        containerConstrains.gridy = 1;
        noText = new JLabel();
        noText.setVisible(false);
        textPanel.add(noText,containerConstrains);
        infoLabel = new JLabel();
        infoLabel.setFont(font3);
        textPanel.add(infoLabel,containerConstrains);
        underPanel.add(textPanel);
        add(underPanel);

        viewConsult.addActionListener(this);
        consultIdField.addActionListener(this);


    }

    public static void main(String[] args) {
        ViewConsultation viewConsultation = new ViewConsultation();
        viewConsultation.setSize(800, 800);
        viewConsultation.setLocationRelativeTo(null);
        viewConsultation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewConsultation.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        String buttonLabel = event.getActionCommand();
        if ("View".equals(buttonLabel)) {
            correctDetailsEntered = false;
            if (consultIdField.getText().isEmpty()) {
                heading2.setVisible(false);
                heading3.setVisible(false);
                imageLabel.setVisible(false);
                textLabel.setVisible(false);
                viewDoc.setVisible(false);
                viewDate.setVisible(false);
                viewTime.setVisible(false);
                duration.setVisible(false);
                viewFName.setVisible(false);
                viewMobileNo.setVisible(false);
                viewLName.setVisible(false);
                viewDob.setVisible(false);
                image.setVisible(false);
                infoLabel.setVisible(false);
                noImage.setVisible(false);
                noText.setVisible(false);
                viewPatientId.setVisible(false);
                viewEmail.setVisible(false);
                JOptionPane.showMessageDialog(null, "Please enter your consultation number !", "Consultation number field is empty", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String consultationId = consultIdField.getText();

            for (Consultation consultation : WestminsterSkinConsultationManager.allConsultations) {
                if (Objects.equals(consultation.getConsultationId(), consultationId)) {
                    correctDetailsEntered = true;
                    viewPatientId.setVisible(true);
                    viewEmail.setVisible(true);
                    heading2.setVisible(true);
                    heading3.setVisible(true);
                    imageLabel.setVisible(true);
                    textLabel.setVisible(true);
                    viewDoc.setVisible(true);
                    viewDate.setVisible(true);
                    viewTime.setVisible(true);
                    duration.setVisible(true);
                    viewFName.setVisible(true);
                    viewMobileNo.setVisible(true);
                    viewLName.setVisible(true);
                    viewDob.setVisible(true);
                    viewDoc.setText("                              Doctor : Dr. " + consultation.getDoctor().getName() + " " + consultation.getDoctor().getSurname() + " ");
                    viewDate.setText("                              Date : " + consultation.getDate() + " ");
                    viewTime.setText("                              Starting Time : " + consultation.getTime() + " ");
                    duration.setText("                              Duration : " + consultation.getDuration() + "H");
                    viewFName.setText("                      Patient Name : " + consultation.getPatient().getName() + " " + consultation.getPatient().getSurname() + " ");
                    viewMobileNo.setText("                      Contact No : " + consultation.getPatient().getMobileNumber() + " ");
                    viewLName.setText("                              Price : £" + consultation.getCost() + " ");
                    viewDob.setText("                      Patient DOB : " + consultation.getPatient().getDateOfBirth() + " ");
                    viewPatientId.setText("                      Patient ID : "+consultation.getPatient().getPatientId()+" ");
                    viewEmail.setText("                      Email : "+consultation.getPatient().getEmail()+"");
                    try {
                        // Read the encrypted JPG file into a byte array
                        try {
                            FileInputStream inFile = new FileInputStream(".\\ConsultationImages\\" + consultationId + "\\" + consultation.getPatient().getName() + ".jpg");
                            byte[] encryptedData = new byte[inFile.available()];
                            inFile.read(encryptedData);
                            inFile.close();

                            // Initialize the Cipher object in decryption mode, using the key
                            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

                            Key secretKey = new SecretKeySpec(PatientDetails.key.getBytes(), "DES");
                            cipher.init(Cipher.DECRYPT_MODE, secretKey);

                            // Decrypt the encrypted data
                            byte[] decryptedData = cipher.doFinal(encryptedData);

                            ImageIcon photo = new ImageIcon(decryptedData);
                            Image image1 = photo.getImage(); // transform it
                            Image newImage = image1.getScaledInstance(240, 180, Image.SCALE_SMOOTH);
                            ImageIcon newImgIcon = new ImageIcon(newImage);
                            image.setIcon(newImgIcon);
                            image.setVisible(true);
                            noText.setVisible(false);
                            noImage.setVisible(false);
                        } catch (Exception e) {
                            image.setVisible(false);
                            noImage.setVisible(true);
                            noImage.setText("                                         No Saved Image");
                        }

                        try {
                            infoLabel.setVisible(true);
                            File textInput = new File(".\\TextInfo\\" + consultationId + ".txt");
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(textInput));
                            String text = bufferedReader.readLine();
                            char[] textChar = text.toCharArray();
                            for (int i = 0; i < textChar.length; i++) {
                                textChar[i] -= PatientDetails.textKey;
                            }
                            infoLabel.setText(String.valueOf(textChar));
                        } catch (Exception e) {
                            infoLabel.setVisible(false);
                            noText.setVisible(true);
                            noText.setText("No Saved Text Info");
                        }
                    } catch (Exception e) {
                        image.setVisible(false);
                        noImage.setVisible(true);
                        noImage.setText("No Saved Image");
                    }
                    break;
                }
            }
            if (!correctDetailsEntered) {
                heading2.setVisible(false);
                heading3.setVisible(false);
                imageLabel.setVisible(false);
                textLabel.setVisible(false);
                viewDoc.setVisible(false);
                viewDate.setVisible(false);
                viewTime.setVisible(false);
                duration.setVisible(false);
                viewFName.setVisible(false);
                viewMobileNo.setVisible(false);
                viewLName.setVisible(false);
                viewDob.setVisible(false);
                image.setVisible(false);
                infoLabel.setVisible(false);
                noImage.setVisible(false);
                noText.setVisible(false);
                viewPatientId.setVisible(false);
                viewEmail.setVisible(false);
                JOptionPane.showMessageDialog(null, "Check your consultation number again and enter the correct number !", "Consultation number is invalid", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
