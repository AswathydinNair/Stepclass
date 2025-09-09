import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

abstract class MagicalStructure implements Serializable {
    String structureName;
    int magicPower;
    String location;
    boolean isActive;

    MagicalStructure(String structureName) {
        this(structureName, 0);
    }

    MagicalStructure(String structureName, int magicPower) {
        this(structureName, magicPower, "Unknown");
    }

    MagicalStructure(String structureName, int magicPower, String location) {
        this.structureName = structureName;
        this.magicPower = magicPower;
        this.location = location;
        this.isActive = true;
    }

    abstract String castMagicSpell();

    @Override
    public String toString() {
        return String.format("%s[%s] power=%d loc=%s active=%b",
                this.getClass().getSimpleName(), structureName, magicPower, location, isActive);
    }
}

class WizardTower extends MagicalStructure {
    int spellCapacity;
    String[] knownSpells;

    WizardTower() {
        this("Tower", 5);
    }

    WizardTower(String structureName, int spellCapacity) {
        this(structureName, spellCapacity * 10, "Arcane Hill", spellCapacity, new String[]{"Spark"});
    }

    WizardTower(String structureName, int magicPower, String location, int spellCapacity, String[] knownSpells) {
        super(structureName, magicPower, location);
        this.spellCapacity = spellCapacity;
        this.knownSpells = knownSpells != null ? knownSpells.clone() : new String[0];
    }

    @Override
    String castMagicSpell() {
        if (!isActive) return structureName + " is inert and cannot cast.";
        String spell = knownSpells.length > 0 ? knownSpells[new Random().nextInt(knownSpells.length)] : "Arcane Pulse";
        int effect = Math.min(spellCapacity, 1 + new Random().nextInt(spellCapacity + 1));
        return structureName + " casts " + spell + " (effect " + effect + ")";
    }

    void learnSpell(String spell) {
        List<String> list = new ArrayList<>(Arrays.asList(knownSpells));
        list.add(spell);
        knownSpells = list.toArray(new String[0]);
        spellCapacity += 1;
    }
}

class EnchantedCastle extends MagicalStructure {
    int defenseRating;
    boolean hasDrawbridge;

    EnchantedCastle() {
        this("Castle", 30, "Valley", 50, false);
    }

    EnchantedCastle(String structureName, int defenseRating) {
        this(structureName, defenseRating * 2, "Highland", defenseRating, true);
    }

    EnchantedCastle(String structureName, int magicPower, String location, int defenseRating, boolean hasDrawbridge) {
        super(structureName, magicPower, location);
        this.defenseRating = defenseRating;
        this.hasDrawbridge = hasDrawbridge;
    }

    @Override
    String castMagicSpell() {
        if (!isActive) return structureName + " is deserted and emits no wards.";
        int ward = defenseRating / 5 + new Random().nextInt(5);
        return structureName + " raises defensive wards (ward " + ward + ")";
    }

    void toggleDrawbridge() {
        hasDrawbridge = !hasDrawbridge;
    }
}

class MysticLibrary extends MagicalStructure {
    int bookCount;
    String ancientLanguage;

    MysticLibrary() {
        this("Library", 20, "Old Quarter", 100, "Ancient Tongue");
    }

    MysticLibrary(String structureName, int bookCount) {
        this(structureName, bookCount / 2, "Scholars' Row", bookCount, "Codex");
    }

    MysticLibrary(String structureName, int magicPower, String location, int bookCount, String ancientLanguage) {
        super(structureName, magicPower, location);
        this.bookCount = bookCount;
        this.ancientLanguage = ancientLanguage;
    }

    @Override
    String castMagicSpell() {
        if (!isActive) return structureName + " is closed and silent.";
        int insight = Math.min(10, Math.max(1, bookCount / 20));
        return structureName + " whispers knowledge (insight " + insight + ")";
    }

    void addBooks(int n) {
        bookCount += n;
        magicPower += n / 10;
    }
}

class DragonLair extends MagicalStructure {
    String dragonType;
    int treasureValue;

    DragonLair() {
        this("Lair", 80, "Volcanic Crag", "Wyrm", 1000);
    }

    DragonLair(String dragonType, int treasureValue) {
        this("Dragon Lair of " + dragonType, treasureValue / 10, "Cavern", dragonType, treasureValue);
    }

    DragonLair(String structureName, int magicPower, String location, String dragonType, int treasureValue) {
        super(structureName, magicPower, location);
        this.dragonType = dragonType;
        this.treasureValue = treasureValue;
    }

    @Override
    String castMagicSpell() {
        if (!isActive) return structureName + " is empty and cold.";
        int breath = magicPower / 10 + new Random().nextInt(10);
        return structureName + " unleashes " + dragonType + " breath (damage " + breath + ")";
    }

    void hoardTreasure(int amount) {
        treasureValue += amount;
    }
}

class MagicalInteractions {
    static boolean canStructuresInteract(MagicalStructure s1, MagicalStructure s2) {
        if (s1 == null || s2 == null) return false;
        if (!s1.isActive || !s2.isActive) return false;
        if (s1.location.equalsIgnoreCase(s2.location)) return true;
        if ((s1 instanceof WizardTower && s2 instanceof MysticLibrary) || (s2 instanceof WizardTower && s1 instanceof MysticLibrary))
            return true;
        if ((s1 instanceof EnchantedCastle && s2 instanceof DragonLair) || (s2 instanceof EnchantedCastle && s1 instanceof DragonLair))
            return true;
        return Math.abs(s1.magicPower - s2.magicPower) <= 30;
    }

    static String performMagicBattle(MagicalStructure attacker, MagicalStructure defender) {
        if (!canStructuresInteract(attacker, defender)) return "No interaction possible between " + attacker.structureName + " and " + defender.structureName + ".";
        int attackScore = attacker.magicPower + new Random().nextInt(20);
        int defendScore = defender.magicPower + new Random().nextInt(20);
        if (attacker instanceof WizardTower && defender instanceof MysticLibrary) {
            attackScore += ((WizardTower) attacker).spellCapacity * 2;
        }
        if (attacker instanceof EnchantedCastle && defender instanceof DragonLair) {
            defendScore += ((EnchantedCastle) attacker).defenseRating * 3;
        }
        if (attacker instanceof MysticLibrary && defender instanceof WizardTower) {
            defendScore += ((WizardTower) defender).spellCapacity;
        }
        String outcome;
        if (attackScore > defendScore + 10) {
            defender.isActive = false;
            outcome = attacker.structureName + " overwhelms " + defender.structureName + " (" + attackScore + " vs " + defendScore + ") -> defender disabled";
        } else if (defendScore > attackScore + 10) {
            attacker.isActive = false;
            outcome = defender.structureName + " repels " + attacker.structureName + " (" + defendScore + " vs " + attackScore + ") -> attacker disabled";
        } else {
            attacker.magicPower = Math.max(0, attacker.magicPower - 5);
            defender.magicPower = Math.max(0, defender.magicPower - 5);
            outcome = "Stalemate between " + attacker.structureName + " and " + defender.structureName + " (" + attackScore + " vs " + defendScore + ")";
        }
        applySpecialCombos(attacker, defender);
        return outcome;
    }

    private static void applySpecialCombos(MagicalStructure s1, MagicalStructure s2) {
        if (s1 instanceof WizardTower && s2 instanceof MysticLibrary) {
            WizardTower w = (WizardTower) s1;
            MysticLibrary l = (MysticLibrary) s2;
            w.spellCapacity *= 2;
            w.magicPower += l.bookCount / 10;
        }
        if (s2 instanceof WizardTower && s1 instanceof MysticLibrary) {
            WizardTower w = (WizardTower) s2;
            MysticLibrary l = (MysticLibrary) s1;
            w.spellCapacity *= 2;
            w.magicPower += l.bookCount / 10;
        }
        if (s1 instanceof EnchantedCastle && s2 instanceof DragonLair) {
            EnchantedCastle c = (EnchantedCastle) s1;
            c.defenseRating *= 3;
        }
        if (s2 instanceof EnchantedCastle && s1 instanceof DragonLair) {
            EnchantedCastle c = (EnchantedCastle) s2;
            c.defenseRating *= 3;
        }
    }

    static int calculateKingdomMagicPower(MagicalStructure[] structures) {
        int total = 0;
        int towerCount = 0;
        for (MagicalStructure s : structures) {
            if (s == null) continue;
            total += s.magicPower;
            if (s instanceof WizardTower) towerCount++;
        }
        if (towerCount > 1) {
            int bonus = towerCount * 20;
            total += bonus;
        }
        for (MagicalStructure s : structures) {
            if (s instanceof EnchantedCastle) {
                EnchantedCastle c = (EnchantedCastle) s;
                if (hasDragonLairPair(structures, c)) total += c.defenseRating * 2;
            }
        }
        return total;
    }

    private static boolean hasDragonLairPair(MagicalStructure[] structures, EnchantedCastle c) {
        for (MagicalStructure s : structures) if (s instanceof DragonLair && s.location.equalsIgnoreCase(c.location)) return true;
        return false;
    }
}

class KingdomManager {
    List<MagicalStructure> structures = new ArrayList<>();

    void add(MagicalStructure s) {
        structures.add(s);
    }

    Map<String, List<MagicalStructure>> categorizeByType() {
        Map<String, List<MagicalStructure>> map = new HashMap<>();
        for (MagicalStructure s : structures) {
            String key = s.getClass().getSimpleName();
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return map;
    }

    Map<String, Double> calculateTaxRates() {
        Map<String, Double> rates = new HashMap<>();
        rates.put("WizardTower", 0.05);
        rates.put("EnchantedCastle", 0.03);
        rates.put("MysticLibrary", 0.02);
        rates.put("DragonLair", 0.1);
        return rates;
    }

    double calculateTotalTax() {
        Map<String, Double> rates = calculateTaxRates();
        double tax = 0.0;
        for (MagicalStructure s : structures) {
            String key = s.getClass().getSimpleName();
            double rate = rates.getOrDefault(key, 0.04);
            double value = s.magicPower * 10;
            if (s instanceof DragonLair) value += ((DragonLair) s).treasureValue / 100.0;
            tax += value * rate;
        }
        return tax;
    }

    String determineSpecialization() {
        int magic = 0;
        int defense = 0;
        int treasure = 0;
        for (MagicalStructure s : structures) {
            if (s instanceof WizardTower) magic += s.magicPower;
            if (s instanceof MysticLibrary) magic += s.magicPower / 2;
            if (s instanceof EnchantedCastle) defense += ((EnchantedCastle) s).defenseRating;
            if (s instanceof DragonLair) treasure += ((DragonLair) s).treasureValue;
        }
        if (magic > defense * 2 && magic > treasure / 10) return "Magic-focused";
        if (defense > magic && defense > treasure / 10) return "Defense-focused";
        if (treasure / 10 > magic && treasure / 10 > defense) return "Wealth-focused";
        return "Balanced";
    }

    String performKingdomAudit() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kingdom Audit:\n");
        sb.append("Total Structures: ").append(structures.size()).append("\n");
        sb.append("Categories:\n");
        Map<String, List<MagicalStructure>> cat = categorizeByType();
        for (Map.Entry<String, List<MagicalStructure>> e : cat.entrySet()) {
            sb.append(" - ").append(e.getKey()).append(": ").append(e.getValue().size()).append("\n");
        }
        sb.append("Total Tax Collected (est): ").append(String.format("%.2f", calculateTotalTax())).append("\n");
        sb.append("Specialization: ").append(determineSpecialization()).append("\n");
        return sb.toString();
    }

    MagicalStructure[] toArray() {
        return structures.toArray(new MagicalStructure[0]);
    }

    List<MagicalStructure> getStructuresInLocation(String location) {
        return structures.stream().filter(s -> s.location.equalsIgnoreCase(location)).collect(Collectors.toList());
    }

    void saveState(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(structures);
        }
    }

    @SuppressWarnings("unchecked")
    void loadState(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            structures = (List<MagicalStructure>) in.readObject();
        }
    }
}

public class MedievalKingdomBuilder {
    public static void main(String[] args) {
        WizardTower wt1 = new WizardTower("Azure Spire", 8);
        wt1.learnSpell("Firebolt");
        WizardTower wt2 = new WizardTower("Gilded Observatory", 6);
        EnchantedCastle castle1 = new EnchantedCastle("Stonehold", 80, "Valley", 120, true);
        EnchantedCastle castle2 = new EnchantedCastle("Highfort", 60, "Highland", 90, false);
        MysticLibrary lib1 = new MysticLibrary("Grand Archive", 40, "Old Quarter", 500, "Elder Script");
        MysticLibrary lib2 = new MysticLibrary("Scribe's Nook", 20, "Arcane Hill", 120, "Common Codex");
        DragonLair lair1 = new DragonLair("Ember Wyrm Lair", 100, "Volcanic Crag", "Ember Wyrm", 5000);
        DragonLair lair2 = new DragonLair("Crystal Drakes", 70, "Highland", "Crystal Drake", 2000);

        KingdomManager km = new KingdomManager();
        km.add(wt1);
        km.add(wt2);
        km.add(castle1);
        km.add(castle2);
        km.add(lib1);
        km.add(lib2);
        km.add(lair1);
        km.add(lair2);

        System.out.println("Initial Structures:");
        for (MagicalStructure s : km.toArray()) System.out.println(s);

        System.out.println("\nCan Azure Spire interact with Grand Archive?");
        System.out.println(MagicalInteractions.canStructuresInteract(wt1, lib1));

        System.out.println("\nPerforming magic battle: Azure Spire vs Ember Wyrm Lair");
        System.out.println(MagicalInteractions.performMagicBattle(wt1, lair1));

        System.out.println("\nPerforming magic battle: Stonehold vs Ember Wyrm Lair");
        System.out.println(MagicalInteractions.performMagicBattle(castle1, lair1));

        System.out.println("\nKingdom magic power: " + MagicalInteractions.calculateKingdomMagicPower(km.toArray()));

        System.out.println("\nGetting structures in Highland:");
        for (MagicalStructure s : km.getStructuresInLocation("Highland")) System.out.println(" - " + s.structureName + " (" + s.getClass().getSimpleName() + ")");

        System.out.println("\nApplying library + tower knowledge boost (if present):");
        System.out.println("WizardTower " + wt1.structureName + " capacity now: " + wt1.spellCapacity);

        System.out.println("\nKingdom Manager Audit:");
        System.out.println(km.performKingdomAudit());

        try {
            String stateFile = "kingdom_state.dat";
            km.saveState(stateFile);
            System.out.println("Saved kingdom state to " + stateFile);
            KingdomManager km2 = new KingdomManager();
            km2.loadState(stateFile);
            System.out.println("Loaded kingdom state from " + stateFile + " with " + km2.toArray().length + " structures");
        } catch (Exception e) {
            System.out.println("Serialization error: " + e.getMessage());
        }

        System.out.println("\nDemonstrating special combos:");
        System.out.println("Before combos: Gilded Observatory capacity=" + wt2.spellCapacity + " Grand Archive books=" + lib1.bookCount);
        MagicalInteractions.performMagicBattle(wt2, lib1);
        System.out.println("After combos: Gilded Observatory capacity=" + wt2.spellCapacity + " Gilded Observatory magic=" + wt2.magicPower);

        System.out.println("\nFinal Audit:");
        System.out.println(km.performKingdomAudit());
    }
}
