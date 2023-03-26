import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterSkinConsultationManagerTest {

    @Test
    void isStringOnlyAlphabetTest1() {
        String str = "abc";             // With a valid string that only contain letters.
        Boolean expected = true;
        Boolean actual = WestminsterSkinConsultationManager.isStringOnlyAlphabet(str);
        assertEquals(expected,actual);
    }

    @Test
    void isStringOnlyAlphabetTest2() {  // With an invalid name that contain numbers
        String str = "ab1";
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isStringOnlyAlphabet(str);
        assertEquals(expected,actual);
    }

    @Test
    void isStringOnlyAlphabetTest3() {  // With an invalid input that only contain numbers,
        String str = "123";
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isStringOnlyAlphabet(str);
        assertEquals(expected,actual);
    }


    @Test
    void isContactNumberValidTest1() {
        String MobNumber = "0761234567"; // With a 10 digit valid mobile number.
        Boolean expected = true;
        Boolean actual = WestminsterSkinConsultationManager.isContactNumberValid(MobNumber);
        assertEquals(expected,actual);
    }

    @Test
    void isContactNumberValidTest2() {
        String MobNumber = "076123456"; // With a 9 digit invalid mobile number.
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isContactNumberValid(MobNumber);
        assertEquals(expected,actual);
    }

    @Test
    void isContactNumberValidTest3() {
        String MobNumber = "07612345678"; // With an 11 digit invalid mobile number.
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isContactNumberValid(MobNumber);
        assertEquals(expected,actual);
    }

    @Test
    void isContactNumberValidTest4() {
        String MobNumber = "07612345aa"; // With a mix of digits and characters.
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isContactNumberValid(MobNumber);
        assertEquals(expected,actual);
    }

    @Test
    void canAddMoreDoctorsTest1() {
        int doctorCount = 9;        // 9 doctors in the medical center.
        Boolean expected = true;
        Boolean actual = WestminsterSkinConsultationManager.canAddMoreDoctors(doctorCount);
        assertEquals(expected,actual);
    }

    @Test
    void canAddMoreDoctorsTest2() {
        int doctorCount = 10;       // 10 doctors in the medical center.
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.canAddMoreDoctors(doctorCount);
        assertEquals(expected,actual);
    }

    @Test
    void canAddMoreDoctorsTest3() {
        int doctorCount = 0;        // 0 doctors in the medical center.
        Boolean expected = true;
        Boolean actual = WestminsterSkinConsultationManager.canAddMoreDoctors(doctorCount);
        assertEquals(expected,actual);
    }

    @Test
    void isMonthValidTest1() {    // With an invalid month input
        int month = 0;
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isMonthValid(month);
        assertEquals(expected,actual);
    }

    @Test
    void isMonthValidTest2() {  // With a valid month input
        int month = 1;
        Boolean expected = true;
        Boolean actual = WestminsterSkinConsultationManager.isMonthValid(month);
        assertEquals(expected,actual);
    }

    @Test
    void isMonthValidTest3() {  // With an invalid month input
        int month = 13;
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isMonthValid(month);
        assertEquals(expected,actual);
    }

    @Test
    void isDateValidTest1() {  // With an invalid date input
        int date = 0;
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isDateValid(date);
        assertEquals(expected,actual);
    }

    @Test
    void isDateValidTest2() {  // With a valid date input
        int date = 1;
        Boolean expected = true;
        Boolean actual = WestminsterSkinConsultationManager.isDateValid(date);
        assertEquals(expected,actual);
    }

    @Test
    void isDateValidTest3() {  // With an invalid date input
        int date = 32;
        Boolean expected = false;
        Boolean actual = WestminsterSkinConsultationManager.isDateValid(date);
        assertEquals(expected,actual);
    }
}