package mk.frizer.service;

import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.dto.TagAddDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SalonService {
    List<Salon> getSalons();
    List<Salon> getTop8Salons();

    Optional<Salon> getSalonById(Long id);

    Optional<Salon> createSalon(SalonAddDTO salonAddDTO);

    Optional<Salon> updateSalon(Long id, SalonUpdateDTO salonUpdateDTO);

    Optional<Salon> deleteSalonById(Long id);

    Optional<Salon> addTagToSalon(TagAddDTO tagAddDTO);

    Optional<Salon> addTreatmentToSalon(Treatment treatment);

    Optional<Salon> editTreatmentForSalon(Treatment treatment);

    Optional<Salon> saveImage(Long id, MultipartFile image) throws IOException;
    Optional<Salon> saveImageWithId(Long id, Integer imageNo, MultipartFile image) throws IOException;

    List<Salon> filterSalons(String name, String city, Float distance, Float rating, String userLocation);

    List<String> getSalonsAsString(List<Salon> salons);

    String getSalonAsString(Salon salons);

    boolean isUserAuthorizedToAddTreatment(Long id, String name);

    boolean isUserAuthorizedToAddSalon(Long id, String email);
}
