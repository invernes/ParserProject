package org.parsingbot.service;

import org.parsingbot.entity.Vacancy;

import java.util.List;
import java.util.function.Predicate;

/**
 * Интерфейс парсера
 */
public interface Parser {

    /**
     * Основная функция парсера - вызывает поиск по вакансиям и сохраняет найденное в БД
     *
     * @param vacancyToSearch   наименование вакансии для поиска
     * @param numberOfVacancies количество вакансий для поиска
     * @param filter            функция фильтрации вакансий
     * @return список найденных вакансий, прошедших фильтрацию
     */
    List<Vacancy> parse(String vacancyToSearch, int numberOfVacancies, Predicate<Vacancy> filter);

    /**
     * Основная функция парсера - вызывает поиск по вакансиям и сохраняет найденное в БД
     *
     * @param vacancyToSearch   наименование вакансии для поиска
     * @param numberOfVacancies количество вакансий для поиска
     * @return список найденных вакансий
     */
    List<Vacancy> parse(String vacancyToSearch, int numberOfVacancies);

    /**
     * Функция достает из БД вакансии, удовлетворяющие функции фильтра
     *
     * @param filter функция фильтрации вакансий
     * @return список найденных вакансий, прошедших фильтрацию
     */
    List<Vacancy> getVacancies(Predicate<Vacancy> filter);

    List<Vacancy> getAllVacancies();
}
