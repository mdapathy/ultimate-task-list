package com.duminska.ultimatetasklist.mail;

import com.duminska.ultimatetasklist.config.constants.Constants;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mail.url}")
    private String url;

    @Value("${mail.key}")
    private  String key;

    public void sendSimpleMessage(String email, String link) throws UnirestException {
        Unirest.post(url)
                .basicAuth("api", key)
                .queryString("from", "UTaskList Support <support@ultimate-task-list.com>")
                .queryString("to", email)
                .queryString("subject", "Ultimate Task List account activation")
                .queryString("text", String.format("Follow this link to activate your account %s%s", Constants.ACTIVATION_URL, link))
                .asJson().getBody();

    }

}
