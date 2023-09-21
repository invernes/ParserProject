package org.parsingbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.State;
import org.parsingbot.entity.User;
import org.parsingbot.repository.UserRepository;
import org.parsingbot.service.Authorisation;
import org.parsingbot.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getSubscribedUsers() {
        return userRepository.findSubscribedUsers();
    }

    @Override
    public void updateAuthorisationById(Long id, Authorisation authorisation) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAuthorisation(Authorisation.asString(authorisation));
            userRepository.save(user);
        }
    }

    @Override
    public Optional<User> getUserByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId);
    }

    @Override
    public User getUserByChatIdCreateIfNotExist(Long chatId, String userName) {
        Optional<User> userOptional = this.getUserByChatId(chatId);
        return userOptional.orElseGet(() -> this.save(User.builder().userName(userName).chatId(chatId).build()));
    }

    @Override
    public void updateNextSendDate(User user) {
        Long nextSendDateDelaySeconds = user.getNextSendDateDelaySeconds();
        LocalDateTime nextSendDate = LocalDateTime.now();
        user.setNextSendDate(nextSendDate.plusSeconds(nextSendDateDelaySeconds));
        userRepository.save(user);
    }

    @Override
    public void updateStateByUserId(Long userId, State state) {
        userRepository.updateStateByUserId(userId, state.toString());
    }

    @Override
    public void updateStateByUser(User user, State state) {
        userRepository.updateStateByUserId(user.getId(), state.toString());
    }

    @Override
    public void setDefaultStateByUserId(Long userId) {
        userRepository.updateStateByUserId(userId, State.NONE.toString());
    }

    @Override
    public String getVacancyNameByUserId(Long userId) {
        return userRepository.findVacancyNameByUserId(userId);
    }

    @Override
    public Long getNumberOfVacanciesByUserId(Long userId) {
        return userRepository.findNumberOfVacanciesByUserId(userId);
    }

    @Override
    public String getKeywordsByUserId(Long userId) {
        return userRepository.findKeywordsByUserId(userId);
    }
}