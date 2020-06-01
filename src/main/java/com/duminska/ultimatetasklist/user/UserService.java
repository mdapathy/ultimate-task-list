package com.duminska.ultimatetasklist.user;

import com.duminska.ultimatetasklist.config.token.JwtTokenProvider;
import com.duminska.ultimatetasklist.exception.ValidationException;
import com.duminska.ultimatetasklist.mail.MailService;
import com.duminska.ultimatetasklist.projects.ProjectService;
import com.duminska.ultimatetasklist.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDao userDao;
    private final MailService mailService;
    private final ProjectService projectService;
    private final TaskService taskService;


    @Autowired
    public UserService(UserDao userDao,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       MailService mailService,
                       ProjectService projectService,
                       TaskService taskService
    ) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailService = mailService;
        this.projectService = projectService;
        this.taskService = taskService;
    }


    User signUpUser(DtoUserAuth user) {
        if (userDao.getByEmail(user.getEmail()) != null) {
            throw new ValidationException("User with such email already exist");
        }

        User toSave = User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .activationLink(bCryptPasswordEncoder.encode(user.getEmail() + new Date().toString()))
                .build();
        User signedUpUser = userDao.signUp(toSave);

        try {
            mailService.sendSimpleMessage(signedUpUser.getEmail(), signedUpUser.getActivationLink());
        } catch (Exception e) {
            throw new ValidationException(String.format("Error sending activation email %s", e.getMessage()));
        }
        return signedUpUser;
    }

    String loginUser(DtoUserAuth dtoUser) {
        User currentUser = userDao.getByEmail(dtoUser.getEmail());

        if (currentUser == null) {
            throw new ValidationException(
                    String.format("User with email %s not found ", dtoUser.getEmail()));

        } else if (!currentUser.isActivated()) {

            throw new ValidationException(
                    String.format("User with email %s is not activated", dtoUser.getEmail()));

        } else if (!dtoUser.getPassword().equals(currentUser.getPassword())) {

            throw new ValidationException("Incorrect password");
        }


        return jwtTokenProvider.provideToken(dtoUser.getEmail());

    }

    void activateUser(String activationLink) {
        User user = userDao.getByActivationLink(activationLink);
        if (user == null) {
            throw new ValidationException(
                    "User with such activation link not found");

        } else if (user.isActivated()) {
            throw new ValidationException("User is already activated");
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            if (user.getAccCreationDate().before(calendar.getTime())) {
                userDao.deleteUserById(user.getId());
                throw new ValidationException("The link has expired. Sign up again.");
            }
        }

        userDao.activateUser(user.getId());
        String projectId = projectService.initProjectsForNewUser(user.getId());
        taskService.initTasksForNewUser(user.getId(), projectId);

    }


}
