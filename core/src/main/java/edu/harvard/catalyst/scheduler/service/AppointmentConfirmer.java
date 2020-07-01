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
package edu.harvard.catalyst.scheduler.service;

import edu.harvard.catalyst.scheduler.dto.VisitSpecsDTO;
import edu.harvard.catalyst.scheduler.entity.UserSession;

import java.util.Date;

/**
 * 
 * @author clint
 * @date Sep 4, 2013
 *
 * Factored out of AppointmentService to allow testing a method in that class
 * without requiring complicated DB environment setup. 
 */
public interface AppointmentConfirmer {
    void confirmVisitBooking(final AppointmentService appointmentService, final VisitSpecsDTO visitSpecsDTO, final UserSession userSession, final String ipAddress, final String institution, final String templatePath, final Date startDate, final Date endDate);
    
    void confirmVisitBookingAfterDoubleRoomMessage(final AppointmentService appointmentService, final VisitSpecsDTO visitSpecsDTO, final UserSession userSession, final String ipAddress, final String institution, final String templatePath, final Date startDate, final Date endDate);
}
