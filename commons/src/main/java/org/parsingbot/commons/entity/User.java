package org.parsingbot.commons.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String userName;
    private String authorisation;
    private Boolean isSubscribed;
    private Long chatId;
    private LocalDateTime nextSendDate;
    private Long nextSendDateDelaySeconds;
    private String state;
    private String vacancyName;
    private Long numberOfVacancies;
    private String keywords;
    @ManyToMany
    @JoinTable(
            name = "users_vacancies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private List<Vacancy> userVacancies;

    @OneToMany(mappedBy = "user")
    private List<SearchHistory> searchHistories;

    public void addVacancy(Vacancy vacancy) {
        userVacancies.add(vacancy);
    }

    public void addAllVacancies(List<Vacancy> vacancies) {
        vacancies.forEach(this::addVacancy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;
        return chatId.equals(user.chatId);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + chatId.hashCode();
        return result;
    }
}