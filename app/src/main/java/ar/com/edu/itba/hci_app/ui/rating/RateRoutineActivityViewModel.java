package ar.com.edu.itba.hci_app.ui.rating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.domain.Rating;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;

public class RateRoutineActivityViewModel extends AndroidViewModel {

    RoutineRepository repository;

    public RateRoutineActivityViewModel(MyApplication application, BaseRepository repository){
        super(application);
        this.repository = (RoutineRepository) repository;
    }

    private MutableLiveData<Resource<Rating>> ratings;
    public LiveData<Resource<Rating>> addRating(int id, Rating rating){
        repository.addRating(id,rating).observeForever(ratingResource -> {
            switch (ratingResource.getStatus()){
                case SUCCESS:
                    ratings.setValue(ratingResource);
                    break;
            }
        });
        return ratings;
    }
}
