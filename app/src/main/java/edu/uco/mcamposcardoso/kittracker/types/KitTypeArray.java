package edu.uco.mcamposcardoso.kittracker.types;

import org.springframework.util.MultiValueMap;

import org.springframework.util.LinkedMultiValueMap;
import java.util.Arrays;
import java.util.List;

public class KitTypeArray {
    public MultiValueMap<String, Integer> name_to_id = new LinkedMultiValueMap<>();
    public MultiValueMap<String, String> id_to_name = new LinkedMultiValueMap<>();
    // a posicao aqui é sempre uma a mais (o id)
    public List<String> kits = Arrays.asList("Endodontia", "Cirurgia",
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

    public KitTypeArray() {
        for(int i = 1; i < kits.size() + 1; i++){
            if(i == 12 || i == 98 || i == 101 || i == 102){
                continue;
            }
            name_to_id.add(kits.get(i - 1), i);
            id_to_name.add(String.valueOf(i), kits.get(i - 1));
        }
        name_to_id.add("Kit implante (P) 1", 142);
        name_to_id.add("Kit implante (M) 1", 143);
        name_to_id.add("Kit implante (P) 2", 144);
        name_to_id.add("Kit implante (P) 3", 145);
        name_to_id.add("Kit implante (M) 3", 146);
        name_to_id.add("Kit implante (M) 4", 147);
        name_to_id.add("Kit implante (M) 5", 148);
        name_to_id.add("Kit implante (M) 6", 149);
        name_to_id.add("Kit implante (G) 1", 150);
        name_to_id.add("Kit implante (G) 2", 151);
        name_to_id.add("Kit implante (G) 3", 152);
        name_to_id.add("Kit implante (G) 4", 153);
        name_to_id.add("Kit implante (G) 5", 154);
        name_to_id.add("Kit implante (G) 6", 155);
        name_to_id.add("Kit implante (M) 2", 156);
        name_to_id.add("Isolamento C", 157);
        name_to_id.add("Periodontia D", 158);
        name_to_id.add("Periodontia E", 159);
        name_to_id.add("Periodontia F", 160);
        name_to_id.add("Bandeja L", 162);
        name_to_id.add("Cirurgia Perio.", 163);
        name_to_id.add("Cirurgia Imp.", 164);
        name_to_id.add("Cirurgia Perio. B", 165);
        name_to_id.add("Campo 1", 166);
        name_to_id.add("Campo 2", 167);
        name_to_id.add("Raspagem A", 168);
        name_to_id.add("Raspagem B", 169);
        name_to_id.add("Raspagem C", 170);
        name_to_id.add("Kit implante (M) 7", 171);
        name_to_id.add("Kit implante (G) 7", 172);
        name_to_id.add("Kit implante (G) 8", 175);
        name_to_id.add("Kit implante (G) 9", 176);
        name_to_id.add("Kit implante (G) 10", 177);
        name_to_id.add("Kit implante (M) 8", 178);
        name_to_id.add("Kit implante (M) 9", 179);
        name_to_id.add("Kit implante (M) 10", 180);
        name_to_id.add("Resíduos", 181);
        name_to_id.add("Exame Clin.Infantil", 183);
        name_to_id.add("Cirurgia Completa D", 185);
        name_to_id.add("Diversos A", 217);
        name_to_id.add("Diversos B", 218);
        name_to_id.add("Kit implante (M) 11", 219);
        name_to_id.add("Kit implante (M) 12", 220);
        name_to_id.add("Kit implante (M) 13", 221);
        name_to_id.add("Kit implante (M) 14", 222);
        name_to_id.add("Kit implante (G) 11", 223);
        name_to_id.add("Periodontia H", 224);
        name_to_id.add("Periodontia T", 225);
        name_to_id.add("Periodontia N", 226);
        name_to_id.add("Kit implante GM 1", 227);
        name_to_id.add("Kit implante 2", 228);
        name_to_id.add("Kit de chaves 1", 229);
        name_to_id.add("Kit de chaves 2", 230);
        name_to_id.add("Kit de seleção 1", 231);
        name_to_id.add("Kit de seleção 2", 232);
        name_to_id.add("Kit implante GM 2", 233);

        id_to_name.add("142", "Kit implante (P) 1");
        id_to_name.add("143", "Kit implante (M) 1");
        id_to_name.add("144", "Kit implante (P) 2");
        id_to_name.add("145", "Kit implante (P) 3");
        id_to_name.add("146", "Kit implante (M) 3");
        id_to_name.add("147", "Kit implante (M) 4");
        id_to_name.add("148", "Kit implante (M) 5");
        id_to_name.add("149", "Kit implante (M) 6");
        id_to_name.add("150", "Kit implante (G) 1");
        id_to_name.add("151", "Kit implante (G) 2");
        id_to_name.add("152", "Kit implante (G) 3");
        id_to_name.add("153", "Kit implante (G) 4");
        id_to_name.add("154", "Kit implante (G) 5");
        id_to_name.add("155", "Kit implante (G) 6");
        id_to_name.add("156", "Kit implante (M) 2");
        id_to_name.add("157", "Isolamento C");
        id_to_name.add("158", "Periodontia D");
        id_to_name.add("159", "Periodontia E");
        id_to_name.add("160", "Periodontia F");
        id_to_name.add("162", "Bandeja L");
        id_to_name.add("163", "Cirurgia Perio.");
        id_to_name.add("164", "Cirurgia Imp.");
        id_to_name.add("165", "Cirurgia Perio. B");
        id_to_name.add("166", "Campo 1");
        id_to_name.add("167", "Campo 2");
        id_to_name.add("168", "Raspagem A");
        id_to_name.add("169", "Raspagem B");
        id_to_name.add("170", "Raspagem C");
        id_to_name.add("171", "Kit implante (M) 7");
        id_to_name.add("172", "Kit implante (G) 7");
        id_to_name.add("175", "Kit implante (G) 8");
        id_to_name.add("176", "Kit implante (G) 9");
        id_to_name.add("177", "Kit implante (G) 10");
        id_to_name.add("178", "Kit implante (M) 8");
        id_to_name.add("179", "Kit implante (M) 9");
        id_to_name.add("180", "Kit implante (M) 10");
        id_to_name.add("181", "Resíduos");
        id_to_name.add("183", "Exame Clin.Infantil");
        id_to_name.add("185", "Cirurgia Completa D");
        id_to_name.add("217", "Diversos A");
        id_to_name.add("218", "Diversos B");
        id_to_name.add("219", "Kit implante (M) 11");
        id_to_name.add("220", "Kit implante (M) 12");
        id_to_name.add("221", "Kit implante (M) 13");
        id_to_name.add("222", "Kit implante (M) 14");
        id_to_name.add("223", "Kit implante (G) 11");
        id_to_name.add("224", "Periodontia H");
        id_to_name.add("225", "Periodontia T");
        id_to_name.add("226", "Periodontia N");
        id_to_name.add("227", "Kit implante GM 1");
        id_to_name.add("228", "Kit implante 2");
        id_to_name.add("229", "Kit de chaves 1");
        id_to_name.add("230", "Kit de chaves 2");
        id_to_name.add("231", "Kit de seleção 1");
        id_to_name.add("232", "Kit de seleção 2");
        id_to_name.add("233", "Kit implante GM 2");
    }
}
