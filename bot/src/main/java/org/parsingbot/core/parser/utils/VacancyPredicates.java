package org.parsingbot.core.parser.utils;

import org.parsingbot.commons.entity.Vacancy;

import java.util.List;
import java.util.function.Predicate;

public class VacancyPredicates {
    private static final String ADD_VACANCY_PREFIX = "adsrv";

    public static final Predicate<Vacancy> JUNIOR =
            vacancy -> vacancy.getVacancyName().toLowerCase().contains("junior");

    public static final Predicate<Vacancy> MIDDLE =
            vacancy -> vacancy.getVacancyName().toLowerCase().contains("middle");

    public static final Predicate<Vacancy> SENIOR =
            vacancy -> vacancy.getVacancyName().toLowerCase().contains("senior");

    public static Predicate<Vacancy> uniqueVacancy(List<Vacancy> vacancies) {
        return vacancy -> !vacancies.contains(vacancy);
    }

    public static Predicate<Vacancy> uniqueAndNonAddVacancy(List<Vacancy> vacancies) {
        return vacancy -> !vacancies.contains(vacancy) && !vacancy.getVacancyLink().contains(ADD_VACANCY_PREFIX);
    }
}