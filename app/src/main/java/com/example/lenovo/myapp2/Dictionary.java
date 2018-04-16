package com.example.lenovo.myapp2;

import java.util.HashMap;

/**
 * Created by lenovo on 05-Jan-18.
 */

class Dictionary {
    private static HashMap<String, Integer> dictionary() {
        HashMap<String, Integer> map = new HashMap<>();

        map.put("light_on", 1);
        map.put("light_off", 2);
        map.put("lights_on", 1);
        map.put("lights_off", 2);
        map.put("turn_all_on", 1);
        map.put("turn_all_off", 2);
        map.put("red_light_on", 3);
        map.put("red_light_off", 4);
        map.put("yellow_light_on", 5);
        map.put("yellow_light_off", 6);
        map.put("blue_light_on", 7);
        map.put("blue_light_off", 8);
        map.put("fan_on", 9);
        map.put("fan_off", 10);


        map.put("red_light_on_and_yellow_light_on", 11);
        map.put("red_light_on_and_blue_light_on", 12);
        map.put("red_light_on_and_fan_on", 13);
        map.put("red_light_on_and_yellow_light_off", 14);
        map.put("red_light_on_and_blue_light_off", 15);
        map.put("red_light_on_and_fan_off", 16);

        map.put("red_light_off_and_yellow_light_on", 17);
        map.put("red_light_off_and_blue_light_on", 18);
        map.put("red_light_off_and_fan_on", 19);
        map.put("red_light_off_and_yellow_light_off", 20);
        map.put("red_light_off_and_blue_light_off", 21);
        map.put("red_light_off_and_fan_off", 22);


        map.put("yellow_light_on_and_red_light_on", 23);
        map.put("yellow_light_on_and_blue_light_on", 24);
        map.put("yellow_light_on_and_fan_on", 25);
        map.put("yellow_light_on_and_red_light_off", 26);
        map.put("yellow_light_on_and_blue_light_off", 27);
        map.put("yellow_light_on_and_fan_off", 28);

        map.put("yellow_light_off_and_red_light_on", 29);
        map.put("yellow_light_off_and_blue_light_on", 30);
        map.put("yellow_light_off_and_fan_on", 31);
        map.put("yellow_light_off_and_red_light_off", 32);
        map.put("yellow_light_off_and_blue_light_off", 33);
        map.put("yellow_light_off_and_fan_off", 34);


        map.put("blue_light_on_and_red_light_on", 35);
        map.put("blue_light_on_and_yellow_light_on", 36);
        map.put("blue_light_on_and_fan_on", 37);
        map.put("blue_light_on_and_red_light_off", 38);
        map.put("blue_light_on_and_yellow_light_off", 39);
        map.put("blue_light_on_and_fan_off", 40);

        map.put("blue_light_off_and_red_light_on", 41);
        map.put("blue_light_off_and_yellow_light_on", 42);
        map.put("blue_light_off_and_fan_on", 43);
        map.put("blue_light_off_and_red_light_off", 44);
        map.put("blue_light_off_and_yellow_light_off", 45);
        map.put("blue_light_off_and_fan_off", 46);


        map.put("fan_on_and_red_light_on", 47);
        map.put("fan_on_and_yellow_light_on", 48);
        map.put("fan_on_and_turn_blue_on", 49);
        map.put("fan_on_and_red_light_off", 50);
        map.put("fan_on_and_yellow_light_off", 51);
        map.put("fan_on_and_blue_light_off", 52);

        map.put("fan_off_and_red_light_on", 53);
        map.put("fan_off_and_yellow_light_on", 54);
        map.put("fan_off_and_blue_light_on", 55);
        map.put("fan_off_and_red_light_off", 56);
        map.put("fan_off_and_yellow_light_off", 57);
        map.put("fan_off_and_blue_light_off", 58);


        map.put(" ", 59);
        map.put(" ", 60);


        return map;

    }

    static int getCode(String code) {
        if (dictionary().containsKey(code)) {
            return dictionary().get(code);
        }
        return 0;
    }
}

