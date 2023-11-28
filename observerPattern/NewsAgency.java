package observerPattern;
import java.util.ArrayList;
import java.util.List;

public class NewsAgency implements NewsAgencySubject {
    private List<Subscriber> subscriberList = new ArrayList<>();

    @Override
    public void subscribe(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    @Override
    public void notifySubscribers(String newsUpdate) {
        for(Subscriber subscriber : subscriberList){
            subscriber.update(newsUpdate);
        }
    }

    public void publishNews(String newsUpdate){
        System.out.println("Breaking News! " + newsUpdate + "\n");
        notifySubscribers(newsUpdate);
    }
}
