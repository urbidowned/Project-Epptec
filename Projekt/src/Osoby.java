import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Scanner;


public class Osoby {
    public static HashMap<String, Osoba> databazeOsob = new HashMap<>();
    static final int novyZakon = 54;
    static int den;
    static int mesic;
    static int rok;

    public static void pridatOsobu() throws Exception {
        System.out.println(" Zadejte jmeno:");
        Scanner scanner = new Scanner(System.in);
        Osoba osoba = new Osoba();
        osoba.jmeno = scanner.nextLine();

        if (osoba.zvalidujJmeno() == false) {
            System.out.println("Nevalidni jmeno");
            return;
        }
        System.out.println("Zadejte prijmeni");
        osoba.prijmeni = scanner.nextLine();
        if (osoba.zvalidujPrijmeni() == false) {
            System.out.println("Nevalidni prijmeni");
            return;
        }
        System.out.println("Zadejte rodne cislo ve formatu YYMMDDXXXX nebo YYMMDD/XXXX");
        String prepisRC = scanner.nextLine();
        if (osoba.zvalidujRodneCislo(prepisRC) == false) {
            System.out.println("Rodne cislo neni ve spravnem formatu");
            return;
        }
        if (databazeOsob.containsKey(osoba.rodneCislo)) {
            throw new Exception("Osoba uz existuje");
        } else databazeOsob.put(osoba.rodneCislo, osoba);
        System.out.println(databazeOsob);
    }

    private static void odebratOsobu() throws Exception {
        System.out.println("Napiste rodne cislo toho koho chcete odebrat?");
        Scanner scanner = new Scanner(System.in);
        String rodneCislo = scanner.nextLine();
        Osoba osoba = new Osoba();
        if (osoba.zvalidujRodneCislo(rodneCislo) == false) {
            System.out.println("Rodne cislo neni ve spravnem formatu");
            return;
        }

        if (!databazeOsob.containsKey(osoba.rodneCislo)) {
            throw new Exception("Osoba jeste neexistuje");
        } else databazeOsob.remove(osoba.rodneCislo);
        System.out.println(databazeOsob);


    }

    private static void vyhledatOsobu() throws Exception {
        System.out.println("Napiste rodne cislo toho koho chcete vyhledat?");
        Scanner scanner = new Scanner(System.in);
        String rodneCislo = scanner.nextLine();

        Osoba osoba = new Osoba();
        if (osoba.zvalidujRodneCislo(rodneCislo) == false) {
            System.out.println("Rodne cislo neni ve spravnem formatu");
            return;
        }

        if (!databazeOsob.containsKey(osoba.rodneCislo)) {
            throw new Exception("Osoba jeste neexistuje");
        } else {

            zpracujRadku(rodneCislo);


            System.out.println(databazeOsob.get(osoba.rodneCislo));

        }

    }

    public static void rozeberRodneCislo(String cislo) {
        String srok = cislo.substring(0, 2);
        String smesic = cislo.substring(2, 4);
        String sden = cislo.substring(4, 6);
        rok = Integer.parseInt(srok);
        if (cislo.length() == 9 || rok >= novyZakon) {
            rok += 1900;
        } else {
            rok += 2000;
        }

        mesic = Integer.parseInt(smesic);

        if (mesic > 50) {
            mesic -= 50;
        }
        if (mesic > 20) {
            mesic -= 20;
        }

        den = Integer.parseInt(sden);

    }

    public static void zpracujRadku(String radka) {
        System.out.print(radka + "\t -> ");
        String cislo = radka;
        rozeberRodneCislo(cislo);
        vypis();
    }

    public static void vypis() {
        System.out.format("%04d.%02d.%02d -> ", rok, mesic, den);
        LocalDate dob = LocalDate.of(rok, mesic, den);
        LocalDate curDate = LocalDate.now();
        Period period = Period.between(dob, curDate);
        System.out.printf("Osobe je %d let", period.getYears(), period.getMonths(), period.getDays());
        System.out.println(" ");
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String zprava = "Vyberte z moznosti\n" +
                    "1: Pridat novou osobu.\n" +
                    "2: Odebrat existujici osobu\n" +
                    "3: Vyhledat osobu podle rodneho cisla";

            System.out.println(zprava);
            String odpoved;
            boolean validita;
            do {
                odpoved = scanner.nextLine();
                validita = true;
                if (odpoved.equals("1")) {
                    pridatOsobu();

                } else if (odpoved.equals("2")) {
                    odebratOsobu();

                } else if (odpoved.equals("3")) {
                    vyhledatOsobu();

                } else {
                    System.out.println("Zadejte prosim jednu z moznosti");
                    validita = false;
                }
            }
            while (validita == false);


            System.out.println("Chcete ukoncit(u) program? Pokud ano stistknete klavesu u, pokud ne stisknete jinou libovolnou klavesu");
            if ((scanner.nextLine().equalsIgnoreCase("u"))) {
                break;
            }


        }
    }


    public static class Osoba {
        public String jmeno;
        public String prijmeni;
        public String rodneCislo;

        @Override
        public String toString() {
            return "Osoba{" +
                    "jmeno='" + jmeno + '\'' +
                    ", prijmeni='" + prijmeni + '\'' +
                    ", rodneCislo='" + rodneCislo + '\'' +
                    '}';
        }

        public boolean zvalidujJmeno() {
            return !jmeno.isEmpty();
        }

        public boolean zvalidujPrijmeni() {
            return !prijmeni.isEmpty();
        }

        public boolean zvalidujRodneCislo(String vstup) {
            rodneCislo = vstup;
            if (rodneCislo.length() == 11) {
                if (rodneCislo.charAt(6) == '/') {
                    rodneCislo = rodneCislo.substring(0, 6) + rodneCislo.substring(7, 11);
                } else {
                    return false;
                }
            }
            if (rodneCislo.length() == 10) {
                try {
                    Long.parseLong(rodneCislo);


                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                return false;
            }
            return true;
        }
    }


}
