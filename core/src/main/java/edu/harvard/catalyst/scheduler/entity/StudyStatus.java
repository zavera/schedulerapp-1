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
package edu.harvard.catalyst.scheduler.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "study_status")
public class StudyStatus extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4239632414023594891L;
    private String name;
    private String shortName;
    private boolean isPending;
    private boolean isOpen;
    private boolean isClosed;


    public StudyStatus() {
        super(null);
    }

    public StudyStatus(Integer id, String name, String shortName) {
        super(id);
        this.name = name;
        this.shortName = shortName;
    }

    public StudyStatus(String name, String shortName, boolean isPending, boolean isOpen, boolean isClosed) {
        super(null);

        this.name = name;
        this.shortName = shortName;
        this.isPending = isPending;
        this.isClosed = isClosed;
        this.isOpen = isOpen;
    }

    /**
     * For testing only
     * @return
     */
    public StudyStatus(int id, String name, String shortName, boolean isPending, boolean isOpen, boolean isClosed) {

        this(name, shortName, isPending, isOpen, isClosed);

        this.id = id;

    }

    @Column(name = "name")
    @Basic(optional = false)  
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "short_name")
    @Basic(optional = false)  
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "is_pending")
    @Basic(optional = false)
    public boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(boolean isPending) {
        this.isPending = isPending;
    }

    @Column(name = "is_open")
    @Basic(optional = false)
    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Column(name = "is_closed")
    @Basic(optional = false)
    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    @Override
    public String toString() {
        return "StudyStatus [id=" + id + ", getId()=" + getId() + "]";
    }
}
