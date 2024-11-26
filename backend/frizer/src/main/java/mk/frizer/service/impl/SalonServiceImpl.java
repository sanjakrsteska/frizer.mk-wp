package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.*;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.dto.TagAddDTO;
import mk.frizer.model.enums.Role;
import mk.frizer.model.events.SalonCreatedEvent;
import mk.frizer.model.events.SalonUpdatedEvent;
import mk.frizer.model.exceptions.CityNotFoundException;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.TagNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.*;
import mk.frizer.service.SalonService;
import mk.frizer.utilities.DistanceCalculator;
import mk.frizer.utilities.SalonAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalonServiceImpl implements SalonService {
    private final SalonRepository salonRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final EmployeeRepository employeeRepository;
    private final TagRepository tagRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final DistanceCalculator distanceCalculator;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/salons/";
    private final CityRepository cityRepository;
    private final BaseUserRepository userRepository;

    public SalonServiceImpl(SalonRepository salonRepository, BusinessOwnerRepository businessOwnerRepository, EmployeeRepository employeeRepository, TagRepository tagRepository, ApplicationEventPublisher applicationEventPublisher, DistanceCalculator distanceCalculator, CityRepository cityRepository, BaseUserRepository userRepository) {
        this.salonRepository = salonRepository;
        this.businessOwnerRepository = businessOwnerRepository;
        this.employeeRepository = employeeRepository;
        this.tagRepository = tagRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.distanceCalculator = distanceCalculator;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Salon> getSalons() {
        return salonRepository.findAll();
    }

    @Override
    public List<Salon> getTop8Salons() {
        return salonRepository.findTop8ByOrderByRatingDesc();
    }

    @Override
    public Optional<Salon> getSalonById(Long id) throws SalonNotFoundException {
        Salon salon = salonRepository.findById(id).orElseThrow(SalonNotFoundException::new);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> createSalon(SalonAddDTO salonAddDTO) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(salonAddDTO.getBusinessOwnerId()).orElseThrow(UserNotFoundException::new);
        City city = cityRepository.findByName(salonAddDTO.getCity())
                .orElseThrow(CityNotFoundException::new);

        Salon salon = new Salon(salonAddDTO.getName(), salonAddDTO.getDescription(), salonAddDTO.getLocation(), city, salonAddDTO.getPhoneNumber(), businessOwner, salonAddDTO.getLatitude(), salonAddDTO.getLongitude());

        salonRepository.save(salon);

        applicationEventPublisher.publishEvent(new SalonCreatedEvent(salon));
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> updateSalon(Long id, SalonUpdateDTO salonUpdateDTO) {
        Salon salon = getSalonById(id).orElseThrow(SalonNotFoundException::new);

        salon.setName(salonUpdateDTO.getName());
        salon.setDescription(salonUpdateDTO.getDescription());
        salon.setLocation(salonUpdateDTO.getLocation());
        salon.setPhoneNumber(salonUpdateDTO.getPhoneNumber());
        salon.setRating(salonUpdateDTO.getRating());
        salon.setLatitude(salonUpdateDTO.getLatitude());
        salon.setLongitude(salonUpdateDTO.getLongitude());
        salonRepository.save(salon);

        applicationEventPublisher.publishEvent(new SalonUpdatedEvent(salon));

        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> deleteSalonById(Long id) {
        Salon salon = getSalonById(id).orElseThrow(SalonNotFoundException::new);
        salonRepository.deleteById(id);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> addTagToSalon(TagAddDTO tagAddDTO) {
        Salon salon = getSalonById(tagAddDTO.getSalonId()).get();
        Tag tag = tagRepository.findById(tagAddDTO.getTagId()).orElseThrow(TagNotFoundException::new);
        salon.getTags().add(tag);
        return Optional.of(salonRepository.save(salon));
    }

    @Override
    @Transactional
    public Optional<Salon> addTreatmentToSalon(Treatment treatment) {
        Salon salon = getSalonById(treatment.getSalon().getId()).get();
        salon.getSalonTreatments().add(treatment);
        salonRepository.save(salon);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> editTreatmentForSalon(Treatment treatment) {
        Salon salon = getSalonById(treatment.getSalon().getId()).get();

        salon.setSalonTreatments(salon.getSalonTreatments().stream().map(item -> {
            if (item.getId().equals(treatment.getId())) {
                return treatment;
            }
            return item;
        }).collect(Collectors.toList()));
        salonRepository.save(salon);
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Salon> saveImage(Long id, MultipartFile image) throws IOException {
        String dirPath = String.format("%s/salon_%d", UPLOAD_DIR, id);

        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(dirPath, fileName);
        Files.write(filePath, image.getBytes());

        Optional<Salon> salon = getSalonById(id);
        if (salon.isPresent()) {
            String fullPath = filePath.toString().replace("templates/", "");
            String pathAfterStatic = fullPath.substring(fullPath.indexOf("static") + 6);
            salon.get().getImagePaths().add(pathAfterStatic);
            salonRepository.save(salon.get());
            return salon;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Salon> saveImageWithId(Long id, Integer imageNo, MultipartFile image) throws IOException {
        String dirPath = String.format("%s/salon_%d", UPLOAD_DIR, id);

        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Optional<Salon> salon = getSalonById(id);
        if (salon.isPresent()) {
            List<String> imagePaths = salon.get().getImagePaths();

            //delete the old one
            if (!imagePaths.get(imageNo).contains("/images/salons/default")) {
                String oldImagePath = String.format("%s%s", "src\\main\\resources\\static", imagePaths.get(imageNo));
                File oldImageFile = new File(oldImagePath);
                if (oldImageFile.exists()) {
                    oldImageFile.delete();
                }
            }

            //save the new one
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(dirPath, fileName);
            Files.write(filePath, image.getBytes());

            String fullPath = filePath.toString().replace("templates/", "");
            String pathAfterStatic = fullPath.substring(fullPath.indexOf("static") + 6);

            imagePaths.set(imageNo, pathAfterStatic);

            salonRepository.save(salon.get());
            return salon;
        }
        return Optional.empty();
    }

    @Override
    public List<Salon> filterSalons(String name, String city, Float distance, Float rating, String userLocation) {
        List<Salon> salonByName = salonRepository.findAll()
                .stream().filter(salon -> salon.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        List<Salon> salonsByRating = salonRepository.findAllByRatingGreaterThanEqual(rating);
        List<Salon> salonsByLocation = new ArrayList<>();
        if (!city.equals("Цела Македонија")) {
            Optional<City> city1 = cityRepository.findByNameEqualsIgnoreCase(city);
            if (city1.isPresent()) {
                salonsByLocation = city1.get().getSalonsInCity();
            }
        } else {
            salonsByLocation = this.getSalons();
        }
        List<Salon> salonsByDistance = this
                .getSalons()
                .stream()
                .filter(salon -> distance >= distanceCalculator.getDistance(userLocation, salon.getLatitude(), salon.getLongitude()))
                .toList();

        List<Salon> interceptSalons = new ArrayList<>(salonByName);
        interceptSalons.retainAll(salonsByRating);
        interceptSalons.retainAll(salonsByLocation);
        interceptSalons.retainAll(salonsByDistance);
        return interceptSalons;
    }

    @Override
    public List<String> getSalonsAsString(List<Salon> salons) {
        return salons.stream()
                .map(SalonAdapter::convertToString)
                .collect(Collectors.toList());
    }

    @Override
    public String getSalonAsString(Salon salon) {
        return SalonAdapter.convertToString(salon);
    }

    @Override
    public boolean isUserAuthorizedToAddTreatment(Long id, String userEmail) {
        Salon salon = salonRepository.findById(id).orElseThrow(SalonNotFoundException::new);
        BaseUser user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        if (user.getRoles().contains(Role.ROLE_OWNER) && salon.getOwner().getBaseUser().getEmail().equals(userEmail)) {
            return true;
        }
        if (user.getRoles().contains(Role.ROLE_EMPLOYEE)) {
            for (Employee employee : salon.getEmployees()) {
                if (employee.getBaseUser().getEmail().equals(userEmail)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isUserAuthorizedToAddSalon(Long id, String email) {
        Salon salon = salonRepository.findById(id).orElseThrow(SalonNotFoundException::new);
        BaseUser user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (user.getRoles().contains(Role.ROLE_OWNER) && salon.getOwner().getBaseUser().getEmail().equals(email)) {
            return true;
        }
        return false;
    }
}

