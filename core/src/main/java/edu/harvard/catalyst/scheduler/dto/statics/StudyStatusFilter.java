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
package edu.harvard.catalyst.scheduler.dto.statics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavier on 9/5/17.
 */
public enum StudyStatusFilter {

    NO_FILTER(null, "Choose..."),
    BY_PENDING_STATUS("isPending", "Pending"),
    BY_OPEN_STATUS("isClosed", "Closed"),
    BY_CLOSED_STATUS("isOpen", "Open");

    private String entityFlagName;
    private String menuLabel;

    StudyStatusFilter(String entityFlagName, String menuLabel) {

        this.entityFlagName = entityFlagName;
        this.menuLabel = menuLabel;

    }

    public String getEntityFlagName() {

        return entityFlagName;

    }

    private static class MenuItem {

        String label; // this will be the label displayed in the menu
        String id; // this will be the value of the menu option, returned to the server

    }

    public static List<MenuItem> getMenuContents() {

        List<MenuItem> menuMap = new ArrayList<>();

        for(StudyStatusFilter filterValue : StudyStatusFilter.values()) {
            MenuItem menuItem = new MenuItem();
            menuItem.id = filterValue.name();
            menuItem.label = filterValue.menuLabel;
            menuMap.add(menuItem);
        }

        return menuMap;

    }
}
