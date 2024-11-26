package mk.frizer.service;

import mk.frizer.model.City;

import java.util.List;

public interface CityService {
    public List<City> getCities();
    public List<City> getTop6Cities();
}
