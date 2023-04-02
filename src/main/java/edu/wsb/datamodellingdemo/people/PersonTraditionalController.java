package edu.wsb.datamodellingdemo.people;

import edu.wsb.datamodellingdemo.authorities.Authority;
import edu.wsb.datamodellingdemo.authorities.AuthorityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.InvalidParameterException;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonTraditionalController {

    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;

    public PersonTraditionalController(PersonRepository personRepository,
                                       AuthorityRepository authorityRepository) {
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping("/{username}/authorities")
    public String addAuthority(@PathVariable String username) {
        return "people/authorities";
    }

    @PostMapping("/{username}/authorities")
    public String addAuthority(@PathVariable String username,
                               @RequestParam String authority) {

        Optional<Person> optPerson = personRepository.findByUsername(username);

        if (optPerson.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy takiego człowieka");
        }

        Optional<Authority> optAuthority = authorityRepository.findByAuthority(authority);

        if (optAuthority.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy takiego uprawnienia!");
        }

        // Zapisujemy do bazy powiązanie między człowiekiem i uprawnieniem

        Person person = optPerson.get();
        Authority auth = optAuthority.get();

        person.authorities.add(auth);
        personRepository.save(person);

        return "redirect:/people/list";
    }

    @GetMapping("/{username}")
    public ModelAndView show(@PathVariable String username) {

        Optional<Person> optPerson = personRepository.findByUsername(username);

        if (optPerson.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy takiego człowieka");
        }

        Person person = optPerson.get();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("person", person);
        modelAndView.setViewName("people/show");

        return modelAndView;
    }

    @GetMapping("/{username}/removeAuthority")
    public String removeAuthority(@PathVariable String username,
                                  @RequestParam String authority) {

        Optional<Person> optPerson = personRepository.findByUsername(username);

        if (optPerson.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy takiego człowieka");
        }

        Optional<Authority> optAuthority = authorityRepository.findByAuthority(authority);

        if (optAuthority.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy takiego uprawnienia!");
        }

        // Usuwamy z bazy powiązanie między człowiekiem i uprawnieniem

        Person person = optPerson.get();
        Authority auth = optAuthority.get();

        person.authorities.remove(auth);
        personRepository.save(person);

        return "redirect:/people/list";
    }
}
