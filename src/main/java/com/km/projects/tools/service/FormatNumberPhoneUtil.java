package com.km.projects.tools.service;

public class FormatNumberPhoneUtil {

    private FormatNumberPhoneUtil() {
        // default constructor
    }

    public static String getNumberFormat(String tel) {

        if (tel == null) {
            return "";
        }
        else {
            tel = tel.trim();

            if (tel.startsWith("+221")) {

                tel = tel.substring(4);

            }
            else if (tel.startsWith("221")) {

                tel = tel.substring(3);

            }
            else if (tel.startsWith("00221")) {

                tel = tel.substring(5);

            }

            tel = tel.replaceAll(" ", "");

            return tel;
        }

    }

}
