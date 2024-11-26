package mk.frizer.service;

import mk.frizer.model.Salon;
import mk.frizer.model.Tag;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> getTags();
    Optional<Tag> getTagById(Long id);
    Optional<Tag> createTag(String name);

    List<Tag> getTagsForSalon(Long id);

    Optional<Tag> deleteTagById(Long id);
}
