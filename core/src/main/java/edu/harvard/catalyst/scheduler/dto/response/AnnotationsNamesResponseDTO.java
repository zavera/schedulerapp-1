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

import static edu.harvard.catalyst.hccrc.core.util.RichList.enrich;

import java.util.List;

import edu.harvard.catalyst.scheduler.core.SchedulerRuntimeException;
import edu.harvard.catalyst.scheduler.entity.LineLevelAnnotations;

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 2/28/14
 * Time: 1:02 PM
 */
public final class AnnotationsNamesResponseDTO {
    private final List<AnnotationNames> annotationsStore;

    AnnotationsNamesResponseDTO(final List<AnnotationNames> annotationNamesList) {
        annotationsStore = annotationNamesList;
    }

    public static final class AnnotationNames {
        private final Integer id;
        private final String name;

        AnnotationNames(final LineLevelAnnotations annotations) {
            if (annotations == null) {
                SchedulerRuntimeException.logAndThrow("LineLevelAnnotations parameter should be non-null");
            }

            /// ok, happy with basic invariants...

            id = annotations.getId();
            name = annotations.getName();
        }
    }

    public static AnnotationsNamesResponseDTO fromAnnotationsList(final List<LineLevelAnnotations> lineLevelAnnotationsList) {
        final List<AnnotationNames> annotationNamesList = enrich(lineLevelAnnotationsList).map(llas -> new AnnotationNames(llas)).toList();

        return new AnnotationsNamesResponseDTO(annotationNamesList);
    }
}
