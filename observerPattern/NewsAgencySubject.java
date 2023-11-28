package observerPattern;
public interface NewsAgencySubject {
    void subscribe(Subscriber subscriber);

    void notifySubscribers(String newsUpdate);
}