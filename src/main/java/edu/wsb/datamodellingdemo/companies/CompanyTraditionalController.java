package edu.wsb.datamodellingdemo.companies;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/companies")
public class CompanyTraditionalController {

    private final CompanyRepository companyRepository;

    public CompanyTraditionalController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/")
    public ModelAndView list() {
        // Znaleźć wszystkie rekordy company w bazie danych
        // przekazać tę listę firm w zmiennej o nazwie np. companies od widoku
        // zdefiniować nazwę widoku html / thymeleaf

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companies", companyRepository.findAll());
        modelAndView.setViewName("companies/index");

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("companies/create");

        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView();

        Company company = new Company();
        company.setName(name);

        companyRepository.save(company);

        modelAndView.setViewName("redirect:/companies/");

        return modelAndView;
    }
}
