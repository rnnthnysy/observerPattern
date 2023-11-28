package observerPattern;

import java.util.List;

public class NewsSubscriber implements Subscriber {
    private String name;
    private List<String> preferencesList;

    public NewsSubscriber(String name, List<String> initialPreferences) {
        this.name = name;
        this.preferencesList = initialPreferences;
    }

    @Override
    public void update(String news) {
        System.out.println(name + " received breaking news: " + news);
    }

    @Override
    public void modifyPreferences(List<String> preferences) {
        preferencesList = preferences;
        System.out.println(name + "'s preferences modified to: " + preferencesList + "\n");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getPreferences() {
        return preferencesList;
    }
}
