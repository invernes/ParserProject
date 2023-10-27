package org.parsingbot.commons.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.repository.UserRepository;
import org.parsingbot.commons.repository.VacancyRepository;
import org.parsingbot.commons.service.VacancyService;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.entity.Vacancy;

import java.util.List;

@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    @Override
    public void save(List<Vacancy> vacancies) {
        vacancyRepository.saveAll(vacancies);
    }

    @Override
    public void save(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }

    @Override
    public List<Vacancy> getVacanciesByIds(List<Integer> vacanciesIdsList) {
        return vacancyRepository.findAllById(vacanciesIdsList);
    }

    @Override
    public List<Vacancy> getVacanciesByUserId(Long userId) {
        List<Integer> userVacanciesIds = userRepository.findUserVacanciesIds(userId);
        return getVacanciesByIds(userVacanciesIds);
    }

    @Override
    public List<Vacancy> getVacanciesByUser(User user) {
        return getVacanciesByUserId(user.getId());
    }
}