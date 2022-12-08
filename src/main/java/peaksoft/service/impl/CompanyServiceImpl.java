package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.model.Company;
import peaksoft.repository.CompanyRepository;
import peaksoft.service.CompanyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    @Override
    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    @Override
    public void addCompany(Company company) {
        repository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void updateCompany(Company company) {
        repository.save(company);
    }

    @Override
    public void deleteCompany(Company company) {
        repository.delete(company);
    }
}
