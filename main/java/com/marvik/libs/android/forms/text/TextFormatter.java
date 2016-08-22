package com.marvik.libs.android.forms.text;

public class TextFormatter {

    /**
     * Method to highlight the presence of a set of characters
     *
     * @param text original text
     * @param key  characters to highlight
     * @return html formatted text with the key in bold
     */
    public static String highlightText(String text, String key) {
        String newText = "";

        int textLength = text.length();

        for (int i = 0; i < textLength; i++) {

            boolean replaced = false;

            Character textChar = text.charAt(i);

            int keyLength = key.length();

            for (int j = 0; j < keyLength; j++) {

                String newChar = "";

                Character keyChar = key.charAt(j);

                if (textChar.toString().toLowerCase().equals(keyChar.toString().toLowerCase())) {
                    replaced = true;
                    newText += "<b>" + textChar + "</b>";
                }
                newText += newChar;
            }
            if (!replaced) {
                newText += textChar;
            }
        }

        return newText;
    }
}