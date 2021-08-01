package com.project.exhibitions.view;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class View {

    public static final String BUNDLE_NAME = "texts";

    private ResourceBundle bundle =
            ResourceBundle.getBundle(BUNDLE_NAME, new Locale("en"));

    public String getBundleText(String path) {
        return bundle.getString(path);
    }

    public boolean changeLocale(Optional<Locale> locale) {
        if (locale.isPresent()) {
            bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale.get());
            return true;
        } else {
            return false;
        }
    }


}
