package com.projects.eventticket.eventticket.email;

import com.projects.eventticket.eventticket.utils.HtmlUtil;
import com.projects.eventticket.eventticket.utils.Mail;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InviteStaffMail {
    private final Mail mail;
    private final HtmlUtil htmlUtil;

    public void send(String to, UUID inviteId) throws MessagingException, IOException {
        String link = "/invitation/"+inviteId.toString();
        Map<String,String> payload = new HashMap<>();
        payload.put("link",link);

        String subject = "Inviting you as staff";
        String body = htmlUtil.parse("InviteStaffMail.html",payload);
        mail.sendHtml(to,subject,body);
    }
}
