package tracker;

import java.util.Arrays;
import java.util.List;

public enum MenuItems {
    EXIT ("exit"),
    ADD_STUDENTS ("add students"),
    ADD_POINTS ("add points"),
    LIST ("list"),
    FIND ("find"),
    BACK ("back"),
    STATISTICS ("statistics"),
    NOTIFY ("notify"),
    UNKNOWN (""),
    NO_INPUT("");

    private final String valueString;

    MenuItems(String valueString) {
        this.valueString = valueString;
    }

    @Override
    public String toString() {
        return valueString;
    }

    public boolean equals(String other) {
        return valueString.equals(other);
    }

    public static MenuItems getValueOf(String value) {
        if (value.isEmpty() || value.isBlank()) {
            return MenuItems.NO_INPUT;
        }

        List<MenuItems> items = Arrays.stream(values()).filter((MenuItems e) -> e.valueString.equals(value)).toList();

        if (items.size() == 1) {
            return items.get(0);
        } else {
            return MenuItems.UNKNOWN;
        }
    }
}