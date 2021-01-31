package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import java.util.ResourceBundle;

public class I18N {
    private final ResourceBundle bundle;
    private final String prefix;

    public I18N(Class<?> classs) {
        bundle = ResourceBundle.getBundle(("cz/muni/fi/pv168/podzim2020/group05/team1/") + "i18n");
        prefix = classs.getSimpleName() + ".";
    }

    public String getString(String key) {
        return bundle.getString(prefix + key);
    }

    public <E extends Enum<E>> String getString(E key) {
        return bundle.getString(key.getClass().getSimpleName() + "." + key.name());
    }
}
