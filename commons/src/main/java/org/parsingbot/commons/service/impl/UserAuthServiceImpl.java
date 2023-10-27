package org.parsingbot.commons.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.commons.service.Authorisation;
import org.parsingbot.commons.service.UserAuthService;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.commons.entity.User;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private static final String USER_NOT_FOUND_ERROR = "User with name {} wasn't found";

    private final UserService userService;

    @Override
    public boolean isAuthorised(String userName, Authorisation minimumAuthorisation) {
        Optional<User> userOptional = userService.getUserByName(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Authorisation userAuthorisation = Authorisation.valueOf(user.getAuthorisation());
            return userAuthorisation.compareTo(minimumAuthorisation) >= 0;
        } else {
            log.warn(USER_NOT_FOUND_ERROR, userName);
        }
        return false;
    }

    @Override
    public void setAuthorisedById(Long id, Authorisation authorisation) {
        userService.updateAuthorisationById(id, authorisation);
    }
}