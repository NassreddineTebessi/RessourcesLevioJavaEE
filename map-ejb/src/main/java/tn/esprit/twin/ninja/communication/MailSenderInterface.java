package tn.esprit.twin.ninja.communication;

import javax.ejb.Local;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@Local
public interface MailSenderInterface {

	public void sendMessage(
			String Host,
			String user,
			String password,
			String port,
			String auth,
			String starttls,
			String recipient,
			String subject,
			String messageBody
	) throws MessagingException;
}
