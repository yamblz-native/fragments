package ru.yandex.yamblz.homework;

/**
 * Created by platon on 06.08.2016.
 */
public class Utils
{
    public static String concatStrings(String ... strings)
    {
        StringBuilder builder = new StringBuilder();

        if (strings != null) {

            for (String s : strings)
            {
                builder.append(s);
            }
        }

        return builder.toString();
    }
}
