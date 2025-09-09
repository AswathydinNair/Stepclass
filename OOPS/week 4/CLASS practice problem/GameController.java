public class GameController { 
    private String controllerBrand; 
    private String connectionType; 
    private boolean hasVibration; 
    private int batteryLevel; 
    private double sensitivity; 
  
    public GameController() { 
        this.controllerBrand = "GenericPad"; 
        this.connectionType = "USB"; 
        this.hasVibration = true; 
        this.batteryLevel = 100; 
        this.sensitivity = 1.0; 
    } 
  
    public GameController(String controllerBrand, String connectionType, boolean hasVibration, int batteryLevel, double sensitivity) { 
        this.controllerBrand = controllerBrand; 
        this.connectionType = connectionType; 
        this.hasVibration = hasVibration; 
        this.batteryLevel = (batteryLevel >= 0 && batteryLevel <= 100) ? batteryLevel : 100; 
        this.sensitivity = (sensitivity >= 0.1 && sensitivity <= 3.0) ? sensitivity : 1.0; 
    } 
  
    public GameController(String brand, String connectionType) {  
        this.controllerBrand = brand; 
        this.connectionType = connectionType; 
        this.hasVibration = true; 
        this.batteryLevel = 100; 
        this.sensitivity = 1.0; 
    } 
  
    public void calibrateController() { 
        System.out.println("Calibrating " + controllerBrand + " controller..."); 
    } 
  
    public void displayConfiguration() { 
        System.out.println("Controller Brand: " + controllerBrand); 
        System.out.println("Connection Type: " + connectionType); 
        System.out.println("Has Vibration: " + hasVibration); 
        System.out.println("Battery Level: " + batteryLevel + "%"); 
        System.out.println("Sensitivity: " + sensitivity); 
        System.out.println("-----------------------------"); 
    } 
  
    public void testVibration() { 
        if (hasVibration) { 
            System.out.println("*BUZZ* Vibration test successful!");  
        } else { 
            System.out.println("Vibration disabled on this controller."); 
        } 
    } 
  
    public static void main(String[] args) { 
        System.out.println("=== GAMING CONTROLLER SETUP ===");  

        GameController c1 = new GameController(); 
        GameController c2 = new GameController("ProGamer", "Bluetooth", true, 85, 2.0); 
        GameController c3 = new GameController("LitePad", "Wired"); 

        c1.displayConfiguration(); 
        c1.calibrateController(); 
        c1.testVibration(); 

        c2.displayConfiguration(); 
        c2.calibrateController(); 
        c2.testVibration(); 

        c3.displayConfiguration(); 
        c3.calibrateController(); 
        c3.testVibration(); 
    } 
} 

