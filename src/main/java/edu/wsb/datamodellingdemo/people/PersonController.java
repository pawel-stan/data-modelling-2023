package edu.wsb.datamodellingdemo.people;

import edu.wsb.datamodellingdemo.companies.Company;
import edu.wsb.datamodellingdemo.companies.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {

    // Dependency injection -> https://www.digitalocean.com/community/tutorials/java-dependency-injection-design-pattern-example-tutorial
    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, CompanyRepository companyRepository) {
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
    }


    @GetMapping("/demo")
    String demo() {
        return "WSB!";
    }

    @GetMapping("/list")
    public Iterable<Person> list() {
        return personRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public Optional<Person> find(@PathVariable Long id) {
        return personRepository.findById(id);
    }

    @GetMapping("/usernames/{username}")
    public Optional<Person> byUsername(@PathVariable String username) {
        return personRepository.findByUsername(username);
    }

    // 1. Znajdź wszystkich użytkowników, których hasło zaczyna się od danej literki (np. "a")
    @GetMapping("/passwords/{p}")
    public Iterable<Person> byPassword(@PathVariable String p) {
        return personRepository.findAllByPasswordLikeIgnoreCase(p + "%");
    }

    // 2. Znajdź wszystkich użytkowników, których data utworzenia jest większa niż argument i posortuj ich po loginie (username)
    @GetMapping("/created")
    public Iterable<Person> usersCreatedAfter(@RequestParam String d) throws ParseException {

        // 2023-11-03T14:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = sdf.parse(d);

        return personRepository.findAllUsersCreatedAfter(date);
    }

    @PostMapping("/save")
    public Person save(@RequestParam String username,
                       @RequestParam String password) {

        Person newPerson = new Person(username, password, true);

        personRepository.save(newPerson);

        return newPerson;
    }

    // http://localhost:8080/people/saveWithCompany?username=wsb-4&password=1234&companyName=WSB
    @PostMapping("/saveWithCompany")
    public Person saveWithCompany(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String companyName) {

        Person person = new Person(username, password, true);

        // Musimy jakoś dodać firmę
        // 1. Wyszukujemy firmę po nazwie
        Optional<Company> optionalCompany = companyRepository.findByName(companyName);

        if (optionalCompany.isEmpty()) {
            throw new InvalidParameterException();
        }

        Company company = optionalCompany.get();

        // 2. Przypisujemy tę firmę do instancji `person`
        person.setCompany(company);

        personRepository.save(person);

        return person;
    }
}
