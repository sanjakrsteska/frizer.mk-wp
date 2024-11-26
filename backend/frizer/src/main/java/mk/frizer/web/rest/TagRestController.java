package mk.frizer.web.rest;

import mk.frizer.model.BaseUser;
import mk.frizer.model.Tag;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.TreatmentAddDTO;
import mk.frizer.model.dto.TreatmentUpdateDTO;
import mk.frizer.model.exceptions.TagNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/tags", "/api/tag"})
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})
public class TagRestController {
    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }
    @GetMapping()
    public List<Tag> getTags() {
        return tagService.getTags();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id){
        return this.tagService.getTagById(id)
                .map(tag -> ResponseEntity.ok().body(tag))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Tag> createTag(@RequestParam String name) {
        return this.tagService.createTag(name)
                .map(tag -> ResponseEntity.ok().body(tag))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Tag> deleteTagById(@PathVariable Long id) {
        Optional<Tag> tag = this.tagService.deleteTagById(id);
        try{
            this.tagService.getTagById(id);
            return ResponseEntity.badRequest().build();
        }
        catch(TagNotFoundException exception){
            return ResponseEntity.ok().body(tag.get());
        }
    }
}
