package edu.wsb.datamodellingdemo.people;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PersonController {

    // Dependency injection -> https://www.digitalocean.com/community/tutorials/java-dependency-injection-design-pattern-example-tutorial
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @GetMapping("/demo")
    String demo() {
        return "WSB!";
    }

    @GetMapping("/list")
    public Iterable<Person> list() {
        return personRepository.findAll();
    }

    @PostMapping("/save")
    public Person save(@RequestParam String username,
                       @RequestParam String password) {

        Person newPerson = new Person(username, password, true);

        personRepository.save(newPerson);

        return newPerson;
    }
}
