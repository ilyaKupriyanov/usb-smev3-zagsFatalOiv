package ru.kck.usb.zags.fataloiv.utils;

import java.util.HashMap;
import java.util.Map;

public class Translate {
    public static final Map<Character, String> kir2lat = initialize();

    private static Map<Character, String> initialize() {
        Map<Character, String> map = new HashMap<>();
        map.put('А', "A");
        map.put('Б', "B");
        map.put('В', "V");
        map.put('Г', "G");
        map.put('Д', "D");
        map.put('Е', "E");
        map.put('Ё', "E");
        map.put('Ж', "ZH");
        map.put('З', "Z");
        map.put('И', "I");
        map.put('Й', "I");
        map.put('К', "K");
        map.put('Л', "L");
        map.put('М', "M");
        map.put('Н', "N");
        map.put('О', "O");
        map.put('П', "P");
        map.put('Р', "R");
        map.put('С', "S");
        map.put('Т', "T");
        map.put('У', "U");
        map.put('Ф', "F");
        map.put('Х', "H");
        map.put('Ц', "C");
        map.put('Ч', "CH");
        map.put('Ш', "SH");
        map.put('Щ', "SHCH");
        map.put('Ъ', "");
        map.put('Ы', "Y");
        map.put('Ь', "");
        map.put('Э', "E");
        map.put('Ю', "YU");
        map.put('Я', "YA");
        map.put('а', "a");
        map.put('б', "b");
        map.put('в', "v");
        map.put('г', "g");
        map.put('д', "d");
        map.put('е', "e");
        map.put('ё', "e");
        map.put('ж', "zh");
        map.put('з', "z");
        map.put('и', "i");
        map.put('й', "i");
        map.put('к', "k");
        map.put('л', "l");
        map.put('м', "m");
        map.put('н', "n");
        map.put('о', "o");
        map.put('п', "p");
        map.put('р', "r");
        map.put('с', "s");
        map.put('т', "t");
        map.put('у', "u");
        map.put('ф', "f");
        map.put('х', "h");
        map.put('ц', "c");
        map.put('ч', "ch");
        map.put('ш', "sh");
        map.put('щ', "shch");
        map.put('ъ', "");
        map.put('ы', "y");
        map.put('ь', "");
        map.put('э', "e");
        map.put('ю', "yu");
        map.put('я', "ya");
        return map;
    }
}