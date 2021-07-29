package ua.project.exhibition.view;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class View {
    public static ResourceBundle bundle =
            ResourceBundle.getBundle("messages",
                    //new Locale("ua", "UA"));
                    new Locale("en"));

    public static boolean changeLocale(Optional<Locale> locale) {
        locale.ifPresent(element -> {
            View.changeBundle(locale.get());
        });
        return locale.isPresent();
    }

    private static void changeBundle(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
    }
}
