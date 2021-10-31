package com.donnekt.attendanceapp;
import androidx.annotation.NonNull;

import java.util.Locale;

public class Methods {

    public static String capitalizeFirstLetter(@NonNull String myString) {
        return myString.length() == 0 ? myString
        : myString.length() == 1 ? myString.toUpperCase()
        : myString.substring(0, 1).toUpperCase() + myString.substring(1).toLowerCase();
    }

    public static String capitalizeAllFirstLetters(@NonNull String myString){
        char[] array = myString.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        for(int g= 1; g < array.length; g++) {
            if(Character.isWhitespace(array[g - 1])) {
                array[g] = Character.toUpperCase(array[g]);
            }
        }
        return new String(array);
    }

    public static String cutStringToShow(String myString, int limit){
        return myString.substring(0, limit)+"...";
    }



}
