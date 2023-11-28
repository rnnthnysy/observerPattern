package observerPattern;
import java.util.*;
public interface Subscriber {
    void update(String news);
    void modifyPreferences(List<String> preferences);
    String getName();
    List<String> getPreferences();
}