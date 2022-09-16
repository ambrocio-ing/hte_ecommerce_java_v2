package com.hteecommerce.hteapp.send_mail.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hteecommerce.hteapp.send_mail.dto.EmailValuesDto;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.urlAdmin}")
    private String urlAdmin;

    @Value("${mail.urlEcommerce}")
    private String urlEcommerce;

    public void mailSend(EmailValuesDto dto) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        Context context = new Context();

        Map<String, Object> model = new HashMap<>();
        model.put("username", dto.getUsername());

        if (dto.getTypeUser().equals("Admin")) {
            model.put("url", urlAdmin + dto.getToken());
        } else {
            model.put("url", urlEcommerce + dto.getToken());
        }

        context.setVariables(model);
        String htmlText = templateEngine.process("email_template", context);
        helper.setFrom(dto.getMailFrom());
        helper.setTo(dto.getMailTo());
        helper.setSubject(dto.getSubject());
        helper.setText(htmlText, true);

        javaMailSender.send(mimeMessage);

    }
}
