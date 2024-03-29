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
package edu.harvard.catalyst.scheduler.dto.response;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 9/16/14
 * Time: 2:52 PM
 */
public class ReportTemplateMetadataDTO {
    private Integer id;
    private Integer reportTemplateId;
    private String reportTemplateName;
    private String reportTemplateType;
    private String reportTemplateBase;
    private Date latestUpdate;
    private Boolean shared;
    private String ecommons;


    public ReportTemplateMetadataDTO(Integer id,
                                     Integer reportTemplateId,
                                     String reportTemplateName,
                                     String reportTemplateType,
                                     String reportTemplateBase,
                                     Date latestUpdate,
                                     Boolean shared
                                   ) {

        this.id = id;
        this.reportTemplateId = reportTemplateId;
        this.reportTemplateName = reportTemplateName;
        this.reportTemplateType = reportTemplateType;
        this.reportTemplateBase = reportTemplateBase;
        this.latestUpdate = latestUpdate;
        this.shared = shared;

    }

    public ReportTemplateMetadataDTO(Integer id, String ecommons, String reportTemplateName, Date latestUpdate) {
        this.id = id;
        this.ecommons = ecommons;
        this.reportTemplateName = reportTemplateName;
        this.latestUpdate = latestUpdate;
    }
}
