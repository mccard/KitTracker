package edu.uco.mcamposcardoso.kittracker.types;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

/**
 * Created by matheuscamposcardoso on 26/02/18.
 */
public class KitType {
    public MultiValueMap<String, Integer> name_to_id = new LinkedMultiValueMap<>();
    public static final List<String> kits = Arrays.asList("Selecione..", "Endodontia", "Cirurgia",
            "Bandeja A", "Periodontia", "Cirurgia G", "Bandeja B", "Prótese (G) A", "Prótese", "Prótese A",
            "Prótese B", "Prótese (G) B", "Bandeja B", "Bandeja C", "Bandeja D", "Bandeja E", "Dentística",
            "Dentística G", "Brocas", "Ex. Clín.", "Ex. Clín. A", "Ex. Clín. B", "Grampos", "Isolamento",
            "Dentística A", "Dentística B", "Ortodontia G", "Ortodontia", "Posicionadores", "Prótese G",
            "Moldeiras", "Moldeiras G", "Ex. Clín. C", "Implante", "Seleção", "Prótese C", "Seleção G",
            "Protético", "Moldeiras A (G)", "Periodontia C", "Afastadores", "Periodontia G", "Bandeja F",
            "Extração", "Periodontia A", "Periodontia B", "Broqueiro", "Caneta", "Bandeja G", "Moldeiras B (G)",
            "Posicionadores B", "Moldeiras G (A)", "Posicionadores A", "Moldeiras G (B)", "Posicion. Endo",
            "Rem. de Sutura", "Bandeja H", "Bandeja K", "Ortodontia A", "Ortodontia B", "Ortodontia C",
            "Odontopediatria A", "OdontoPediatria B", "Moldeiras Adulto", "Dentística D", "Dentística E",
            "Ex. Clín. D", "Ex Clín. E", "Ex. Clín. F", "Ex. Clín. G", "Cirurgia A", "Remoção C", "Bandeja I",
            "Bandeja J", "Cirurgia Completa A", "Cirurgia Completa B", "Cirurgia Completa C", "Isolamento A",
            "Isolamento B", "Dentística C", "Biópsia A", "Biópsia B", "Cirurgia B", "Remoção A", "Remoção B",
            "Remoção D", "Moldeiras B", "Raspagem", "Moldeiras A", "Kit de Implante", "Diversos", "DTM", "Kit de Implante A",
            "Kit de Implante B", "Moldeiras Desdentados", "Moldeiras alumínio", "Moldeiras inox", "Diversos G",
            "Diversos ", "Dentística B (G)", "Fórceps", "Periodontia A", "Periodontia B", "Diversos Plásticos", "Endodontia A",
            "Endodontia B", "Cirurgia G (B)", "Moldeiras plásticas", "Biópsia C", "Campo");

    public int getKit_type_id() {
        return kit_type_id;
    }

    public void setKit_type_id(int kit_type_id) {
        this.kit_type_id = kit_type_id;
    }

    private int kit_type_id;
    private String kit_name;

    public KitType(int kit_type_id, String kit_name) {
        this.kit_type_id = kit_type_id;
        this.kit_name = kit_name;
    }

    public String getKit_name() {
        name_to_id.add("Endodontia", 1);
        name_to_id.add("Cirurgia", 2);
        name_to_id.add("Bandeja A", 3);
        name_to_id.add("Periodontia", 4);
        name_to_id.add("Cirurgia G", 5);
        name_to_id.add("Bandeja B", 6);
        name_to_id.add("Prótese (G) A", 7);
        name_to_id.add("Prótese", 8);
        name_to_id.add("Prótese A", 9);
        name_to_id.add("Prótese B", 10);
        name_to_id.add("Prótese (G) B", 11);
        name_to_id.add("Bandeja C", 13);
        name_to_id.add("Bandeja D", 14);
        name_to_id.add("Bandeja E", 15);
        name_to_id.add("Dentística", 16);
        name_to_id.add("Dentística G", 17);
        name_to_id.add("Brocas", 18);
        name_to_id.add("Ex. Clín.", 19);
        name_to_id.add("Ex. Clín. A", 20);
        name_to_id.add("Ex. Clín. B", 21);
        name_to_id.add("Grampos", 22);
        name_to_id.add("Isolamento", 23);
        name_to_id.add("Dentística A", 24);
        name_to_id.add("Dentística B", 25);
        name_to_id.add("Ortodontia G", 26);
        name_to_id.add("Ortodontia", 27);
        name_to_id.add("Posicionadores", 28);
        name_to_id.add("Prótese G", 29);
        name_to_id.add("Moldeiras", 30);
        name_to_id.add("Moldeiras G", 31);
        name_to_id.add("Ex. Clín. C", 32);
        name_to_id.add("Implante", 33);
        name_to_id.add("Seleção", 34);
        name_to_id.add("Prótese C", 35);
        name_to_id.add("Seleção G", 36);
        name_to_id.add("Protético", 37);
        name_to_id.add("Moldeiras A (G)", 38);
        name_to_id.add("Periodontia C", 39);
        name_to_id.add("Seleção", 34);
        name_to_id.add("Afastadores", 40);
        name_to_id.add("Periodontia G", 41);
        name_to_id.add("Bandeja F", 42);
        name_to_id.add("Extração", 43);
        name_to_id.add("Periodontia A", 44);
        name_to_id.add("Periodontia B", 45);
        name_to_id.add("Broqueiro", 46);
        name_to_id.add("Caneta", 47);
        name_to_id.add("Bandeja G", 48);
        name_to_id.add("Moldeiras B (G)", 49);
        name_to_id.add("Posicionadores B", 50);
        name_to_id.add("Moldeiras G (A)", 51);
        name_to_id.add("Posicionadores A", 52);
        name_to_id.add("Moldeiras G (B)", 53);
        name_to_id.add("Posicion. Endo", 54);
        name_to_id.add("Rem. de Sutura", 55);
        name_to_id.add("Bandeja H", 56);
        name_to_id.add("Bandeja K", 57);
        name_to_id.add("Ortodontia A", 58);
        name_to_id.add("Ortodontia B", 59);
        name_to_id.add("Ortodontia C", 60);
        name_to_id.add("Odontopediatria A", 61);
        name_to_id.add("Odontopediatria B", 62);
        name_to_id.add("Moldeiras Adulto", 63);
        name_to_id.add("Dentística D", 64);
        name_to_id.add("Dentística E", 65);
        name_to_id.add("Ex. Clín. D", 66);
        name_to_id.add("Ex. Clín. E", 67);
        name_to_id.add("Ex. Clín. F", 68);
        name_to_id.add("Ex. Clín. G", 69);
        name_to_id.add("Cirurgia A", 70);
        name_to_id.add("Remoção C", 71);
        name_to_id.add("Bandeja I", 72);
        name_to_id.add("Bandeja J", 73);
        name_to_id.add("Cirurgia Completa A", 74);
        name_to_id.add("Cirurgia Completa B", 75);
        name_to_id.add("Cirurgia Completa C", 76);
        name_to_id.add("Isolamento A", 77);
        name_to_id.add("Isolamento B", 78);
        name_to_id.add("Dentística C", 79);
        name_to_id.add("Biópsia A", 80);
        name_to_id.add("Biópsia B", 81);
        name_to_id.add("Cirurgia B", 82);
        name_to_id.add("Remoção A", 83);
        name_to_id.add("Remoção B", 84);
        name_to_id.add("Remoção D", 85);
        name_to_id.add("Moldeiras B", 86);
        name_to_id.add("Raspagem", 87);
        name_to_id.add("Moldeiras A", 88);
        name_to_id.add("kit de Implante", 89);
        name_to_id.add("Diversos", 90);
        name_to_id.add("DTM", 91);

        return kit_name;
    }

    public void setKit_name(String kit_name) {
        this.kit_name = kit_name;
    }
}
