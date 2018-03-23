package com.example.audittracker.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Employee domain object
 * 
 * @author vmudigal
 *
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "emp_id", length = 50)
    private String employeeId;
    
    @Column(name = "passwd", length = 50)
    private String password;
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    /**
     * 12600000 corresponds to 09:00:00 which will be the default login time
     *
     */
    @Column(name = "current_login_time")
    private Time currentLoginTime = new Time(12600000);

    /**
     * 45000000 corresponds to 18:00:00
     * 
     */
    @Column(name = "current_logout_time")
    private Time currentLogoutTime = new Time(45000000);

    @Column(name = "future_login_time")
    private Time futureLoginTime = new Time(12600000);

    @Column(name = "future_logout_time")
    private Time futureLogoutTime = new Time(45000000);

    @Column(name = "future_date")
    private Date futureDate;

    @OneToMany(mappedBy = "managerId")
    private Set<Employee> team = new HashSet<Employee>();

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "manager_id")
    private Employee managerId;


    public String getEmployeeId() {
	return employeeId;
    }

    public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Time getCurrentLoginTime() {
	return currentLoginTime;
    }

    public void setCurrentLoginTime(Time currentLoginTime) {
	this.currentLoginTime = currentLoginTime;
    }

    public Time getCurrentLogoutTime() {
	return currentLogoutTime;
    }

    public void setCurrentLogoutTime(Time currentLogoutTime) {
	this.currentLogoutTime = currentLogoutTime;
    }

    public Time getFutureLoginTime() {
	return futureLoginTime;
    }

    public void setFutureLoginTime(Time futureLoginTime) {
	this.futureLoginTime = futureLoginTime;
    }

    public Time getFutureLogoutTime() {
	return futureLogoutTime;
    }

    public void setFutureLogoutTime(Time futureLogoutTime) {
	this.futureLogoutTime = futureLogoutTime;
    }

    public Date getFutureDate() {
	return futureDate;
    }

    public void setFutureDate(Date futureDate) {
	this.futureDate = futureDate;
    }

    public Set<Employee> getTeam() {
	return team;
    }

    public void setTeam(Set<Employee> team) {
	this.team = team;
    }

    public Employee getManagerId() {
	return managerId;
    }

    public void setManagerId(Employee managerId) {
	this.managerId = managerId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Employee other = (Employee) obj;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (employeeId == null) {
	    if (other.employeeId != null)
		return false;
	} else if (!employeeId.equals(other.employeeId))
	    return false;
	return true;
    }

}
