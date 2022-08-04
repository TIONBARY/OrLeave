package com.orleave.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
 
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.orleave.entity.EmailConfirm;
import com.orleave.exception.EmailTimeoutException;
import com.orleave.repository.EmailConfirmRepository;
 
@Service
@PropertySource("classpath:email.properties")
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	EmailConfirmRepository emailConfirmRepository;
	
    @Autowired
    JavaMailSender emailSender;
    
    @Value("${AdminMail.id}")
    private String id;
 
    public static String ePw;
 
    private MimeMessage createMessage(String to) throws Exception{
        ePw = createKey();
    	System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ ePw);
        MimeMessage message = emailSender.createMimeMessage();
 
        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("Orleave 회원가입 이메일 인증");//제목
 
        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Orleave입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress(id,"Orleave"));//보내는 사람
 
        return message;
    }
 
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
 
        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
 
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
 
        return key.toString();
    }
    @Override
    @Transactional
    public void sendSimpleMessage(String to) throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
        try {
            emailSender.send(message);
            Optional<EmailConfirm> emailConfirm = emailConfirmRepository.findById(to);
            EmailConfirm ec = null;
            if (!emailConfirm.isPresent()) {
            	ec = EmailConfirm.builder()
            			.email(to)
            			.code(ePw)
            			.time(LocalDateTime.now())
            			.build();
            } else {
            	ec = emailConfirm.get();
            	ec.setCode(ePw);
            	ec.setTime(LocalDateTime.now());
            }
            emailConfirmRepository.save(ec);
        } catch(MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

	@Override
	@Transactional
	public boolean checkCode(String email, String code) throws Exception {
		Optional<EmailConfirm> emailConfirm = emailConfirmRepository.findById(email);
		if (!emailConfirm.isPresent()) throw new IllegalArgumentException();
		EmailConfirm ec = emailConfirm.get();
		if (Duration.between(ec.getTime(), LocalDateTime.now()).getSeconds() > 60 * 3) {
			emailConfirmRepository.delete(ec);
			throw new EmailTimeoutException();
		}
		if (ec.getCode().equals(code)) {
			emailConfirmRepository.delete(ec);
			return true;
		}
		return false;
	}
}
