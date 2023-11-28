package observerPattern;
import java.util.*;

public class RealTimeNewsSubscriptionService {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NewsAgency newsAgency = new NewsAgency();

        System.out.println("Real-time News Subscription Service" + "\n");

        List<String> newsCategoriesList = new ArrayList<>(Arrays.asList("Health", "Entertainment", "Business"));
        System.out.println("Available News Categories: " + newsCategoriesList);

        Subscriber subscriberOne = new NewsSubscriber("Ron", List.of("Health"));
        Subscriber subscriberTwo = new NewsSubscriber("Anthony", List.of("Health"));
        Subscriber subscriberThree = new NewsSubscriber("Sy", List.of("Health"));

        List<Subscriber> subscriberList = new ArrayList<>(Arrays.asList(subscriberOne, subscriberTwo, subscriberThree));

        newsAgency.subscribe(subscriberOne);
        newsAgency.subscribe(subscriberTwo);
        newsAgency.subscribe(subscriberThree);

        System.out.println();

        //providing the user an initial news update
        newsAgency.publishNews("[Health] DOH announces a new virus like COVID");

        while (true) {
            try {
                System.out.println("\nMenu:");
                System.out.println("1. Modify Preferences");
                System.out.println("2. Subscribe to a News Category");
                System.out.println("3. Unsubscribe from a News Category");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        modifyPreferences(scanner, subscriberList);
                        break;
                    case 2:
                        subscribeToCategory(scanner, subscriberList);
                        break;
                    case 3:
                        unsubscribeFromCategory(scanner, subscriberList);
                        break;
                    case 4:
                        System.out.println("Exiting the program... " +
                                "\nThankyou for using the App! " +
                                "\nRon Anthony Sy");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private static void modifyPreferences(Scanner scanner, List<Subscriber> subscribers) {
        System.out.println("Enter subscriber name to modify preferences [Ron, Anthony, Sy]: ");
        String subscriberName = scanner.nextLine();
        Subscriber subscriber = findSubscriber(subscribers, subscriberName);

        if (subscriber != null) {
            System.out.println("Enter new preferences [Health, Entertainment, Business] (comma-separated): ");
            String userInput = scanner.nextLine();
            List<String> newPreferences = Arrays.asList(userInput.split(","));

            // Filter out invalid categories using a loop
            List<String> validPreferences = new ArrayList<>();
            for (String category : newPreferences) {
                String trimmedCategory = category.trim();
                validPreferences.add(trimmedCategory);
            }

            if (!validPreferences.isEmpty()) {
                subscriber.modifyPreferences(validPreferences);

                // Display the updated news category for all subscribers
                System.out.println("\nUpdated News Category for Subscribers:");
                for (Subscriber updatedSubscriber : subscribers) {
                    List<String> preferences = updatedSubscriber.getPreferences();
                    String displayedCategory = preferences.isEmpty() ?
                            getDefaultPreference(updatedSubscriber) :
                            preferences.get(0);
                    System.out.println(updatedSubscriber.getName() + ": " + displayedCategory);
                }

                // Display breaking news based on the modified preferences
                System.out.println();
                displayBreakingNews(subscribers);
            } else {
                System.out.println("No valid preferences entered. Preferences remain unchanged.");
            }
        } else {
            System.out.println("Subscriber not found.");
        }
    }

    private static void subscribeToCategory(Scanner scanner, List<Subscriber> subscribers) {
        System.out.println("Enter subscriber name to subscribe to a category [Ron, Anthony, Sy]: ");
        String subscriberName = scanner.nextLine();
        Subscriber subscriber = findSubscriber(subscribers, subscriberName);

        if (subscriber != null) {
            System.out.println("Enter the category to subscribe to [Health, Entertainment, Business]: ");
            String category = scanner.nextLine().trim();

            System.out.println("Debug: Entered category: " + category);

            if (isValidCategory(category)) {
                System.out.println("Debug: Valid category");

                // Case-insensitive check for existing subscriptions
                List<String> currentPreferences = subscriber.getPreferences();
                boolean alreadySubscribed = false;

                for (String pref : currentPreferences) {
                    if (pref.equalsIgnoreCase(category)) {
                        alreadySubscribed = true;
                        break;
                    }
                }

                if (alreadySubscribed) {
                    System.out.println(subscriber.getName() + " is already subscribed to category: " + category);
                } else {
                    subscriber.modifyPreferences(Collections.singletonList(category));
                    System.out.println(subscriber.getName() + " subscribed to category: " + category);

                    // Display breaking news based on the modified preferences
                    System.out.println();
                    displayBreakingNews(subscribers);
                }
            } else {
                System.out.println("Debug: Invalid category. Subscription failed.");
            }
        } else {
            System.out.println("Debug: Subscriber not found.");
        }
    }

    private static void unsubscribeFromCategory(Scanner scanner, List<Subscriber> subscribers) {
        System.out.println("Enter subscriber name to unsubscribe from a category [Ron, Anthony, Sy]: ");
        String subscriberName = scanner.nextLine();
        Subscriber subscriber = findSubscriber(subscribers, subscriberName);

        if (subscriber != null) {
            System.out.println("Enter the category to unsubscribe from [Health, Entertainment, Business]: ");
            String category = scanner.nextLine().trim().toLowerCase();

            if (isValidCategory(category)) {
                List<String> currentPreferences = new ArrayList<>(subscriber.getPreferences()); // Create a modifiable copy
                boolean categoryRemoved = false;

                for (int i = 0; i < currentPreferences.size(); i++) {
                    String pref = currentPreferences.get(i);
                    if (pref.equalsIgnoreCase(category)) {
                        currentPreferences.remove(i);
                        categoryRemoved = true;
                        break;
                    }
                }

                if (categoryRemoved) {
                    System.out.println(subscriber.getName() + " unsubscribed from category: " + category);
                } else {
                    System.out.println(subscriber.getName() + " is not subscribed to category: " + category);
                }

                // Update the subscriber's preferences with the modified list
                subscriber.modifyPreferences(currentPreferences);

                // Display breaking news based on the modified preferences
                System.out.println();
                displayBreakingNews(subscribers);
            } else {
                System.out.println("Invalid category. Unsubscription failed.");
            }
        } else {
            System.out.println("Subscriber not found.");
        }
    }

    private static Subscriber findSubscriber(List<Subscriber> subscribers, String name) {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getName().equalsIgnoreCase(name)) {
                return subscriber;
            }
        }
        return null;
    }

    private static String getDefaultPreference(Subscriber subscriber) {
        List<String> preferences = subscriber.getPreferences();
        return preferences.isEmpty() ? "Health" : preferences.get(0);
    }

    private static boolean isValidCategory(String category) {
        String[] validCategories = {"Health", "Entertainment", "Business"};
        String lowercaseCategory = category.toLowerCase();

        for (String validCat : validCategories) {
            if (validCat.equalsIgnoreCase(lowercaseCategory)) {
                return true;
            }
        }
        return false;
    }

    private static void displayBreakingNews(List<Subscriber> subscribers) {
        System.out.println("Breaking News!");

        for (Subscriber subscriber : subscribers) {
            List<String> preferences = subscriber.getPreferences();
            String displayedCategory = preferences.isEmpty() ?
                    getDefaultPreference(subscriber) :
                    preferences.get(0);

            // Display specific news based on the category
            String breakingNews = getBreakingNewsForCategory(displayedCategory);
            System.out.println(subscriber.getName() + " received breaking news: [" + displayedCategory + "] " + breakingNews);
        }
    }

    private static String getBreakingNewsForCategory(String category) {
        switch (category.toLowerCase()) {
            case "health":
                return "DOH announces a new virus like COVID";
            case "entertainment":
                return "Actor Juan wants to raise a fund";
            case "business":
                return "A millionaire spends his money on crypto";
            default:
                return "No specific news for this category";
        }
    }
}