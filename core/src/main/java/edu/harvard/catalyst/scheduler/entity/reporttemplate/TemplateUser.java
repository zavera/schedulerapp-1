/**
 * Copyright (c) 2015-2016, President and Fellows of Harvard College
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. The name of the author may not be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.harvard.catalyst.scheduler.entity.reporttemplate;

import edu.harvard.catalyst.scheduler.entity.BaseEntity;
import edu.harvard.catalyst.scheduler.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ak303 on 11/18/2014.
 */
@Entity
@Table(name = "template_user")
public class TemplateUser extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private ReportTemplate reportTemplate;
    private User user;

    private String name;
    private Date lastUpdateTime;

    Set<TemplateUserSelection> userSelections;
    private Boolean shared;

    public TemplateUser() {
        super(null);
    }

    public TemplateUser(ReportTemplate reportTemplate, User user, String name, Boolean shared) {
        super(null);

        this.reportTemplate = reportTemplate;
        this.user = user;
        this.name = name;
        this.lastUpdateTime = Calendar.getInstance().getTime();
        this.shared = shared;
    }


    @Column(name = "shared")
    public Boolean getShared(){return shared;}

    public void setShared(Boolean shared) {this.shared = shared;}

    @JoinColumn(name = "report_template_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public ReportTemplate getReportTemplate() {
        return reportTemplate;
    }

    public void setReportTemplate(ReportTemplate reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "name")
    @Basic(optional = false)
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "last_update_time")
    @Basic(optional = false)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "templateUser")
    public Set<TemplateUserSelection> getUserSelections() {
        return userSelections;
    }
    @Transient public Set<TemplateCategoryField> getSelectedTcfs() {
        return userSelections.stream().map(r -> r.getTcf()).collect(Collectors.toSet());
    }

    public void setUserSelections(Set<TemplateUserSelection> userSelections) {
        this.userSelections = userSelections;
    }

    @Override
    public String toString() {
        return "TemplateUser [id=" + id + ", getId()=" + getId() + "]";
    }
}
