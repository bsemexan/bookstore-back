package com.shop.bookstore.util;

public class TextUtil {

    public String sanitize(String textToSanitize){
        return textToSanitize.replaceAll("\\+s", " ");
    }
}
