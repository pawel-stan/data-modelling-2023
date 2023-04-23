package edu.wsb.datamodellingdemo.config;

import edu.wsb.datamodellingdemo.authorities.Authority;
import edu.wsb.datamodellingdemo.authorities.AuthorityRepository;
import edu.wsb.datamodellingdemo.people.Person;
import edu.wsb.datamodellingdemo.people.PersonRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Znajduję w bazie użytkownika na podstawie username

        Person person = personRepository.findByUsername(username).orElse(null);

        if (person == null) {
            throw new UsernameNotFoundException(username);
        }

        // 2. Dodaję listę uprawnień tego użytkownika
        List<GrantedAuthority> authorities = getUserAuthorities(person);

        // 3. Zwracam tego użytkownika w postaci rozumianej przez Spring Security


        return new User(person.getUsername(),
                person.getPassword(),
                authorities);
    }

    private List<GrantedAuthority> getUserAuthorities(Person person) {
        // 1. Znajduję w bazie listę uprawnień danego użytkownika
        Set<Authority> authorities = person.getAuthorities();

        // 2. Przekształcam każdy obiekt w tej liście na GrantedAuthority
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Authority a : authorities) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(a.getAuthority());
            grantedAuthorities.add(simpleGrantedAuthority);
        }

        // 3. Zwracam listę tak przekształconych obiektów
        return new ArrayList<>(grantedAuthorities);
    }
}
