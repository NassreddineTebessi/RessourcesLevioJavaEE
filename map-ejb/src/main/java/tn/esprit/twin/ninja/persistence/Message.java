package tn.esprit.twin.ninja.persistence;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Message implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	private Date modifyDate;
	@ManyToOne
	@JoinColumn(name = "conversation", referencedColumnName = "id")
	private Conversation conversation;
	@Enumerated(EnumType.STRING)
	private MessageType type;
	private String subject;
	private String message;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "from_user")
    @JsonIgnore
    private User fromUser;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "to_user")
    @JsonIgnore
    private  User toUser;
    private String toUserEmail;
	public User getFromUser() {
		return fromUser;
	}

	public String getToUserEmail() {
		return toUserEmail;
	}

	public void setToUserEmail(String toUserEmail) {
		this.toUserEmail = toUserEmail;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	

}
