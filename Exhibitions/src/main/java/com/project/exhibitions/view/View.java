package com.project.exhibitions.view;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Illia Koshkin
 */
public class View {

    public static final String BUNDLE_NAME = "texts";

    public static View view = new View();

    private ResourceBundle bundle =
            ResourceBundle.getBundle(BUNDLE_NAME, new Locale("en"));

    public View() {

    }
    /**
     * Chooses proper text from bundle
     * @param path Path of a text in bundle
     * @return message from bundle
     */
    public String getBundleText(String path) {
        return bundle.getString(path);
    }

    /**
     * Changes current locale
     * @param locale object with desirable locale
     * @return boolean value if it has changed locale
     */
    public boolean changeLocale(Optional<Locale> locale) {
        if (locale.isPresent()) {
            bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale.get());
            return true;
        } else {
            return false;
        }
    }


}
