import java.util.*;

class Patient {
    String patientId;
    String patientName;
    int age;
    String gender;
    String contactInfo;
    List<String> medicalHistory;
    List<String> currentTreatments;

    static int totalPatients = 0;

    public Patient(String patientId, String patientName, int age, String gender, String contactInfo) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.medicalHistory = new ArrayList<>();
        this.currentTreatments = new ArrayList<>();
        totalPatients++;
    }

    public void updateTreatment(String treatment) {
        currentTreatments.add(treatment);
        medicalHistory.add("Treatment: " + treatment);
        System.out.println("Treatment updated for " + patientName);
    }

    public void dischargePatient() {
        currentTreatments.clear();
        System.out.println(patientName + " has been discharged.");
    }
}

class Doctor {
    String doctorId;
    String doctorName;
    String specialization;
    List<String> availableSlots;
    int patientsHandled;
    double consultationFee;

    public Doctor(String doctorId, String doctorName, String specialization, double consultationFee) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.availableSlots = new ArrayList<>();
        this.patientsHandled = 0;
    }

    public void addAvailableSlot(String slot) {
        availableSlots.add(slot);
    }
}

class Appointment {
    String appointmentId;
    Patient patient;
    Doctor doctor;
    String appointmentDate;
    String appointmentTime;
    String status; // Scheduled, Completed, Cancelled
    String type;   // Consultation, Follow-up, Emergency
    double billAmount;

    static int totalAppointments = 0;
    static String hospitalName = "Mystery Hospital";
    static double totalRevenue = 0;

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                       String appointmentDate, String appointmentTime, String type) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = "Scheduled";
        this.type = type;
        this.billAmount = 0;
        totalAppointments++;
        doctor.patientsHandled++;
    }

    public void cancelAppointment() {
        status = "Cancelled";
        System.out.println("Appointment " + appointmentId + " has been cancelled.");
    }

    public void generateBill() {
        switch (type) {
            case "Consultation":
                billAmount = doctor.consultationFee;
                break;
            case "Follow-up":
                billAmount = doctor.consultationFee * 0.5;
                break;
            case "Emergency":
                billAmount = doctor.consultationFee * 2;
                break;
            default:
                billAmount = doctor.consultationFee;
        }
        totalRevenue += billAmount;
        status = "Completed";
        System.out.println("Bill generated for " + patient.patientName + ": " + billAmount);
    }

    public static void generateHospitalReport() {
        System.out.println("\n--- Hospital Report ---");
        System.out.println("Hospital Name: " + hospitalName);
        System.out.println("Total Patients: " + Patient.totalPatients);
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Total Revenue: " + totalRevenue);
    }

    public static void getDoctorUtilization(List<Doctor> doctors) {
        System.out.println("\n--- Doctor Utilization ---");
        for (Doctor d : doctors) {
            System.out.println(d.doctorName + " (" + d.specialization + ") - Patients handled: " + d.patientsHandled);
        }
    }

    public static void getPatientStatistics(List<Patient> patients) {
        System.out.println("\n--- Patient Statistics ---");
        for (Patient p : patients) {
            System.out.println(p.patientName + " | History entries: " + p.medicalHistory.size());
        }
    }
}

public class HospitalManagementSystem {
    public static void main(String[] args) {
        List<Patient> patients = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();

        Patient p1 = new Patient("P1", "akshay", 30, "Female", "1234567890");
        Patient p2 = new Patient("P2", "Babita", 45, "Male", "7893456298");
        patients.add(p1);
        patients.add(p2);

        Doctor d1 = new Doctor("D1", "Dr. Seema", "Cardiology", 1000);
        Doctor d2 = new Doctor("D2", "Dr. Jacob", "Neurology", 1200);
        d1.addAvailableSlot("2025-09-01 10:00");
        d2.addAvailableSlot("2025-09-01 11:00");
        doctors.add(d1);
        doctors.add(d2);

        Appointment a1 = new Appointment("A001", p1, d1, "2025-09-01", "10:00", "Consultation");
        Appointment a2 = new Appointment("A002", p2, d2, "2025-09-01", "11:00", "Emergency");
        appointments.add(a1);
        appointments.add(a2);

        p1.updateTreatment("Blood Pressure Monitoring");
        p2.updateTreatment("MRI Scan");

        a1.generateBill();
        a2.generateBill();

        p1.dischargePatient();

        Appointment.generateHospitalReport();
        Appointment.getDoctorUtilization(doctors);
        Appointment.getPatientStatistics(patients);
    }
}
